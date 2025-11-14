package service;

import java.sql.*;
import java.util.ArrayList;

import database.ConexionUVRate;
import model.Catedratico;
import model.Curso;
import model.Estudiante;
import model.Upvotes;

/**
 * UVRate (headless / sin Swing)
 *
 * - Clase de servicio que encapsula TODA la lógica de acceso a datos (SQLite).
 * - No contiene ninguna referencia a javax.swing ni UI.
 * - Segura para ser usada por controladores JavaFX.
 *
 * TODO (más adelante, sin cambiar lógica):
 * - Mover a package src.service y renombrar a UVRateService.
 * - Mantener misma API pública para no romper controladores.
 */
public class UVRateService {

    // Mantener referencia al estudiante logueado (si aplica a ciertas operaciones).
    private Estudiante estudiante;

    public UVRateService() {
        this.estudiante = estudiante;
    }

    // ===================== CATEDRÁTICOS =====================

    /**
     * Obtiene todos los catedráticos desde la BD.
     * Mapea cada fila a Catedratico incluyendo:
     * - Lista de cursos (join a tabla intermedia)
     * - Conteo de upvotes (consulta separada)
     */
    public ArrayList<Catedratico> obtenerTodosCatedraticos() {
        ArrayList<Catedratico> lista = new ArrayList<>();

        try (Connection conn = ConexionUVRate.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM catedratico")) {

            while (rs.next()) {
                lista.add(mapearCatedratico(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    /**
     * Obtiene catedráticos que imparten un curso dado (por id),
     * e incluye su total de upvotes, ordenados desc por ranking.
     */
    public ArrayList<Catedratico> obtenerCatedraticosPorCurso(int idCurso) {
        ArrayList<Catedratico> lista = new ArrayList<>();

        String sql = """
                    SELECT catedratico.*,
                           COUNT(upvote.id) AS total_upvotes
                    FROM catedratico
                    JOIN catedratico_curso ON catedratico.id = catedratico_curso.catedratico_id
                    LEFT JOIN upvote ON upvote.catedratico_id = catedratico.id
                    WHERE catedratico_curso.curso_id = ?
                    GROUP BY catedratico.id
                    ORDER BY total_upvotes DESC;
                """;

        try (Connection conn = ConexionUVRate.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCurso);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int idCat = rs.getInt("id");
                String nombre = rs.getString("nombre");
                int totalUpvotes = rs.getInt("total_upvotes");

                Upvotes upvotes = new Upvotes(totalUpvotes);
                ArrayList<Curso> cursos = new ArrayList<>();
                cursos.add(obtenerCurso(idCurso));

                int semestres = rs.getInt("semestres");

                lista.add(new Catedratico(idCat, nombre, upvotes, cursos, semestres));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    // ===================== CURSOS =====================

    /**
     * Lista todos los cursos.
     */
    public ArrayList<Curso> obtenerTodosCursos() {
        ArrayList<Curso> lista = new ArrayList<>();

        try (Connection conn = ConexionUVRate.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM curso")) {

            while (rs.next()) {
                lista.add(new Curso(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("descripcion")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    /**
     * Obtiene un curso por id (o null si no existe).
     */
    public Curso obtenerCurso(int idCurso) {
        try (Connection conn = ConexionUVRate.getConnection();
                PreparedStatement stmt = conn.prepareStatement("SELECT * FROM curso WHERE id = ?")) {

            stmt.setInt(1, idCurso);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Curso(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("descripcion"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // ===================== UPVOTES =====================

    /**
     * Verifica si un usuario (estudianteId) ya dio upvote a un catedrático
     * (catedraticoId).
     */
    public boolean yaVoto(int estudianteId, int catedraticoId) {
        try (Connection conn = ConexionUVRate.getConnection();
                PreparedStatement stmt = conn.prepareStatement(
                        "SELECT COUNT(*) FROM upvote WHERE usuario_id = ? AND catedratico_id = ?")) { // 👈 CORREGIDO
            stmt.setInt(1, estudianteId);
            stmt.setInt(2, catedraticoId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                return rs.getInt(1) > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Alterna el upvote:
     * - Si ya existe, lo quita.
     * - Si no existe, lo inserta.
     */
    public void toggleUpvote(int estudianteId, int catedraticoId) {
        if (yaVoto(estudianteId, catedraticoId)) {

            try (Connection conn = ConexionUVRate.getConnection();
                    PreparedStatement stmt = conn.prepareStatement(
                            "DELETE FROM upvote WHERE usuario_id = ? AND catedratico_id = ?")) {

                stmt.setInt(1, estudianteId);
                stmt.setInt(2, catedraticoId);
                stmt.executeUpdate();

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {

            try (Connection conn = ConexionUVRate.getConnection();
                    PreparedStatement stmt = conn.prepareStatement(
                            "INSERT INTO upvote (usuario_id, catedratico_id) VALUES (?, ?)")) {

                stmt.setInt(1, estudianteId);
                stmt.setInt(2, catedraticoId);
                stmt.executeUpdate();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Cuenta upvotes de un catedrático por id.
     */
    public int contarUpvotes(int idCatedratico) {
        try (Connection conn = ConexionUVRate.getConnection();
                PreparedStatement stmt = conn.prepareStatement(
                        "SELECT COUNT(*) FROM upvote WHERE catedratico_id = ?")) {

            stmt.setInt(1, idCatedratico);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                return rs.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    // ===================== MAPPER =====================

    /**
     * Mapea una fila de ResultSet (catedratico) a objeto Catedratico,
     * incluyendo sus cursos y el conteo de upvotes.
     */
    private Catedratico mapearCatedratico(ResultSet rs) throws SQLException {
        int idCat = rs.getInt("id");
        String nombreCat = rs.getString("nombre");
        int semestres = rs.getInt("semestres");

        ArrayList<Curso> cursos = obtenerCursosPorCatedratico(idCat);

        int upvoteCount = contarUpvotes(idCat);
        Upvotes upvotes = new Upvotes(upvoteCount);

        return new Catedratico(idCat, nombreCat, upvotes, cursos, semestres);
    }

    /**
     * Devuelve los cursos impartidos por un catedrático (via tabla intermedia
     * catedratico_curso).
     */
    public ArrayList<Curso> obtenerCursosPorCatedratico(int idCatedratico) {
        ArrayList<Curso> cursos = new ArrayList<>();

        try (Connection conn = ConexionUVRate.getConnection();
                PreparedStatement stmt = conn.prepareStatement(
                        "SELECT c.* FROM curso c " +
                                "JOIN catedratico_curso cc ON c.id = cc.curso_id " +
                                "WHERE cc.catedratico_id = ?")) {
            stmt.setInt(1, idCatedratico);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                cursos.add(new Curso(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("descripcion")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cursos;
    }

    // ===================== ORIENTADOR / METAS =====================

    /**
     * Sugerencias de cursos en función de una meta (join curso_meta -> meta).
     */
    public ArrayList<Curso> sugerirCursosPorMeta(String metaNombre) {
        ArrayList<Curso> sugeridos = new ArrayList<>();

        try (Connection conn = ConexionUVRate.getConnection();
                PreparedStatement stmt = conn.prepareStatement(
                        "SELECT c.* FROM curso c " +
                                "JOIN curso_meta cm ON c.id = cm.curso_id " +
                                "JOIN meta m ON cm.meta_id = m.id " +
                                "WHERE LOWER(m.nombre) = LOWER(?)")) {

            stmt.setString(1, metaNombre);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                sugeridos.add(new Curso(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("descripcion")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sugeridos;
    }

    public ArrayList<Catedratico> obtenerRankingUpvotesPorCurso(int idCurso) {
        ArrayList<Catedratico> lista = obtenerCatedraticosPorCurso(idCurso);
        lista.sort((a, b) -> Integer.compare(
                b.getCantidadUpvotes(),
                a.getCantidadUpvotes()));
        return lista;
    }

    public ArrayList<Catedratico> obtenerRankingSemestresPorCurso(int idCurso) {
        ArrayList<Catedratico> lista = obtenerCatedraticosPorCurso(idCurso);

        lista.sort((a, b) -> Integer.compare(
                b.getSemestres(),
                a.getSemestres()));
        return lista;

    }

    public int obtenerPosicionCatedraticoEnCurso(int idCatedratico, int idCurso) {
        ArrayList<Catedratico> ranking = obtenerRankingUpvotesPorCurso(idCurso);

        for (int i = 0; i < ranking.size(); i++) {
            if (ranking.get(i).getId() == idCatedratico) {
                return i + 1; // posiciones empiezan en 1
            }
        }
        return -1; // no imparte ese curso
    }

    public Curso obtenerCursoPorCodigo(String codigo) {
        try (Connection conn = ConexionUVRate.getConnection();
                PreparedStatement stmt = conn.prepareStatement(
                        "SELECT * FROM curso WHERE codigo = ?")) {

            stmt.setString(1, codigo);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Curso(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("descripcion"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Catedratico obtenerCatedraticoPorId(int id) {
        String sql = "SELECT * FROM catedratico WHERE id = ?";

        try (Connection conn = ConexionUVRate.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapearCatedratico(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}

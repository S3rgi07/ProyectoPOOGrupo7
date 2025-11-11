import java.sql.*;
import java.util.ArrayList;

public class UVRate{

    private Estudiante estudiante;

    public UVRate(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    // ===================== CATEDRÁTICOS =====================

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

    public ArrayList<Catedratico> obtenerCatedraticosPorCurso(int idCurso) {
        ArrayList<Catedratico> lista = new ArrayList<>();

        try (Connection conn = ConexionUVRate.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT * FROM catedratico WHERE curso_id = ? ORDER BY (SELECT COUNT(*) FROM upvote WHERE upvote.catedratico_id = catedratico.id) DESC"
             )) {

            stmt.setInt(1, idCurso);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                lista.add(mapearCatedratico(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    // ===================== CURSOS =====================

    public ArrayList<Curso> obtenerTodosCursos() {
        ArrayList<Curso> lista = new ArrayList<>();

        try (Connection conn = ConexionUVRate.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM curso")) {

            while (rs.next()) {
                lista.add(new Curso(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("descripcion")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public Curso obtenerCurso(int idCurso) {
        try (Connection conn = ConexionUVRate.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM curso WHERE id = ?")) {

            stmt.setInt(1, idCurso);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Curso(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("descripcion")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // ===================== UPVOTES =====================

    public boolean yaVoto(int estudianteId, int catedraticoId) {
    try (Connection conn = ConexionUVRate.getConnection();
         PreparedStatement stmt = conn.prepareStatement(
             "SELECT COUNT(*) FROM upvote WHERE usuario_id = ? AND catedratico_id = ?")) { // 👈 CORREGIDO
        stmt.setInt(1, estudianteId);
        stmt.setInt(2, catedraticoId);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) return rs.getInt(1) > 0;
    } catch (Exception e) {
        e.printStackTrace();
    }
    return false;
}


    public void toggleUpvote(int estudianteId, int catedraticoId) {
        if (yaVoto(estudianteId, catedraticoId)) {

            try (Connection conn = ConexionUVRate.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(
                         "DELETE FROM upvote WHERE usuario_id = ? AND catedratico_id = ?"
                 )) {

                stmt.setInt(1, estudianteId);
                stmt.setInt(2, catedraticoId);
                stmt.executeUpdate();

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {

            try (Connection conn = ConexionUVRate.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(
                         "INSERT INTO upvote (usuario_id, catedratico_id) VALUES (?, ?)"
                 )) {

                stmt.setInt(1, estudianteId);
                stmt.setInt(2, catedraticoId);
                stmt.executeUpdate();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public int contarUpvotes(int idCatedratico) {
        try (Connection conn = ConexionUVRate.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT COUNT(*) FROM upvote WHERE catedratico_id = ?"
             )) {

            stmt.setInt(1, idCatedratico);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }


    // ===================== MAPPER =====================

    private Catedratico mapearCatedratico(ResultSet rs) throws SQLException {

        int idCat = rs.getInt("id");
        String nombreCat = rs.getString("nombre");
        int cursoId = rs.getInt("curso_id");

        Curso curso = obtenerCurso(cursoId);
        ArrayList<Curso> cursos = new ArrayList<>();
        if (curso != null) cursos.add(curso);

        int upvoteCount = contarUpvotes(idCat);
        Upvotes upvotes = new Upvotes(upvoteCount);

        return new Catedratico(idCat, nombreCat, upvotes, cursos);
    }
}

import java.sql.*;
import java.util.ArrayList;

public class UVRate {

    private Estudiante estudiante;

    public UVRate(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    // ----------- CATEDRÁTICOS -----------
    public ArrayList<Catedratico> obtenerTodosCatedraticos() {
        ArrayList<Catedratico> lista = new ArrayList<>();

        try (Connection conn = ConexionUVRate.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM catedratico")) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");

                // Obtener curso único asignado
                Curso curso = obtenerCursoDeCatedratico(rs.getInt("curso_id"));
                ArrayList<Curso> cursos = new ArrayList<>();
                if (curso != null) cursos.add(curso);

                int upvoteCount = contarUpvotes(id);

                // Crear objeto Catedratico
                Upvotes upvotes = new Upvotes(upvoteCount);
                Catedratico c = new Catedratico(id, nombre, upvotes, cursos);
                lista.add(c);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // ----------- CURSOS -----------
    public ArrayList<Curso> obtenerTodosCursos() {
        ArrayList<Curso> lista = new ArrayList<>();

        try (Connection conn = ConexionUVRate.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM curso")) {

            while (rs.next()) {
                Curso c = new Curso(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("descripcion")
                );
                lista.add(c);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public Curso obtenerCursoDeCatedratico(int cursoId) {
        if (cursoId == 0) return null;

        try (Connection conn = ConexionUVRate.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM curso WHERE id = ?")) {
            stmt.setInt(1, cursoId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Curso(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("descripcion")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // ----------- UPVOTES -----------
    public boolean yaVoto(int estudianteId, int catedraticoId) {
        try (Connection conn = ConexionUVRate.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT COUNT(*) FROM upvote WHERE usuario_id = ? AND catedratico_id = ?")) {
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

    public int contarUpvotes(int catedraticoId) {
        try (Connection conn = ConexionUVRate.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT COUNT(*) FROM upvote WHERE catedratico_id = ?")) {
            stmt.setInt(1, catedraticoId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public ArrayList<Catedratico> obtenerCatedraticosPorCurso(int cursoId) {
    ArrayList<Catedratico> lista = new ArrayList<>();

    try (Connection conn = ConexionUVRate.getConnection();
         PreparedStatement stmt = conn.prepareStatement(
                 "SELECT c.id, c.nombre, c.curso_id " +
                 "FROM catedratico c " +
                 "WHERE c.curso_id = ?")) {
        stmt.setInt(1, cursoId);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("id");
            String nombre = rs.getString("nombre");

            // Obtener curso del catedrático
            Curso curso = obtenerCursoDeCatedratico(rs.getInt("curso_id"));
            ArrayList<Curso> cursos = new ArrayList<>();
            if (curso != null) cursos.add(curso);

            int upvotes = contarUpvotes(id);
            Upvotes upvoteObj = new Upvotes(null, null, null); // dummy, solo para tener objeto Upvotes
            upvoteObj.setUpvotes(null, null, null); // temporal si necesitas inicializar
            Catedratico cat = new Catedratico(id, nombre, upvoteObj, cursos);
            lista.add(cat);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return lista;
}

}

import java.util.List;
import java.util.ArrayList;

public class UVRate {
    private Estudiante estudiante;
    private Curso curso;
    private Catedratico catedratico;
    private Orientador orientador;
    
    public UVRate() {
        this.estudiante = null;
        this.curso = null;
        this.catedratico = null;
        this.orientador = new Orientador();
    }

    public UVRate(Estudiante estudiante, Curso curso, Catedratico catedratico, Orientador orientador) {
        this.estudiante = estudiante;
        this.curso = curso;
        this.catedratico = catedratico;
        this.orientador = orientador != null ? orientador : new Orientador();
    }

    public void iniciar() {
        ConexionUVRate.getConnection();
    }

    public void crearEstudiante(String nombre, String correo, String contraseña, int carnet) {
        try {
            Estudiante estudiante = new Estudiante(nombre, carnet, correo);
            //método temporal, conexión a base de datos
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void crearCurso(int codigo, String nombre, String descripcion, String competencias, String tipo, Upvotes upvotesCurso) {
        try {
            Curso curso = new Curso(codigo, nombre, descripcion, competencias, tipo, upvotesCurso);
            //método temporal, conexión a base de datos
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void calificar(int usuarioId, int cursoId, int catedraticoId, Upvotes upvote) {
        try {
            //método temporal, conexión a base de datos
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void darUpvote(int usuarioId, int catedraticoId) {
        try {
            //método temporal, conexión a base de datos
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void contarUpvotes(int catedraticoId) {
        try {
            //método temporal, conexión a base de datos
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Curso> obtenerCursosRecomendados(Orientador orientador) {
        try {
            return orientador.getCursos();
            //método temporal, conexión a base de datos
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    //Getters
    public Estudiante getEstudiante() { 
        return this.estudiante; 
    }

    public Curso getCurso() { 
        return this.curso; 
    }

    public Catedratico getCatedratico() { 
        return this.catedratico; 
    }

    public Orientador getOrientador() { 
        return this.orientador; 
    }

    //Setters
    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public void setCusro(Curso curso) {
        this.curso = curso;
    }

    public void setCatedrcatico(Catedratico catedratico) {
        this.catedratico = catedratico;
    }

    public void setOrientador(Orientador orientador) {
        this.orientador = orientador;
    }


}
public class Estudiante {

    // atributos
    private String nombre;
    private int carnet;
    private String correo;
    private EstadoCursos estadoCursos;

    // constructor
    public Estudiante() {
        this.estadoCursos = new EstadoCursos();
    }

    // constructor con parámetros
    public Estudiante(String nombre, int carnet, String correo) {
        this.nombre = nombre;
        this.carnet = carnet;
        this.correo = correo;
        this.estadoCursos = new EstadoCursos();
    }

    // get y setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCarnet() {
        return carnet;
    }

    public void setCarnet(int carnet) {
        this.carnet = carnet;
    }

    public String getCorreo() {
        return correo;
    }
    //se me ocurrió agregar validaciones en los setters :D
public void setCorreo(String correo) {
        if (correo == null || !correo.contains("@") || correo.trim().isEmpty()) {
            throw new IllegalArgumentException("El correo es inválido.");
        }
        this.correo = correo.trim();
    }

    public EstadoCursos getEstadoCursos() {
        return estadoCursos;
    }

    public void setEstadoCursos(EstadoCursos estadoCursos) {
        this.estadoCursos = estadoCursos;
    }

    // son metódos para manejar cursos a través de estadoCursos :D


    // Inicia un curso en el estado de este estudiante

    public boolean iniciarCurso(Curso curso) {
        return estadoCursos.iniciarCurso(curso);
    }

    // Finaliza un curso en el estado de este estudiante
    public boolean finalizarCurso(Curso curso) {
        return estadoCursos.finalizarCurso(curso);
    }
    // Verifica si el estudiante está actualmente cursando el curso
    public boolean estaCursando(Curso curso) {
        return estadoCursos.estaCursando(curso);
    }

    // Verifica si el estudiante ya ha cursado el curso antes
    public boolean yaCursado(Curso curso) {
        return estadoCursos.yaCursado(curso);
    }
    
    
}

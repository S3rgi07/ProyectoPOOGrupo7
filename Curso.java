public class Curso {
    private int codigo;
    private String nombre;
    private String descripcion;
    private String competencias;
    private String tipo;
    private Upvotes upvotesCurso;

    public Curso(int codigo, String nombre, String descripcion, String competencias, String tipo, Upvotes upvotesCurso){
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.competencias = competencias;
        this.tipo = tipo;
        this.upvotesCurso = upvotesCurso;
    }
}
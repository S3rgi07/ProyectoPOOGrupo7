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

    public int getCodigo(){
        return codigo;
}
    public String getTipo(){
        return tipo;
    }

    public Upvotes getUpvotes(){
        return upvotesCurso;
    }
    
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setUpvotesCur(Upvotes upvotesCurso) {  
        this.upvotesCurso = upvotesCurso;
    }

    public String getNombre(){
        return nombre;
    }

    public void setNombre(){
        this.nombre = nombre;
    }
}

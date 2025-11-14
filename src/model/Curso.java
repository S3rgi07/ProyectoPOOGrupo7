package model;

public class Curso {
    private int id;
    private String nombre;
    private String descripcion;
    private String competencias;
    private String tipo;
    private Upvotes upvotesCurso;

    // Añade este constructor a Curso.java
    public Curso(int id, String nombre, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        // Inicializa los demás campos a valores por defecto
        this.competencias = "";
        this.tipo = "";
        this.upvotesCurso = null; // O new Upvotes(0) si también aplica
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public Upvotes getUpvotes() {
        return upvotesCurso;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setUpvotesCur(Upvotes upvotesCurso) {
        this.upvotesCurso = upvotesCurso;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCompetencias(String competencias) {
        this.competencias = competencias;
    }

    public String getCompetencias() {
        return competencias;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Curso curso = (Curso) obj;
        return id == curso.id; // Compara cursos por su 'codigo'
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(id); // Genera un hash a partir del 'codigo'
    }

}

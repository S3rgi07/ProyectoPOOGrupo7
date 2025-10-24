import java.util.ArrayList;

public class Catedratico {
    private String nombre;
    private String descripcion;
    private Upvotes upvotesCatedratico;

    public Catedratico(String nombre, String descripcion, Upvotes upvotesCatedratico){ //Constructor
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.upvotesCatedratico = upvotesCatedratico;
    }

    public String getNombre(){ //Métodos
        return nombre;
    }

    public String getDescripcion(){
        return descripcion;
    }

    public Upvotes getUpvotesCat() {
        return upvotesCatedratico;
    }

    public void setUpvotesCat(Upvotes upvotesCatedratico) {
    this.upvotesCatedratico = upvotesCatedratico;
    }

    // Lista que contiene los cursos
    public ArrayList<Curso> getCursos() {
        return cursos;
    }

    public void setCursos(ArrayList<Curso> cursos) {
        this.cursos = cursos;
    }
}
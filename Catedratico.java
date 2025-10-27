import java.util.ArrayList;

public class Catedratico {
    private int id;
    private String nombre;
    private Upvotes upvotes;
    private ArrayList<Curso> cursos;

    public Catedratico(int id, String nombre, Upvotes upvotes, ArrayList<Curso> cursos) {
        this.id = id;
        this.nombre = nombre;
        this.upvotes = upvotes;
        this.cursos = cursos;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public Upvotes getUpvotes() { return upvotes; }
    public ArrayList<Curso> getCursos() { return cursos; }
}

import java.util.ArrayList;
import java.util.Objects;

import src.model.Curso;
import src.model.Upvotes;

/**
 * Clase Catedratico
 * Incluye constructores compatibles con el controller:
 *   Catedratico(int id, String nombre, Upvotes upvotes, ArrayList<Curso> cursos)
 *   Catedratico(String nombre, Upvotes upvotes, ArrayList<Curso> cursos)
 */
public class Catedratico {
    private int id;
    private String nombre;
    private Upvotes upvotesCatedratico;
    private ArrayList<Curso> cursos;
    private int semestres;

    // ---------------- Constructores ----------------

    /** Constructor con id (útil al cargar desde BD) */
    public Catedratico(int id, String nombre, Upvotes upvotesCatedratico, ArrayList<Curso> cursos) {
        this.id = id;
        this.nombre = nombre;
        this.upvotesCatedratico = upvotesCatedratico != null ? upvotesCatedratico : new Upvotes(0);
        this.cursos = cursos != null ? cursos : new ArrayList<>();
    }

    /** Constructor original sin id */
    public Catedratico(String nombre, Upvotes upvotesCatedratico, ArrayList<Curso> cursos) {
        this.nombre = nombre;
        this.upvotesCatedratico = upvotesCatedratico != null ? upvotesCatedratico : new Upvotes(0);
        this.cursos = cursos != null ? cursos : new ArrayList<>();
    }

    /** Constructor mínimo */
    public Catedratico(String nombre) {
        this(nombre, new Upvotes(0), new ArrayList<>());
    }

    // ---------------- Getters / Setters ----------------

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    } 

    public String getNombre() {
        return nombre;
    }

    public int getSemestres() {
    return semestres;
}

    public void setSemestres(int semestres) {
        this.semestres = semestres;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /** Nombre del getter para compatibilidad con código existente */
    public Upvotes getUpvotesCat() {
        return upvotesCatedratico;
    }

    /** Alternativa corta que también podría usarse */
    public Upvotes getUpvotes() {
        return upvotesCatedratico;
    }

    public void setUpvotesCat(Upvotes upvotesCatedratico) {
        this.upvotesCatedratico = upvotesCatedratico != null ? upvotesCatedratico : new Upvotes(0);
    }

    public ArrayList<Curso> getCursos() {
        return cursos;
    }

    public void setCursos(ArrayList<Curso> cursos) {
        this.cursos = cursos != null ? cursos : new ArrayList<>();
    }

    // ---------------- Utilidades ----------------

    public void agregarCurso(Curso curso) {
        if (curso == null) return;
        if (this.cursos == null) this.cursos = new ArrayList<>();
        // evita duplicados según id si es posible
        boolean existe = false;
        for (Curso c : this.cursos) {
            if (c.getCodigo() == curso.getCodigo()) { existe = true; break; }
        }
        if (!existe) this.cursos.add(curso);
    }

    /** Devuelve la cantidad de upvotes (si Upvotes implementa getUpvotes()) */
    public int getCantidadUpvotes() {
        try {
            return upvotesCatedratico != null ? upvotesCatedratico.getUpvotes() : 0;
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public String toString() {
        return "Catedratico{id=" + id + ", nombre='" + nombre + '\'' +
                ", upvotes=" + getCantidadUpvotes() +
                ", cursos=" + (cursos != null ? cursos.size() : 0) + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Catedratico)) return false;
        Catedratico that = (Catedratico) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

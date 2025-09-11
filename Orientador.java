import java.util.ArrayList;
import java.util.List;

// Creamos 2 listas: un listado de metas y otro listado de cursos
public class Orientador {
    private List<Meta> metas;
    private List<Curso> cursos;

// Constructor
public Orientador() {
    this.metas = new ArrayList<>();
    this.cursos = new ArrayList<>();
    }

// Método que sugiere los cursos al estudiante según la meta que tenga.
public List<Curso> sugerirCursos(String metaNombre) {
    List<Curso> sugeridos = new ArrayList<>();
    }
}
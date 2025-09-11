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


// Para poder buscar las metas por nombre
        for (Meta meta : metas) {
            String metasNombre;
            if (meta.getNombre().equalsIgnoreCase(metasNombre)) {
                String competenciaMeta = meta.getCompetencias();

// Para poder buscar cursos que coincidan con las competencias del estudiante.
            for (Curso curso : cursos) {
                    if (curso.getCompetencias().contains(competenciaMeta)) {
                        sugeridos.add(curso);
                    }
                }
            }
        }
    }

    return sugeridos;

// Métodos para agregar metas y cursos
    public void agregarMeta(Meta meta) {
        metas.add(meta);
    }

    public void agregarCurso(Curso curso) {
        curso.add(curso);
    }

// Getters
    public List<Meta> getMetas() {
        return metas;
    }

    public List<Curso> getCursos() {
        return curso;
    }
}
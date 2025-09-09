import java.util.ArrayList;
import java.util.List;

public class EstadoCursos {

    // atributos
    private List<Curso> cursosActuales;
    private List<Curso> cursosHistoria;

    // constructor
    public EstadoCursos() {
        this.cursosActuales = new ArrayList<>();
        this.cursosHistoria = new ArrayList<>();
    }

    // get y setters
    public List<Curso> getCursosActuales() {
        return cursosActuales;
    }

    public void setCursosActuales(List<Curso> cursosActuales) {
        this.cursosActuales = cursosActuales;
    }

    public List<Curso> getCursosHistoria() {
        return cursosHistoria;
    }

    public void setCursosHistoria(List<Curso> cursosHistoria) {
        this.cursosHistoria = cursosHistoria;
    }

    // metodos
    public void iniciarCurso(Curso curso) {
        if (curso != null) {
            cursosActuales.add(curso);
        }
    }

    
    //  Mueve un curso de actuales a historia, indicando que se ha finalizado

    public void finalizarCurso(Curso curso) {
        if (curso != null && cursosActuales.contains(curso)) {
            cursosActuales.remove(curso);
            cursosHistoria.add(curso);
        }
    }

//  va a verificar si un curso está ya está en la lista de actuales
    
    public boolean estaCursando(Curso curso) {
        return cursosActuales.contains(curso);
    }
// va a verificar si un curso ya fue finalizado y si está en la historia
    public boolean yaCursado(Curso curso) {
        return cursosHistoria.contains(curso);
    }
}

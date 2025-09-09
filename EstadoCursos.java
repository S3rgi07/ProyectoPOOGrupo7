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
        this.cursosActuales = (cursosActuales != null) ? cursosActuales : new ArrayList<>();
    }

    public List<Curso> getCursosHistoria() {
        return cursosHistoria;
    }

    public void setCursosHistoria(List<Curso> cursosHistoria) {
        this.cursosHistoria = (cursosHistoria != null) ? cursosHistoria : new ArrayList<>();
    }

    // métodos

    /**
     * va a agregar un curso a la lista de actuales si no existe 
     * @return true si se agregó y false si no (null o duplicado)
     */
    public boolean iniciarCurso(Curso curso) {
        if (curso == null) {
            System.out.println("No se puede agregar un curso nulo.");
            return false;
        }
        if (cursosActuales.contains(curso)) {
            System.out.println("El curso ya está en cursos actuales.");
            return false;
        }
        cursosActuales.add(curso);
        return true;
    }

    /**
     * Mueve un curso de actuales a historia mostrando  que ya se ha finalizado yei
     * @return true si se movió, false si no (null o no estaba en actuales)
     */
    public boolean finalizarCurso(Curso curso) {
        if (curso == null) {
            System.out.println("No se puede finalizar un curso nulo.");
            return false;
        }
        if (!cursosActuales.contains(curso)) {
            System.out.println("El curso no está en cursos actuales.");
            return false;
        }
        cursosActuales.remove(curso);
        if (!cursosHistoria.contains(curso)) {
            cursosHistoria.add(curso);
        } else {
            System.out.println("El curso ya estaba en la historia.");
        }
        return true;
    }

 // va a verificar si un curso está ya está en la lista de actuales
    public boolean estaCursando(Curso curso) {
        return curso != null && cursosActuales.contains(curso);
    }

// va a verificar si un curso ya fue finalizado y si está en la historia
    public boolean yaCursado(Curso curso) {
        return curso != null && cursosHistoria.contains(curso);
    }

    
    //  Limpia todas las listas (útil para pruebas o reinicio de estado).
    
    public void resetEstado() {
        cursosActuales.clear();
        cursosHistoria.clear();
    }
}

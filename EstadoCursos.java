

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
}

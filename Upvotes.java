import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Upvotes {
    private Catedratico catedratico;
    private Estudiante estudiante;
    private Curso curso;
    private LocalDate fecha;
    
    public Upvotes(Catedratico catedratico, Estudiante estudiante, Curso curso) {
        this.catedratico = catedratico;
        this.estudiante = estudiante;
        this.curso = curso;
        this.fecha = LocalDate.now(); //asigna la fecha automáticamente
    }

    private List<Upvotes> upvotesCatedraticos = new ArrayList<>();
    
    //Getters 
    public Catedratico getCatedratico() {
        return this.catedratico;
    }

    public Estudiante getEstudiante() {
        return this.estudiante;
    }

    public Curso getCurso() {
        return this.curso;
    }

    public LocalDate getFecha() {
        return this.fecha;
    }

    public Upvotes getUpvotes() {
       return this; //método temporal, la versión final va conectada a la base de datos
    }

    //Setters
    public void setCatedrcatico(Catedratico catedratico) {
        this.catedratico = catedratico;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    // "setUpvotes" permite modificar los datos del voto completo
    public void setUpvotes(Catedratico catedratico, Estudiante estudiante, LocalDate fecha, Curso curso) {
        if (catedratico == null || estudiante == null || curso == null || fecha == null) {
            throw new IllegalArgumentException("Ningún campo de Upvotes puede ser nulo.");
        }
        this.catedratico = catedratico;
        this.estudiante = estudiante;
        this.fecha = LocalDate.now();
        this.curso = curso;
    }
}
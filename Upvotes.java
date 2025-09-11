import java.time.LocalDate;

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

    public int getUpvotes() {
        return this.Upvotes; //método temporal, la versión final va conectada a la base de datos
    }

    //Setters
    public void setCatedrcatico(catedratico catedratico) {
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
}
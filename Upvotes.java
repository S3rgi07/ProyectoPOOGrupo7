import java.time.LocalDate;

public class Upvotes {
    private Catedrático catedratico;
    private Estudiante estudiante;
    private Curso curso;
    private LocalDate fechas;
    
    public Upvotes(Catedrático catedratico, Estudiante estudiante, Curso curso) {
        this.catedratico = catedratico;
        this.estudiante = estudiante;
        this.curso = curso;
        this.fecha = LocalDate.now(); //asigna la fecha automáticamente
    }
    
}

public class Estudiante {

    // atributos
    private String nombre;
    private int carnet;
    private String correo;
    private EstadoCursos estadoCursos;

    // construct
    public Estudiante() {
        this.estadoCursos = new EstadoCursos();
    }

    // get y setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCarnet() {
        return carnet;
    }

    public void setCarnet(int carnet) {
        this.carnet = carnet;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public EstadoCursos getEstadoCursos() {
        return estadoCursos;
    }

    public void setEstadoCursos(EstadoCursos estadoCursos) {
        this.estadoCursos = estadoCursos;
    }
}

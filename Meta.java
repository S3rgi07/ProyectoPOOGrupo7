public class Meta {
    private String nombre;
    private String competencias;

    // Constructor
    public Meta (String nombre, String competencias) {
        this.nombre = nombre;
        this.competencias = competencias;
    }

    // Métodos
    public void MostrarRequisitos() {
        System.out.println("Meta: " + nombre);
        System.out.println("Competencias asociadas a la meta: " + competencias);
    }

    // Getters
    public String getNombre() {
        return nombre;
    }

    public String getCompetencias() {
        return competencias;
    }

    // Setters
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCompetencias(String competencias) {
        this.competencias = competencias;
    }
}
public class Meta {
    private String nombre;
    private String competencias;

    // Constructor
    public Meta (String nombre, String competencias) {
        this.nombre = nombre;
        this.competencias = competencias;
    }

    // Métodos
    public void MostrarResultaods() {
        System.out.println("Meta: " + nombre);
        System.out.println("Competencias asociadas a la meta: " + competencias);
    }
}
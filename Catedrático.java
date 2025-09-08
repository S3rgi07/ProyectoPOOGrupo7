public class Catedrático {
    private String nombre;
    private String descripcion;
    private int upvotesCatedratico;

    public Catedratico(String nombre, String descripcion, int upvotesCatedratico){ //Constructor
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.upvotesCatedratico = upvotesCatedratico;
    }

    public String getNombre(){ //Métodos
        return nombre;
    }

    public String getDescripcion(){
        return descripcion;
    }
}
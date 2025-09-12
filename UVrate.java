public class UVrate {
    private Estudiante estudiante;
    private Curso curso;
    private Catedrático catedratico;
    private Orientador orientador;
    
    public UVRate() {
        this.estudiante = null;
        this.curso = null;
        this.catedratico = null;
        this.orientador = null;
    }

    public UVRate(Estudiante estudiante, Curso curso, Catedratico catedratico, Orientador orientador) {
        this.estudiante = estudiante;
        this.curso = curso;
        this.catedratico = catedratico;
        this.orientador = orientador != null ? orientador : new Orientador();
    }

    public void iniciar() {
        ConexionUVRate.getConnection();
    }

    //Getters
    public Estudiante getEstudiante() { 
        return this.estudiante; 
    }

    public Curso getCurso() { 
        return this.curso; 
    }

    public Catedratico getCatedratico() { 
        return this.catedratico; 
    }

    public Orientador getOrientador() { 
        return this.orientador; 
    }
}
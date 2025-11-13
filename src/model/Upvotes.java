package model;

import java.time.LocalDate;

/**
 * Upvotes
 *
 * - Soporta dos modos de uso:
 * 1) Representar un conteo total de upvotes (new Upvotes(int count))
 * 2) Representar un upvote individual (new Upvotes(Catedratico, Estudiante,
 * Curso))
 *
 * - Provee getUpvotes() (para compatibilidad con el resto del código),
 * y métodos increment/ decrement para actualizar el conteo en memoria.
 *
 * Nota: No es una colección. No uses contains/add sobre esta clase.
 */
public class Upvotes {
    // conteo total (puede venir de BD)
    private int upvoteCount;

    // información de un voto individual (opcional)
    private Catedratico catedratico;
    private Estudiante estudiante;
    private Curso curso;
    private LocalDate fecha;

    // ---------------- Constructores ----------------

    /** Constructor para representar un conteo (p. ej. desde BD) */
    public Upvotes(int count) {
        this.upvoteCount = Math.max(0, count);
        this.catedratico = null;
        this.estudiante = null;
        this.curso = null;
        this.fecha = null;
    }

    /** Constructor para representar un voto individual */
    public Upvotes(Catedratico catedratico, Estudiante estudiante, Curso curso) {
        if (catedratico == null || estudiante == null || curso == null) {
            // permitimos crear con nulls si aún no tienes todos los objetos,
            // pero es preferible pasar objetos válidos
            // no lanzamos excepción para compatibilidad con código previo.
        }
        this.catedratico = catedratico;
        this.estudiante = estudiante;
        this.curso = curso;
        this.fecha = LocalDate.now();
        // cuando se crea un voto individual, por defecto se considera 1
        this.upvoteCount = 1;
    }

    // ---------------- Getters ----------------

    /**
     * Devuelve el conteo de upvotes (compatible con llamadas existentes
     * getUpvotes())
     */
    public int getUpvotes() {
        return upvoteCount;
    }

    public void setUpvotes(Catedratico catedratico, Estudiante estudiante, Curso curso) {
        this.catedratico = catedratico;
        this.estudiante = estudiante;
        this.curso = curso;
        this.fecha = LocalDate.now();
    }

    public Catedratico getCatedratico() {
        return catedratico;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public Curso getCurso() {
        return curso;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    // ---------------- Setters / utilitarios ----------------

    public void setUpvoteCount(int count) {
        this.upvoteCount = Math.max(0, count);
    }

    public void increment() {
        this.upvoteCount++;
    }

    public void decrement() {
        if (this.upvoteCount > 0)
            this.upvoteCount--;
    }

    public void setCatedratico(Catedratico catedratico) {
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

    /** Representación útil para debugging */
    @Override
    public String toString() {
        if (catedratico != null && estudiante != null && curso != null) {
            return "Upvote[voto individual: " + estudiante.getCorreo() + " -> " +
                    catedratico.getNombre() + "(" + curso.getNombre() + ") at " + fecha + "]";
        }
        return "Upvotes[total=" + upvoteCount + "]";
    }
}

package gestor.prestamos.modelo;

import java.time.LocalDate;

/**
 * Representa el periodo de un préstamo, desde la fecha en que se realiza
 * hasta la fecha límite de devolución.
 */
public final class PeriodoPrestamo {
    private final LocalDate fechaPrestamo;
    private final LocalDate fechaLimite;

    /**
     * Crea un periodo de préstamo con sus fechas de inicio y límite.
     *
     * @param fechaPrestamo fecha en que se realiza el préstamo
     * @param fechaLimite fecha máxima de devolución
     * @throws IllegalArgumentException si alguna fecha es nula o la fecha límite
     *         es anterior a la fecha del préstamo
     */
    public PeriodoPrestamo(LocalDate fechaPrestamo, LocalDate fechaLimite) {
        if (fechaPrestamo == null || fechaLimite == null) {
            throw new IllegalArgumentException("Las fechas son obligatorias");
        }
        if (fechaLimite.isBefore(fechaPrestamo)) {
            throw new IllegalArgumentException(
                    "La fecha límite no puede ser anterior al préstamo");
        }

        this.fechaPrestamo = fechaPrestamo;
        this.fechaLimite = fechaLimite;
    }

    /**
     * Devuelve la fecha en que se realiza el préstamo.
     *
     * @return fecha de inicio del periodo
     */
    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }

    /**
     * Devuelve la fecha máxima de devolución.
     *
     * @return fecha límite del periodo
     */
    public LocalDate getFechaLimite() {
        return fechaLimite;
    }
}

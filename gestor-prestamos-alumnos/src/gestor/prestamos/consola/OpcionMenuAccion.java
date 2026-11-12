package gestor.prestamos.consola;

/**
 * Implementa una opción del menú delegando su ejecución en una acción.
 */
public final class OpcionMenuAccion implements OpcionMenu {

    private final Runnable accion;

    /**
     * Crea una opción a partir de una acción ejecutable.
     *
     * @param accion acción que se ejecutará al seleccionar la opción
     */
    public OpcionMenuAccion(Runnable accion) {
        this.accion = accion;
    }

    /**
     * Ejecuta la acción asociada.
     */
    @Override
    public void ejecutar() {
        accion.run();
    }
}

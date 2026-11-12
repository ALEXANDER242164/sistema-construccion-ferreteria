package gestor.prestamos.consola;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Collection;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Lectura de datos desde la consola con validación. Repite la petición hasta obtener un valor correcto.
 */
public final class Entrada {

    // Formato aceptado para las fechas: cuatro dígitos de año, dos de mes y dos de dia 
    private static final Pattern FORMATO_FECHA = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");

    private final Scanner scanner;

    /**
     * Crea una entrada que lee del escáner indicado.
     *
     * @param scanner fuente de las líneas introducidas por el usuario; no puede ser nula
     */
    public Entrada(Scanner scanner) {
        this.scanner = Objects.requireNonNull(scanner, "scanner");
    }

    /**
     * Pide un texto no vacío.
     *
     * @param mensaje texto que se muestra antes de leer
     * @return el texto introducido, sin espacios sobrantes
     */
    public String pedirTexto(String mensaje) {
        while (true) {
            String valor = leer(mensaje).trim();
            if (!valor.isEmpty()) {
                return valor;
            }
            System.out.println("  El campo no puede estar vacío.");
        }
    }

    /**
     * Pide un número entero que debe pertenecer a un conjunto de opciones válidas.
     *
     * @param mensaje texto que se muestra antes de leer
     * @param validas números aceptados
     * @return el número elegido
     */
    public int pedirOpcion(String mensaje, Collection<Integer> validas) {
        while (true) {
            String texto = leer(mensaje).trim();
            try {
                int numero = Integer.parseInt(texto);
                if (validas.contains(numero)) {
                    return numero;
                }
            } catch (NumberFormatException e) {
                // El texto no es un número: se informa abajo y se vuelve a pedir.
            }
            System.out.println("  Opción no válida.");
        }
    }

    /**
     * Pide una fecha en formato {@code AAAA-MM-DD}. Si el usuario pulsa Enter y hay un valor
     * por defecto, se devuelve ese valor.
     *
     * @param mensaje texto que se muestra antes de leer
     * @param valorPorDefecto fecha que se usa al pulsar Enter, o {@code null} si no hay
     * @return la fecha introducida o el valor por defecto
     */
    public LocalDate pedirFecha(String mensaje, LocalDate valorPorDefecto) {
        while (true) {
            String texto = leer(mensaje).trim();
            if (texto.isEmpty() && valorPorDefecto != null) {
                return valorPorDefecto;
            }
            // Tecnica: Replace Exception with Test
            // Antes se llamaba directamente a LocalDate.parse() y se capturaba DateTimeParseException para saber si la fecha era valida, es decir, la
            // excepción se usaba para controlar el flujo. Ahora se comprueba primero con esFechaValida() y solo se convierte cuando ya se sabe que el texto es correcto,
            // por lo que LocalDate.parse() ya no puede fallar y el try/catch desaparece.
            if (esFechaValida(texto)) {
                return LocalDate.parse(texto);
            }
            System.out.println("  Formato inválido. Usa AAAA-MM-DD.");
        }
    }

    /**
     * Comprueba, sin lanzar excepciones, si un texto es una fecha valida con el formato
     * {@code AAAA-MM-DD}. El texto debe tener ese formato y tener a un día que
     * valido en el calendario
     *
     * @param texto texto introducido por el usuario, ya sin espacios sobrantes
     * @return {@code true} si el texto representa una fecha existente con formato AAAA-MM-DD
     */
    // Tecnica: Replace Exception with Test
    // Este es el test que sustituye a la excepción. No solo se revisa el formato, también se comprueba que el mes y el día existan, porque si se dejara pasar una
    // fecha como 2026-02-30, LocalDate.parse() lanzaría una excepción que nadie captura.
    private static boolean esFechaValida(String texto) {
        if (!FORMATO_FECHA.matcher(texto).matches()) {
            return false;
        }
        int anio = Integer.parseInt(texto.substring(0, 4));
        int mes = Integer.parseInt(texto.substring(5, 7));
        int dia = Integer.parseInt(texto.substring(8, 10));
        return mes >= 1 && mes <= 12
                && dia >= 1 && dia <= YearMonth.of(anio, mes).lengthOfMonth();
    }

    private String leer(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine();
    }
}
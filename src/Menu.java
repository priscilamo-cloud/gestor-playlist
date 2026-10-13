import java.util.Scanner;

/**
 * Menú de consola. Solo interactúa con el usuario; la lógica vive en ListaReproduccion.
 */
public class Menu {
    private final ListaReproduccion lista = new ListaReproduccion();
    private final Scanner sc = new Scanner(System.in);

    public void ejecutar() {
        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero("Elige una opción: ");
            procesar(opcion);
        } while (opcion != 13);
    }

    private void mostrarMenu() {
        System.out.println("\n===== GESTOR DE LISTA DE REPRODUCCIÓN =====");
        System.out.println(" 1. Agregar canción al inicio");
        System.out.println(" 2. Agregar canción al final");
        System.out.println(" 3. Buscar canción por ID");
        System.out.println(" 4. Seleccionar canción por ID como actual");
        System.out.println(" 5. Eliminar canción por ID");
        System.out.println(" 6. Eliminar canción actual");
        System.out.println(" 7. Consultar canción actual");
        System.out.println(" 8. Avanzar a la siguiente");
        System.out.println(" 9. Retroceder a la anterior");
        System.out.println("10. Mostrar lista completa (ambos sentidos)");
        System.out.println("11. Cantidad de canciones");
        System.out.println("12. Simular reproducción de k canciones");
        System.out.println("13. Salir");
    }

    private void procesar(int opcion) {
        switch (opcion) {
            case 1: agregar(true); break;
            case 2: agregar(false); break;
            case 3: buscar(); break;
            case 4: seleccionar(); break;
            case 5: eliminarPorId(); break;
            case 6: eliminarActual(); break;
            case 7: consultarActual(); break;
            case 8: mover(true); break;
            case 9: mover(false); break;
            case 10: mostrarLista(); break;
            case 11: System.out.println("Cantidad de canciones: " + lista.cantidad()); break;
            case 12: simular(); break;
            case 13: System.out.println("¡Hasta luego!"); break;
            default: System.out.println("Opción inválida. Elige un número del 1 al 13.");
        }
    }

    private void agregar(boolean alInicio) {
        String titulo = leerTexto("Título: ");
        String artista = leerTexto("Artista: ");
        int duracion = leerEnteroPositivo("Duración (segundos): ");
        Cancion c = alInicio
                ? lista.agregarAlInicio(titulo, artista, duracion)
                : lista.agregarAlFinal(titulo, artista, duracion);
        System.out.println("Canción agregada: " + c);
    }

    private void buscar() {
        if (vacia()) return;
        int id = leerEntero("ID a buscar: ");
        Cancion c = lista.buscar(id);
        System.out.println(c == null ? "No existe una canción con ID " + id + "." : "Encontrada: " + c);
    }

    private void seleccionar() {
        if (vacia()) return;
        int id = leerEntero("ID a seleccionar: ");
        System.out.println(lista.seleccionar(id)
                ? "Canción actual: " + lista.consultarActual()
                : "No existe una canción con ID " + id + ". La lista no cambió.");
    }

    private void eliminarPorId() {
        if (vacia()) return;
        int id = leerEntero("ID a eliminar: ");
        Cancion c = lista.eliminarPorId(id);
        if (c == null) {
            System.out.println("No existe una canción con ID " + id + ". La lista no cambió.");
        } else {
            System.out.println("Eliminada: " + c);
            informarActual();
        }
    }

    private void eliminarActual() {
        if (vacia()) return;
        System.out.println("Eliminada: " + lista.eliminarActual());
        informarActual();
    }

    private void consultarActual() {
        if (vacia()) return;
        System.out.println("Canción actual: " + lista.consultarActual());
    }

    private void mover(boolean adelante) {
        if (vacia()) return;
        Cancion c = adelante ? lista.avanzar() : lista.retroceder();
        System.out.println((adelante ? "Siguiente: " : "Anterior: ") + c);
    }

    private void mostrarLista() {
        if (vacia()) return;
        System.out.println("--- De inicio a fin (> = actual) ---");
        System.out.println(lista.mostrarAdelante());
        System.out.println("--- De fin a inicio ---");
        System.out.println(lista.mostrarAtras());
    }

    private void simular() {
        if (vacia()) return;
        int k = leerEnteroPositivo("¿Cuántas canciones reproducir (k)? ");
        System.out.println(lista.simular(k));
        System.out.println("Siguiente en reproducirse: " + lista.consultarActual());
    }


    private boolean vacia() {
        if (lista.estaVacia()) {
            System.out.println("La lista está vacía. Agrega canciones primero (opciones 1 o 2).");
            return true;
        }
        return false;
    }

    private void informarActual() {
        if (lista.estaVacia()) {
            System.out.println("La lista quedó vacía.");
        } else {
            System.out.println("Canción actual: " + lista.consultarActual());
        }
    }

    private String leerTexto(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String s = sc.nextLine().trim();
            if (!s.isEmpty()) return s;
            System.out.println("El texto no puede estar vacío.");
        }
    }

    private int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida: escribe un número entero.");
            }
        }
    }

    private int leerEnteroPositivo(String mensaje) {
        while (true) {
            int n = leerEntero(mensaje);
            if (n > 0) return n;
            System.out.println("El número debe ser mayor que cero.");
        }
    }
}
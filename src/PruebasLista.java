/**
 * Pruebas reproducibles sin librerías externas.
 * Ejecutar: main de esta clase. Cada caso imprime estado inicial, operación,
 * resultado esperado y obtenido, y comprueba además tamaño y enlaces.
 */
public class PruebasLista {
    private static int total = 0;
    private static int aprobadas = 0;

    public static void main(String[] args) {
        // 1. Operaciones sobre lista vacía
        ListaReproduccion l = new ListaReproduccion();
        caso("1.1 Vacía: consultar actual", l, "consultarActual()", "null", String.valueOf(l.consultarActual()));
        caso("1.2 Vacía: avanzar", l, "avanzar()", "null", String.valueOf(l.avanzar()));
        caso("1.3 Vacía: retroceder", l, "retroceder()", "null", String.valueOf(l.retroceder()));
        caso("1.4 Vacía: eliminarActual", l, "eliminarActual()", "null", String.valueOf(l.eliminarActual()));
        caso("1.5 Vacía: buscar(1)", l, "buscar(1)", "null", String.valueOf(l.buscar(1)));
        caso("1.6 Vacía: eliminarPorId(1)", l, "eliminarPorId(1)", "null", String.valueOf(l.eliminarPorId(1)));
        caso("1.7 Vacía: seleccionar(1)", l, "seleccionar(1)", "false", String.valueOf(l.seleccionar(1)));
        caso("1.8 Vacía: recorrido", l, "idsAdelante()", "", l.idsAdelante());
        caso("1.9 Vacía: simular(3)", l, "simularIds(3)", "", l.simularIds(3));

        // 2. Primera inserción
        l = new ListaReproduccion();
        String ini = l.estado();
        l.agregarAlFinal("A", "X", 100);
        caso("2.1 Primera inserción (final)", ini, l, "agregarAlFinal", "[1] actual=1 n=1", l.estado());
        l = new ListaReproduccion();
        l.agregarAlInicio("A", "X", 100);
        caso("2.2 Primera inserción (inicio)", "[] actual=null n=0", l, "agregarAlInicio", "[1] actual=1 n=1", l.estado());

        // 3. Navegación con un solo nodo
        l = crear(1);
        ini = l.estado();
        l.avanzar();
        caso("3.1 Un nodo: avanzar", ini, l, "avanzar()", "[1] actual=1 n=1", l.estado());
        l.retroceder();
        caso("3.2 Un nodo: retroceder", ini, l, "retroceder()", "[1] actual=1 n=1", l.estado());

        // 4. Inserciones al inicio y al final (la actual no cambia)
        l = crear(1);
        ini = l.estado();
        l.agregarAlInicio("B", "X", 90);   // id 2
        l.agregarAlFinal("C", "X", 80);    // id 3
        l.agregarAlInicio("D", "X", 70);   // id 4
        caso("4.1 Inserciones mixtas", ini, l, "inicio, final, inicio", "[4,2,1,3] actual=1 n=4", l.estado());

        // 5. Recorridos con 5 canciones
        l = crear(5);
        caso("5.1 Recorrido adelante", l, "idsAdelante()", "1 2 3 4 5", l.idsAdelante());
        caso("5.2 Recorrido atrás", l, "idsAtras()", "5 4 3 2 1", l.idsAtras());

        // 6. Avance desde la última y retroceso desde la primera
        l = crear(5);
        l.seleccionar(5);
        ini = l.estado();
        l.avanzar();
        caso("6.1 Avanzar desde la última", ini, l, "avanzar()", "[1,2,3,4,5] actual=1 n=5", l.estado());
        l = crear(5);
        ini = l.estado();
        l.retroceder();
        caso("6.2 Retroceder desde la primera", ini, l, "retroceder()", "[1,2,3,4,5] actual=5 n=5", l.estado());

        // 7. Eliminación de primero, último e intermedio (actual distinta se conserva)
        l = crear(5);
        l.seleccionar(3);
        ini = l.estado();
        l.eliminarPorId(1);
        caso("7.1 Eliminar primero", ini, l, "eliminarPorId(1)", "[2,3,4,5] actual=3 n=4", l.estado());
        caso("7.1b Recorrido atrás tras eliminar", l, "idsAtras()", "5 4 3 2", l.idsAtras());
        l = crear(5);
        l.seleccionar(3);
        ini = l.estado();
        l.eliminarPorId(5);
        caso("7.2 Eliminar último", ini, l, "eliminarPorId(5)", "[1,2,3,4] actual=3 n=4", l.estado());
        caso("7.2b Avanzar desde nuevo último", l, "seleccionar(4), avanzar()", "1",
                String.valueOf(avanzarDesde(l, 4)));
        l = crear(5);
        ini = l.estado();
        l.eliminarPorId(3);
        caso("7.3 Eliminar intermedio", ini, l, "eliminarPorId(3)", "[1,2,4,5] actual=1 n=4", l.estado());

        // 8. Eliminar la actual y la única canción
        l = crear(5);
        l.seleccionar(3);
        ini = l.estado();
        l.eliminarActual();
        caso("8.1 Eliminar actual intermedia", ini, l, "eliminarActual()", "[1,2,4,5] actual=4 n=4", l.estado());
        l = crear(5);
        l.seleccionar(5);
        ini = l.estado();
        l.eliminarActual();
        caso("8.2 Eliminar actual = última", ini, l, "eliminarActual()", "[1,2,3,4] actual=1 n=4", l.estado());
        l = crear(5);
        ini = l.estado();
        l.eliminarActual();
        caso("8.3 Eliminar actual = primera", ini, l, "eliminarActual()", "[2,3,4,5] actual=2 n=4", l.estado());
        l = crear(1);
        ini = l.estado();
        l.eliminarActual();
        caso("8.4 Eliminar la única canción", ini, l, "eliminarActual()", "[] actual=null n=0", l.estado());
        l.agregarAlFinal("N", "X", 60);
        caso("8.5 Reinsertar tras vaciar", "[] actual=null n=0", l, "agregarAlFinal", "[2] actual=2 n=1", l.estado());

        // 9. Identificador inexistente
        l = crear(3);
        ini = l.estado();
        caso("9.1 buscar(99)", l, "buscar(99)", "null", String.valueOf(l.buscar(99)));
        caso("9.2 eliminarPorId(99)", l, "eliminarPorId(99)", "null", String.valueOf(l.eliminarPorId(99)));
        caso("9.3 seleccionar(99)", l, "seleccionar(99)", "false", String.valueOf(l.seleccionar(99)));
        caso("9.4 Lista sin cambios", ini, l, "(ninguna)", ini, l.estado());

        // 10. Simulación con k > n
        l = crear(3);
        ini = l.estado();
        String reproducidas = l.simularIds(7);
        caso("10.1 simular(7) con 3 canciones", ini, l, "simularIds(7)", "1 2 3 1 2 3 1 | [1,2,3] actual=2 n=3",
                reproducidas + " | " + l.estado());
        l = crear(3);
        l.simularIds(3);
        caso("10.2 simular(k = n) deja la actual igual", "[1,2,3] actual=1 n=3", l, "simularIds(3)",
                "[1,2,3] actual=1 n=3", l.estado());

        // 11. Entradas inválidas
        l = new ListaReproduccion();
        final ListaReproduccion lv = l;
        caso("11.1 Duración 0", l, "agregarAlFinal(\"A\",\"X\",0)", "IllegalArgumentException",
                lanza(() -> lv.agregarAlFinal("A", "X", 0)));
        caso("11.2 Duración negativa", l, "agregarAlInicio(\"A\",\"X\",-5)", "IllegalArgumentException",
                lanza(() -> lv.agregarAlInicio("A", "X", -5)));
        caso("11.3 Título vacío", l, "agregarAlFinal(\"  \",\"X\",10)", "IllegalArgumentException",
                lanza(() -> lv.agregarAlFinal("  ", "X", 10)));
        caso("11.4 Artista nulo", l, "agregarAlFinal(\"A\",null,10)", "IllegalArgumentException",
                lanza(() -> lv.agregarAlFinal("A", null, 10)));
        caso("11.5 Lista sigue vacía", l, "(ninguna)", "[] actual=null n=0", l.estado());
        l.agregarAlFinal("A", "X", 10);
        caso("11.6 IDs no se consumen por inserciones fallidas", "[] actual=null n=0", l, "agregarAlFinal válida",
                "[1] actual=1 n=1", l.estado());
        final ListaReproduccion l5 = crear(2);
        caso("11.7 simular(0)", l5, "simular(0)", "IllegalArgumentException", lanza(() -> l5.simular(0)));
        caso("11.8 simular(-2)", l5, "simular(-2)", "IllegalArgumentException", lanza(() -> l5.simular(-2)));

        System.out.println("\n==============================================");
        System.out.println("RESULTADO: " + aprobadas + " de " + total + " pruebas aprobadas.");
        System.out.println("==============================================");
    }

    // Utilidades

    private static ListaReproduccion crear(int n) {
        ListaReproduccion l = new ListaReproduccion();
        for (int i = 1; i <= n; i++) {
            l.agregarAlFinal("Cancion " + i, "Artista", 100 + i);
        }
        return l;
    }

    private static int avanzarDesde(ListaReproduccion l, int id) {
        l.seleccionar(id);
        return l.avanzar().getId();
    }

    private static String lanza(Runnable r) {
        try {
            r.run();
            return "sin excepcion";
        } catch (IllegalArgumentException e) {
            return "IllegalArgumentException";
        }
    }

    /** Variante cuando el estado inicial es el estado actual de la lista. */
    private static void caso(String nombre, ListaReproduccion l, String operacion, String esperado, String obtenido) {
        caso(nombre, l.estado(), l, operacion, esperado, obtenido);
    }

    private static void caso(String nombre, String estadoInicial, ListaReproduccion l,
                             String operacion, String esperado, String obtenido) {
        total++;
        boolean consistente = l.verificarConsistencia();
        boolean ok = esperado.equals(obtenido) && consistente;
        if (ok) aprobadas++;
        System.out.println("\n[" + (ok ? "OK" : "FALLO") + "] " + nombre);
        System.out.println("  Estado inicial : " + estadoInicial);
        System.out.println("  Operación      : " + operacion);
        System.out.println("  Esperado       : " + esperado);
        System.out.println("  Obtenido       : " + obtenido);
        System.out.println("  Cantidad final : " + l.cantidad() + " | Enlaces consistentes: " + (consistente ? "sí" : "NO"));
    }
}
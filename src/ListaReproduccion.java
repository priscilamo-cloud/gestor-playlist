public class ListaReproduccion {
    private Nodo inicio;
    private Nodo fin;
    private Nodo actual;
    private int tamanio;
    private int siguienteId;

    public ListaReproduccion() {
        inicio = null;
        fin = null;
        actual = null;
        tamanio = 0;
        siguienteId = 1;
    }


    public boolean estaVacia() { return tamanio == 0; }          // O(1)

    public int cantidad() { return tamanio; }                    // O(1)

    public Cancion consultarActual() {                           // O(1)
        return actual == null ? null : actual.cancion;
    }


    /** Agrega al inicio. O(1). Devuelve la canción creada (con su id automático). */
    public Cancion agregarAlInicio(String titulo, String artista, int duracion) {
        Nodo nuevo = crearNodo(titulo, artista, duracion);
        if (estaVacia()) {
            insertarEnVacia(nuevo);
        } else {
            nuevo.siguiente = inicio;
            nuevo.anterior = fin;
            inicio.anterior = nuevo;
            fin.siguiente = nuevo;
            inicio = nuevo;            // fin y actual no cambian
            tamanio++;
        }
        return nuevo.cancion;
    }

    /** Agrega al final. O(1). */
    public Cancion agregarAlFinal(String titulo, String artista, int duracion) {
        Nodo nuevo = crearNodo(titulo, artista, duracion);
        if (estaVacia()) {
            insertarEnVacia(nuevo);
        } else {
            nuevo.anterior = fin;
            nuevo.siguiente = inicio;
            fin.siguiente = nuevo;
            inicio.anterior = nuevo;
            fin = nuevo;               // inicio y actual no cambian
            tamanio++;
        }
        return nuevo.cancion;
    }


    private Nodo crearNodo(String titulo, String artista, int duracion) {
        Cancion c = new Cancion(siguienteId, titulo, artista, duracion);
        siguienteId++;
        return new Nodo(c);
    }

    private void insertarEnVacia(Nodo nuevo) {
        nuevo.anterior = nuevo;
        nuevo.siguiente = nuevo;
        inicio = nuevo;
        fin = nuevo;
        actual = nuevo;                // la primera canción es la actual
        tamanio = 1;
    }


    /** Localiza un nodo por id recorriendo a lo sumo n nodos. O(n). */
    private Nodo buscarNodo(int id) {
        if (estaVacia()) return null;
        Nodo n = inicio;
        for (int i = 0; i < tamanio; i++) {   // condición de término para estructura circular
            if (n.cancion.getId() == id) return n;
            n = n.siguiente;
        }
        return null;
    }

    public Cancion buscar(int id) {                              // O(n)
        Nodo n = buscarNodo(id);
        return n == null ? null : n.cancion;
    }

    public boolean seleccionar(int id) {                         // O(n)
        Nodo n = buscarNodo(id);
        if (n == null) return false;
        actual = n;
        return true;
    }


    /** Eliminar por id: búsqueda O(n) + desenlace O(1) = O(n). */
    public Cancion eliminarPorId(int id) {
        Nodo n = buscarNodo(id);
        if (n == null) return null;
        Cancion c = n.cancion;
        desenlazar(n);
        return c;
    }

    /** Eliminar la actual: ya tenemos la referencia, solo se desenlaza. O(1). */
    public Cancion eliminarActual() {
        if (estaVacia()) return null;
        Cancion c = actual.cancion;
        desenlazar(actual);
        return c;
    }

    /** Quita el nodo de la lista conservando las referencias antes de cambiar enlaces. O(1). */
    private void desenlazar(Nodo nodo) {
        if (tamanio == 1) {
            inicio = null;
            fin = null;
            actual = null;
            tamanio = 0;
        } else {
            Nodo ant = nodo.anterior;
            Nodo sig = nodo.siguiente;
            ant.siguiente = sig;
            sig.anterior = ant;
            if (nodo == inicio) inicio = sig;
            if (nodo == fin) fin = ant;
            if (nodo == actual) actual = sig;   // la nueva actual es la que le seguía
            tamanio--;
        }
        // Se cortan los enlaces del nodo eliminado para que no quede enganchado a la lista
        nodo.anterior = null;
        nodo.siguiente = null;
    }



    public Cancion avanzar() {                                   // O(1)
        if (estaVacia()) return null;
        actual = actual.siguiente;
        return actual.cancion;
    }

    public Cancion retroceder() {                                // O(1)
        if (estaVacia()) return null;
        actual = actual.anterior;
        return actual.cancion;
    }



    /** Muestra cada canción una sola vez de inicio a fin. O(n). */
    public String mostrarAdelante() { return recorrer(true, true); }

    /** Muestra cada canción una sola vez de fin a inicio. O(n). */
    public String mostrarAtras() { return recorrer(false, true); }

    /** Solo ids, de inicio a fin (útil para pruebas). O(n). */
    public String idsAdelante() { return recorrer(true, false); }

    /** Solo ids, de fin a inicio. O(n). */
    public String idsAtras() { return recorrer(false, false); }

    private String recorrer(boolean adelante, boolean detalle) {
        if (estaVacia()) return "";
        StringBuilder sb = new StringBuilder();
        Nodo punto = adelante ? inicio : fin;
        Nodo n = punto;
        do {                                  // do-while: se detiene al volver al punto de partida
            if (detalle) {
                sb.append(n == actual ? "> " : "  ").append(n.cancion).append("\n");
            } else {
                sb.append(n.cancion.getId()).append(" ");
            }
            n = adelante ? n.siguiente : n.anterior;
        } while (n != punto);
        return sb.toString().trim();
    }

    /** Reproduce k canciones (muestra la actual y avanza). O(k). */
    public String simular(int k) { return simular(k, true); }

    /** Igual que simular pero solo devuelve los ids reproducidos. */
    public String simularIds(int k) { return simular(k, false); }

    private String simular(int k, boolean detalle) {
        if (k <= 0) throw new IllegalArgumentException("k debe ser un entero positivo.");
        if (estaVacia()) return "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < k; i++) {
            if (detalle) {
                sb.append("Reproduciendo: ").append(actual.cancion).append("\n");
            } else {
                sb.append(actual.cancion.getId()).append(" ");
            }
            actual = actual.siguiente;
        }
        return sb.toString().trim();
    }



    /** Estado compacto, ej: "[1,2,3] actual=2 n=3". */
    public String estado() {
        StringBuilder sb = new StringBuilder("[");
        if (!estaVacia()) {
            Nodo n = inicio;
            for (int i = 0; i < tamanio; i++) {
                sb.append(n.cancion.getId());
                if (i < tamanio - 1) sb.append(",");
                n = n.siguiente;
            }
        }
        sb.append("] actual=").append(actual == null ? "null" : String.valueOf(actual.cancion.getId()));
        sb.append(" n=").append(tamanio);
        return sb.toString();
    }

    /** Comprueba todas las invariantes de la estructura. O(n). */
    public boolean verificarConsistencia() {
        if (tamanio == 0) {
            return inicio == null && fin == null && actual == null;
        }
        if (inicio == null || fin == null || actual == null) return false;
        if (fin.siguiente != inicio || inicio.anterior != fin) return false;
        if (tamanio == 1) {
            if (inicio != fin || inicio.siguiente != inicio || inicio.anterior != inicio) return false;
        }
        int contados = 0;
        boolean actualEncontrada = false;
        Nodo n = inicio;
        do {
            if (n.siguiente == null || n.anterior == null) return false;
            if (n.siguiente.anterior != n || n.anterior.siguiente != n) return false;
            if (n == actual) actualEncontrada = true;
            contados++;
            n = n.siguiente;
        } while (n != inicio && contados <= tamanio);
        return contados == tamanio && actualEncontrada;
    }
}

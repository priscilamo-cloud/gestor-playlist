public class Nodo {
    Cancion cancion;
    Nodo anterior;
    Nodo siguiente;

    Nodo(Cancion cancion) {
        this.cancion = cancion;
    }

    public Cancion getCancion() { return cancion; }
}

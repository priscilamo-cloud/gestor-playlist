public class Cancion {
    private final int id;
    private final String titulo;
    private final String artista;
    private final int duracion; // segundos

    public Cancion(int id, String titulo, String artista, int duracion) {
        if (titulo == null || titulo.trim().isEmpty())
            throw new IllegalArgumentException("El título no puede estar vacío.");
        if (artista == null || artista.trim().isEmpty())
            throw new IllegalArgumentException("El artista no puede estar vacío.");
        if (duracion <= 0)
            throw new IllegalArgumentException("La duración debe ser mayor que cero.");
        this.id = id;
        this.titulo = titulo.trim();
        this.artista = artista.trim();
        this.duracion = duracion;
    }

    public int getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getArtista() { return artista; }
    public int getDuracion() { return duracion; }

    @Override
    public String toString() {
        return "[" + id + "] " + titulo + " - " + artista
                + " (" + (duracion / 60) + ":" + String.format("%02d", duracion % 60) + ")";
    }
}
package ReproductorV3;

public class Song {
    private String title;
    private String artist;
    private String album;
    private String duration; // Utiliza java.time.Duration para manejar duraciones
    private String genre;
    private int year;

    // Constructor
    public Song(String title, String artist, String album, String duration, String genre, int year) {
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.duration = duration;
        this.genre = genre;
        this.year = year;
    }

    // Getters y Setters para cada propiedad
    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbum() {
        return album;
    }

    public String getDuration() {
        return duration;
    }

    public String getGenre() {
        return genre;
    }

    public int getYear() {
        return year;
    }
}


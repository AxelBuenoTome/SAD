package ReproductorV2;

public class Song {
    private String title;
    private String artist;
    private String album;
    private String duration; 
    private String genre;
    private int year;
    private String fileName;

    // Constructor
    public Song(String title, String artist, String album, String duration, String genre, int year, String fileName) {
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.duration = duration;
        this.genre = genre;
        this.year = year;
        this.fileName = fileName;
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

    public String getFileName(){
        return fileName;
    }

    public void setFileName(String name) {
        this.fileName = name;
    }
}


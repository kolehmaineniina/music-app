package fi.haagahelia.musicapp.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Song {

    @Id 
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;

    private String title;
    private String artist;

    private int releaseYear;

    public Song() {

    }
    
    public Song(String title, String artist, int year) {
        this.title = title;
        this.artist = artist;
        this.releaseYear = year;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getArtist() {
        return artist;
    }
    public void setArtist(String artist) {
        this.artist = artist;
    }
    public int getReleaseYear() {
        return releaseYear;
    }
    public void setReleaseYear(int year) {
        this.releaseYear = year;
    }

}

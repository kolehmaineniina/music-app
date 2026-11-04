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

    private String songName;
    private String artist;
    private int releaseYear;

    public Song() {

    }
    
    public Song(String songName, String artist, int year) {
        this.songName = songName;
        this.artist = artist;
        this.releaseYear = year;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getSongName() {
        return songName;
    }
    public void setSongName(String songName) {
        this.songName = songName;
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

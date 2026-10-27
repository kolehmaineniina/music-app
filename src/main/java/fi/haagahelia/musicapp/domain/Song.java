package fi.haagahelia.audioplayer.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Song {

    @Id 
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;

    private String songName;
    private String artist;
    private int releaseYear;
    private String filePath;

    @ManyToOne
    @JoinColumn
    private AppUser uploader;

    public Song() {

    }
    
    public Song(String songName, String artist, int year, String filePath, AppUser uploader) {
        this.songName = songName;
        this.artist = artist;
        this.releaseYear = year;
        this.filePath = filePath;
        this.uploader = uploader;
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
    public String getFilePath() {
        return filePath;
    }
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    public AppUser getUploader() {
        return uploader;
    }
    public void setUploader(AppUser uploader) {
        this.uploader = uploader;
    }

}

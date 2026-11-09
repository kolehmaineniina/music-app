package fi.haagahelia.musicapp.domain;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class SubmissionForm {

    @NotBlank(message = "Title required")
    private String title;

    @NotBlank(message = "Artist required")
    private String artist;
    
    @Min(1500)
    @Max(2100)
    private int releaseYear;

    public SubmissionForm() {
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

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }
}
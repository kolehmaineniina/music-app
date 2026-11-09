package fi.haagahelia.musicapp.domain;

import jakarta.persistence.*;

@Entity
public class SongSubmission {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;
    private String artist;
    private int releaseYear;

    // fetch AppUser when/if needed
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "submitted_by")
    private AppUser submittedBy;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private SubmissionStatus status;

    public SongSubmission() {
    }

    public SongSubmission(String title, String artist, int releaseYear, AppUser submittedBy) {
        this.title = title;
        this.artist = artist;
        this.releaseYear = releaseYear;
        this.submittedBy = submittedBy;
        this.status = SubmissionStatus.PENDING;
    }

    public Long getId() {
        return id;
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

    public AppUser getSubmittedBy() {
        return submittedBy;
    }

    public SubmissionStatus getStatus() {
        return status;
    }

    public void setStatus(SubmissionStatus status) {
        this.status = status;
    }
}
package fi.haagahelia.musicapp.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity 
@Table (
    name = "collection_entry",
    uniqueConstraints = @UniqueConstraint(
        name = "uq_collection_user_song",
        columnNames = {"user_id", "song_id"}
    )
)
public class CollectionEntry {

    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne 
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser user;

    @ManyToOne 
    @JoinColumn(name = "song_id", nullable = false)
    private Song song;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }

    public CollectionEntry() {
        
    }

    public CollectionEntry(AppUser user, Song song) {
        this.user = user;
        this.song = song;
    }

}

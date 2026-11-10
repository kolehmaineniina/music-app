package fi.haagahelia.musicapp.domain;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

// Provides methods to save, find and delete songs.
public interface SongRepository extends CrudRepository <Song, Long> {
    // Match part of the title or artist, ignoring letter case.
    List<Song> findByTitleContainingIgnoreCaseOrArtistContainingIgnoreCase(String title, String artis);
    
}

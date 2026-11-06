package fi.haagahelia.musicapp.domain;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface SongRepository extends CrudRepository <Song, Long> {
    List<Song> findByTitleContainingIgnoreCaseOrArtistContainingIgnoreCase(String title, String artis);
    
}

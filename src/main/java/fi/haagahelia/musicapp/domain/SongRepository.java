package fi.haagahelia.audioplayer.domain;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface SongRepository extends CrudRepository <Song, Long> {
     List<Song> findByUploaderUsername(String username);
    List<Song> findBySongNameContainingIgnoreCase(String songName);
    List<Song> findByArtistContainingIgnoreCaseOrderBySongName(String artist);
}

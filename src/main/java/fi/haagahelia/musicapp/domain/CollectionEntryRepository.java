package fi.haagahelia.musicapp.domain;

import org.springframework.data.repository.CrudRepository;
import java.util.List;


public interface CollectionEntryRepository extends CrudRepository <CollectionEntry, Long> {
        List<CollectionEntry> findByUser(AppUser user);
        List<CollectionEntry> findByUser_Username(String username);
        boolean existsByUserAndSong(AppUser user, Song song);

}

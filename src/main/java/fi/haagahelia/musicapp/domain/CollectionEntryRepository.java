package fi.haagahelia.musicapp.domain;

import org.springframework.data.repository.CrudRepository;
import java.util.List;


// Provides methods to save, find and delete collection entries.
public interface CollectionEntryRepository extends CrudRepository <CollectionEntry, Long> {
        // Find collection entries belonging to this user.
        List<CollectionEntry> findByUser(AppUser user);
        // Find collection entries using the username.
        List<CollectionEntry> findByUser_Username(String username);
        // Check whether this song is already in the user collection.
        boolean existsByUserAndSong(AppUser user, Song song);
        // Find all collection entries that contain this song.
        List<CollectionEntry> findBySong(Song song);

}

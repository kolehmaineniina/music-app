package fi.haagahelia.musicapp.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fi.haagahelia.musicapp.domain.AppUser;
import fi.haagahelia.musicapp.domain.CollectionEntry;
import fi.haagahelia.musicapp.domain.CollectionEntryRepository;
import fi.haagahelia.musicapp.domain.Song;
import fi.haagahelia.musicapp.domain.SongRepository;

// Handles changes to catalog songs and their collection entries.
@Service 
public class SongService {
    private final SongRepository songRepository;
    private final CollectionEntryRepository collectionEntryRepository;
    
    public SongService(
            SongRepository songRepository,
            CollectionEntryRepository collectionEntryRepository){
        
        this.songRepository = songRepository;
        this.collectionEntryRepository = collectionEntryRepository;
    }

    @Transactional
    public Song createSong(Song song) {

        // Only songs without an existing ID can be created here.
        if (song.getId() != null) {
            throw new IllegalArgumentException("Song already exists");
        }

        return songRepository.save(song);
    }

    @Transactional 
    // Save the song and collection entry together; undo both if either step fails.
    public void createSongAndAddToCollection(Song song, AppUser user) {
        
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        Song savedSong = createSong(song);
        collectionEntryRepository.save(new CollectionEntry(user, savedSong));

    }

    @Transactional
    public void updateSong(Long id, Song updatedSong) {

        Song existingSong = songRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                    "Song not found: " + id
                ));

        // Changes to this loaded song are saved when the transaction completes.
        existingSong.setTitle(updatedSong.getTitle());
        existingSong.setArtist(updatedSong.getArtist());
        existingSong.setReleaseYear(updatedSong.getReleaseYear());

        // songRepository.save(existingSong) unnecessary: 
        // @Transactional keeps existingSong managed by Hibernate;
        // dirty checking keeps track of changes and flushes them at commit.
    }

    @Transactional
    public void deleteSong(Long id) {

        Song song = songRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(
                    "Song not found: " + id
                ));

        // Remove collection entries before deleting the song they refer to.
        collectionEntryRepository.deleteAll(
                collectionEntryRepository.findBySong(song)
        );

        songRepository.delete(song);
    }

}

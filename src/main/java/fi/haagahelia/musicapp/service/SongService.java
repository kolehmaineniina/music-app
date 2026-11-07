package fi.haagahelia.musicapp.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fi.haagahelia.musicapp.domain.AppUser;
import fi.haagahelia.musicapp.domain.AppUserRepository;
import fi.haagahelia.musicapp.domain.CollectionEntry;
import fi.haagahelia.musicapp.domain.CollectionEntryRepository;
import fi.haagahelia.musicapp.domain.Song;
import fi.haagahelia.musicapp.domain.SongRepository;

@Service 
public class SongService {
    private final SongRepository songRepository;
    private final AppUserRepository appUserRepository;
    private final CollectionEntryRepository collectionEntryRepository;
    
    public SongService(
            SongRepository songRepository,
            AppUserRepository appUserRepository,
            CollectionEntryRepository collectionEntryRepository){
        
        this.songRepository = songRepository;
        this.appUserRepository = appUserRepository;
        this.collectionEntryRepository = collectionEntryRepository;
    }

    @Transactional 
    public void createSongAndAddToCollection(Song song, String username) {
        AppUser user = appUserRepository.findByUsername(username);
        
        if (user == null) {
            throw new IllegalArgumentException("User " + username + " not found");
        }

       if (song.getId() != null) {
            throw new IllegalArgumentException("Song already exists");
       }
        Song savedSong = songRepository.save(song);
        collectionEntryRepository.save(new CollectionEntry(user, savedSong));

    }

}

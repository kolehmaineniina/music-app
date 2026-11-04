package fi.haagahelia.musicapp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import fi.haagahelia.musicapp.domain.AppUser;
import fi.haagahelia.musicapp.domain.AppUserRepository;
import fi.haagahelia.musicapp.domain.CollectionEntry;
import fi.haagahelia.musicapp.domain.CollectionEntryRepository;
import fi.haagahelia.musicapp.domain.Song;
import fi.haagahelia.musicapp.domain.SongRepository;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner demoData(
            SongRepository songRepository,
            AppUserRepository appUserRepository,
            CollectionEntryRepository collectionEntryRepository) {

        return args -> {

            // Save songs
            Song songA = songRepository.save(
                new Song("Song A", "Artist A", 2020)
            );

            Song songB = songRepository.save(
                new Song("Song B", "Artist B", 2022)
            );

            // Save users
            AppUser alice = appUserRepository.save(
                new AppUser("alice", "testpassword", "alice@example.com", "USER")
            );

            AppUser bob = appUserRepository.save(
                new AppUser("bob", "testpassword", "bob@example.com", "USER")
            );

            // Add songs to user collections
            collectionEntryRepository.save(new CollectionEntry(alice, songA));
            collectionEntryRepository.save(new CollectionEntry(alice, songB));
            collectionEntryRepository.save(new CollectionEntry(bob, songA));

            // Test repository methods
            System.out.println("Saved songs: " + songRepository.count());
            System.out.println("Saved users: " + appUserRepository.count());
            System.out.println("Collection entries: " + collectionEntryRepository.count());

            System.out.println("Alice's collection:");

            for (CollectionEntry entry :
                    collectionEntryRepository.findByUser_Username("alice")) {

                System.out.println(entry.getSong().getSongName());
            }

            System.out.println("Bob's collection:");

            for (CollectionEntry entry :
                    collectionEntryRepository.findByUser(bob)) {

                System.out.println(entry.getSong().getSongName());
            }
        };
    }
}

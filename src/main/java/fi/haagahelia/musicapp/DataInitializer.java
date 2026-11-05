package fi.haagahelia.musicapp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

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
            CollectionEntryRepository collectionEntryRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {

            // Save songs
            Song songA = songRepository.save(
                new Song("Song A", "Artist A", 2020)
            );

            Song songB = songRepository.save(
                new Song("Song B", "Artist B", 2022)
            );

            AppUser alice = appUserRepository.save(
                new AppUser("alice", passwordEncoder.encode("testpassword"), "alice@example.com", "ADMIN")
            );
            AppUser bob = appUserRepository.save(
                new AppUser("bob", passwordEncoder.encode("testpassword"), "bob@example.com", "USER")
            );

            collectionEntryRepository.save(new CollectionEntry(alice, songA));
            collectionEntryRepository.save(new CollectionEntry(alice, songB));
            collectionEntryRepository.save(new CollectionEntry(bob, songA));

        };
    }
}

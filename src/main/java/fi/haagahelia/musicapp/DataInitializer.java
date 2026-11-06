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
            Song bohemianRhapsody = songRepository.save(
                new Song("Bohemian Rhapsody", "Queen", 1975)
            );

            Song dontStopMeNow = songRepository.save(
                new Song("Don't Stop Me Now", "Queen", 1978)
            );

            Song dancingQueen = songRepository.save(
                new Song("Dancing Queen", "ABBA", 1976)
            );

            Song mammaMia = songRepository.save(
                new Song("Mamma Mia", "ABBA", 1975)
            );

            Song yesterday = songRepository.save(
                new Song("Yesterday", "The Beatles", 1965)
            );

            Song heyJude = songRepository.save(
                new Song("Hey Jude", "The Beatles", 1968)
            );

            Song somebodyToLove = songRepository.save(
                new Song("Somebody to Love", "Queen", 1976)
            );

            Song someoneLikeYou = songRepository.save(
                new Song("Someone Like You", "Adele", 2011)
            );

            Song rollingInTheDeep = songRepository.save(
                new Song("Rolling in the Deep", "Adele", 2010)
            );

            AppUser alice = appUserRepository.save(
                new AppUser("alice", passwordEncoder.encode("admin"), "alice@example.com", "ADMIN")
            );

            AppUser bob = appUserRepository.save(
                new AppUser("bob", passwordEncoder.encode("user"), "bob@example.com", "USER")
            );

            collectionEntryRepository.save(new CollectionEntry(alice, bohemianRhapsody));
            collectionEntryRepository.save(new CollectionEntry(alice, dancingQueen));
            collectionEntryRepository.save(new CollectionEntry(alice, yesterday));

            collectionEntryRepository.save(new CollectionEntry(bob, dontStopMeNow));
            collectionEntryRepository.save(new CollectionEntry(bob, dancingQueen));
            collectionEntryRepository.save(new CollectionEntry(bob, someoneLikeYou));

        };
    }
}

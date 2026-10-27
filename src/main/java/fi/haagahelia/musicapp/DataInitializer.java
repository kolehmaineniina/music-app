package fi.haagahelia.audioplayer;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import fi.haagahelia.audioplayer.domain.Song;
import fi.haagahelia.audioplayer.domain.SongRepository;

@Configuration 
public class DataInitializer {

@Bean
public CommandLineRunner demoData(SongRepository songRepository) {
    return args -> {

        songRepository.save(
            new Song("Song A", "Artist A", 2020, "uploads/songA.mp3", null)
        );

        songRepository.save(
            new Song("Song B", "Artist B", 2022, "uploads/songB.mp3", null)
        );

        System.out.println("Saved songs: " + songRepository.count());
    };
}

}

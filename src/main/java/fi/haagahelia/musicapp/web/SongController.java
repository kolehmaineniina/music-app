package fi.haagahelia.musicapp.web;

import java.security.Principal;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fi.haagahelia.musicapp.domain.CollectionEntryRepository;
import fi.haagahelia.musicapp.domain.Song;
import fi.haagahelia.musicapp.domain.SongRepository;

@Controller
public class SongController {

    private final SongRepository songRepository;
    private final CollectionEntryRepository collectionEntryRepository;

    public SongController(SongRepository songRepository, CollectionEntryRepository collectionEntryRepository) {
        this.songRepository = songRepository;
        this.collectionEntryRepository = collectionEntryRepository;
    }

@GetMapping("/songs")
public String showSongs(
        Principal principal,
        Model model,
        @RequestParam(name = "search", required = false) String search
) {

    // Songs from the shared catalog
    if (search == null || search.isBlank()) {
        model.addAttribute("songs", songRepository.findAll());
    } else {
        model.addAttribute("songs",
            songRepository.findByTitleContainingIgnoreCaseOrArtistContainingIgnoreCase(
                search.trim(), search.trim()));
    }

    model.addAttribute("search", search);
    
    boolean loggedIn = principal != null;
    model.addAttribute("loggedIn", loggedIn);

    // Songs in user's collection
    if (loggedIn) {
        Set<Long> collectedSongIds = collectionEntryRepository
                .findByUser_Username(principal.getName())
                .stream()
                .map(entry -> entry.getSong().getId())
                .collect(Collectors.toSet());

        model.addAttribute("collectedSongIds", collectedSongIds);
    } else {
        model.addAttribute("collectedSongIds", Collections.emptySet());
    }

    return "songlist";
}

    @GetMapping("/songs/add")
    public String addSong(Model model) {
        model.addAttribute("song", new Song());
        return "songform";
    }

    @GetMapping("/songs/edit/{id}")
    public String editSong(@PathVariable Long id, Model model) {
        Song song = songRepository.findById(id).orElseThrow();
        model.addAttribute("song", song);
        return "songform";
    }

    @PostMapping("songs/save")
    public String saveSong(@ModelAttribute Song song) {
        songRepository.save(song);
        return "redirect:/songs";
    }

}
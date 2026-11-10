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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fi.haagahelia.musicapp.domain.CollectionEntryRepository;
import fi.haagahelia.musicapp.domain.Song;
import fi.haagahelia.musicapp.domain.SongRepository;
import fi.haagahelia.musicapp.service.SongService;

// TO DO: implement SongForm DTO 
// Shows the shared catalog and handles song forms.
@Controller
@RequestMapping("/songs")
public class SongController {

    private final SongRepository songRepository;
    private final CollectionEntryRepository collectionEntryRepository;
    private final SongService songService;

    public SongController(SongRepository songRepository, CollectionEntryRepository collectionEntryRepository, SongService songService) {
        this.songRepository = songRepository;
        this.collectionEntryRepository = collectionEntryRepository;
        this.songService = songService;
    }

    @GetMapping
    public String showSongs(
            Principal principal,
            Model model,
            @RequestParam(name = "search", required = false) String search
    ) {

        // Songs from the shared catalog
        // Show all songs when no search text is given.
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
            // Give the page song IDs so it can mark songs already collected.
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

    // Open the song form with an empty song.
    @GetMapping("/add")
    public String addSong(Model model) {
        model.addAttribute("song", new Song());
        return "songform";
    }

    // Use the same form with the existing song details.
    @GetMapping("/edit/{id}")
    public String editSong(@PathVariable Long id, Model model) {
        Song song = songRepository.findById(id).orElseThrow();
        model.addAttribute("song", song);
        return "songform";
    }

    // Pass the submitted changes to the service, then return to the catalog.
    @PostMapping("/update")
    public String updateSong(@ModelAttribute Song song) {
        songService.updateSong(song.getId(), song);
        return "redirect:/songs";
    }
    
    // Create a new catalog song from the submitted form.
    @PostMapping("/save")
    public String saveSong(@ModelAttribute Song song) {
        songService.createSong(song);
        return "redirect:/songs";
    }

    // Let the service remove the song and its collection entries.
    @PostMapping("/delete/{id}")
    public String deleteSong(@PathVariable Long id) {

        songService.deleteSong(id);

        return "redirect:/songs";
    }

}
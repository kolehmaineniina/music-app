package fi.haagahelia.musicapp.web;

import java.security.Principal;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import fi.haagahelia.musicapp.domain.AppUser;
import fi.haagahelia.musicapp.domain.AppUserRepository;
import fi.haagahelia.musicapp.domain.CollectionEntry;
import fi.haagahelia.musicapp.domain.CollectionEntryRepository;
import fi.haagahelia.musicapp.domain.Song;
import fi.haagahelia.musicapp.domain.SongRepository;


@Controller
public class CollectionController {

    private final SongRepository songRepository;
    private final CollectionEntryRepository collectionEntryRepository;
    private final AppUserRepository appUserRepository;


    public CollectionController(CollectionEntryRepository collectionEntryRepository, AppUserRepository appUserRepository, SongRepository songRepository) {
        this.collectionEntryRepository = collectionEntryRepository;
        this.appUserRepository = appUserRepository;
        this.songRepository = songRepository;
    }

    @GetMapping("/collection")
    public String showCollection(Model model, Principal principal) {
        String username = principal.getName();
        
        model.addAttribute(
            "collection",
            collectionEntryRepository.findByUser_Username(username)
        );

        return "collection";
    }

    @PostMapping("/collection/add/{id}")
    public String addToCollection(@PathVariable Long id, Principal principal) {
        AppUser user = appUserRepository.findByUsername(principal.getName());
        Song song = songRepository.findById(id).orElseThrow();
        
        if (!collectionEntryRepository.existsByUserAndSong(user, song)) {
            collectionEntryRepository.save(new CollectionEntry(user, song));
        }  

        return "redirect:/collection";
    }
    
}
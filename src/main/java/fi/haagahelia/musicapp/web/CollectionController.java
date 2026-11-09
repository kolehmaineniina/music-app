package fi.haagahelia.musicapp.web;

import org.springframework.security.access.AccessDeniedException;
import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import fi.haagahelia.musicapp.domain.AppUser;
import fi.haagahelia.musicapp.domain.AppUserRepository;
import fi.haagahelia.musicapp.domain.CollectionEntry;
import fi.haagahelia.musicapp.domain.CollectionEntryRepository;
import fi.haagahelia.musicapp.domain.Song;
import fi.haagahelia.musicapp.domain.SongRepository;

// Consider ControllerService and move business logic there  
@Controller
@RequestMapping("/collection")
public class CollectionController {

    private final SongRepository songRepository;
    private final CollectionEntryRepository collectionEntryRepository;
    private final AppUserRepository appUserRepository;


    public CollectionController(CollectionEntryRepository collectionEntryRepository, AppUserRepository appUserRepository, SongRepository songRepository) {
        this.collectionEntryRepository = collectionEntryRepository;
        this.appUserRepository = appUserRepository;
        this.songRepository = songRepository;
    }

    @GetMapping
    public String showCollection(Model model, Principal principal) {
        String username = principal.getName();
        
        model.addAttribute(
            "collection",
            collectionEntryRepository.findByUser_Username(username)
        );

        return "collection";
    }

    @PostMapping("/add/{id}")
    public String addToCollection(@PathVariable Long id, Principal principal) {
        AppUser user = appUserRepository.findByUsername(principal.getName());
        Song song = songRepository.findById(id).orElseThrow();
        
        if (!collectionEntryRepository.existsByUserAndSong(user, song)) {
            collectionEntryRepository.save(new CollectionEntry(user, song));
        }  

        return "redirect:/collection";
    }

    @PostMapping("/remove/{id}")
    public String removeFromCollection(@PathVariable Long id, Principal principal) {
        CollectionEntry entry = collectionEntryRepository.findById(id).orElseThrow(()
            -> new IllegalArgumentException("Entry not found"));

        if (!entry.getUser().getUsername().equals(principal.getName())) {
            throw new AccessDeniedException("Cannot remove entry from another user's collection");
        }

        collectionEntryRepository.delete(entry);
        
        return "redirect:/collection";
    }
    
}
package fi.haagahelia.musicapp.web;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import fi.haagahelia.musicapp.domain.CollectionEntryRepository;

@Controller
public class CollectionController {

    private final CollectionEntryRepository collectionEntryRepository;

    public CollectionController(CollectionEntryRepository collectionEntryRepository) {
        this.collectionEntryRepository = collectionEntryRepository;
    }

    @GetMapping("/collection")
    public String showCollection(Model model, Authentication authentication) {
        String username = authentication.getName();
        
        model.addAttribute(
            "collection",
            collectionEntryRepository.findByUser_Username(username)
        );

        return "collection";
    }
}
package fi.haagahelia.musicapp.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import fi.haagahelia.musicapp.domain.Song;
import fi.haagahelia.musicapp.domain.SongRepository;

@Controller
public class SongController {

    private final SongRepository songRepository;

    public SongController(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    @GetMapping("/songs")
    public String listSongs(Model model) {
        model.addAttribute("songs", songRepository.findAll()); 
        return "songlist";
    }

    @GetMapping("/add")
    public String addBooks(Model model) {
        model.addAttribute("song", new Song());
        return "songform";
    }

    @GetMapping("/edit/{id}")
    public String editSong(@PathVariable Long id, Model model) {
        Song song = songRepository.findById(id).orElseThrow();
        model.addAttribute("song", song);
        return "songform";
    }

    @PostMapping("/save")
    public String saveSong(@ModelAttribute Song song) {
        songRepository.save(song);
        return "redirect:/songs";
    }

}
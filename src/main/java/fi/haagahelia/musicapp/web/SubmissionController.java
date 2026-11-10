package fi.haagahelia.musicapp.web;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import fi.haagahelia.musicapp.domain.SongSubmission;
import fi.haagahelia.musicapp.domain.SongSubmissionRepository;
import fi.haagahelia.musicapp.domain.SubmissionForm;
import fi.haagahelia.musicapp.domain.SubmissionStatus;
import fi.haagahelia.musicapp.service.SubmissionService;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

// TO DO: Concurrency control: enable locking with JPA @Version?

@Controller 
public class SubmissionController {
    private final SubmissionService submissionService;
    private final SongSubmissionRepository submissionRepository;
    
    public SubmissionController(SubmissionService submissionService, SongSubmissionRepository submissionRepository) {
        this.submissionService = submissionService;
        this.submissionRepository = submissionRepository;
    }

    @GetMapping("/submissions/new")
    public String showSubmissionForm(Model model) {
        model.addAttribute("submissionForm", new SubmissionForm());
        return "submissionform";
    }

    @PostMapping("/submissions")
    public String createSubmission(
        @Valid @ModelAttribute("submissionForm") SubmissionForm submissionForm,
        BindingResult bindingResult,
        Principal principal) {
        
            
        if (bindingResult.hasErrors()) {
            return "submissionform";
        }

        submissionService.createSubmission(submissionForm, principal.getName());
        
        return "redirect:/songs";
    }
    
    @GetMapping("/admin/submissions")
    public String showSubmissions(Model model) {
        model.addAttribute("submissions", submissionRepository.findByStatus(SubmissionStatus.PENDING));
        return "adminsubmissions";
    }

    @GetMapping("/admin/submissions/review/{id}")
    public String reviewSubmission(@PathVariable Long id, Model model) {

        SongSubmission submission = submissionRepository.findById(id).orElseThrow(() ->
            new IllegalArgumentException("Submission not found: " + id));

        if (submission.getStatus() != SubmissionStatus.PENDING) {
            throw new IllegalStateException("Only pending submissions can be reviewed");
        }

        SubmissionForm form = new SubmissionForm();
        form.setTitle(submission.getTitle());
        form.setArtist(submission.getArtist());
        form.setReleaseYear(submission.getReleaseYear());

        model.addAttribute("submissionForm", form);
        model.addAttribute("submissionId", id);
        model.addAttribute("reviewMode", true);

        return "submissionform";
    }

    @PostMapping("/admin/submissions/reject/{id}")
    public String rejectSubmission(@PathVariable Long id) {
        submissionService.rejectSubmission(id);
        return "redirect:/admin/submissions?rejected";
    }  
    
    @PostMapping("/admin/submissions/approve/{id}")
    public String approveSubmission(
            @PathVariable Long id,
            @Valid @ModelAttribute("submissionForm") SubmissionForm form,
            BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("submissionId", id);
            model.addAttribute("reviewMode", true);

            return "submissionform";
        }

        submissionService.approveSubmission(id, form);

        return "redirect:/admin/submissions?approved";
    }
}

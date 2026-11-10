package fi.haagahelia.musicapp.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fi.haagahelia.musicapp.domain.AppUser;
import fi.haagahelia.musicapp.domain.AppUserRepository;
import fi.haagahelia.musicapp.domain.Song;
import fi.haagahelia.musicapp.domain.SongSubmission;
import fi.haagahelia.musicapp.domain.SongSubmissionRepository;
import fi.haagahelia.musicapp.domain.SubmissionForm;
import fi.haagahelia.musicapp.domain.SubmissionStatus;

// TO DO: Concurrent transaction control wiht @Version?

// Handles song submissions and review decisions.
@Service 
public class SubmissionService {
    private final SongSubmissionRepository songSubmissionRepository;
    private final AppUserRepository appUserRepository;
    private final SongService songService;

    public SubmissionService(
        SongSubmissionRepository songSubmissionRepository,
        AppUserRepository appUserRepository,
        SongService songService) {
            this.songSubmissionRepository = songSubmissionRepository;
            this.appUserRepository = appUserRepository;
            this.songService = songService;
        }

    @Transactional 
    // Link the submitted song details to the account that sent them.
    public void createSubmission(SubmissionForm submissionForm, String username) {
        AppUser user = appUserRepository.findByUsername(username);

        if (user == null) {
            throw new IllegalArgumentException("User not found: " + username);
        }

        SongSubmission submission = new SongSubmission(
                submissionForm.getTitle(),
                submissionForm.getArtist(),
                submissionForm.getReleaseYear(),
                user 
        );

        songSubmissionRepository.save(submission);
    }

    @Transactional
    // Only a submission still waiting for review can be rejected.
    public void rejectSubmission(Long id) {

        SongSubmission submission = songSubmissionRepository.findById(id).orElseThrow(() ->
            new IllegalArgumentException("Submission not found: " + id));

        if (submission.getStatus() != SubmissionStatus.PENDING) {
            throw new IllegalStateException("Only pending submissions can be rejected");
        }

        // The status change is saved when the transaction completes.
        submission.setStatus(SubmissionStatus.REJECTED);
    }

    @Transactional
    // Save the review changes, song and collection entry together, or undo them on failure.
    public void approveSubmission(Long id, SubmissionForm form) {

        SongSubmission submission = songSubmissionRepository.findById(id).orElseThrow(() ->
            new IllegalArgumentException("Submission not found: " + id));

        if (submission.getStatus() != SubmissionStatus.PENDING) {
            throw new IllegalStateException("Only pending submissions can be approved");
        }

        // Retrieve values from form fields (with admin corrections)
        submission.setTitle(form.getTitle());
        submission.setArtist(form.getArtist());
        submission.setReleaseYear(form.getReleaseYear());

        Song song = new Song();
        song.setTitle(submission.getTitle());
        song.setArtist(submission.getArtist());
        song.setReleaseYear(submission.getReleaseYear());

        // Add the approved song to the catalog and the sender collection.
        songService.createSongAndAddToCollection(
                song,
                submission.getSubmittedBy()
        );

        // Mark the submission approved after creating the song and collection entry.
        submission.setStatus(SubmissionStatus.APPROVED);
    }
}

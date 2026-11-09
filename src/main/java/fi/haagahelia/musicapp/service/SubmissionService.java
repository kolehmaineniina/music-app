package fi.haagahelia.musicapp.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fi.haagahelia.musicapp.domain.AppUser;
import fi.haagahelia.musicapp.domain.AppUserRepository;
import fi.haagahelia.musicapp.domain.SongSubmission;
import fi.haagahelia.musicapp.domain.SongSubmissionRepository;
import fi.haagahelia.musicapp.domain.SubmissionForm;
import fi.haagahelia.musicapp.domain.SubmissionStatus;

@Service 
public class SubmissionService {
    private final SongSubmissionRepository songSubmissionRepository;
    private final AppUserRepository appUserRepository;

    public SubmissionService(
        SongSubmissionRepository songSubmissionRepository,
        AppUserRepository appUserRepository) {
            this.songSubmissionRepository = songSubmissionRepository;
            this.appUserRepository = appUserRepository;
        }

    @Transactional 
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
    public void rejectSubmission(Long id) {

        SongSubmission submission = songSubmissionRepository.findById(id).orElseThrow(() ->
            new IllegalArgumentException("Submission not found: " + id));

        if (submission.getStatus() != SubmissionStatus.PENDING) {
            throw new IllegalStateException("Only pending submissions can be rejected");
        }

        submission.setStatus(SubmissionStatus.REJECTED);
    }
}

package fi.haagahelia.musicapp.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

// Provides methods to save, find and delete song submissions.
public interface SongSubmissionRepository extends CrudRepository <SongSubmission, Long>{
    // Find submissions with the given review status.
    List<SongSubmission> findByStatus(SubmissionStatus status);
    // Find submissions sent by this user.
    List<SongSubmission> findBySubmittedBy(AppUser user);
}

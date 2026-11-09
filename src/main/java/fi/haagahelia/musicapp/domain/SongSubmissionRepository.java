package fi.haagahelia.musicapp.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface SongSubmissionRepository extends CrudRepository <SongSubmission, Long>{
    List<SongSubmission> findByStatus(SubmissionStatus status);
    List<SongSubmission> findBySubmittedBy(AppUser user);
}

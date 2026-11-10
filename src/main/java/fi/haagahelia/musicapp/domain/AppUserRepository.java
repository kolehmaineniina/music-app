package fi.haagahelia.musicapp.domain;

import org.springframework.data.repository.CrudRepository;


// Provides methods to save, find and delete user accounts.
public interface AppUserRepository extends CrudRepository <AppUser, Long> {
        // Find an account by its username.
        AppUser findByUsername(String username);
        // Find an account by its email address.
        AppUser findByEmail(String email);
}

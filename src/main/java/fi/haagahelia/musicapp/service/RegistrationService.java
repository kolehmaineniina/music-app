
package fi.haagahelia.musicapp.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import fi.haagahelia.musicapp.domain.AppUser;
import fi.haagahelia.musicapp.domain.AppUserRepository;
import fi.haagahelia.musicapp.domain.SignUpForm;

//TO DO: Password reset
// Checks registration details and saves new accounts.
@Service 
public class RegistrationService {
    private final AppUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public RegistrationService(
        AppUserRepository userRepository,
        PasswordEncoder passwordEncoder) {
        
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean usernameExists(String username) {
        return userRepository.findByUsername(username) != null;
    }

    public boolean emailExists(String email) {
        return userRepository.findByEmail(email) != null;
    }

    // Check for duplicate accounts and matching passwords before saving.
    public void register(SignUpForm form) {


        if (usernameExists(form.getUsername())) {
            throw new RegistrationException(
                "username",
                "Username is already taken"
            );
        }

        if (emailExists(form.getEmail())) {
            throw new RegistrationException(
                "email",
                "Email address is already registered"
            );
        }

        if (!form.getPassword().equals(form.getPasswordConfirmed())) {
            throw new RegistrationException(
                "passwordConfirmed",
                "Passwords do not match"
            );
        }

        AppUser user = new AppUser();

        user.setUsername(form.getUsername());
        user.setEmail(form.getEmail());
        // Store a password hash rather than the original password.
        user.setPasswordHash(passwordEncoder.encode(form.getPassword()));
        // New accounts receive the regular user role.
        user.setRole("USER");

        userRepository.save(user);
}

}


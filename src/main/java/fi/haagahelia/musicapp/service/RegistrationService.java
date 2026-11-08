
package fi.haagahelia.musicapp.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import fi.haagahelia.musicapp.domain.AppUser;
import fi.haagahelia.musicapp.domain.AppUserRepository;
import fi.haagahelia.musicapp.domain.SignUpForm;

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

    public void register(SignUpForm form) {

        AppUser user = new AppUser();

        user.setUsername(form.getUsername());
        user.setEmail(form.getEmail());
        user.setPasswordHash(passwordEncoder.encode(form.getPassword()));
        user.setRole("USER");

        userRepository.save(user);
}

}


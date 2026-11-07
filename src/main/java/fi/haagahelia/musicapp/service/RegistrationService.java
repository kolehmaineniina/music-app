
package fi.haagahelia.musicapp.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import fi.haagahelia.musicapp.domain.AppUserRepository;

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
}


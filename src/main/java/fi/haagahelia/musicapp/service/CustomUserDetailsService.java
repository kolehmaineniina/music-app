package fi.haagahelia.musicapp.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import fi.haagahelia.musicapp.domain.AppUser;
import fi.haagahelia.musicapp.domain.AppUserRepository;

// Loads account details for Spring Security during sign-in.
@Service 
public class CustomUserDetailsService implements UserDetailsService {
    private final AppUserRepository appUserRepository;

    public CustomUserDetailsService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override 
    public UserDetails loadUserByUsername(String username) 
        throws UsernameNotFoundException {
            AppUser appUser = appUserRepository.findByUsername(username);

            // Stop sign-in if the username does not belong to an account.
            if (appUser == null) {
                throw new UsernameNotFoundException("User " + username + "not found");
            }

            // Pass the stored password hash and role to Spring Security.
            UserDetails user = User.withUsername(appUser.getUsername())
                                .password(appUser.getPasswordHash())
                                .roles(appUser.getRole())
                                .build();

            return user;

        }
}

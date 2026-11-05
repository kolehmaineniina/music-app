package fi.haagahelia.musicapp.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import fi.haagahelia.musicapp.domain.AppUser;
import fi.haagahelia.musicapp.domain.AppUserRepository;

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

            if (appUser == null) {
                throw new UsernameNotFoundException("User " + username + "not found");
            }

            UserDetails user = User.withUsername(appUser.getUsername())
                                .password(appUser.getPasswordHash())
                                .roles(appUser.getRole())
                                .build();

            return user;

        }
}

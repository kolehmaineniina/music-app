package fi.haagahelia.musicapp.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

// Define password hashing, page access and sign-in settings.
@Configuration 
public class WebSecurityConfig {

    @Bean 
    // Use BCrypt to hash passwords and check them during sign-in.
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    // These pages and CSS files can be opened without signing in.
                    "/songs",
                    "/login",
                    "/register",
                    "/css/**"
                ).permitAll()
                // Other song routes and admin pages require the ADMIN role.
                .requestMatchers("/songs/**").hasRole("ADMIN")
                .requestMatchers("/admin/**").hasRole("ADMIN")
                // Submissions and all remaining routes require sign-in.
                .requestMatchers("/submissions/**").authenticated()
                .anyRequest().authenticated()
            )
            // Skip the form security token check for H2 console requests only.
            .csrf(csrf -> csrf
            .ignoringRequestMatchers("/h2-console/**")
            )
            .headers(headers -> headers
                // Allow pages from this same site to appear inside frames.
                .frameOptions(frameOptions -> frameOptions.sameOrigin())
            )
            .formLogin(form -> form
                .loginPage("/login")
                // Use the catalog after sign-in. Must be true in the current implementation since there is nothing at root endpoint ("/").
                .defaultSuccessUrl("/songs", true)
                .permitAll()
            )
            // Allow access to the logout route.
            .logout(logout -> logout
                .permitAll()
            );

        return http.build();
    }
}

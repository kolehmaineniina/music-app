package fi.haagahelia.musicapp.domain;

import jakarta.validation.constraints.*;

public class SignUpForm {

    @NotBlank(message = "Username required")
    @Size(
        min = 5, max = 30,
        message = "Username must be between 5 and 30 characters")
    @Pattern(
        regexp = "^[a-zA-Z][a-zA-Z0-9_]*$",
        message = "Username must start with a letter and contain only letters, numbers or underscores")
    private String username;

    @NotBlank(message = "Email required")
    @Email(message = "Please enter a valid email address")
    private String email;

    @NotBlank(message = "Password required")
    @Size(min = 8, max = 72,
        message = "Password must be between 8 and 72 characters")
    private String password;

    @NotBlank
    private String passwordConfirmed;

    public SignUpForm() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirmed() {
        return passwordConfirmed;
    }

    public void setPasswordConfirmed(String passwordConfirmed) {
        this.passwordConfirmed = passwordConfirmed;
    }


}
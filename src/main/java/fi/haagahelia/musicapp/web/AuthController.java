package fi.haagahelia.musicapp.web;

import fi.haagahelia.musicapp.service.RegistrationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import fi.haagahelia.musicapp.domain.SignUpForm;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;

@Controller 
public class AuthController {

    private final RegistrationService registrationService;

    AuthController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("signupform", new SignUpForm());
        return "signup";
    }

    @PostMapping("/register")
    public String register(
            @Valid @ModelAttribute("signupform") SignUpForm signUpForm,
            BindingResult bindingResult) {

        // Annotation validation
        if (bindingResult.hasErrors()) {
            return "signup";
        }

        // Manual validation
        if (!signUpForm.getPassword().equals(signUpForm.getPasswordConfirmed())) {
            bindingResult.rejectValue(
                "passwordConfirmed",
                "password.mismatch",
                "Passwords do not match"
            );
        }

        if (registrationService.usernameExists(signUpForm.getUsername())) {
            bindingResult.rejectValue(
                "username",
                "username.exists",
                "Username is already taken"
            );
        }

        if (registrationService.emailExists(signUpForm.getEmail())) {
            bindingResult.rejectValue(
                "email",
                "email.exists",
                "Email address is already registered"
            );
        }

        // Manual validation check; if hasErrors() == true -> return to signup
        if (bindingResult.hasErrors()) {
            return "signup";
        }

        registrationService.register(signUpForm);

        return "redirect:/login";
    }

    
    

}

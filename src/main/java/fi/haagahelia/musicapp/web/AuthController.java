package fi.haagahelia.musicapp.web;

import fi.haagahelia.musicapp.service.RegistrationException;
import fi.haagahelia.musicapp.service.RegistrationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import fi.haagahelia.musicapp.domain.SignUpForm;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;

// Shows the sign-in and registration pages and handles registration forms.
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

    // Give the registration page an empty form to fill in.
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("signupform", new SignUpForm());
        return "signup";
    }

    @PostMapping("/register")
    public String register(
            @Valid @ModelAttribute("signupform") SignUpForm signUpForm,
            BindingResult bindingResult) {

        // Show the form again if any field fails its validation rules.
        if (bindingResult.hasErrors()) {
            return "signup";
        }

        try {
            registrationService.register(signUpForm);
        } catch (RegistrationException exception) {
            // Show the registration problem beside the field it belongs to.
            bindingResult.rejectValue(
                exception.getField(),
                "registration.error",
                exception.getMessage()
            );

            return "signup";
        }

        // Send the browser to sign-in after successful registration.
        return "redirect:/login?registered";
    }

}

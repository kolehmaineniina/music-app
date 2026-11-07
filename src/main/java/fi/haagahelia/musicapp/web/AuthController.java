package fi.haagahelia.musicapp.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import fi.haagahelia.musicapp.domain.SignUpForm;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller 
public class AuthController {


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
    public String register (
        @Valid @ModelAttribute("signupform") SignUpForm signUpForm,
        BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "signup";
        }
        
        if (!signUpForm.getPassword().equals(signUpForm.getPasswordConfirmed())) {
            bindingResult.rejectValue(
                "passwordConfirmed",
                "password.mismatch",
                "Passwords do not match"
            );
            return "signup";
        }
        
        return "redirect:/register";
    }
    

}

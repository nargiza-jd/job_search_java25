package kg.attractor.job_search_java25.controller;

import kg.attractor.job_search_java25.dto.UserRegistrationDto;
import kg.attractor.job_search_java25.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @GetMapping("/register")
    public String showRegistrationForm(@ModelAttribute("userRegistrationDto") UserRegistrationDto userRegistrationDto,
                                       @RequestParam(value = "registrationError", required = false) String registrationError,
                                       Model model) {
        if (userRegistrationDto == null || userRegistrationDto.getEmail() == null) {
            model.addAttribute("userRegistrationDto", new UserRegistrationDto());
        }

        if (registrationError != null) {
            model.addAttribute("errorMessage", registrationError);
        }
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("userRegistrationDto") @Valid UserRegistrationDto registrationDto,
                               RedirectAttributes redirectAttributes) {
        try {
            userService.createUser(registrationDto);
            return "redirect:/login?registered";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("userRegistrationDto", registrationDto);
            redirectAttributes.addAttribute("registrationError", e.getMessage());
            return "redirect:/register";
        }
    }

    @GetMapping("/login")
    public String showLoginForm(@RequestParam(value = "error", required = false) String error,
                                @RequestParam(value = "logout", required = false) String logout,
                                @RequestParam(value = "registered", required = false) String registered,
                                Model model) {
        if (error != null) {
            model.addAttribute("loginError", true);
            model.addAttribute("errorMessage", "Неверный email или пароль.");
        }
        if (logout != null) {
            model.addAttribute("logoutMessage", true);
        }
        if (registered != null) {
            model.addAttribute("registrationSuccess", true);
            model.addAttribute("successMessage", "Регистрация прошла успешно! Теперь вы можете войти.");
        }
        return "login";
    }

    @GetMapping("/dashboard")
    public String showDashboard(Model model, Principal principal) {
        if (principal != null) {
            model.addAttribute("username", principal.getName());
        } else {
            model.addAttribute("username", "Гость");
        }
        return "dashboard";
    }

    @GetMapping("/search")
    public String searchPage() {
        return "search";
    }

    @GetMapping("/greet")
    public String greetPage() {
        return "greet";
    }
}
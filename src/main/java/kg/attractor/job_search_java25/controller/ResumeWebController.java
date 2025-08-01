package kg.attractor.job_search_java25.controller;

import jakarta.validation.Valid;
import kg.attractor.job_search_java25.dto.ResumeCreateDto;
import kg.attractor.job_search_java25.dto.UserDto;
import kg.attractor.job_search_java25.service.UserService;
import kg.attractor.job_search_java25.service.ResumeService;
import kg.attractor.job_search_java25.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Optional;


@Controller
@RequestMapping("/resumes")
@RequiredArgsConstructor
public class ResumeWebController {

    private final ResumeService resumeService;
    private final UserService userService;
    private final CategoryService categoryService;

    private void addCategoriesToModel(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
    }

    @GetMapping
    public String showAllResumes(Model model, Principal principal) {
        model.addAttribute("resumes", resumeService.getAllResumes());

        if (principal != null) {
            model.addAttribute("username", principal.getName());
        } else {
            model.addAttribute("username", "Гость");
        }
        return "resumes";
    }

    @GetMapping("/my")
    public String showMyResumes(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        String userEmail = principal.getName();
        Optional<UserDto> currentUserOptional = userService.findByEmail(userEmail);

        UserDto currentUser = currentUserOptional.orElseThrow(() -> new RuntimeException("User not found for email: " + userEmail));

        model.addAttribute("resumes", resumeService.getResumesByApplicantId(currentUser.getId()));
        return "my-resumes";
    }

    @GetMapping("/new")
    public String showResumeCreationForm(Model model) {
        model.addAttribute("resumeCreateDto", new ResumeCreateDto());
        addCategoriesToModel(model);
        return "resume-create-form";
    }

    @PostMapping("/new")
    public String createResume(@ModelAttribute("resumeCreateDto") @Valid ResumeCreateDto dto,
                               BindingResult bindingResult,
                               Model model,
                               Principal principal,
                               RedirectAttributes redirectAttributes) {
        if (principal == null) {
            return "redirect:/login";
        }

        String userEmail = principal.getName();
        Optional<UserDto> currentUserOptional = userService.findByEmail(userEmail);

        UserDto currentUser = currentUserOptional.orElseThrow(() -> new RuntimeException("User not found for email: " + userEmail));

        dto.setApplicantId(currentUser.getId());

        if (bindingResult.hasErrors()) {
            addCategoriesToModel(model);
            return "resume-create-form";
        }

        try {
            resumeService.createResume(dto);

            redirectAttributes.addFlashAttribute("successMessage", "Резюме успешно создано!");
            return "redirect:/resumes/my";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Ошибка при создании резюме: " + e.getMessage());
            addCategoriesToModel(model);
            e.printStackTrace();
            return "resume-create-form";
        }
    }

    @GetMapping("/{id}")
    public String viewResumeDetails(@PathVariable int id, Model model, Principal principal) {
        return resumeService.getResumeById(id)
                .map(resume -> {
                    model.addAttribute("resume", resume);
                    if (principal != null) {
                        model.addAttribute("username", principal.getName());
                    } else {
                        model.addAttribute("username", "Гость");
                    }
                    return "resume-details";
                })
                .orElseGet(() -> {
                    return "error/404";
                });
    }
}

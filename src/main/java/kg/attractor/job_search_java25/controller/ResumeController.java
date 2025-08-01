package kg.attractor.job_search_java25.controller;

import jakarta.validation.Valid;
import kg.attractor.job_search_java25.dto.ResumeCreateDto;
import kg.attractor.job_search_java25.service.ResumeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/resumes")
public class ResumeController {

    private final ResumeService resumeService;

    public ResumeController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    @GetMapping("/resumes/add")
    public String showAddResumeForm(Model model) {
        model.addAttribute("resumeCreateDto", new ResumeCreateDto());
        return "resume-create-form";
    }

    @PostMapping("/add")
    public String addResume(@ModelAttribute("resumeDto") @Valid ResumeCreateDto resumeDto,
                            BindingResult bindingResult,
                            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "resume-create-form";
        }
        resumeService.createResume(resumeDto);

        redirectAttributes.addFlashAttribute("successMessage", "Резюме успешно создано!");
        return "redirect:/resumes";
    }
}

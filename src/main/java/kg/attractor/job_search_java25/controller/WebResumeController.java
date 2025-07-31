package kg.attractor.job_search_java25.controller;

import kg.attractor.job_search_java25.model.Resume;
import kg.attractor.job_search_java25.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.security.Principal;

@Controller
@RequestMapping("/resumes")
@RequiredArgsConstructor
public class WebResumeController {

    private final ResumeService resumeService;

    @GetMapping
    public String showAllResumes(Model model, Principal principal) {
        List<Resume> resumes = resumeService.getAllResumes();
        model.addAttribute("resumes", resumes);

        if (principal != null) {
            model.addAttribute("username", principal.getName());
        }

        return "resumes";
    }

    @GetMapping("/{id}")
    public String showResumeDetails(@PathVariable int id, Model model, Principal principal) {
        return resumeService.getResumeById(id).map(resume -> {
            model.addAttribute("resume", resume);
            if (principal != null) {
                model.addAttribute("username", principal.getName());
            }
            return "resume-details";
        }).orElse("error/404");
    }
}
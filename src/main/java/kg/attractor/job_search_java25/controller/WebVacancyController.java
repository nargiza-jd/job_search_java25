package kg.attractor.job_search_java25.controller;

import kg.attractor.job_search_java25.model.Vacancy;
import kg.attractor.job_search_java25.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.security.Principal;

@Controller
@RequestMapping("/vacancies")
@RequiredArgsConstructor
public class WebVacancyController {

    private final VacancyService vacancyService;

    @GetMapping
    public String showAllVacancies(Model model, Principal principal) {
        List<Vacancy> vacancies = vacancyService.getAllActiveVacancies();
        model.addAttribute("vacancies", vacancies);

        if (principal != null) {
            model.addAttribute("username", principal.getName());
        }

        return "vacancies";
    }

    @GetMapping("/{id}")
    public String showVacancyDetails(@PathVariable int id, Model model, Principal principal) {
        return vacancyService.getVacancyById(id).map(vacancy -> {
            model.addAttribute("vacancy", vacancy);
            if (principal != null) {
                model.addAttribute("username", principal.getName());
            }
            return "vacancy-details";
        }).orElse("error/404");
    }
}
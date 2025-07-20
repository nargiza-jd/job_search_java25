package kg.attractor.job_search_java25.service;

import kg.attractor.job_search_java25.model.RespondedApplicant;
import kg.attractor.job_search_java25.model.User;
import kg.attractor.job_search_java25.model.Vacancy;

import java.util.List;

public interface RespondedApplicantService {
    RespondedApplicant createResponse(int resumeId, int vacancyId);
    List<RespondedApplicant> getAll();
    List<RespondedApplicant> getByVacancyId(int vacancyId);
    List<RespondedApplicant> getByResumeId(int resumeId);
    List<Vacancy> getVacanciesByIds(List<Integer> ids);

    List<Integer> getVacancyIdsByApplicantId(int applicantId);

    List<User> getApplicantsByVacancyId(int vacancyId);
}
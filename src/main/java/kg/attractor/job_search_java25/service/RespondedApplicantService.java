package kg.attractor.job_search_java25.service;

import kg.attractor.job_search_java25.model.RespondedApplicant;

import java.util.List;

public interface RespondedApplicantService {
    RespondedApplicant createResponse(int resumeId, int vacancyId);
    List<RespondedApplicant> getAll();
    List<RespondedApplicant> getByVacancyId(int vacancyId);
    List<RespondedApplicant> getByResumeId(int resumeId);
}
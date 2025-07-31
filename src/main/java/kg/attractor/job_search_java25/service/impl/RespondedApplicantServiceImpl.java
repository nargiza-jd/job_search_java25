package kg.attractor.job_search_java25.service.impl;

import kg.attractor.job_search_java25.dao.RespondedApplicantDao;
import kg.attractor.job_search_java25.exceptions.NotFoundException;
import kg.attractor.job_search_java25.model.RespondedApplicant;
import kg.attractor.job_search_java25.model.Resume;
import kg.attractor.job_search_java25.model.User;
import kg.attractor.job_search_java25.model.Vacancy;
import kg.attractor.job_search_java25.service.RespondedApplicantService;
import kg.attractor.job_search_java25.service.ResumeService;
import kg.attractor.job_search_java25.service.VacancyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RespondedApplicantServiceImpl implements RespondedApplicantService {

    private final RespondedApplicantDao respondedApplicantDao;
    private final VacancyService vacancyService;
    private final ResumeService resumeService;

    @Override
    public RespondedApplicant createResponse(int resumeId, int vacancyId) {
        Resume resume = resumeService.getResumeById(resumeId)
                .orElseThrow(() -> new NotFoundException("Резюме с ID " + resumeId + " не найдено."));

        vacancyService.getVacancyById(vacancyId)
                .orElseThrow(() -> new NotFoundException("Вакансия с ID " + vacancyId + " не найдена."));

        int applicantId = resume.getApplicantId();

        return respondedApplicantDao.save(resumeId, vacancyId, applicantId);
    }

    @Override
    public List<RespondedApplicant> getAll() {
        return respondedApplicantDao.findAll();
    }

    @Override
    public List<RespondedApplicant> getByVacancyId(int vacancyId) {
        return respondedApplicantDao.findByVacancyId(vacancyId);
    }

    @Override
    public List<RespondedApplicant> getByResumeId(int resumeId) {
        return respondedApplicantDao.findByResumeId(resumeId);
    }

    @Override
    public List<Integer> getVacancyIdsByApplicantId(int applicantId) {
        return respondedApplicantDao.getVacancyIdsByApplicantId(applicantId);
    }

    @Override
    public List<Vacancy> getVacanciesByIds(List<Integer> ids) {
        return vacancyService.getVacanciesByIds(ids);
    }

    @Override
    public List<User> getApplicantsByVacancyId(int vacancyId) {
        return respondedApplicantDao.getApplicantsByVacancyId(vacancyId);
    }
}
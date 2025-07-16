package kg.attractor.job_search_java25.service.impl;

import jakarta.annotation.PostConstruct;
import kg.attractor.job_search_java25.model.RespondedApplicant;
import kg.attractor.job_search_java25.service.RespondedApplicantService;
import kg.attractor.job_search_java25.util.FileUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class RespondedApplicantServiceImpl implements RespondedApplicantService {

    private final FileUtil fileUtil;
    private List<RespondedApplicant> responses;

    public RespondedApplicantServiceImpl(FileUtil fileUtil) {
        this.fileUtil = fileUtil;
    }

    @PostConstruct
    public void init() {
        responses = new CopyOnWriteArrayList<>(fileUtil.loadRespondedApplicants());
    }

    @Override
    public RespondedApplicant createResponse(int resumeId, int vacancyId) {
        RespondedApplicant ra = new RespondedApplicant();
        ra.setId(fileUtil.generateId(responses));
        ra.setResumeId(resumeId);
        ra.setVacancyId(vacancyId);
        ra.setConfirmation(false);
        responses.add(ra);
        fileUtil.saveRespondedApplicants(responses);
        return ra;
    }

    @Override
    public List<RespondedApplicant> getAll() {
        return responses;
    }

    @Override
    public List<RespondedApplicant> getByVacancyId(int vacancyId) {
        return responses.stream()
                .filter(r -> r.getVacancyId() == vacancyId)
                .toList();
    }

    @Override
    public List<RespondedApplicant> getByResumeId(int resumeId) {
        return responses.stream()
                .filter(r -> r.getResumeId() == resumeId)
                .toList();
    }
}

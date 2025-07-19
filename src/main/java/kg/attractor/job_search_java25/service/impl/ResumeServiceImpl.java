package kg.attractor.job_search_java25.service.impl;

import kg.attractor.job_search_java25.dao.ResumeDao;
import kg.attractor.job_search_java25.dto.ResumeCreateDto;
import kg.attractor.job_search_java25.dto.ResumeUpdateDto;
import kg.attractor.job_search_java25.model.Resume;
import kg.attractor.job_search_java25.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {

    private final ResumeDao resumeDao;

    @Override
    public Resume createResume(ResumeCreateDto dto) {
        throw new UnsupportedOperationException("Удаление резюме пока не реализовано");
    }

    @Override
    public Optional<Resume> updateResume(int id, ResumeUpdateDto dto) {
        throw new UnsupportedOperationException("Удаление резюме пока не реализовано");
    }

    @Override
    public boolean deleteResume(int id) {
        throw new UnsupportedOperationException("Удаление резюме пока не реализовано");
    }

    @Override
    public List<Resume> getAllResumes() {
        return resumeDao.getAll();
    }

    @Override
    public Optional<Resume> getResumeById(int id) {
        return resumeDao.getById(id);
    }

    @Override
    public List<Resume> getResumesByCategory(int categoryId) {
        return resumeDao.getByCategory(categoryId);
    }

    @Override
    public List<Resume> getResumesByApplicantId(int applicantId) {
        return resumeDao.getByApplicantId(applicantId);
    }

    @Override
    public List<Resume> findByCategory(int categoryId) {
        return resumeDao.getByCategory(categoryId);
    }
}
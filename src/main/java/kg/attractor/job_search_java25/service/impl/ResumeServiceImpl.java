package kg.attractor.job_search_java25.service.impl;

import kg.attractor.job_search_java25.dao.ResumeDao;
import kg.attractor.job_search_java25.dto.ResumeCreateDto;
import kg.attractor.job_search_java25.dto.ResumeUpdateDto;
import kg.attractor.job_search_java25.model.Resume;
import kg.attractor.job_search_java25.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {

    private final ResumeDao resumeDao;

    @Override
    public Resume createResume(ResumeCreateDto dto) {
        Resume resume = new Resume();
        resume.setName(dto.getName());
        resume.setCategoryId(dto.getCategoryId());
        resume.setSalary(dto.getSalary());
        resume.setApplicantId(dto.getApplicantId());
        resume.setActive(true);
        resume.setCreatedDate(LocalDateTime.now());
        resume.setUpdateTime(LocalDateTime.now());

        return resumeDao.save(resume);
    }

    @Override
    public Optional<Resume> updateResume(int id, ResumeUpdateDto dto) {
        Optional<Resume> optional = resumeDao.getById(id);
        if (optional.isEmpty()) {
            return Optional.empty();
        }

        Resume resume = optional.get();
        resume.setName(dto.getName());
        resume.setCategoryId(dto.getCategoryId());
        resume.setSalary(dto.getSalary());
        resume.setActive(dto.isActive());

        resumeDao.update(id, resume);
        return resumeDao.getById(id);
    }

    @Override
    public boolean deleteResume(int id) {
        return resumeDao.delete(id);
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

    @Override
    public List<Resume> searchByName(String name) {
        return resumeDao.searchByName(name);
    }
}
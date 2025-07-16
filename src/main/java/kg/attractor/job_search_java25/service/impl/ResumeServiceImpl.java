package kg.attractor.job_search_java25.service.impl;

import kg.attractor.job_search_java25.dto.ResumeCreateDto;
import kg.attractor.job_search_java25.dto.ResumeUpdateDto;
import kg.attractor.job_search_java25.model.Resume;
import kg.attractor.job_search_java25.service.ResumeService;
import kg.attractor.job_search_java25.util.FileUtil;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Service
public class ResumeServiceImpl implements ResumeService {

    private final FileUtil fileUtil;
    private List<Resume> resumes;

    public ResumeServiceImpl(FileUtil fileUtil) {
        this.fileUtil = fileUtil;
    }

    @PostConstruct
    public void init() {
        resumes = new CopyOnWriteArrayList<>(fileUtil.loadResumes());
    }

    @Override
    public Resume createResume(ResumeCreateDto dto) {
        Resume resume = new Resume();
        resume.setId(fileUtil.getNextResumeId());
        resume.setName(dto.getName());
        resume.setCategoryId(dto.getCategoryId());
        resume.setSalary(dto.getSalary());
        resume.setApplicantId(dto.getApplicantId());
        resume.setCreatedDate(LocalDateTime.now());
        resume.setUpdateTime(LocalDateTime.now());
        resume.setActive(true);

        resumes.add(resume);
        fileUtil.saveResumes(resumes);
        return resume;
    }

    @Override
    public Optional<Resume> updateResume(int id, ResumeUpdateDto dto) {
        Optional<Resume> existing = getResumeById(id);
        if (existing.isPresent()) {
            Resume resume = existing.get();
            resume.setName(dto.getName());
            resume.setCategoryId(dto.getCategoryId());
            resume.setSalary(dto.getSalary());
            resume.setActive(dto.isActive());
            resume.setUpdateTime(LocalDateTime.now());

            fileUtil.saveResumes(resumes);
            return Optional.of(resume);
        }
        return Optional.empty();
    }

    @Override
    public boolean deleteResume(int id) {
        boolean removed = resumes.removeIf(r -> r.getId() == id);
        if (removed) {
            fileUtil.saveResumes(resumes);
        }
        return removed;
    }

    @Override
    public List<Resume> getAllResumes() {
        return resumes;
    }

    @Override
    public List<Resume> getResumesByCategoryId(int categoryId) {
        return resumes.stream()
                .filter(Resume::isActive)
                .filter(r -> r.getCategoryId() == categoryId)
                .collect(Collectors.toList());
    }

    @Override
    public List<Resume> getResumesByAuthorId(int applicantId) {
        return resumes.stream()
                .filter(r -> r.getApplicantId() == applicantId)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Resume> getResumeById(int id) {
        return resumes.stream()
                .filter(r -> r.getId() == id)
                .findFirst();
    }
}
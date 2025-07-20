package kg.attractor.job_search_java25.service;

import kg.attractor.job_search_java25.dto.ResumeCreateDto;
import kg.attractor.job_search_java25.dto.ResumeUpdateDto;
import kg.attractor.job_search_java25.model.Resume;

import java.util.List;
import java.util.Optional;

public interface ResumeService {
    Resume createResume(ResumeCreateDto dto);
    Optional<Resume> updateResume(int id, ResumeUpdateDto dto);
    boolean deleteResume(int id);
    List<Resume> getAllResumes();
    Optional<Resume> getResumeById(int id);
    List<Resume> getResumesByCategory(int categoryId);
    List<Resume> getResumesByApplicantId(int applicantId);

    List<Resume> findByCategory(int categoryId);
    List<Resume> searchByName(String name);
}
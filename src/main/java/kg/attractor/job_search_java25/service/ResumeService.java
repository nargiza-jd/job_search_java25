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

    List<Resume> getResumesByCategoryId(int categoryId);

    List<Resume> getResumesByAuthorId(int authorId);

    Optional<Resume> getResumeById(int id);
}
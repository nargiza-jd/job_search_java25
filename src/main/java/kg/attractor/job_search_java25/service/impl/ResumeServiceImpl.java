package kg.attractor.job_search_java25.service.impl;

import kg.attractor.job_search_java25.dao.*;
import kg.attractor.job_search_java25.dto.ResumeCreateDto;
import kg.attractor.job_search_java25.dto.ResumeUpdateDto;
import kg.attractor.job_search_java25.exceptions.NotFoundException;
import kg.attractor.job_search_java25.model.Resume;
import kg.attractor.job_search_java25.model.WorkExperienceInfo;
import kg.attractor.job_search_java25.service.ResumeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import kg.attractor.job_search_java25.model.EducationInfo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class ResumeServiceImpl implements ResumeService {

    private final ResumeDao resumeDao;
    private final EducationInfoDao educationDao;
    private final WorkExperienceInfoDao workDao;
    private final CategoryDao categoryDao;
    private final UserDao userDao;

    @Override
    public Resume createResume(ResumeCreateDto dto) {
        log.info("Создание нового резюме для заявителя с ID: {}", dto.getApplicantId());

        if (!categoryDao.existsById(dto.getCategoryId())) {
            log.warn("Попытка создать резюме с несуществующей категорией ID: {}", dto.getCategoryId());
            throw new NotFoundException("Категория с ID " + dto.getCategoryId() + " не найдена.");
        }

        if (userDao.findById(dto.getApplicantId()).isEmpty()) {
            log.warn("Попытка создать резюме для несуществующего заявителя с ID: {}", dto.getApplicantId());
            throw new NotFoundException("Заявитель с ID " + dto.getApplicantId() + " не найден.");
        }

        Resume resume = new Resume();
        resume.setName(dto.getName());
        resume.setCategoryId(dto.getCategoryId());
        resume.setSalary(dto.getSalary());
        resume.setApplicantId(dto.getApplicantId());
        resume.setActive(true);
        resume.setCreatedDate(LocalDateTime.now());
        resume.setUpdateTime(LocalDateTime.now());

        Resume savedResume = resumeDao.save(resume);
        log.info("Резюме с ID: {} успешно создано.", savedResume.getId());

        if (dto.getEducationList() != null && !dto.getEducationList().isEmpty()) {
            List<EducationInfo> educationList = dto.getEducationList().stream().map(e -> {
                EducationInfo edu = new EducationInfo();
                edu.setResumeId(savedResume.getId());
                edu.setInstitution(e.getInstitution());
                edu.setProgram(e.getProgram());
                edu.setStartDate(e.getStartDate());
                edu.setEndDate(e.getEndDate());
                edu.setDegree(e.getDegree());
                return edu;
            }).toList();
            educationDao.saveAll(savedResume.getId(), educationList);
        }

        if (dto.getWorkExperienceList() != null && !dto.getWorkExperienceList().isEmpty()) {
            List<WorkExperienceInfo> workList = dto.getWorkExperienceList().stream().map(w -> {
                WorkExperienceInfo info = new WorkExperienceInfo();
                info.setResumeId(savedResume.getId());
                info.setYears(w.getYears());
                info.setCompanyName(w.getCompanyName());
                info.setPosition(w.getPosition());
                info.setResponsibilities(w.getResponsibilities());
                return info;
            }).toList();
            workDao.saveAll(savedResume.getId(), workList);
        }

        return savedResume;
    }

    @Override
    public Optional<Resume> updateResume(int id, ResumeUpdateDto dto) {
        log.info("Обновление резюме с ID: {}", id);
        Optional<Resume> optional = resumeDao.getById(id);
        if (optional.isEmpty()) {
            log.warn("Попытка обновить несуществующее резюме с ID: {}", id);
            return Optional.empty();
        }

        if (!categoryDao.existsById(dto.getCategoryId())) {
            log.warn("Попытка обновить резюме с ID: {} с несуществующей категорией ID: {}", id, dto.getCategoryId());
            throw new NotFoundException("Категория с ID " + dto.getCategoryId() + " не найдена.");
        }

        Resume resume = optional.get();
        resume.setName(dto.getName());
        resume.setCategoryId(dto.getCategoryId());
        resume.setSalary(dto.getSalary());
        resume.setActive(dto.isActive());

        resumeDao.update(id, resume);
        log.info("Резюме с ID: {} успешно обновлено.", id);
        return resumeDao.getById(id);
    }

    @Override
    public boolean deleteResume(int id) {
        log.info("Попытка удаления резюме с ID: {}", id);
        boolean isDeleted = resumeDao.delete(id);
        if (isDeleted) {
            log.info("Резюме с ID: {} успешно удалено.", id);
        } else {
            log.warn("Попытка удаления несуществующего резюме с ID: {}", id);
        }
        return isDeleted;
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
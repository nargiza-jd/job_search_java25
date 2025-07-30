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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
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

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<User> getApplicantsByVacancyId(int vacancyId) {
        String sql = """
            SELECT u.* FROM users u
            JOIN resumes r ON u.id = r.applicant_id
            JOIN responded_applicants ra ON ra.resume_id = r.id
            WHERE ra.vacancy_id = ?
            """;

        return jdbcTemplate.query(sql, new Object[]{vacancyId}, (rs, rowNum) -> {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setName(rs.getString("name"));
            user.setSurname(rs.getString("surname"));
            user.setAge(rs.getInt("age"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            user.setPhoneNumber(rs.getString("phone_number"));
            user.setAvatar(rs.getString("avatar"));
            user.setAccountType(rs.getString("account_type"));
            return user;
        });
    }
}

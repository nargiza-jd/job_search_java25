package kg.attractor.job_search_java25.controller;

import kg.attractor.job_search_java25.dao.EducationInfoDao;
import kg.attractor.job_search_java25.model.EducationInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/education")
@RequiredArgsConstructor
public class EducationInfoController {

    private final EducationInfoDao educationInfoDao;

    @GetMapping("/resume/{resumeId}")
    public List<EducationInfo> getEducationByResumeId(@PathVariable int resumeId) {
        return educationInfoDao.getByResumeId(resumeId);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        educationInfoDao.delete(id);
    }
}
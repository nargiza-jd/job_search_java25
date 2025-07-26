package kg.attractor.job_search_java25.controller;

import kg.attractor.job_search_java25.dao.WorkExperienceInfoDao;
import kg.attractor.job_search_java25.model.WorkExperienceInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/work")
@RequiredArgsConstructor
public class WorkExperienceInfoController {

    private final WorkExperienceInfoDao workExperienceInfoDao;

    @GetMapping("/resume/{resumeId}")
    public List<WorkExperienceInfo> getWorkByResumeId(@PathVariable int resumeId) {
        return workExperienceInfoDao.getByResumeId(resumeId);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        workExperienceInfoDao.delete(id);
    }
}
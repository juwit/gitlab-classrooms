package fr.univ_lille.gitlab.classrooms.export;

import fr.univ_lille.gitlab.classrooms.classrooms.ClassroomService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@Controller
@RolesAllowed("TEACHER")
class ExportController {

    private final ExportService exportService;

    private final ClassroomService classroomService;

    ExportController(ExportService exportService, ClassroomService classroomService) {
        this.exportService = exportService;
        this.classroomService = classroomService;
    }


    @GetMapping(value = "/classrooms/{classroomId}/export/clone-classroom.sh")
    ModelAndView exportCloneClassroomScriptByStudent(@PathVariable UUID classroomId, HttpServletResponse response) throws ExportException {
        var classroom = this.classroomService.getClassroom(classroomId).orElseThrow();
        var studentRepos = exportService.listStudentRepositories(classroom);

        var modelAndView = new ModelAndView("export/clone-classroom.sh");
        modelAndView.addObject("studentRepositories", studentRepos);
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);

        return modelAndView;
    }

}

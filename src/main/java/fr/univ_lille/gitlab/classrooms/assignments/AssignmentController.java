package fr.univ_lille.gitlab.classrooms.assignments;

import fr.univ_lille.gitlab.classrooms.domain.ClassroomService;
import fr.univ_lille.gitlab.classrooms.domain.ClassroomUser;
import fr.univ_lille.gitlab.classrooms.quiz.QuizRepository;
import fr.univ_lille.gitlab.classrooms.quiz.QuizScoreService;
import jakarta.annotation.security.RolesAllowed;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Controller
class AssignmentController {

    private final AssignmentService assignmentService;

    private final QuizScoreService quizScoreService;

    private final QuizRepository quizRepository;

    private final ClassroomService classroomService;

    private final GitLabApi gitLabApi;

    public AssignmentController(AssignmentService assignmentService, QuizScoreService quizScoreService, QuizRepository quizRepository, ClassroomService classroomService, GitLabApi gitLabApi) {
        this.assignmentService = assignmentService;
        this.quizScoreService = quizScoreService;
        this.quizRepository = quizRepository;
        this.classroomService = classroomService;
        this.gitLabApi = gitLabApi;
    }

    @GetMapping("/assignments/{assignmentId}")
    String viewAssignment(@PathVariable UUID assignmentId, Model model) {
        var assignment = this.assignmentService.getAssignment(assignmentId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (assignment.getType() == AssignmentType.QUIZ) {
            var quizAssignment = (QuizAssignment)assignment;
            // view quiz results for the classroom
            model.addAttribute("quiz", quizAssignment.getQuiz());

            var quizResult = this.quizScoreService.getQuizResultForClassroom(quizAssignment.getQuiz(), assignment.getClassroom());
            model.addAttribute("quizResult", quizResult);

            return "quiz/all-submissions";
        }
        if (assignment.getType() == AssignmentType.EXERCISE ){
            var exerciseAssignment = (ExerciseAssignment)assignment;
            model.addAttribute("exercise", exerciseAssignment);
            return "exercise/all-submissions";
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/assignments/{assignmentId}/accept")
    String showAcceptAssignment(@PathVariable UUID assignmentId, Model model) {
        var assignment = this.assignmentService.getAssignment(assignmentId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        model.addAttribute("assignment", assignment);
        return "assignments/accept";
    }

    @PostMapping("/assignments/{assignmentId}/accept")
    String acceptAssignment(@PathVariable UUID assignmentId, @ModelAttribute("user") ClassroomUser student, Model model) {
        var assignment = this.assignmentService.getAssignment(assignmentId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        this.assignmentService.acceptAssigment(assignment, student);

        model.addAttribute("assignment", assignment);
        return "assignments/accepted";
    }

    @RolesAllowed("TEACHER")
    @GetMapping("/classrooms/{classroomId}/assignments/new")
    String newAssignment(@PathVariable UUID classroomId, Model model) throws GitLabApiException {
        var classroom = this.classroomService.getClassroom(classroomId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        model.addAttribute("classroom", classroom);

        model.addAttribute("quizzes", quizRepository.findAll());

        model.addAttribute("repositories", this.gitLabApi.getProjectApi().getMemberProjects());
        return "assignments/new";
    }

    record CreateAssignmentDTO(String assignmentName,
                               AssignmentType assignmentType,
                               String quizName,
                               String repositoryId) {

    }

    @RolesAllowed("TEACHER")
    @PostMapping("/classrooms/{classroomId}/assignments/new")
    String createAssignment(@PathVariable UUID classroomId, CreateAssignmentDTO createAssignmentDTO) throws GitLabApiException {
        var classroom = this.classroomService.getClassroom(classroomId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (createAssignmentDTO.assignmentType == AssignmentType.QUIZ) {
            this.assignmentService.createQuizAssignment(classroom, createAssignmentDTO.assignmentName, createAssignmentDTO.quizName);
        } else if (createAssignmentDTO.assignmentType == AssignmentType.EXERCISE) {
            this.assignmentService.createExerciseAssignment(classroom, createAssignmentDTO.assignmentName, createAssignmentDTO.repositoryId);
        }

        return "redirect:/classrooms/"+classroomId;
    }

}

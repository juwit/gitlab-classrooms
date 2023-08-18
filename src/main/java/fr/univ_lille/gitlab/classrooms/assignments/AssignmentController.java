package fr.univ_lille.gitlab.classrooms.assignments;

import fr.univ_lille.gitlab.classrooms.domain.ClassroomService;
import fr.univ_lille.gitlab.classrooms.quiz.QuizScoreService;
import fr.univ_lille.gitlab.classrooms.quiz.QuizService;
import fr.univ_lille.gitlab.classrooms.users.ClassroomUser;
import jakarta.annotation.security.RolesAllowed;
import jakarta.servlet.http.HttpSession;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Controller
class AssignmentController {

    private final AssignmentService assignmentService;

    private final QuizScoreService quizScoreService;

    private final QuizService quizService;

    private final ClassroomService classroomService;

    private final GitLabApi gitLabApi;

    private static final System.Logger LOGGER = System.getLogger(AssignmentController.class.getName());

    public AssignmentController(AssignmentService assignmentService, QuizScoreService quizScoreService, QuizService quizService, ClassroomService classroomService, GitLabApi gitLabApi) {
        this.assignmentService = assignmentService;
        this.quizScoreService = quizScoreService;
        this.quizService = quizService;
        this.classroomService = classroomService;
        this.gitLabApi = gitLabApi;
    }

    @GetMapping("/assignments/{assignmentId}")
    String viewAssignment(@PathVariable UUID assignmentId, Model model) {
        var assignment = this.assignmentService.getAssignment(assignmentId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (assignment.getType() == AssignmentType.QUIZ) {
            var quizAssignment = (QuizAssignment) assignment;
            // view quiz results for the classroom
            model.addAttribute("quiz", quizAssignment.getQuiz());

            var quizResult = this.quizScoreService.getQuizResultForClassroom(quizAssignment.getQuiz(), assignment.getClassroom());
            model.addAttribute("quizResult", quizResult);

            return "quiz/all-submissions";
        }
        if (assignment.getType() == AssignmentType.EXERCISE) {
            var exerciseAssignment = (ExerciseAssignment) assignment;
            model.addAttribute("exercise", exerciseAssignment);
            return "exercise/all-submissions";
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/assignments/{assignmentId}/accept")
    String showAcceptAssignment(@PathVariable UUID assignmentId, @ModelAttribute("user") ClassroomUser student, Model model, HttpSession session) {
        var assignment = this.assignmentService.getAssignment(assignmentId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        // check if the student already belongs to the classroom, if not, redirect to the classroom join link
        var classroom = assignment.getClassroom();
        if (!classroom.getStudents().contains(student)) {
            session.setAttribute("redirect", "/assignments/" + assignmentId + "/accept");
            return "redirect:/classrooms/" + classroom.getId() + "/join";
        }

        model.addAttribute("assignment", assignment);
        return "assignments/accept";
    }

    @PostMapping("/assignments/{assignmentId}/accept")
    String acceptAssignment(@PathVariable UUID assignmentId, @ModelAttribute("user") ClassroomUser student, Model model) {
        var assignment = this.assignmentService.getAssignment(assignmentId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        try {
            this.assignmentService.acceptAssigment(assignment, student);
        }
        catch (GitLabApiException e){
            LOGGER.log(System.Logger.Level.ERROR, "Could not accept assignment");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not accept assignment", e);
        }


        model.addAttribute("assignment", assignment);
        return "assignments/accepted";
    }

    @RolesAllowed("TEACHER")
    @GetMapping("/classrooms/{classroomId}/assignments/new")
    String newAssignment(@PathVariable UUID classroomId, Model model) throws GitLabApiException {
        var classroom = this.classroomService.getClassroom(classroomId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        model.addAttribute("classroom", classroom);

        model.addAttribute("quizzes", this.quizService.getAllQuizzes());

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

        return "redirect:/classrooms/" + classroomId;
    }

}

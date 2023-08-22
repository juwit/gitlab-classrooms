package fr.univ_lille.gitlab.classrooms.assignments;

import fr.univ_lille.gitlab.classrooms.classrooms.Classroom;
import fr.univ_lille.gitlab.classrooms.classrooms.ClassroomService;
import fr.univ_lille.gitlab.classrooms.gitlab.Gitlab;
import fr.univ_lille.gitlab.classrooms.quiz.QuizService;
import fr.univ_lille.gitlab.classrooms.users.ClassroomUser;
import jakarta.transaction.Transactional;
import org.gitlab4j.api.GitLabApiException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
class AssignmentServiceImpl implements AssignmentService {

    private final QuizService quizService;

    private final Gitlab gitlab;

    private final ClassroomService classroomService;

    private final AssignmentRepository assignmentRepository;

    private final StudentAssignmentRepository studentAssignmentRepository;

    AssignmentServiceImpl(QuizService quizService, Gitlab gitlab, ClassroomService classroomService, AssignmentRepository assignmentRepository, StudentAssignmentRepository studentAssignmentRepository) {
        this.quizService = quizService;
        this.gitlab = gitlab;
        this.classroomService = classroomService;
        this.assignmentRepository = assignmentRepository;
        this.studentAssignmentRepository = studentAssignmentRepository;
    }

    @Override
    public Optional<Assignment> getAssignment(UUID id) {
        return this.assignmentRepository.findById(id);
    }

    @Override
    @Transactional
    public void acceptAssigment(Assignment assignment, ClassroomUser student) throws GitLabApiException {
        assignment.accept(student);
        this.assignmentRepository.save(assignment);

        if (assignment instanceof ExerciseAssignment exerciseAssignment) {
            // create the project in gitlab
            var project = gitlab.createProject(exerciseAssignment, student);

            // create the student exercise assignment
            var studentExercise = new StudentExerciseAssignment();
            studentExercise.setAssignment(exerciseAssignment);
            studentExercise.setStudent(student);
            studentExercise.setGitlabProjectId(project.getId());
            studentExercise.setGitlabProjectUrl(project.getWebUrl());
            this.studentAssignmentRepository.save(studentExercise);
        }
        else if (assignment instanceof QuizAssignment quizAssignment) {
            // create the student quiz assignment
            var studentExercise = new StudentQuizAssignment();
            studentExercise.setAssignment(quizAssignment);
            studentExercise.setStudent(student);
            this.studentAssignmentRepository.save(studentExercise);
        }
    }

    @Override
    public List<StudentAssignment> getAssignmentResults(Assignment assignment) {
        return this.studentAssignmentRepository.findAllByAssignment(assignment);
    }

    @Override
    @Transactional
    public Assignment createQuizAssignment(Classroom classroom, String assignmentName, String quizName){
        var quiz = this.quizService.getQuiz(quizName).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var quizAssignment = new QuizAssignment();
        quizAssignment.setName(assignmentName);
        quizAssignment.setQuiz(quiz);

        classroom.addAssignment(quizAssignment);

        this.assignmentRepository.save(quizAssignment);
        this.classroomService.saveClassroom(classroom);

        return quizAssignment;
    }

    @Override
    @Transactional
    public Assignment createExerciseAssignment(Classroom classroom, String assignmentName, String repositoryId) throws GitLabApiException {
        var exerciseAssignment = new ExerciseAssignment();
        exerciseAssignment.setName(assignmentName);
        exerciseAssignment.setGitlabRepositoryTemplateId(repositoryId);

        this.gitlab.createGroup(exerciseAssignment, classroom);

        classroom.addAssignment(exerciseAssignment);

        this.assignmentRepository.save(exerciseAssignment);
        this.classroomService.saveClassroom(classroom);

        return exerciseAssignment;
    }

    @Override
    public StudentAssignment getAssignmentResultsForStudent(Assignment assignment, ClassroomUser student) {
        return this.studentAssignmentRepository.findByAssignmentAndStudent(assignment, student);
    }
}

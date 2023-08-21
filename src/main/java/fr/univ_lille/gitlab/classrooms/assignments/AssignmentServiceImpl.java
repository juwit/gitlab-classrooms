package fr.univ_lille.gitlab.classrooms.assignments;

import fr.univ_lille.gitlab.classrooms.domain.Classroom;
import fr.univ_lille.gitlab.classrooms.domain.ClassroomService;
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

    private final StudentExerciseRepository studentExerciseRepository;

    AssignmentServiceImpl(QuizService quizService, Gitlab gitlab, ClassroomService classroomService, AssignmentRepository assignmentRepository, StudentExerciseRepository studentExerciseRepository) {
        this.quizService = quizService;
        this.gitlab = gitlab;
        this.classroomService = classroomService;
        this.assignmentRepository = assignmentRepository;
        this.studentExerciseRepository = studentExerciseRepository;
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

            // create the student exercise in the database
            var studentExercise = new StudentExercise();
            studentExercise.setAssignment(exerciseAssignment);
            studentExercise.setStudent(student);
            studentExercise.setGitlabProjectId(project.getId());
            studentExercise.setGitlabProjectUrl(project.getWebUrl());
            this.studentExerciseRepository.save(studentExercise);
        }
    }

    @Override
    public List<StudentExercise> getAssignmentResults(Assignment assignment) {
        return this.studentExerciseRepository.findAllByAssignment(assignment);
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
    public StudentExercise getAssignmentResultsForStudent(Assignment assignment, ClassroomUser student) {
        return this.studentExerciseRepository.findByAssignmentAndStudent(assignment, student);
    }
}

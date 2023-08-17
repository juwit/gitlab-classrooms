package fr.univ_lille.gitlab.classrooms.assignments;

import fr.univ_lille.gitlab.classrooms.domain.*;
import fr.univ_lille.gitlab.classrooms.quiz.QuizService;
import fr.univ_lille.gitlab.classrooms.users.ClassroomUser;
import jakarta.transaction.Transactional;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.GroupParams;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

@Service
class AssignmentServiceImpl implements AssignmentService {

    private final QuizService quizService;

    private final GitLabApi gitLabApi;

    private final ClassroomService classroomService;

    private final AssignmentRepository assignmentRepository;

    AssignmentServiceImpl(QuizService quizService, GitLabApi gitLabApi, ClassroomService classroomService, AssignmentRepository assignmentRepository) {
        this.quizService = quizService;
        this.gitLabApi = gitLabApi;
        this.classroomService = classroomService;
        this.assignmentRepository = assignmentRepository;
    }

    @Override
    public Optional<Assignment> getAssignment(UUID id) {
        return this.assignmentRepository.findById(id);
    }

    @Override
    @Transactional
    public void acceptAssigment(Assignment assignment, ClassroomUser student){
        assignment.accept(student);

        this.assignmentRepository.save(assignment);
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
        var groupPath = assignmentName.trim().replaceAll("[^\\w\\-.]", "_");
        var groupParams = new GroupParams()
                .withName(assignmentName)
                .withPath(groupPath)
                .withDescription("Gitlab group for the assignment " + assignmentName)
                .withParentId(classroom.getGitlabGroupId());
        var group = this.gitLabApi.getGroupApi().createGroup(groupParams);

        var exerciseAssignment = new ExerciseAssignment();
        exerciseAssignment.setName(assignmentName);
        exerciseAssignment.setGitlabRepositoryTemplateId(repositoryId);
        exerciseAssignment.setGitlabGroupId(group.getId());

        classroom.addAssignment(exerciseAssignment);

        this.assignmentRepository.save(exerciseAssignment);
        this.classroomService.saveClassroom(classroom);

        return exerciseAssignment;
    }
}
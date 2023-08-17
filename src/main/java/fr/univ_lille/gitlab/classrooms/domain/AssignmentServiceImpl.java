package fr.univ_lille.gitlab.classrooms.domain;

import fr.univ_lille.gitlab.classrooms.quiz.QuizRepository;
import jakarta.transaction.Transactional;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.GroupParams;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
class AssignmentServiceImpl {

    private final QuizRepository quizRepository;

    private final GitLabApi gitLabApi;

    private ClassroomRepository classroomRepository;

    private AssignmentRepository assignmentRepository;

    AssignmentServiceImpl(QuizRepository quizRepository, GitLabApi gitLabApi, ClassroomRepository classroomRepository, AssignmentRepository assignmentRepository) {
        this.quizRepository = quizRepository;
        this.gitLabApi = gitLabApi;
        this.classroomRepository = classroomRepository;
        this.assignmentRepository = assignmentRepository;
    }

    @Transactional
    public Assignment createQuizAssignment(Classroom classroom, String assignmentName, String quizName){
        var quiz = this.quizRepository.findById(quizName).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var quizAssignment = new QuizAssignment();
        quizAssignment.setName(assignmentName);
        quizAssignment.setQuiz(quiz);

        classroom.addAssignment(quizAssignment);

        this.assignmentRepository.save(quizAssignment);
        this.classroomRepository.save(classroom);

        return quizAssignment;
    }

    @Transactional
    public Assignment createExerciseAssignment(Classroom classroom, String assignmentName, String repositoryId) throws GitLabApiException {
        var groupPath = assignmentName.trim().replaceAll("[^\\w\\d\\-_.]", "_");
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
        this.classroomRepository.save(classroom);

        return exerciseAssignment;
    }
}
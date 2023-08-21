package fr.univ_lille.gitlab.classrooms.assignments;

import fr.univ_lille.gitlab.classrooms.domain.*;
import fr.univ_lille.gitlab.classrooms.gitlab.GitlabApiFactory;
import fr.univ_lille.gitlab.classrooms.quiz.QuizService;
import fr.univ_lille.gitlab.classrooms.users.ClassroomUser;
import jakarta.transaction.Transactional;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.AccessLevel;
import org.gitlab4j.api.models.GroupParams;
import org.gitlab4j.api.models.Project;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
class AssignmentServiceImpl implements AssignmentService {

    private final QuizService quizService;

    private final GitLabApi gitLabApi;

    private final GitlabApiFactory gitlabApiFactory;

    private final ClassroomService classroomService;

    private final AssignmentRepository assignmentRepository;

    private final StudentExerciseRepository studentExerciseRepository;

    AssignmentServiceImpl(QuizService quizService, GitLabApi gitLabApi, GitlabApiFactory gitlabApiFactory, ClassroomService classroomService, AssignmentRepository assignmentRepository, StudentExerciseRepository studentExerciseRepository) {
        this.quizService = quizService;
        this.gitLabApi = gitLabApi;
        this.gitlabApiFactory = gitlabApiFactory;
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

        if (assignment instanceof ExerciseAssignment exerciseAssignment) {
            // get a gitlab api client ith the teacher's rights
            var teacherGitlabApi = this.gitlabApiFactory.userGitlabApi(assignment.getClassroom().getTeacher());

            var templateRepo = exerciseAssignment.getGitlabRepositoryTemplateId();
            if (templateRepo == null || templateRepo.isBlank()) {
                // get student id in gitlab
                var studentUserId = teacherGitlabApi.getUserApi().getUser(student.getName()).getId();

                // create a blank project
                var projectParams = new Project()
                        .withName(assignment.getName() + "-" + student.getName())
                        .withNamespaceId(exerciseAssignment.getGitlabGroupId());
                var project = teacherGitlabApi.getProjectApi().createProject(projectParams);

                // grant the student access to its project
                teacherGitlabApi.getProjectApi().addMember(project.getId(), studentUserId, AccessLevel.MAINTAINER);

                // create the student exercise in the database
                var studentExercise = new StudentExercise();
                studentExercise.setAssignment(exerciseAssignment);
                studentExercise.setStudent(student);
                studentExercise.setGitlabProjectId(project.getId());
                studentExercise.setGitlabProjectUrl(project.getWebUrl());
                this.studentExerciseRepository.save(studentExercise);
            }
        }

        this.assignmentRepository.save(assignment);
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

    @Override
    public StudentExercise getAssignmentResultsForStudent(Assignment assignment, ClassroomUser student) {
        return this.studentExerciseRepository.findByAssignmentAndStudent(assignment, student);
    }
}

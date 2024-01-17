package fr.univ_lille.gitlab.classrooms.export;

import fr.univ_lille.gitlab.classrooms.assignments.AssignmentService;
import fr.univ_lille.gitlab.classrooms.assignments.StudentAssignment;
import fr.univ_lille.gitlab.classrooms.assignments.StudentExerciseAssignment;
import fr.univ_lille.gitlab.classrooms.classrooms.Classroom;
import fr.univ_lille.gitlab.classrooms.gitlab.Gitlab;
import fr.univ_lille.gitlab.classrooms.users.ClassroomUser;
import org.gitlab4j.api.GitLabApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
class ExportServiceImpl implements ExportService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExportServiceImpl.class.getName());

    private final Gitlab gitlab;

    private final StudentAssignmentService assignmentService;

    ExportServiceImpl(Gitlab gitlab, StudentAssignmentService assignmentService) {
        this.gitlab = gitlab;
        this.assignmentService = assignmentService;
    }

    public record StudentRepository(String studentName, List<String> cloneUrls){}

    @Override
    public List<StudentRepository> listStudentRepositories(Classroom classroom) throws ExportException {
        LOGGER.info("Listing student repositories for classroom {} with name {}", classroom.getId(), classroom.getName());

        var studentRepositoriesList = new LinkedList<StudentRepository>();

        for(ClassroomUser student : classroom.getStudents()){
            var studentExerciseAssignments = assignmentService.getAllStudentExerciseAssignmentsForAClassroom(classroom, student);

            var cloneUrls = new LinkedList<String>();

            for (StudentExerciseAssignment studentAssignment : studentExerciseAssignments){
                var sshCloneUrl = studentAssignment.getGitlabCloneUrl();
                if (sshCloneUrl == null) {
                    try {
                        sshCloneUrl = gitlab.getAssignmentCloneUrl(studentAssignment);
                    } catch (GitLabApiException e) {
                        throw new ExportException("Could not get clone URL for Assignment " + studentAssignment.getGitlabProjectId(), e);
                    }
                }
                cloneUrls.add(sshCloneUrl);
            }

            studentRepositoriesList.add(new StudentRepository(student.getName(), cloneUrls));
        }

        return studentRepositoriesList;
    }

}

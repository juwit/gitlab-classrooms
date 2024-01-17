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

import java.io.StringWriter;
import java.util.LinkedList;
import java.util.List;

@Service
class ExportServiceImpl implements ExportService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExportServiceImpl.class.getName());

    private final Gitlab gitlab;

    private final AssignmentService assignmentService;

    ExportServiceImpl(Gitlab gitlab, AssignmentService assignmentService) {
        this.gitlab = gitlab;
        this.assignmentService = assignmentService;
    }

    public record StudentRepository(String studentName, List<String> cloneUrls){}

    @Override
    public List<StudentRepository> listStudentRepositories(Classroom classroom) throws ExportException {
        LOGGER.info("Listing student repositories for classroom {} with name {}", classroom.getId(), classroom.getName());

        var studentRepositoriesList = new LinkedList<StudentRepository>();

        for(ClassroomUser student : classroom.getStudents()){
            var studentExerciseAssignments = assignmentService.getAllStudentAssignmentsForAClassroom(classroom, student);

            var cloneUrls = new LinkedList<String>();

            for (StudentAssignment studentAssignment : studentExerciseAssignments){
                StudentExerciseAssignment studentExerciseAssignment = (StudentExerciseAssignment) studentAssignment;
                var sshCloneUrl = studentExerciseAssignment.getGitlabCloneUrl();
                if (sshCloneUrl == null) {
                    try {
                        sshCloneUrl = gitlab.getAssignmentCloneUrl(studentExerciseAssignment);
                    } catch (GitLabApiException e) {
                        throw new ExportException("Could not get clone URL for Assignment " + studentExerciseAssignment.getGitlabProjectId(), e);
                    }
                }
                cloneUrls.add(sshCloneUrl);
            }

            studentRepositoriesList.add(new StudentRepository(student.getName(), cloneUrls));
        }

        return studentRepositoriesList;
    }

    @Override
    public String generateCloneClassroomScriptByStudent(Classroom classroom) throws ExportException {
        LOGGER.info("Generating clone script for classroom {} with name {}", classroom.getId(), classroom.getName());

        StringWriter writer = new StringWriter();

        writer.write("#!/bin/sh");
        writer.write("\n");
        writer.write("\n");
        writer.write("mkdir -p classroom");
        writer.write("\n");
        writer.write("cd classroom");
        writer.write("\n");
        for (ClassroomUser student : classroom.getStudents()) {
            writer.write("\n");
            writer.write("mkdir -p " + student.getName());
            writer.write("\n");
            writer.write("cd " + student.getName());
            writer.write("\n");

            var studentExerciseAssignments = assignmentService.getAllStudentAssignmentsForAClassroom(classroom, student);
            for (StudentAssignment studentAssignment : studentExerciseAssignments) {
                StudentExerciseAssignment studentExerciseAssignment = (StudentExerciseAssignment) studentAssignment;
                try {
                    String sshCloneUrl = gitlab.getAssignmentCloneUrl(studentExerciseAssignment);
                    writer.write("git clone " + sshCloneUrl);
                    writer.write("\n");
                } catch (GitLabApiException e) {
                    throw new ExportException("Could not get clone URL for Assignment " + studentExerciseAssignment.getGitlabProjectId(), e);
                }
            }
            writer.write("cd ..");
            writer.write("\n");
        }

        writer.write("\n");
        writer.write("cd ..");
        writer.write("\n");

        return writer.toString();
    }
}

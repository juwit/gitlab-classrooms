package fr.univ_lille.gitlab.classrooms.classrooms;

import fr.univ_lille.gitlab.classrooms.assignments.Assignment;
import fr.univ_lille.gitlab.classrooms.users.ClassroomUser;
import jakarta.persistence.*;

import java.net.URL;
import java.util.*;

@Entity
public class Classroom {

    @Id
    private UUID id = UUID.randomUUID();

    private String name;

    private URL gitlabUrl;

    private Long gitlabGroupId;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<ClassroomUser> teachers = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<ClassroomUser> students = new HashSet<>();

    @OneToMany(mappedBy = "classroom")
    private List<Assignment> assignments = new LinkedList<>();

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public URL getGitlabUrl() {
        return gitlabUrl;
    }

    public void setGitlabUrl(URL gitlabUrl) {
        this.gitlabUrl = gitlabUrl;
    }

    public Set<ClassroomUser> getTeachers() {
        return teachers;
    }

    public void setTeachers(Set<ClassroomUser> teachers) {
        this.teachers = teachers;
    }

    public void addTeacher(ClassroomUser teacher) {
        this.teachers.add(teacher);
    }

    public Set<ClassroomUser> getStudents() {
        return students;
    }

    public void setStudents(Set<ClassroomUser> students) {
        this.students = students;
    }

    public void join(ClassroomUser classroomUser){
        this.students.add(classroomUser);
    }

    public List<Assignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(List<Assignment> assignments) {
        this.assignments = assignments;
    }

    public void addAssignment(Assignment assignment) {
        this.assignments.add(assignment);
        assignment.setClassroom(this);
    }

    public Long getGitlabGroupId() {
        return gitlabGroupId;
    }

    public void setGitlabGroupId(Long gitlabGroupId) {
        this.gitlabGroupId = gitlabGroupId;
    }
}

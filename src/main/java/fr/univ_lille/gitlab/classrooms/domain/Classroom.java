package fr.univ_lille.gitlab.classrooms.domain;

import jakarta.persistence.*;

import java.net.URL;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
public class Classroom {

    @Id
    UUID id;

    String name;

    URL gitlabUrl;

    @ManyToMany(fetch = FetchType.EAGER)
    Set<ClassroomUser> students;

    @OneToMany
    List<Assignment> assignments;

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
}

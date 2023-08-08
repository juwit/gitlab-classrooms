package fr.univ_lille.gitlab.classrooms.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

import java.net.URL;
import java.util.Set;
import java.util.UUID;

@Entity
public class Classroom {

    @Id
    UUID id;

    String name;

    URL gitlabUrl;

    @ManyToMany
    Set<ClassroomUser> students;

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
}

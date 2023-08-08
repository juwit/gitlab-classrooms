package fr.univ_lille.gitlab.classrooms.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

import java.util.Set;
import java.util.UUID;

@Entity
class Assignment {

    @Id
    private UUID id;

    private String name;

    @ManyToMany
    private Set<ClassroomUser> students;

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

    public Set<ClassroomUser> getStudents() {
        return students;
    }

    public void setStudents(Set<ClassroomUser> students) {
        this.students = students;
    }

    public void accept(ClassroomUser student){
        this.students.add(student);
    }
}

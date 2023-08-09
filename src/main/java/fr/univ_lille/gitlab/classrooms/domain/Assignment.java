package fr.univ_lille.gitlab.classrooms.domain;

import jakarta.persistence.*;

import java.util.Set;
import java.util.UUID;

enum AssignmentType {
    QUIZ, EXERCICE
}

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
abstract class Assignment {

    @Id
    private UUID id = UUID.randomUUID();

    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<ClassroomUser> students;

    @Enumerated(EnumType.STRING)
    private AssignmentType type;

    @ManyToOne
    private Classroom classroom;

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

    public AssignmentType getType() {
        return type;
    }

    public void setType(AssignmentType type) {
        this.type = type;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }
}

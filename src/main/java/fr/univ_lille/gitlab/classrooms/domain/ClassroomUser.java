package fr.univ_lille.gitlab.classrooms.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;

import java.util.Collection;

@Entity
public class ClassroomUser {

    @Id
    private String name;

    @Enumerated(EnumType.STRING)
    private Collection<ClassroomRole> roles;

    public ClassroomUser() {
    }

    public ClassroomUser(String name, Collection<ClassroomRole> roles) {
        this.name = name;
        this.roles = roles;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<ClassroomRole> getRoles() {
        return roles;
    }

    public void setRoles(Collection<ClassroomRole> roles) {
        this.roles = roles;
    }
}

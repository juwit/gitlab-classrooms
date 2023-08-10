package fr.univ_lille.gitlab.classrooms.domain;

import jakarta.persistence.*;

import java.io.Serializable;
import java.net.URL;
import java.util.Collection;

@Entity
public class ClassroomUser implements Serializable {

    @Id
    private String name;

    @Enumerated(EnumType.STRING)
    private Collection<ClassroomRole> roles;

    private URL avatarUrl;

    private String email;

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

    public URL getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(URL avatarUri) {
        this.avatarUrl = avatarUri;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

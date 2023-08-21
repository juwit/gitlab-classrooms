package fr.univ_lille.gitlab.classrooms.users;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;

import java.io.Serializable;
import java.net.URL;
import java.util.Collection;
import java.util.Objects;

@Entity
public class ClassroomUser implements Serializable {

    @Id
    private String name;

    @Enumerated(EnumType.STRING)
    private Collection<ClassroomRole> roles;

    private Long gitlabUserId;

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

    public Long getGitlabUserId() {
        return gitlabUserId;
    }

    public void setGitlabUserId(Long gitlabUserId) {
        this.gitlabUserId = gitlabUserId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClassroomUser that = (ClassroomUser) o;

        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}

package fr.univ_lille.gitlab.classrooms.quiz;

import jakarta.persistence.*;

@Entity
@Table(name="quiz")
public class QuizEntity {

    @Id
    private String name;

    @Column(columnDefinition = "TEXT")
    private String markdownContent;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMarkdownContent() {
        return markdownContent;
    }

    public void setMarkdownContent(String markdownContent) {
        this.markdownContent = markdownContent;
    }
}

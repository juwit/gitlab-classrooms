package fr.univ_lille.gitlab.classrooms.quiz;

import java.util.Collection;

public record QuizResult(Collection<QuizScore> quizScores) {

    public double average(){
        return quizScores.stream().mapToLong(it -> it.score)
                .average()
                .orElse(0);
    }

    public long questionCount(){
        return quizScores.stream().findFirst().map(it -> it.maxScore).orElse(0L);
    }

}

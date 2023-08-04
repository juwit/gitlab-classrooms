package fr.univ_lille.gitlab.classrooms.quiz;

import java.util.Optional;

public interface QuizScoreService {
    void registerScoreForStudent(Quiz quiz, String studentId);

    Optional<QuizScore> getPreviousQuizSubmission(String quizId, String studentId);
}

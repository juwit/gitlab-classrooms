package fr.univ_lille.gitlab.classrooms.quiz;

public interface QuizScoreService {
    void registerScoreForStudent(Quiz quiz, String studentId);

}

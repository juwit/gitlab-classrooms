package fr.univ_lille.gitlab.classrooms.quiz;

import fr.univ_lille.gitlab.classrooms.domain.Classroom;
import fr.univ_lille.gitlab.classrooms.domain.ClassroomUser;

import java.util.Optional;

public interface QuizScoreService {
    void registerScoreForStudent(Quiz quiz, ClassroomUser student);

    Optional<QuizScore> getPreviousQuizSubmission(String quizId, ClassroomUser student);

    QuizResult getQuizResult(String quizId);

    /**
     * Returns the results for the given quiz, only for the classroom members
     * @param quiz
     * @param classroom
     * @return
     */
    QuizResult getQuizResultForClassroom(QuizEntity quiz, Classroom classroom);
}

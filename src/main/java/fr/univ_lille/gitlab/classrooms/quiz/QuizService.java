package fr.univ_lille.gitlab.classrooms.quiz;

import java.util.List;
import java.util.Optional;

public interface QuizService {

    List<QuizEntity> getAllQuizzes();

    Optional<QuizEntity> getQuiz(String id);

}

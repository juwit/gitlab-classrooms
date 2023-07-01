package fr.univ_lille.gitlab.classrooms.quiz;

import org.springframework.data.repository.CrudRepository;

public interface QuizScoreRepository extends CrudRepository<QuizScore, QuizScoreId> {
}

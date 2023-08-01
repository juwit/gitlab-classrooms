package fr.univ_lille.gitlab.classrooms.quiz;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizRepository extends CrudRepository<QuizEntity, String> {
}

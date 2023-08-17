package fr.univ_lille.gitlab.classrooms.quiz;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface QuizRepository extends JpaRepository<QuizEntity, String> {
}

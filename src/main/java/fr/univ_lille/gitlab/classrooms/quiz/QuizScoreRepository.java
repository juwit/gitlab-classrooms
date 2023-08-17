package fr.univ_lille.gitlab.classrooms.quiz;

import fr.univ_lille.gitlab.classrooms.users.ClassroomUser;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface QuizScoreRepository extends CrudRepository<QuizScore, QuizScoreId> {
    Collection<QuizScore> findByQuizId(String quizId);

    Collection<QuizScore> findByQuizIdAndAndClassroomUserIn(String quizId, Collection<ClassroomUser> classroomUsers);
}

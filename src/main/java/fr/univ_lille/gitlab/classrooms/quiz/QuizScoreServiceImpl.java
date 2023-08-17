package fr.univ_lille.gitlab.classrooms.quiz;

import fr.univ_lille.gitlab.classrooms.domain.Classroom;
import fr.univ_lille.gitlab.classrooms.domain.ClassroomUser;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
class QuizScoreServiceImpl implements QuizScoreService {

    private final QuizScoreRepository quizScoreRepository;

    QuizScoreServiceImpl(QuizScoreRepository quizScoreRepository) {
        this.quizScoreRepository = quizScoreRepository;
    }

    @Override
    public void registerScoreForStudent(Quiz quiz, ClassroomUser student) {
        // saves the score of the student
        var score = new QuizScore();
        score.setQuizId(quiz.getName());
        score.setClassroomUser(student);
        score.setScore(quiz.score());
        score.setMaxScore(quiz.getQuestions().size());
        quizScoreRepository.save(score);
    }

    @Override
    public Optional<QuizScore> getPreviousQuizSubmission(String quizId, ClassroomUser student) {
        return quizScoreRepository.findById(new QuizScoreId(quizId, student));
    }

    @Override
    public QuizResult getQuizResult(String quizId) {
        var quizScores = quizScoreRepository.findByQuizId(quizId);
        return new QuizResult(quizScores);
    }

    @Override
    public QuizResult getQuizResultForClassroom(QuizEntity quiz, Classroom classroom) {
        var quizScores = quizScoreRepository.findByQuizIdAndAndClassroomUserIn(quiz.getName(), classroom.getStudents());
        return new QuizResult(quizScores);
    }
}

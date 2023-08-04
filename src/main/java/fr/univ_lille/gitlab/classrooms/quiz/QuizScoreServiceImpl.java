package fr.univ_lille.gitlab.classrooms.quiz;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
class QuizScoreServiceImpl implements QuizScoreService {

    private final QuizScoreRepository quizScoreRepository;

    QuizScoreServiceImpl(QuizScoreRepository quizScoreRepository) {
        this.quizScoreRepository = quizScoreRepository;
    }

    @Override
    public void registerScoreForStudent(Quiz quiz, String studentId) {
        // saves the score of the student
        var score = new QuizScore();
        score.setQuizId(quiz.getName());
        score.setStudentId(studentId);
        score.setScore(quiz.score());
        score.setMaxScore(quiz.getQuestions().size());
        quizScoreRepository.save(score);
    }

    @Override
    public Optional<QuizScore> getPreviousQuizSubmission(String quizId, String studentId) {
        return quizScoreRepository.findById(new QuizScoreId(quizId, studentId));
    }
}

package fr.univ_lille.gitlab.classrooms.quiz;

import fr.univ_lille.gitlab.classrooms.domain.Classroom;
import fr.univ_lille.gitlab.classrooms.domain.ClassroomUserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
class QuizScoreServiceImpl implements QuizScoreService {

    private final QuizScoreRepository quizScoreRepository;

    private final ClassroomUserService classroomUserService;

    QuizScoreServiceImpl(QuizScoreRepository quizScoreRepository, ClassroomUserService classroomUserService) {
        this.quizScoreRepository = quizScoreRepository;
        this.classroomUserService = classroomUserService;
    }

    @Override
    public void registerScoreForStudent(Quiz quiz, String studentId) {
        // saves the score of the student
        var score = new QuizScore();
        score.setQuizId(quiz.getName());
        score.setClassroomUser(this.classroomUserService.getClassroomUser(studentId));
        score.setScore(quiz.score());
        score.setMaxScore(quiz.getQuestions().size());
        quizScoreRepository.save(score);
    }

    @Override
    public Optional<QuizScore> getPreviousQuizSubmission(String quizId, String studentId) {
        var classroomUser = this.classroomUserService.getClassroomUser(studentId);
        return quizScoreRepository.findById(new QuizScoreId(quizId, classroomUser));
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

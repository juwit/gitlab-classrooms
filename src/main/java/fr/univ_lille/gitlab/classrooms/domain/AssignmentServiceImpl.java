package fr.univ_lille.gitlab.classrooms.domain;

import fr.univ_lille.gitlab.classrooms.quiz.QuizRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
class AssignmentServiceImpl {

    private QuizRepository quizRepository;

    AssignmentServiceImpl(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    Assignment createQuizAssignment(String assignmentName, String quizName){
        var quiz = this.quizRepository.findById(quizName).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var quizAssignment = new QuizAssignment();
        quizAssignment.setName(assignmentName);
        quizAssignment.setQuiz(quiz);

        return quizAssignment;
    }

    public Assignment createExerciseAssignment(String assignmentName, String repositoryId) {
        var exerciseAssignment = new ExerciseAssignment();
        exerciseAssignment.setName(assignmentName);
        exerciseAssignment.setGitlabRepositoryTemplateId(repositoryId);
        return exerciseAssignment;
    }
}

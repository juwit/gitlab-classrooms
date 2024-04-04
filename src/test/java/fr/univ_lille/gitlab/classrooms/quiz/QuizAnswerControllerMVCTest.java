package fr.univ_lille.gitlab.classrooms.quiz;

import fr.univ_lille.gitlab.classrooms.assignments.AssignmentScoreService;
import fr.univ_lille.gitlab.classrooms.assignments.AssignmentService;
import fr.univ_lille.gitlab.classrooms.assignments.QuizAssignment;
import fr.univ_lille.gitlab.classrooms.assignments.StudentQuizAssignment;
import fr.univ_lille.gitlab.classrooms.users.ClassroomUserService;
import fr.univ_lille.gitlab.classrooms.users.WithMockStudent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.time.ZonedDateTime;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Sql("/sql/init-test-users.sql")
class QuizAnswerControllerMVCTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClassroomUserService classroomUserService;

    @MockBean
    private AssignmentService assignmentService;

    @MockBean
    private AssignmentScoreService assignmentScoreService;

    @Test
    @WithMockStudent
    void shouldReturn404_whenQuizDoesNotExists() throws Exception {
        mockMvc.perform(get("/quiz/unknown"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockStudent
    void shouldReturnQuizPage_whenQuizExists() throws Exception {
        var quiz = new QuizEntity();
        quiz.setName("death-star-quiz");
        quiz.setMarkdownContent("""
                # who build the Death Star ?
                (x) the Galactic Empire
                ( ) Sauron
                """);

        var assignment = new QuizAssignment();
        assignment.setQuiz(quiz);

        when(assignmentService.getAssignment(assignment.getId())).thenReturn(Optional.of(assignment));

        var student = classroomUserService.getClassroomUser("luke.skywalker");
        when(assignmentService.getAssignmentResultsForStudent(assignment, student)).thenReturn(new StudentQuizAssignment());

        mockMvc.perform(get("/assignments/"+assignment.getId()+"/quiz"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("quiz/answer"))
                .andExpect(model().attributeExists("quiz"));
    }

    @Test
    @WithMockStudent
    void shouldReturnQuizPage_withPreviouslySubmittedAnswer_whenQuizIsRetaken() throws Exception {
        var quiz = new QuizEntity();
        quiz.setName("death-star-quiz");
        quiz.setMarkdownContent("""
                # who build the Death Star ?
                (x) the Galactic Empire
                ( ) Sauron
                """);

        var assignment = new QuizAssignment();
        assignment.setQuiz(quiz);
        assignment.setMaxRetakes(1);

        when(assignmentService.getAssignment(assignment.getId())).thenReturn(Optional.of(assignment));

        var student = classroomUserService.getClassroomUser("luke.skywalker");
        var studentAssignment = new StudentQuizAssignment();
        studentAssignment.setSubmissionDate(ZonedDateTime.now());
        studentAssignment.setAssignment(assignment);

        when(assignmentService.getAssignmentResultsForStudent(assignment, student)).thenReturn(studentAssignment);

        mockMvc.perform(get("/assignments/"+assignment.getId()+"/quiz"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("quiz/answer"))
                .andExpect(model().attributeExists("quiz"))
                .andExpect(model().attributeExists("previousSubmission"));
    }

    @Test
    @WithMockStudent
    void submitQuizAnswers_shouldOutputAnError_whenQuizIsNotFullyAnswered() throws Exception {
        var quiz = new QuizEntity();
        quiz.setName("death-star-quiz");
        quiz.setMarkdownContent("""
                # who build the Death Star ?
                (x) the Galactic Empire
                ( ) Sauron
                """);

        var assignment = new QuizAssignment();
        assignment.setQuiz(quiz);

        when(assignmentService.getAssignment(assignment.getId())).thenReturn(Optional.of(assignment));

        mockMvc.perform(
                    post("/assignments/"+assignment.getId()+"/quiz/submit")
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("quiz/answer"))
                .andExpect(model().attributeExists("quiz"))
                .andExpect(model().attribute("message", "Il manque des réponses à certaines questions."));
    }

    @Test
    @WithMockStudent
    void submitQuizAnswers_shouldSaveTheScore_whenQuizIsAnswered() throws Exception {
        var quiz = new QuizEntity();
        quiz.setName("death-star-quiz");
        quiz.setMarkdownContent("""
                # who build the Death Star ?
                (x) the Galactic Empire
                ( ) Sauron
                """);

        var assignment = new QuizAssignment();
        assignment.setQuiz(quiz);

        var answerKey = Quiz.fromMarkdown(quiz.getMarkdownContent(), "").getQuestions().get(0).getAnswers().get(0).getId();

        when(assignmentService.getAssignment(assignment.getId())).thenReturn(Optional.of(assignment));

        var student = classroomUserService.getClassroomUser("luke.skywalker");
        var studentAssignment = new StudentQuizAssignment();

        mockMvc.perform(
                        post("/assignments/"+assignment.getId()+"/quiz/submit")
                                .with(csrf())
                                .param(answerKey, "the Galactic Empire")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("quiz/results"))
                .andExpect(model().attributeExists("quiz"));

        verify(assignmentScoreService).registerScore(assignment, student, 1, 1);
    }

}

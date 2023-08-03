package fr.univ_lille.gitlab.classrooms.ui;

import fr.univ_lille.gitlab.classrooms.domain.ClassroomRole;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class QuizControllerMVCTest {

    @Autowired
    private MockMvc mockMvc;

    @Nested
    @WithMockClassroomUser(username = "obiwan.kenobi", roles = {ClassroomRole.TEACHER})
    class TeacherRole {

        @Test
        void shouldAccessQuizListPage() throws Exception {
            mockMvc.perform(get("/quiz"))
                    .andDo(print())
                    .andExpect(status().isOk());
        }

        @Test
        void shouldAccessNewQuizPage() throws Exception {
            mockMvc.perform(get("/quiz/new/edit"))
                    .andDo(print())
                    .andExpect(status().isOk());
        }

    }

    @Nested
    @WithMockClassroomUser(username = "luke.skywalker", roles = {ClassroomRole.STUDENT})
    class StudentRole {

        @Test
        void shouldNotAccessQuizListPage() throws Exception {
            mockMvc.perform(get("/quiz"))
                    .andDo(print())
                    .andExpect(status().isForbidden());
        }

        @Test
        void shouldNotAccessNewQuizPage() throws Exception {
            mockMvc.perform(get("/quiz/new/edit"))
                    .andDo(print())
                    .andExpect(status().isForbidden());
        }

    }

}

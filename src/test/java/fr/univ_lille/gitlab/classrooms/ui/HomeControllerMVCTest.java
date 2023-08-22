package fr.univ_lille.gitlab.classrooms.ui;

import fr.univ_lille.gitlab.classrooms.domain.ClassroomService;
import fr.univ_lille.gitlab.classrooms.users.WithMockStudent;
import fr.univ_lille.gitlab.classrooms.users.WithMockTeacher;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = "/sql/init-test-users.sql")
class HomeControllerMVCTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClassroomService classroomService;

    @Nested
    @WithMockTeacher
    class TeacherRole {

        @Test
        void shouldAccessHomePage() throws Exception {
            mockMvc.perform(get("/"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(view().name("dashboard/teacher-dashboard"));
        }
    }

    @Nested
    @WithMockStudent
    class StudentRole {

        @Test
        void shouldNotAccessHomePage() throws Exception {
            mockMvc.perform(get("/"))
                    .andDo(print())
                    .andExpect(status().isForbidden());
        }
    }
}

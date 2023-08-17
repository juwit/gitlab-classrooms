package fr.univ_lille.gitlab.classrooms.ui;

import fr.univ_lille.gitlab.classrooms.domain.ClassroomRole;
import fr.univ_lille.gitlab.classrooms.domain.ClassroomService;
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

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = "/sql/init-test-users.sql")
class HomeControllerMVCTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClassroomService classroomService;

    @Nested
    @WithMockClassroomUser(username = "obiwan.kenobi", roles = {ClassroomRole.TEACHER})
    class TeacherRole {

        @Test
        void shouldAccessHomePage() throws Exception {
            mockMvc.perform(get("/"))
                    .andDo(print())
                    .andExpect(status().isOk());
        }
    }

    @Nested
    @WithMockClassroomUser(username = "luke.skywalker", roles = {ClassroomRole.STUDENT})
    class StudentRole {

        @Test
        void shouldNotAccessHomePage() throws Exception {
            mockMvc.perform(get("/"))
                    .andDo(print())
                    .andExpect(status().isForbidden());
        }
    }
}

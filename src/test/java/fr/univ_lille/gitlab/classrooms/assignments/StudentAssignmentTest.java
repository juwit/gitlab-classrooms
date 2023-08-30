package fr.univ_lille.gitlab.classrooms.assignments;

import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.TimeZone;

import static org.assertj.core.api.Assertions.*;

class StudentAssignmentTest {

    @Test
    void getLocalizedSubmissionDate_shouldChangeTheTimeZoneOfTheDate() {
        var nowAtUTC = ZonedDateTime.now(ZoneId.of("UTC"));

        var studentAssignment = new StudentExerciseAssignment();
        studentAssignment.setSubmissionDate(nowAtUTC);

        LocaleContextHolder.setTimeZone(TimeZone.getTimeZone("CET"));
        var nowAtCET = studentAssignment.getLocalizedSubmissionDate();

        assertThat(nowAtUTC).isEqualTo(nowAtCET);

        assertThat(nowAtUTC.getZone()).isEqualTo(ZoneId.of("UTC"));
        assertThat(nowAtCET.getZone()).isEqualTo(ZoneId.of("CET"));
    }
}

package fr.univ_lille.gitlab.classrooms.assignments.testReports;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import javax.xml.transform.stream.StreamSource;
import java.io.IOException;

import static org.assertj.core.api.Assertions.*;

class TestSuiteTest {

    @Test
    void testReadXmlJaxb() throws IOException, JAXBException {
        var unmarshaller = JAXBContext.newInstance(TestSuite.class).createUnmarshaller();

        var file = new ClassPathResource("/cucumber-junit-output.xml");

        var root = unmarshaller.unmarshal(new StreamSource(file.getInputStream()), TestSuite.class);
        var testSuite = root.getValue();

        assertThat(testSuite.getName()).isEqualTo("Cucumber");
        assertThat(testSuite.getTime()).isEqualTo(10.772f);
        assertThat(testSuite.getTests()).isEqualTo("2");
        assertThat(testSuite.getSkipped()).isEqualTo("0");
        assertThat(testSuite.getFailures()).isEqualTo("1");
        assertThat(testSuite.getErrors()).isEqualTo("0");

        assertThat(testSuite.getTestcase()).hasSize(2);
        var firstTest = testSuite.getTestcase().get(0);
        assertThat(firstTest.getClassname()).isEqualTo("Premier Contrôleur Spring");
        assertThat(firstTest.getFailure()).isNullOrEmpty();
        assertThat(firstTest.getSystemOut()).contains("And La réponse a un body \"Hello World\"");

        var secondTest = testSuite.getTestcase().get(1);
        assertThat(secondTest.getClassname()).isEqualTo("Premier Contrôleur Spring");
        assertThat(secondTest.getFailure()).hasSize(1);
        assertThat(secondTest.getFailure().get(0).getType()).isEqualTo("java.lang.AssertionError");
        assertThat(secondTest.getFailure().get(0).getMessage()).isEqualTo("Element with name 'password_verify' not present.");
        assertThat(secondTest.getFailure().get(0).getValue()).contains("java.lang.AssertionError: Element with name 'password_verify' not present");
    }


}

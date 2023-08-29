package fr.univ_lille.gitlab.classrooms.assignments.grading.junit.reports;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import javax.xml.transform.stream.StreamSource;
import java.io.IOException;

import static org.assertj.core.api.Assertions.*;

class TestSuiteParsingTest {

    @Test
    void testReadCucumberOuptut() throws IOException, JAXBException {
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

    @Test
    void testReadSurefireOutput() throws IOException, JAXBException {
        var unmarshaller = JAXBContext.newInstance(TestSuite.class).createUnmarshaller();

        var file = new ClassPathResource("/junit-reports/TEST-test.dao.CatalogDaoTest.xml");

        var root = unmarshaller.unmarshal(new StreamSource(file.getInputStream()), TestSuite.class);
        var testSuite = root.getValue();

        assertThat(testSuite.getName()).isEqualTo("test.dao.CatalogDaoTest");
        assertThat(testSuite.getTime()).isEqualTo(0.274f);
        assertThat(testSuite.getTests()).isEqualTo("10");
        assertThat(testSuite.getSkipped()).isEqualTo("0");
        assertThat(testSuite.getFailures()).isEqualTo("0");
        assertThat(testSuite.getErrors()).isEqualTo("0");

        assertThat(testSuite.getTestcase()).hasSize(10);
        var firstTest = testSuite.getTestcase().get(0);
        assertThat(firstTest.getClassname()).isEqualTo("test.dao.CatalogDaoTest");
        assertThat(firstTest.getName()).isEqualTo("test_getArticleCategories_match");
        assertThat(firstTest.getFailure()).isNullOrEmpty();

        var secondTest = testSuite.getTestcase().get(1);
        assertThat(secondTest.getClassname()).isEqualTo("test.dao.CatalogDaoTest");
        assertThat(secondTest.getName()).isEqualTo("test_getCategories");
        assertThat(secondTest.getFailure()).isNullOrEmpty();
    }

}

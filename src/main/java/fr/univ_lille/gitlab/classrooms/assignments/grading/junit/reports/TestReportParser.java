package fr.univ_lille.gitlab.classrooms.assignments.grading.junit.reports;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import org.springframework.stereotype.Component;

import javax.xml.transform.stream.StreamSource;
import java.io.InputStream;

@Component
public class TestReportParser {

    public Testsuite parseTestReport(InputStream inputStream) throws JAXBException {
        var unmarshaller = JAXBContext.newInstance(Testsuite.class).createUnmarshaller();
        var root = unmarshaller.unmarshal(new StreamSource(inputStream), Testsuite.class);
        return root.getValue();
    }

}

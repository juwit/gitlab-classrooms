package fr.univ_lille.gitlab.classrooms.assignments.grading.junit.reports;

import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each
 * Java content interface and Java element interface
 * generated in the generated package.
 * <p>An ObjectFactory allows you to programatically
 * construct new instances of the Java representation
 * for XML content. The Java representation of XML
 * content can consist of schema derived interfaces
 * and classes representing the binding of schema
 * type definitions, element declarations and model
 * groups.  Factory methods for each of these are
 * provided in this class.
 *
 */
@XmlRegistry
public class ObjectFactory {

    private static final QName _TestSuiteTestcaseSkipped_QNAME = new QName("", "skipped");
    private static final QName _TestSuiteTestcaseError_QNAME = new QName("", "error");

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TestSuite.Testcase.Skipped }{@code >}
     *
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link TestSuite.Testcase.Skipped }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "skipped", scope = TestSuite.Testcase.class)
    public JAXBElement<TestSuite.Testcase.Skipped> createTestSuiteTestcaseSkipped(TestSuite.Testcase.Skipped value) {
        return new JAXBElement<>(_TestSuiteTestcaseSkipped_QNAME, TestSuite.Testcase.Skipped.class, TestSuite.Testcase.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TestSuite.Testcase.Error }{@code >}
     *
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link TestSuite.Testcase.Error }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "error", scope = TestSuite.Testcase.class)
    public JAXBElement<TestSuite.Testcase.Error> createTestSuiteTestcaseError(TestSuite.Testcase.Error value) {
        return new JAXBElement<>(_TestSuiteTestcaseError_QNAME, TestSuite.Testcase.Error.class, TestSuite.Testcase.class, value);
    }

}

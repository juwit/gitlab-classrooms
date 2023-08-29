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

    private final static QName _TestSuiteTestcaseSkipped_QNAME = new QName("", "skipped");
    private final static QName _TestSuiteTestcaseError_QNAME = new QName("", "error");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: generated
     *
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link TestSuite }
     *
     */
    public TestSuite createTestSuite() {
        return new TestSuite();
    }

    /**
     * Create an instance of {@link TestSuite.Testcase }
     *
     */
    public TestSuite.Testcase createTestSuiteTestcase() {
        return new TestSuite.Testcase();
    }

    /**
     * Create an instance of {@link TestSuite.Properties }
     *
     */
    public TestSuite.Properties createTestSuiteProperties() {
        return new TestSuite.Properties();
    }

    /**
     * Create an instance of {@link TestSuite.Testcase.Failure }
     *
     */
    public TestSuite.Testcase.Failure createTestSuiteTestcaseFailure() {
        return new TestSuite.Testcase.Failure();
    }

    /**
     * Create an instance of {@link TestSuite.Testcase.RerunFailure }
     *
     */
    public TestSuite.Testcase.RerunFailure createTestSuiteTestcaseRerunFailure() {
        return new TestSuite.Testcase.RerunFailure();
    }

    /**
     * Create an instance of {@link TestSuite.Testcase.FlakyFailure }
     *
     */
    public TestSuite.Testcase.FlakyFailure createTestSuiteTestcaseFlakyFailure() {
        return new TestSuite.Testcase.FlakyFailure();
    }

    /**
     * Create an instance of {@link TestSuite.Testcase.Skipped }
     *
     */
    public TestSuite.Testcase.Skipped createTestSuiteTestcaseSkipped() {
        return new TestSuite.Testcase.Skipped();
    }

    /**
     * Create an instance of {@link TestSuite.Testcase.Error }
     *
     */
    public TestSuite.Testcase.Error createTestSuiteTestcaseError() {
        return new TestSuite.Testcase.Error();
    }

    /**
     * Create an instance of {@link TestSuite.Testcase.RerunError }
     *
     */
    public TestSuite.Testcase.RerunError createTestSuiteTestcaseRerunError() {
        return new TestSuite.Testcase.RerunError();
    }

    /**
     * Create an instance of {@link TestSuite.Testcase.FlakyError }
     *
     */
    public TestSuite.Testcase.FlakyError createTestSuiteTestcaseFlakyError() {
        return new TestSuite.Testcase.FlakyError();
    }

    /**
     * Create an instance of {@link TestSuite.Properties.Property }
     *
     */
    public TestSuite.Properties.Property createTestSuitePropertiesProperty() {
        return new TestSuite.Properties.Property();
    }

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
        return new JAXBElement<TestSuite.Testcase.Skipped>(_TestSuiteTestcaseSkipped_QNAME, TestSuite.Testcase.Skipped.class, TestSuite.Testcase.class, value);
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
        return new JAXBElement<TestSuite.Testcase.Error>(_TestSuiteTestcaseError_QNAME, TestSuite.Testcase.Error.class, TestSuite.Testcase.class, value);
    }

}

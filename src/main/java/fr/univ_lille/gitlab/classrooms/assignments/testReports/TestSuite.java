package fr.univ_lille.gitlab.classrooms.assignments.testReports;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementRef;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.XmlValue;


/**
 * <p>Java class for anonymous complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="properties" maxOccurs="unbounded" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="property" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                           &lt;attribute name="value" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="testcase" maxOccurs="unbounded" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="failure" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;simpleContent&gt;
 *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
 *                           &lt;attribute name="message" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                           &lt;attribute name="type" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                         &lt;/extension&gt;
 *                       &lt;/simpleContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="rerunFailure" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="stackTrace" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="system-out" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="system-err" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                           &lt;/sequence&gt;
 *                           &lt;attribute name="message" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                           &lt;attribute name="type" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="flakyFailure" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="stackTrace" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="system-out" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="system-err" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                           &lt;/sequence&gt;
 *                           &lt;attribute name="message" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                           &lt;attribute name="type" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="skipped" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;simpleContent&gt;
 *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
 *                           &lt;attribute name="message" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                         &lt;/extension&gt;
 *                       &lt;/simpleContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="error" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;simpleContent&gt;
 *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
 *                           &lt;attribute name="message" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                           &lt;attribute name="type" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                         &lt;/extension&gt;
 *                       &lt;/simpleContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="rerunError" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="stackTrace" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="system-out" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="system-err" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                           &lt;/sequence&gt;
 *                           &lt;attribute name="message" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                           &lt;attribute name="type" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="flakyError" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="stackTrace" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="system-out" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="system-err" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                           &lt;/sequence&gt;
 *                           &lt;attribute name="message" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                           &lt;attribute name="type" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="system-out" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="system-err" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                 &lt;/sequence&gt;
 *                 &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                 &lt;attribute name="classname" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                 &lt;attribute name="group" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                 &lt;attribute name="time" use="required" type="{http://www.w3.org/2001/XMLSchema}float" /&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="version" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="time" type="{http://www.w3.org/2001/XMLSchema}float" /&gt;
 *       &lt;attribute name="tests" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="errors" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="skipped" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="failures" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="group" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "properties",
    "testcase"
})
@XmlRootElement(name = "testsuite")
public class TestSuite {

    protected List<Properties> properties;
    protected List<Testcase> testcase;
    @XmlAttribute(name = "version")
    protected String version;
    @XmlAttribute(name = "name", required = true)
    protected String name;
    @XmlAttribute(name = "time")
    protected Float time;
    @XmlAttribute(name = "tests", required = true)
    protected String tests;
    @XmlAttribute(name = "errors", required = true)
    protected String errors;
    @XmlAttribute(name = "skipped", required = true)
    protected String skipped;
    @XmlAttribute(name = "failures", required = true)
    protected String failures;
    @XmlAttribute(name = "group")
    protected String group;

    /**
     * Gets the value of the properties property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the properties property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProperties().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Properties }
     *
     *
     */
    public List<Properties> getProperties() {
        if (properties == null) {
            properties = new ArrayList<Properties>();
        }
        return this.properties;
    }

    /**
     * Gets the value of the testcase property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the testcase property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTestcase().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Testcase }
     *
     *
     */
    public List<Testcase> getTestcase() {
        if (testcase == null) {
            testcase = new ArrayList<Testcase>();
        }
        return this.testcase;
    }

    /**
     * Gets the value of the version property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setVersion(String value) {
        this.version = value;
    }

    /**
     * Gets the value of the name property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the time property.
     *
     * @return
     *     possible object is
     *     {@link Float }
     *
     */
    public Float getTime() {
        return time;
    }

    /**
     * Sets the value of the time property.
     *
     * @param value
     *     allowed object is
     *     {@link Float }
     *
     */
    public void setTime(Float value) {
        this.time = value;
    }

    /**
     * Gets the value of the tests property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getTests() {
        return tests;
    }

    /**
     * Sets the value of the tests property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setTests(String value) {
        this.tests = value;
    }

    /**
     * Gets the value of the errors property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getErrors() {
        return errors;
    }

    /**
     * Sets the value of the errors property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setErrors(String value) {
        this.errors = value;
    }

    /**
     * Gets the value of the skipped property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getSkipped() {
        return skipped;
    }

    /**
     * Sets the value of the skipped property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setSkipped(String value) {
        this.skipped = value;
    }

    /**
     * Gets the value of the failures property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getFailures() {
        return failures;
    }

    /**
     * Sets the value of the failures property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setFailures(String value) {
        this.failures = value;
    }

    /**
     * Gets the value of the group property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getGroup() {
        return group;
    }

    /**
     * Sets the value of the group property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setGroup(String value) {
        this.group = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     *
     * <p>The following schema fragment specifies the expected content contained within this class.
     *
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="property" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *                 &lt;attribute name="value" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *       &lt;/sequence&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     *
     *
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "property"
    })
    public static class Properties {

        protected List<Property> property;

        /**
         * Gets the value of the property property.
         *
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the property property.
         *
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getProperty().add(newItem);
         * </pre>
         *
         *
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Property }
         *
         *
         */
        public List<Property> getProperty() {
            if (property == null) {
                property = new ArrayList<Property>();
            }
            return this.property;
        }


        /**
         * <p>Java class for anonymous complex type.
         *
         * <p>The following schema fragment specifies the expected content contained within this class.
         *
         * <pre>
         * &lt;complexType&gt;
         *   &lt;complexContent&gt;
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
         *       &lt;attribute name="value" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         *
         *
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "")
        public static class Property {

            @XmlAttribute(name = "name", required = true)
            protected String name;
            @XmlAttribute(name = "value", required = true)
            protected String value;

            /**
             * Gets the value of the name property.
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getName() {
                return name;
            }

            /**
             * Sets the value of the name property.
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setName(String value) {
                this.name = value;
            }

            /**
             * Gets the value of the value property.
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getValue() {
                return value;
            }

            /**
             * Sets the value of the value property.
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setValue(String value) {
                this.value = value;
            }

        }

    }


    /**
     * <p>Java class for anonymous complex type.
     *
     * <p>The following schema fragment specifies the expected content contained within this class.
     *
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="failure" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;simpleContent&gt;
     *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
     *                 &lt;attribute name="message" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *                 &lt;attribute name="type" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *               &lt;/extension&gt;
     *             &lt;/simpleContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="rerunFailure" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="stackTrace" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="system-out" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="system-err" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                 &lt;/sequence&gt;
     *                 &lt;attribute name="message" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *                 &lt;attribute name="type" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="flakyFailure" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="stackTrace" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="system-out" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="system-err" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                 &lt;/sequence&gt;
     *                 &lt;attribute name="message" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *                 &lt;attribute name="type" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="skipped" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;simpleContent&gt;
     *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
     *                 &lt;attribute name="message" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *               &lt;/extension&gt;
     *             &lt;/simpleContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="error" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;simpleContent&gt;
     *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
     *                 &lt;attribute name="message" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *                 &lt;attribute name="type" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *               &lt;/extension&gt;
     *             &lt;/simpleContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="rerunError" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="stackTrace" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="system-out" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="system-err" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                 &lt;/sequence&gt;
     *                 &lt;attribute name="message" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *                 &lt;attribute name="type" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="flakyError" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="stackTrace" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="system-out" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="system-err" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                 &lt;/sequence&gt;
     *                 &lt;attribute name="message" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *                 &lt;attribute name="type" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="system-out" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="system-err" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *       &lt;/sequence&gt;
     *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *       &lt;attribute name="classname" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *       &lt;attribute name="group" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *       &lt;attribute name="time" use="required" type="{http://www.w3.org/2001/XMLSchema}float" /&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     *
     *
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "failure",
        "rerunFailure",
        "flakyFailure",
        "skipped",
        "error",
        "rerunError",
        "flakyError",
        "systemOut",
        "systemErr"
    })
    public static class Testcase {

        @XmlElement(nillable = true)
        protected List<Failure> failure;
        protected List<RerunFailure> rerunFailure;
        protected List<FlakyFailure> flakyFailure;
        @XmlElementRef(name = "skipped", type = JAXBElement.class, required = false)
        protected JAXBElement<Skipped> skipped;
        @XmlElementRef(name = "error", type = JAXBElement.class, required = false)
        protected JAXBElement<Error> error;
        protected List<RerunError> rerunError;
        protected List<FlakyError> flakyError;
        @XmlElement(name = "system-out")
        protected String systemOut;
        @XmlElement(name = "system-err")
        protected String systemErr;
        @XmlAttribute(name = "name", required = true)
        protected String name;
        @XmlAttribute(name = "classname")
        protected String classname;
        @XmlAttribute(name = "group")
        protected String group;
        @XmlAttribute(name = "time", required = true)
        protected float time;

        /**
         * Gets the value of the failure property.
         *
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the failure property.
         *
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getFailure().add(newItem);
         * </pre>
         *
         *
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Failure }
         *
         *
         */
        public List<Failure> getFailure() {
            if (failure == null) {
                failure = new ArrayList<Failure>();
            }
            return this.failure;
        }

        /**
         * Gets the value of the rerunFailure property.
         *
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the rerunFailure property.
         *
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getRerunFailure().add(newItem);
         * </pre>
         *
         *
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link RerunFailure }
         *
         *
         */
        public List<RerunFailure> getRerunFailure() {
            if (rerunFailure == null) {
                rerunFailure = new ArrayList<RerunFailure>();
            }
            return this.rerunFailure;
        }

        /**
         * Gets the value of the flakyFailure property.
         *
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the flakyFailure property.
         *
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getFlakyFailure().add(newItem);
         * </pre>
         *
         *
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link FlakyFailure }
         *
         *
         */
        public List<FlakyFailure> getFlakyFailure() {
            if (flakyFailure == null) {
                flakyFailure = new ArrayList<FlakyFailure>();
            }
            return this.flakyFailure;
        }

        /**
         * Gets the value of the skipped property.
         *
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link Skipped }{@code >}
         *
         */
        public JAXBElement<Skipped> getSkipped() {
            return skipped;
        }

        /**
         * Sets the value of the skipped property.
         *
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link Skipped }{@code >}
         *
         */
        public void setSkipped(JAXBElement<Skipped> value) {
            this.skipped = value;
        }

        /**
         * Gets the value of the error property.
         *
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link Error }{@code >}
         *
         */
        public JAXBElement<Error> getError() {
            return error;
        }

        /**
         * Sets the value of the error property.
         *
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link Error }{@code >}
         *
         */
        public void setError(JAXBElement<Error> value) {
            this.error = value;
        }

        /**
         * Gets the value of the rerunError property.
         *
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the rerunError property.
         *
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getRerunError().add(newItem);
         * </pre>
         *
         *
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link RerunError }
         *
         *
         */
        public List<RerunError> getRerunError() {
            if (rerunError == null) {
                rerunError = new ArrayList<RerunError>();
            }
            return this.rerunError;
        }

        /**
         * Gets the value of the flakyError property.
         *
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the flakyError property.
         *
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getFlakyError().add(newItem);
         * </pre>
         *
         *
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link FlakyError }
         *
         *
         */
        public List<FlakyError> getFlakyError() {
            if (flakyError == null) {
                flakyError = new ArrayList<FlakyError>();
            }
            return this.flakyError;
        }

        /**
         * Gets the value of the systemOut property.
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        public String getSystemOut() {
            return systemOut;
        }

        /**
         * Sets the value of the systemOut property.
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        public void setSystemOut(String value) {
            this.systemOut = value;
        }

        /**
         * Gets the value of the systemErr property.
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        public String getSystemErr() {
            return systemErr;
        }

        /**
         * Sets the value of the systemErr property.
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        public void setSystemErr(String value) {
            this.systemErr = value;
        }

        /**
         * Gets the value of the name property.
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        public String getName() {
            return name;
        }

        /**
         * Sets the value of the name property.
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        public void setName(String value) {
            this.name = value;
        }

        /**
         * Gets the value of the classname property.
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        public String getClassname() {
            return classname;
        }

        /**
         * Sets the value of the classname property.
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        public void setClassname(String value) {
            this.classname = value;
        }

        /**
         * Gets the value of the group property.
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        public String getGroup() {
            return group;
        }

        /**
         * Sets the value of the group property.
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        public void setGroup(String value) {
            this.group = value;
        }

        /**
         * Gets the value of the time property.
         *
         */
        public float getTime() {
            return time;
        }

        /**
         * Sets the value of the time property.
         *
         */
        public void setTime(float value) {
            this.time = value;
        }


        /**
         * <p>Java class for anonymous complex type.
         *
         * <p>The following schema fragment specifies the expected content contained within this class.
         *
         * <pre>
         * &lt;complexType&gt;
         *   &lt;simpleContent&gt;
         *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
         *       &lt;attribute name="message" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
         *       &lt;attribute name="type" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
         *     &lt;/extension&gt;
         *   &lt;/simpleContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         *
         *
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "value"
        })
        public static class Error {

            @XmlValue
            protected String value;
            @XmlAttribute(name = "message")
            protected String message;
            @XmlAttribute(name = "type", required = true)
            protected String type;

            /**
             * Gets the value of the value property.
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getValue() {
                return value;
            }

            /**
             * Sets the value of the value property.
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setValue(String value) {
                this.value = value;
            }

            /**
             * Gets the value of the message property.
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getMessage() {
                return message;
            }

            /**
             * Sets the value of the message property.
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setMessage(String value) {
                this.message = value;
            }

            /**
             * Gets the value of the type property.
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getType() {
                return type;
            }

            /**
             * Sets the value of the type property.
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setType(String value) {
                this.type = value;
            }

        }


        /**
         * <p>Java class for anonymous complex type.
         *
         * <p>The following schema fragment specifies the expected content contained within this class.
         *
         * <pre>
         * &lt;complexType&gt;
         *   &lt;simpleContent&gt;
         *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
         *       &lt;attribute name="message" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
         *       &lt;attribute name="type" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
         *     &lt;/extension&gt;
         *   &lt;/simpleContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         *
         *
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "value"
        })
        public static class Failure {

            @XmlValue
            protected String value;
            @XmlAttribute(name = "message")
            protected String message;
            @XmlAttribute(name = "type", required = true)
            protected String type;

            /**
             * Gets the value of the value property.
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getValue() {
                return value;
            }

            /**
             * Sets the value of the value property.
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setValue(String value) {
                this.value = value;
            }

            /**
             * Gets the value of the message property.
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getMessage() {
                return message;
            }

            /**
             * Sets the value of the message property.
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setMessage(String value) {
                this.message = value;
            }

            /**
             * Gets the value of the type property.
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getType() {
                return type;
            }

            /**
             * Sets the value of the type property.
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setType(String value) {
                this.type = value;
            }

        }


        /**
         * <p>Java class for anonymous complex type.
         *
         * <p>The following schema fragment specifies the expected content contained within this class.
         *
         * <pre>
         * &lt;complexType&gt;
         *   &lt;complexContent&gt;
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *       &lt;sequence&gt;
         *         &lt;element name="stackTrace" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="system-out" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="system-err" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *       &lt;/sequence&gt;
         *       &lt;attribute name="message" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
         *       &lt;attribute name="type" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         *
         *
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "stackTrace",
            "systemOut",
            "systemErr"
        })
        public static class FlakyError {

            @XmlElement(required = true)
            protected String stackTrace;
            @XmlElement(name = "system-out")
            protected String systemOut;
            @XmlElement(name = "system-err")
            protected String systemErr;
            @XmlAttribute(name = "message")
            protected String message;
            @XmlAttribute(name = "type", required = true)
            protected String type;

            /**
             * Gets the value of the stackTrace property.
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getStackTrace() {
                return stackTrace;
            }

            /**
             * Sets the value of the stackTrace property.
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setStackTrace(String value) {
                this.stackTrace = value;
            }

            /**
             * Gets the value of the systemOut property.
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getSystemOut() {
                return systemOut;
            }

            /**
             * Sets the value of the systemOut property.
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setSystemOut(String value) {
                this.systemOut = value;
            }

            /**
             * Gets the value of the systemErr property.
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getSystemErr() {
                return systemErr;
            }

            /**
             * Sets the value of the systemErr property.
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setSystemErr(String value) {
                this.systemErr = value;
            }

            /**
             * Gets the value of the message property.
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getMessage() {
                return message;
            }

            /**
             * Sets the value of the message property.
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setMessage(String value) {
                this.message = value;
            }

            /**
             * Gets the value of the type property.
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getType() {
                return type;
            }

            /**
             * Sets the value of the type property.
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setType(String value) {
                this.type = value;
            }

        }


        /**
         * <p>Java class for anonymous complex type.
         *
         * <p>The following schema fragment specifies the expected content contained within this class.
         *
         * <pre>
         * &lt;complexType&gt;
         *   &lt;complexContent&gt;
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *       &lt;sequence&gt;
         *         &lt;element name="stackTrace" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="system-out" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="system-err" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *       &lt;/sequence&gt;
         *       &lt;attribute name="message" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
         *       &lt;attribute name="type" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         *
         *
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "stackTrace",
            "systemOut",
            "systemErr"
        })
        public static class FlakyFailure {

            @XmlElement(required = true)
            protected String stackTrace;
            @XmlElement(name = "system-out")
            protected String systemOut;
            @XmlElement(name = "system-err")
            protected String systemErr;
            @XmlAttribute(name = "message")
            protected String message;
            @XmlAttribute(name = "type", required = true)
            protected String type;

            /**
             * Gets the value of the stackTrace property.
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getStackTrace() {
                return stackTrace;
            }

            /**
             * Sets the value of the stackTrace property.
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setStackTrace(String value) {
                this.stackTrace = value;
            }

            /**
             * Gets the value of the systemOut property.
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getSystemOut() {
                return systemOut;
            }

            /**
             * Sets the value of the systemOut property.
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setSystemOut(String value) {
                this.systemOut = value;
            }

            /**
             * Gets the value of the systemErr property.
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getSystemErr() {
                return systemErr;
            }

            /**
             * Sets the value of the systemErr property.
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setSystemErr(String value) {
                this.systemErr = value;
            }

            /**
             * Gets the value of the message property.
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getMessage() {
                return message;
            }

            /**
             * Sets the value of the message property.
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setMessage(String value) {
                this.message = value;
            }

            /**
             * Gets the value of the type property.
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getType() {
                return type;
            }

            /**
             * Sets the value of the type property.
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setType(String value) {
                this.type = value;
            }

        }


        /**
         * <p>Java class for anonymous complex type.
         *
         * <p>The following schema fragment specifies the expected content contained within this class.
         *
         * <pre>
         * &lt;complexType&gt;
         *   &lt;complexContent&gt;
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *       &lt;sequence&gt;
         *         &lt;element name="stackTrace" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="system-out" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="system-err" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *       &lt;/sequence&gt;
         *       &lt;attribute name="message" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
         *       &lt;attribute name="type" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         *
         *
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "stackTrace",
            "systemOut",
            "systemErr"
        })
        public static class RerunError {

            @XmlElement(required = true)
            protected String stackTrace;
            @XmlElement(name = "system-out")
            protected String systemOut;
            @XmlElement(name = "system-err")
            protected String systemErr;
            @XmlAttribute(name = "message")
            protected String message;
            @XmlAttribute(name = "type", required = true)
            protected String type;

            /**
             * Gets the value of the stackTrace property.
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getStackTrace() {
                return stackTrace;
            }

            /**
             * Sets the value of the stackTrace property.
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setStackTrace(String value) {
                this.stackTrace = value;
            }

            /**
             * Gets the value of the systemOut property.
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getSystemOut() {
                return systemOut;
            }

            /**
             * Sets the value of the systemOut property.
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setSystemOut(String value) {
                this.systemOut = value;
            }

            /**
             * Gets the value of the systemErr property.
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getSystemErr() {
                return systemErr;
            }

            /**
             * Sets the value of the systemErr property.
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setSystemErr(String value) {
                this.systemErr = value;
            }

            /**
             * Gets the value of the message property.
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getMessage() {
                return message;
            }

            /**
             * Sets the value of the message property.
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setMessage(String value) {
                this.message = value;
            }

            /**
             * Gets the value of the type property.
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getType() {
                return type;
            }

            /**
             * Sets the value of the type property.
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setType(String value) {
                this.type = value;
            }

        }


        /**
         * <p>Java class for anonymous complex type.
         *
         * <p>The following schema fragment specifies the expected content contained within this class.
         *
         * <pre>
         * &lt;complexType&gt;
         *   &lt;complexContent&gt;
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *       &lt;sequence&gt;
         *         &lt;element name="stackTrace" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="system-out" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="system-err" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *       &lt;/sequence&gt;
         *       &lt;attribute name="message" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
         *       &lt;attribute name="type" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         *
         *
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "stackTrace",
            "systemOut",
            "systemErr"
        })
        public static class RerunFailure {

            @XmlElement(required = true)
            protected String stackTrace;
            @XmlElement(name = "system-out")
            protected String systemOut;
            @XmlElement(name = "system-err")
            protected String systemErr;
            @XmlAttribute(name = "message")
            protected String message;
            @XmlAttribute(name = "type", required = true)
            protected String type;

            /**
             * Gets the value of the stackTrace property.
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getStackTrace() {
                return stackTrace;
            }

            /**
             * Sets the value of the stackTrace property.
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setStackTrace(String value) {
                this.stackTrace = value;
            }

            /**
             * Gets the value of the systemOut property.
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getSystemOut() {
                return systemOut;
            }

            /**
             * Sets the value of the systemOut property.
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setSystemOut(String value) {
                this.systemOut = value;
            }

            /**
             * Gets the value of the systemErr property.
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getSystemErr() {
                return systemErr;
            }

            /**
             * Sets the value of the systemErr property.
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setSystemErr(String value) {
                this.systemErr = value;
            }

            /**
             * Gets the value of the message property.
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getMessage() {
                return message;
            }

            /**
             * Sets the value of the message property.
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setMessage(String value) {
                this.message = value;
            }

            /**
             * Gets the value of the type property.
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getType() {
                return type;
            }

            /**
             * Sets the value of the type property.
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setType(String value) {
                this.type = value;
            }

        }


        /**
         * <p>Java class for anonymous complex type.
         *
         * <p>The following schema fragment specifies the expected content contained within this class.
         *
         * <pre>
         * &lt;complexType&gt;
         *   &lt;simpleContent&gt;
         *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
         *       &lt;attribute name="message" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
         *     &lt;/extension&gt;
         *   &lt;/simpleContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         *
         *
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "value"
        })
        public static class Skipped {

            @XmlValue
            protected String value;
            @XmlAttribute(name = "message")
            protected String message;

            /**
             * Gets the value of the value property.
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getValue() {
                return value;
            }

            /**
             * Sets the value of the value property.
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setValue(String value) {
                this.value = value;
            }

            /**
             * Gets the value of the message property.
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getMessage() {
                return message;
            }

            /**
             * Sets the value of the message property.
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setMessage(String value) {
                this.message = value;
            }

        }

    }

}

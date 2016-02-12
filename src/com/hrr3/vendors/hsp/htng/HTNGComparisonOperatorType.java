
package com.hrr3.vendors.hsp.htng;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for HTNG_ComparisonOperatorType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="HTNG_ComparisonOperatorType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Equals"/>
 *     &lt;enumeration value="Does Not Equal"/>
 *     &lt;enumeration value="Contains"/>
 *     &lt;enumeration value="Does Not Contain"/>
 *     &lt;enumeration value="Begins With"/>
 *     &lt;enumeration value="Does Not Begin With"/>
 *     &lt;enumeration value="Ends With"/>
 *     &lt;enumeration value="Does Not End With"/>
 *     &lt;enumeration value="Contains Data"/>
 *     &lt;enumeration value="Does Not Contain Data"/>
 *     &lt;enumeration value="Is Greater Than"/>
 *     &lt;enumeration value="Is Greater Than or Equal To"/>
 *     &lt;enumeration value="Is Less Than"/>
 *     &lt;enumeration value="Is Less Than or Equal To"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "HTNG_ComparisonOperatorType")
@XmlEnum
public enum HTNGComparisonOperatorType {

    @XmlEnumValue("Equals")
    EQUALS("Equals"),
    @XmlEnumValue("Does Not Equal")
    DOES_NOT_EQUAL("Does Not Equal"),
    @XmlEnumValue("Contains")
    CONTAINS("Contains"),
    @XmlEnumValue("Does Not Contain")
    DOES_NOT_CONTAIN("Does Not Contain"),
    @XmlEnumValue("Begins With")
    BEGINS_WITH("Begins With"),
    @XmlEnumValue("Does Not Begin With")
    DOES_NOT_BEGIN_WITH("Does Not Begin With"),
    @XmlEnumValue("Ends With")
    ENDS_WITH("Ends With"),
    @XmlEnumValue("Does Not End With")
    DOES_NOT_END_WITH("Does Not End With"),
    @XmlEnumValue("Contains Data")
    CONTAINS_DATA("Contains Data"),
    @XmlEnumValue("Does Not Contain Data")
    DOES_NOT_CONTAIN_DATA("Does Not Contain Data"),
    @XmlEnumValue("Is Greater Than")
    IS_GREATER_THAN("Is Greater Than"),
    @XmlEnumValue("Is Greater Than or Equal To")
    IS_GREATER_THAN_OR_EQUAL_TO("Is Greater Than or Equal To"),
    @XmlEnumValue("Is Less Than")
    IS_LESS_THAN("Is Less Than"),
    @XmlEnumValue("Is Less Than or Equal To")
    IS_LESS_THAN_OR_EQUAL_TO("Is Less Than or Equal To");
    private final String value;

    HTNGComparisonOperatorType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static HTNGComparisonOperatorType fromValue(String v) {
        for (HTNGComparisonOperatorType c: HTNGComparisonOperatorType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}

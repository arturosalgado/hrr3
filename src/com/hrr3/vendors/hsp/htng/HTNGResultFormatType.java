
package com.hrr3.vendors.hsp.htng;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for HTNG_ResultFormatType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="HTNG_ResultFormatType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="XML"/>
 *     &lt;enumeration value="CSV"/>
 *     &lt;enumeration value="PlainText"/>
 *     &lt;enumeration value="Base64Binary"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "HTNG_ResultFormatType")
@XmlEnum
public enum HTNGResultFormatType {

    XML("XML"),
    CSV("CSV"),
    @XmlEnumValue("PlainText")
    PLAIN_TEXT("PlainText"),
    @XmlEnumValue("Base64Binary")
    BASE_64_BINARY("Base64Binary");
    private final String value;

    HTNGResultFormatType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static HTNGResultFormatType fromValue(String v) {
        for (HTNGResultFormatType c: HTNGResultFormatType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}


package com.hrr3.vendors.hsp.htng;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for HTNG_QueryResultListType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="HTNG_QueryResultListType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="All Ascending"/>
 *     &lt;enumeration value="All Descending"/>
 *     &lt;enumeration value="Top Ascending"/>
 *     &lt;enumeration value="Top Descending"/>
 *     &lt;enumeration value="Bytes"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "HTNG_QueryResultListType")
@XmlEnum
public enum HTNGQueryResultListType {

    @XmlEnumValue("All Ascending")
    ALL_ASCENDING("All Ascending"),
    @XmlEnumValue("All Descending")
    ALL_DESCENDING("All Descending"),
    @XmlEnumValue("Top Ascending")
    TOP_ASCENDING("Top Ascending"),
    @XmlEnumValue("Top Descending")
    TOP_DESCENDING("Top Descending"),
    @XmlEnumValue("Bytes")
    BYTES("Bytes");
    private final String value;

    HTNGQueryResultListType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static HTNGQueryResultListType fromValue(String v) {
        for (HTNGQueryResultListType c: HTNGQueryResultListType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}

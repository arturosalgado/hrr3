
package com.hrr3.vendors.hsp;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for HSP_AccessTokenResponseType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="HSP_AccessTokenResponseType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Token"/>
 *     &lt;enumeration value="Error"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "HSP_AccessTokenResponseType")
@XmlEnum
public enum HSPAccessTokenResponseType {

    @XmlEnumValue("Token")
    TOKEN("Token"),
    @XmlEnumValue("Error")
    ERROR("Error");
    private final String value;

    HSPAccessTokenResponseType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static HSPAccessTokenResponseType fromValue(String v) {
        for (HSPAccessTokenResponseType c: HSPAccessTokenResponseType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}

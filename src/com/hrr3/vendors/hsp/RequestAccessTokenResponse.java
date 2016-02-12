
package com.hrr3.vendors.hsp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="RequestAccessTokenResult" type="{http://www.hotelSystemsPro.com/}AccessTokenResponse" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "requestAccessTokenResult"
})
@XmlRootElement(name = "RequestAccessTokenResponse")
public class RequestAccessTokenResponse {

    @XmlElement(name = "RequestAccessTokenResult")
    protected AccessTokenResponse requestAccessTokenResult;

    /**
     * Gets the value of the requestAccessTokenResult property.
     * 
     * @return
     *     possible object is
     *     {@link AccessTokenResponse }
     *     
     */
    public AccessTokenResponse getRequestAccessTokenResult() {
        return requestAccessTokenResult;
    }

    /**
     * Sets the value of the requestAccessTokenResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link AccessTokenResponse }
     *     
     */
    public void setRequestAccessTokenResult(AccessTokenResponse value) {
        this.requestAccessTokenResult = value;
    }

}

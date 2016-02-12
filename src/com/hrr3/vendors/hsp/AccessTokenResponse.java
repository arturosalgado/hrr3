
package com.hrr3.vendors.hsp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for AccessTokenResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AccessTokenResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="HSP_AccessTokenResponse" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="ResponseType" type="{http://www.hotelSystemsPro.com/}HSP_AccessTokenResponseType"/>
 *                 &lt;/sequence>
 *                 &lt;attribute name="ErrorResponse" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="TokenExpiration" use="required" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *                 &lt;attribute name="AccessToken" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AccessTokenResponse", propOrder = {
    "hspAccessTokenResponse"
})
public class AccessTokenResponse {

    @XmlElement(name = "HSP_AccessTokenResponse")
    protected AccessTokenResponse.HSPAccessTokenResponse hspAccessTokenResponse;

    /**
     * Gets the value of the hspAccessTokenResponse property.
     * 
     * @return
     *     possible object is
     *     {@link AccessTokenResponse.HSPAccessTokenResponse }
     *     
     */
    public AccessTokenResponse.HSPAccessTokenResponse getHSPAccessTokenResponse() {
        return hspAccessTokenResponse;
    }

    /**
     * Sets the value of the hspAccessTokenResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link AccessTokenResponse.HSPAccessTokenResponse }
     *     
     */
    public void setHSPAccessTokenResponse(AccessTokenResponse.HSPAccessTokenResponse value) {
        this.hspAccessTokenResponse = value;
    }


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
     *         &lt;element name="ResponseType" type="{http://www.hotelSystemsPro.com/}HSP_AccessTokenResponseType"/>
     *       &lt;/sequence>
     *       &lt;attribute name="ErrorResponse" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="TokenExpiration" use="required" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
     *       &lt;attribute name="AccessToken" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "responseType"
    })
    public static class HSPAccessTokenResponse {

        @XmlElement(name = "ResponseType", required = true)
        protected HSPAccessTokenResponseType responseType;
        @XmlAttribute(name = "ErrorResponse")
        protected String errorResponse;
        @XmlAttribute(name = "TokenExpiration", required = true)
        @XmlSchemaType(name = "dateTime")
        protected XMLGregorianCalendar tokenExpiration;
        @XmlAttribute(name = "AccessToken")
        protected String accessToken;

        /**
         * Gets the value of the responseType property.
         * 
         * @return
         *     possible object is
         *     {@link HSPAccessTokenResponseType }
         *     
         */
        public HSPAccessTokenResponseType getResponseType() {
            return responseType;
        }

        /**
         * Sets the value of the responseType property.
         * 
         * @param value
         *     allowed object is
         *     {@link HSPAccessTokenResponseType }
         *     
         */
        public void setResponseType(HSPAccessTokenResponseType value) {
            this.responseType = value;
        }

        /**
         * Gets the value of the errorResponse property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getErrorResponse() {
            return errorResponse;
        }

        /**
         * Sets the value of the errorResponse property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setErrorResponse(String value) {
            this.errorResponse = value;
        }

        /**
         * Gets the value of the tokenExpiration property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getTokenExpiration() {
            return tokenExpiration;
        }

        /**
         * Sets the value of the tokenExpiration property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setTokenExpiration(XMLGregorianCalendar value) {
            this.tokenExpiration = value;
        }

        /**
         * Gets the value of the accessToken property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAccessToken() {
            return accessToken;
        }

        /**
         * Sets the value of the accessToken property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAccessToken(String value) {
            this.accessToken = value;
        }

    }

}

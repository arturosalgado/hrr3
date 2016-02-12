
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
 *         &lt;element name="ExecuteQueryResult" type="{http://www.hotelSystemsPro.com/}ExecuteQueryResponse" minOccurs="0"/>
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
    "executeQueryResult"
})
@XmlRootElement(name = "ExecuteQueryResponse")
public class ExecuteQueryResponse {

    @XmlElement(name = "ExecuteQueryResult")
    protected ExecuteQueryResponse2 executeQueryResult;

    /**
     * Gets the value of the executeQueryResult property.
     * 
     * @return
     *     possible object is
     *     {@link ExecuteQueryResponse2 }
     *     
     */
    public ExecuteQueryResponse2 getExecuteQueryResult() {
        return executeQueryResult;
    }

    /**
     * Sets the value of the executeQueryResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExecuteQueryResponse2 }
     *     
     */
    public void setExecuteQueryResult(ExecuteQueryResponse2 value) {
        this.executeQueryResult = value;
    }

}

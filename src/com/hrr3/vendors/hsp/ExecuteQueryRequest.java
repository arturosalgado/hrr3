
package com.hrr3.vendors.hsp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.hrr3.vendors.hsp.htng.HTNGStatisticsRQ;


/**
 * <p>Java class for ExecuteQueryRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ExecuteQueryRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="HTNG_StatisticsRQ" type="{http://htng.org/2010B}HTNG_StatisticsRQ" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ExecuteQueryRequest", propOrder = {
    "htngStatisticsRQ"
})
public class ExecuteQueryRequest {

    @XmlElement(name = "HTNG_StatisticsRQ")
    protected HTNGStatisticsRQ htngStatisticsRQ;

    /**
     * Gets the value of the htngStatisticsRQ property.
     * 
     * @return
     *     possible object is
     *     {@link HTNGStatisticsRQ }
     *     
     */
    public HTNGStatisticsRQ getHTNGStatisticsRQ() {
        return htngStatisticsRQ;
    }

    /**
     * Sets the value of the htngStatisticsRQ property.
     * 
     * @param value
     *     allowed object is
     *     {@link HTNGStatisticsRQ }
     *     
     */
    public void setHTNGStatisticsRQ(HTNGStatisticsRQ value) {
        this.htngStatisticsRQ = value;
    }

}

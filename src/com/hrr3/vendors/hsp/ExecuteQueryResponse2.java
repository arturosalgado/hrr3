
package com.hrr3.vendors.hsp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.hrr3.vendors.hsp.htng.HTNGStatisticsRS;


/**
 * <p>Java class for ExecuteQueryResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ExecuteQueryResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="HTNG_StatisticsRS" type="{http://htng.org/2010B}HTNG_StatisticsRS" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ExecuteQueryResponse", propOrder = {
    "htngStatisticsRS"
})
public class ExecuteQueryResponse2 {

    @XmlElement(name = "HTNG_StatisticsRS")
    protected HTNGStatisticsRS htngStatisticsRS;

    /**
     * Gets the value of the htngStatisticsRS property.
     * 
     * @return
     *     possible object is
     *     {@link HTNGStatisticsRS }
     *     
     */
    public HTNGStatisticsRS getHTNGStatisticsRS() {
        return htngStatisticsRS;
    }

    /**
     * Sets the value of the htngStatisticsRS property.
     * 
     * @param value
     *     allowed object is
     *     {@link HTNGStatisticsRS }
     *     
     */
    public void setHTNGStatisticsRS(HTNGStatisticsRS value) {
        this.htngStatisticsRS = value;
    }

}


package com.hrr3.vendors.hsp.htng;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfHTNG_KeyValueItemsTypeKeyValueItem complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfHTNG_KeyValueItemsTypeKeyValueItem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="KeyValueItem" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="Key" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="Operator" type="{http://htng.org/2010B}HTNG_ComparisonOperatorType" default="Equals" />
 *                 &lt;attribute name="Value" type="{http://www.w3.org/2001/XMLSchema}string" />
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
@XmlType(name = "ArrayOfHTNG_KeyValueItemsTypeKeyValueItem", propOrder = {
    "keyValueItem"
})
public class ArrayOfHTNGKeyValueItemsTypeKeyValueItem {

    @XmlElement(name = "KeyValueItem")
    protected List<ArrayOfHTNGKeyValueItemsTypeKeyValueItem.KeyValueItem> keyValueItem;

    /**
     * Gets the value of the keyValueItem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the keyValueItem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getKeyValueItem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ArrayOfHTNGKeyValueItemsTypeKeyValueItem.KeyValueItem }
     * 
     * 
     */
    public List<ArrayOfHTNGKeyValueItemsTypeKeyValueItem.KeyValueItem> getKeyValueItem() {
        if (keyValueItem == null) {
            keyValueItem = new ArrayList<ArrayOfHTNGKeyValueItemsTypeKeyValueItem.KeyValueItem>();
        }
        return this.keyValueItem;
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
     *       &lt;attribute name="Key" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="Operator" type="{http://htng.org/2010B}HTNG_ComparisonOperatorType" default="Equals" />
     *       &lt;attribute name="Value" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class KeyValueItem {

        @XmlAttribute(name = "Key")
        protected String key;
        @XmlAttribute(name = "Operator")
        protected HTNGComparisonOperatorType operator;
        @XmlAttribute(name = "Value")
        protected String value;

        /**
         * Gets the value of the key property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getKey() {
            return key;
        }

        /**
         * Sets the value of the key property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setKey(String value) {
            this.key = value;
        }

        /**
         * Gets the value of the operator property.
         * 
         * @return
         *     possible object is
         *     {@link HTNGComparisonOperatorType }
         *     
         */
        public HTNGComparisonOperatorType getOperator() {
            if (operator == null) {
                return HTNGComparisonOperatorType.EQUALS;
            } else {
                return operator;
            }
        }

        /**
         * Sets the value of the operator property.
         * 
         * @param value
         *     allowed object is
         *     {@link HTNGComparisonOperatorType }
         *     
         */
        public void setOperator(HTNGComparisonOperatorType value) {
            this.operator = value;
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

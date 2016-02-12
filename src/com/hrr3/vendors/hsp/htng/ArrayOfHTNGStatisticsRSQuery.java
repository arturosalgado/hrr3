
package com.hrr3.vendors.hsp.htng;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfHTNG_StatisticsRSQuery complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfHTNG_StatisticsRSQuery">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Query" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="RequestParameters" type="{http://htng.org/2010B}ArrayOfHTNG_KeyValueItemsTypeKeyValueItem" minOccurs="0"/>
 *                   &lt;element name="ResponseParameters" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;attribute name="ResultFormat" type="{http://htng.org/2010B}HTNG_ResultFormatType" default="XML" />
 *                           &lt;attribute name="Size" type="{http://www.w3.org/2001/XMLSchema}integer" default="1" />
 *                           &lt;attribute name="UnitOfMeasure" type="{http://htng.org/2010B}HTNG_QueryResultListType" default="All Ascending" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="QueryResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="ExecutionTime" type="{http://www.w3.org/2001/XMLSchema}duration" minOccurs="0"/>
 *                   &lt;element name="ExecutionNotes" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                 &lt;/sequence>
 *                 &lt;attribute name="ResultTrackingID" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="QueryTrackingID" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="StoredQueryName" type="{http://www.w3.org/2001/XMLSchema}string" />
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
@XmlType(name = "ArrayOfHTNG_StatisticsRSQuery", propOrder = {
    "query"
})
public class ArrayOfHTNGStatisticsRSQuery {

    @XmlElement(name = "Query")
    protected List<ArrayOfHTNGStatisticsRSQuery.Query> query;

    /**
     * Gets the value of the query property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the query property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getQuery().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ArrayOfHTNGStatisticsRSQuery.Query }
     * 
     * 
     */
    public List<ArrayOfHTNGStatisticsRSQuery.Query> getQuery() {
        if (query == null) {
            query = new ArrayList<ArrayOfHTNGStatisticsRSQuery.Query>();
        }
        return this.query;
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
     *         &lt;element name="RequestParameters" type="{http://htng.org/2010B}ArrayOfHTNG_KeyValueItemsTypeKeyValueItem" minOccurs="0"/>
     *         &lt;element name="ResponseParameters" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;attribute name="ResultFormat" type="{http://htng.org/2010B}HTNG_ResultFormatType" default="XML" />
     *                 &lt;attribute name="Size" type="{http://www.w3.org/2001/XMLSchema}integer" default="1" />
     *                 &lt;attribute name="UnitOfMeasure" type="{http://htng.org/2010B}HTNG_QueryResultListType" default="All Ascending" />
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="QueryResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="ExecutionTime" type="{http://www.w3.org/2001/XMLSchema}duration" minOccurs="0"/>
     *         &lt;element name="ExecutionNotes" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *       &lt;/sequence>
     *       &lt;attribute name="ResultTrackingID" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="QueryTrackingID" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="StoredQueryName" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "requestParameters",
        "responseParameters",
        "queryResult",
        "executionTime",
        "executionNotes"
    })
    public static class Query {

        @XmlElement(name = "RequestParameters")
        protected ArrayOfHTNGKeyValueItemsTypeKeyValueItem requestParameters;
        @XmlElement(name = "ResponseParameters")
        protected ArrayOfHTNGStatisticsRSQuery.Query.ResponseParameters responseParameters;
        @XmlElement(name = "QueryResult")
        protected String queryResult;
        @XmlElement(name = "ExecutionTime")
        protected String executionTime;
        @XmlElement(name = "ExecutionNotes")
        protected String executionNotes;
        @XmlAttribute(name = "ResultTrackingID")
        protected String resultTrackingID;
        @XmlAttribute(name = "QueryTrackingID")
        protected String queryTrackingID;
        @XmlAttribute(name = "StoredQueryName")
        protected String storedQueryName;

        /**
         * Gets the value of the requestParameters property.
         * 
         * @return
         *     possible object is
         *     {@link ArrayOfHTNGKeyValueItemsTypeKeyValueItem }
         *     
         */
        public ArrayOfHTNGKeyValueItemsTypeKeyValueItem getRequestParameters() {
            return requestParameters;
        }

        /**
         * Sets the value of the requestParameters property.
         * 
         * @param value
         *     allowed object is
         *     {@link ArrayOfHTNGKeyValueItemsTypeKeyValueItem }
         *     
         */
        public void setRequestParameters(ArrayOfHTNGKeyValueItemsTypeKeyValueItem value) {
            this.requestParameters = value;
        }

        /**
         * Gets the value of the responseParameters property.
         * 
         * @return
         *     possible object is
         *     {@link ArrayOfHTNGStatisticsRSQuery.Query.ResponseParameters }
         *     
         */
        public ArrayOfHTNGStatisticsRSQuery.Query.ResponseParameters getResponseParameters() {
            return responseParameters;
        }

        /**
         * Sets the value of the responseParameters property.
         * 
         * @param value
         *     allowed object is
         *     {@link ArrayOfHTNGStatisticsRSQuery.Query.ResponseParameters }
         *     
         */
        public void setResponseParameters(ArrayOfHTNGStatisticsRSQuery.Query.ResponseParameters value) {
            this.responseParameters = value;
        }

        /**
         * Gets the value of the queryResult property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getQueryResult() {
            return queryResult;
        }

        /**
         * Sets the value of the queryResult property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setQueryResult(String value) {
            this.queryResult = value;
        }

        /**
         * Gets the value of the executionTime property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getExecutionTime() {
            return executionTime;
        }

        /**
         * Sets the value of the executionTime property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setExecutionTime(String value) {
            this.executionTime = value;
        }

        /**
         * Gets the value of the executionNotes property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getExecutionNotes() {
            return executionNotes;
        }

        /**
         * Sets the value of the executionNotes property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setExecutionNotes(String value) {
            this.executionNotes = value;
        }

        /**
         * Gets the value of the resultTrackingID property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getResultTrackingID() {
            return resultTrackingID;
        }

        /**
         * Sets the value of the resultTrackingID property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setResultTrackingID(String value) {
            this.resultTrackingID = value;
        }

        /**
         * Gets the value of the queryTrackingID property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getQueryTrackingID() {
            return queryTrackingID;
        }

        /**
         * Sets the value of the queryTrackingID property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setQueryTrackingID(String value) {
            this.queryTrackingID = value;
        }

        /**
         * Gets the value of the storedQueryName property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getStoredQueryName() {
            return storedQueryName;
        }

        /**
         * Sets the value of the storedQueryName property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setStoredQueryName(String value) {
            this.storedQueryName = value;
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
         *       &lt;attribute name="ResultFormat" type="{http://htng.org/2010B}HTNG_ResultFormatType" default="XML" />
         *       &lt;attribute name="Size" type="{http://www.w3.org/2001/XMLSchema}integer" default="1" />
         *       &lt;attribute name="UnitOfMeasure" type="{http://htng.org/2010B}HTNG_QueryResultListType" default="All Ascending" />
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "")
        public static class ResponseParameters {

            @XmlAttribute(name = "ResultFormat")
            protected HTNGResultFormatType resultFormat;
            @XmlAttribute(name = "Size")
            protected BigInteger size;
            @XmlAttribute(name = "UnitOfMeasure")
            protected HTNGQueryResultListType unitOfMeasure;

            /**
             * Gets the value of the resultFormat property.
             * 
             * @return
             *     possible object is
             *     {@link HTNGResultFormatType }
             *     
             */
            public HTNGResultFormatType getResultFormat() {
                if (resultFormat == null) {
                    return HTNGResultFormatType.XML;
                } else {
                    return resultFormat;
                }
            }

            /**
             * Sets the value of the resultFormat property.
             * 
             * @param value
             *     allowed object is
             *     {@link HTNGResultFormatType }
             *     
             */
            public void setResultFormat(HTNGResultFormatType value) {
                this.resultFormat = value;
            }

            /**
             * Gets the value of the size property.
             * 
             * @return
             *     possible object is
             *     {@link BigInteger }
             *     
             */
            public BigInteger getSize() {
                if (size == null) {
                    return new BigInteger("1");
                } else {
                    return size;
                }
            }

            /**
             * Sets the value of the size property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigInteger }
             *     
             */
            public void setSize(BigInteger value) {
                this.size = value;
            }

            /**
             * Gets the value of the unitOfMeasure property.
             * 
             * @return
             *     possible object is
             *     {@link HTNGQueryResultListType }
             *     
             */
            public HTNGQueryResultListType getUnitOfMeasure() {
                if (unitOfMeasure == null) {
                    return HTNGQueryResultListType.ALL_ASCENDING;
                } else {
                    return unitOfMeasure;
                }
            }

            /**
             * Sets the value of the unitOfMeasure property.
             * 
             * @param value
             *     allowed object is
             *     {@link HTNGQueryResultListType }
             *     
             */
            public void setUnitOfMeasure(HTNGQueryResultListType value) {
                this.unitOfMeasure = value;
            }

        }

    }

}

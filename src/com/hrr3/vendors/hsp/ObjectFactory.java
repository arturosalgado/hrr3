
package com.hrr3.vendors.hsp;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.hrr3.vendors.hsp package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.hrr3.vendors.hsp
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AccessTokenResponse }
     * 
     */
    public AccessTokenResponse createAccessTokenResponse() {
        return new AccessTokenResponse();
    }

    /**
     * Create an instance of {@link RequestAccessTokenResponse }
     * 
     */
    public RequestAccessTokenResponse createRequestAccessTokenResponse() {
        return new RequestAccessTokenResponse();
    }

    /**
     * Create an instance of {@link ExecuteQuery }
     * 
     */
    public ExecuteQuery createExecuteQuery() {
        return new ExecuteQuery();
    }

    /**
     * Create an instance of {@link ExecuteQueryRequest }
     * 
     */
    public ExecuteQueryRequest createExecuteQueryRequest() {
        return new ExecuteQueryRequest();
    }

    /**
     * Create an instance of {@link ExecuteQueryResponse }
     * 
     */
    public ExecuteQueryResponse createExecuteQueryResponse() {
        return new ExecuteQueryResponse();
    }

    /**
     * Create an instance of {@link ExecuteQueryResponse2 }
     * 
     */
    public ExecuteQueryResponse2 createExecuteQueryResponse2() {
        return new ExecuteQueryResponse2();
    }

    /**
     * Create an instance of {@link RequestAccessToken }
     * 
     */
    public RequestAccessToken createRequestAccessToken() {
        return new RequestAccessToken();
    }

    /**
     * Create an instance of {@link AccessTokenResponse.HSPAccessTokenResponse }
     * 
     */
    public AccessTokenResponse.HSPAccessTokenResponse createAccessTokenResponseHSPAccessTokenResponse() {
        return new AccessTokenResponse.HSPAccessTokenResponse();
    }

}

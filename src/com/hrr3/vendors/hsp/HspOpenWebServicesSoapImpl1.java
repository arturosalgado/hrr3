
/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

package com.hrr3.vendors.hsp;

import java.util.logging.Logger;

/**
 * This class was generated by Apache CXF 2.7.5
 * 2014-01-17T15:09:46.458-06:00
 * Generated source version: 2.7.5
 * 
 */

@javax.jws.WebService(
                      serviceName = "hspOpenWebServices",
                      portName = "hspOpenWebServicesSoap",
                      targetNamespace = "http://www.hotelSystemsPro.com/",
                      wsdlLocation = "file:/C:/Users/vlarios/workspace/HRR3/src/com/hrr3/vendors/HSPWebService.wsdl",
                      endpointInterface = "com.hrr3.vendors.hsp.HspOpenWebServicesSoap")
                      
public class HspOpenWebServicesSoapImpl1 implements HspOpenWebServicesSoap {

    private static final Logger LOG = Logger.getLogger(HspOpenWebServicesSoapImpl.class.getName());

    /* (non-Javadoc)
     * @see com.hrr3.vendors.hsp.HspOpenWebServicesSoap#executeQuery(com.hrr3.vendors.hsp.ExecuteQueryRequest  request )*
     */
    public com.hrr3.vendors.hsp.ExecuteQueryResponse2 executeQuery(com.hrr3.vendors.hsp.ExecuteQueryRequest request) { 
        LOG.info("Executing operation executeQuery");
        System.out.println(request);
        try {
            com.hrr3.vendors.hsp.ExecuteQueryResponse2 _return = new com.hrr3.vendors.hsp.ExecuteQueryResponse2();
            com.hrr3.vendors.hsp.htng.HTNGStatisticsRS _returnHTNGStatisticsRS = new com.hrr3.vendors.hsp.htng.HTNGStatisticsRS();
            com.hrr3.vendors.hsp.opentravel.UniqueIDType _returnHTNGStatisticsRSRequestorID = new com.hrr3.vendors.hsp.opentravel.UniqueIDType();
            com.hrr3.vendors.hsp.opentravel.CompanyNameType _returnHTNGStatisticsRSRequestorIDCompanyName = new com.hrr3.vendors.hsp.opentravel.CompanyNameType();
            _returnHTNGStatisticsRSRequestorIDCompanyName.setValue("Value1803440549");
            _returnHTNGStatisticsRSRequestorIDCompanyName.setCompanyShortName("CompanyShortName-1677455325");
            _returnHTNGStatisticsRSRequestorIDCompanyName.setTravelSector("TravelSector603115264");
            _returnHTNGStatisticsRSRequestorIDCompanyName.setCode("Code-718257125");
            _returnHTNGStatisticsRSRequestorIDCompanyName.setCodeContext("CodeContext1353125587");
            _returnHTNGStatisticsRSRequestorIDCompanyName.setDivision("Division104203198");
            _returnHTNGStatisticsRSRequestorIDCompanyName.setDepartment("Department2092838356");
            _returnHTNGStatisticsRSRequestorID.setCompanyName(_returnHTNGStatisticsRSRequestorIDCompanyName);
            _returnHTNGStatisticsRSRequestorID.setInstance("Instance-1202416184");
            _returnHTNGStatisticsRSRequestorID.setIDContext("IDContext-1251034135");
            _returnHTNGStatisticsRSRequestorID.setURL("URL1688094731");
            _returnHTNGStatisticsRSRequestorID.setType("Type-1290733544");
            _returnHTNGStatisticsRS.setRequestorID(_returnHTNGStatisticsRSRequestorID);
            com.hrr3.vendors.hsp.htng.ArrayOfHTNGStatisticsRSQuery _returnHTNGStatisticsRSQueries = new com.hrr3.vendors.hsp.htng.ArrayOfHTNGStatisticsRSQuery();
            java.util.List<com.hrr3.vendors.hsp.htng.ArrayOfHTNGStatisticsRSQuery.Query> _returnHTNGStatisticsRSQueriesQuery = new java.util.ArrayList<com.hrr3.vendors.hsp.htng.ArrayOfHTNGStatisticsRSQuery.Query>();
            _returnHTNGStatisticsRSQueries.getQuery().addAll(_returnHTNGStatisticsRSQueriesQuery);
            _returnHTNGStatisticsRS.setQueries(_returnHTNGStatisticsRSQueries);
            com.hrr3.vendors.hsp.opentravel.HTNGStatisticsRQTransactionStatusCode _returnHTNGStatisticsRSTransactionStatusCode = com.hrr3.vendors.hsp.opentravel.HTNGStatisticsRQTransactionStatusCode.END;
            _returnHTNGStatisticsRS.setTransactionStatusCode(_returnHTNGStatisticsRSTransactionStatusCode);
            _returnHTNGStatisticsRS.setSequenceNmbr(new java.math.BigInteger("-48029157035036119682087698221620424771"));
            _returnHTNGStatisticsRS.setCorrelationID("CorrelationID80311548");
            _returnHTNGStatisticsRS.setRetransmissionIndicator(Boolean.valueOf(false));
            _returnHTNGStatisticsRS.setTransactionIdentifier("TransactionIdentifier80962839");
            _returnHTNGStatisticsRS.setTimeStamp(javax.xml.datatype.DatatypeFactory.newInstance().newXMLGregorianCalendar("2014-01-17T15:09:46.481-06:00").toString());
            _returnHTNGStatisticsRS.setEchoToken("EchoToken-1675037916");
            _returnHTNGStatisticsRS.setVersion(new java.math.BigDecimal("-7229993217237699656.1911184575089571986"));
            com.hrr3.vendors.hsp.opentravel.HTNGStatisticsRQTarget _returnHTNGStatisticsRSTarget = com.hrr3.vendors.hsp.opentravel.HTNGStatisticsRQTarget.TEST;
            _returnHTNGStatisticsRS.setTarget(_returnHTNGStatisticsRSTarget);
            _return.setHTNGStatisticsRS(_returnHTNGStatisticsRS);
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see com.hrr3.vendors.hsp.HspOpenWebServicesSoap#requestAccessToken(java.lang.String  clientId ,)java.lang.String  clientSecret )*
     */
    public com.hrr3.vendors.hsp.AccessTokenResponse requestAccessToken(java.lang.String clientId,java.lang.String clientSecret) { 
        LOG.info("Executing operation requestAccessToken");
        System.out.println(clientId);
        System.out.println(clientSecret);
        try {
            com.hrr3.vendors.hsp.AccessTokenResponse _return = new com.hrr3.vendors.hsp.AccessTokenResponse();
            com.hrr3.vendors.hsp.AccessTokenResponse.HSPAccessTokenResponse _returnHSPAccessTokenResponse = new com.hrr3.vendors.hsp.AccessTokenResponse.HSPAccessTokenResponse();
            com.hrr3.vendors.hsp.HSPAccessTokenResponseType _returnHSPAccessTokenResponseResponseType = com.hrr3.vendors.hsp.HSPAccessTokenResponseType.ERROR;
            _returnHSPAccessTokenResponse.setResponseType(_returnHSPAccessTokenResponseResponseType);
            _returnHSPAccessTokenResponse.setErrorResponse("ErrorResponse500442466");
            _returnHSPAccessTokenResponse.setTokenExpiration(javax.xml.datatype.DatatypeFactory.newInstance().newXMLGregorianCalendar("2014-01-17T15:09:46.484-06:00"));
            _returnHSPAccessTokenResponse.setAccessToken("AccessToken-1917268987");
            _return.setHSPAccessTokenResponse(_returnHSPAccessTokenResponse);
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

}

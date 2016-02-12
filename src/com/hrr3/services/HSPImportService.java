package com.hrr3.services;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.ws.BindingProvider;

import org.zkoss.zk.ui.Sessions;

import com.google.gson.Gson;
import com.hrr3.entity.ImportSegment;
import com.hrr3.entity.hsp.Day;
import com.hrr3.entity.hsp.Group;
import com.hrr3.entity.hsp.Groups;
import com.hrr3.model.ImportSegmentDAO;
import com.hrr3.vendors.hsp.AccessTokenResponse;
import com.hrr3.vendors.hsp.ExecuteQueryRequest;
import com.hrr3.vendors.hsp.ExecuteQueryResponse2;
import com.hrr3.vendors.hsp.HspOpenWebServices;
import com.hrr3.vendors.hsp.HspOpenWebServicesSoap;
import com.hrr3.vendors.hsp.htng.ArrayOfHTNGKeyValueItemsTypeKeyValueItem;
import com.hrr3.vendors.hsp.htng.ArrayOfHTNGKeyValueItemsTypeKeyValueItem.KeyValueItem;
import com.hrr3.vendors.hsp.htng.ArrayOfHTNGStatisticsRQQuery;
import com.hrr3.vendors.hsp.htng.ArrayOfHTNGStatisticsRQQuery.Query;
import com.hrr3.vendors.hsp.htng.HTNGComparisonOperatorType;
import com.hrr3.vendors.hsp.htng.HTNGStatisticsRQ;
import com.hrr3.vendors.hsp.opentravel.UniqueIDType;

public class HSPImportService {
	
	HspOpenWebServices ss;
	HspOpenWebServicesSoap port;
	BindingProvider provider;
	
	public static String  DEFAULT_CLIENT_SECRET = "46ea50c0-0ff9-48d4-a817-7dd3e4293d8b";
	public static String currentHostName = Sessions.getCurrent().getServerName().equals("localhost")?"http://dev2.valetsoftware.com":"http://" + Sessions.getCurrent().getServerName();
	public static String URL_API_IMPORTHSP = currentHostName + "/hrr_yii_rm3_api/services/import/ImportFromHSP";
	
	public static String URL_API_MOVEIMPORT_HSP = currentHostName + "/hrr_yii_rm3_api/services/import/MoveImportedRFT";
	
	public HSPImportService() {
		
		ss = new HspOpenWebServices(HspOpenWebServices.WSDL_LOCATION, HspOpenWebServices.SERVICE);		
	    port = ss.getHspOpenWebServicesSoap();  
	    
	    //In case of debugging with TCPMonitor uncomment these lines.
		//provider = (BindingProvider) port;
		//provider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, "http://localhost:6060/hspOpenWebService/hspOpenWebService.asmx");		
	}
	
	public String getHSPToken(String clientId, String clientSecret) {
		
		System.out.println("**************** HSP Service:requestAccessToken *******************");
        AccessTokenResponse response = port.requestAccessToken(clientId, clientSecret);
        
        if(response!=null && response.getHSPAccessTokenResponse()!=null && response.getHSPAccessTokenResponse().getAccessToken()!=null)
        	return response.getHSPAccessTokenResponse().getAccessToken();
        else return null;
	}
	
	public Groups getHSPData(String token, String date) {
		
		InputStream inputStreamToMarshal = null;
		
		try {
			
			System.out.println("**************** HSP Service:queryExecution *******************");
	        //Create Request object
	        ExecuteQueryRequest request = new ExecuteQueryRequest();
	        //Create HTNGStatistics object
	        HTNGStatisticsRQ HTNGStatisticsRQ = new HTNGStatisticsRQ();       
	        //Create RequestorID object
	        UniqueIDType requestorID = new UniqueIDType();
	        //Create Queries object
	        ArrayOfHTNGStatisticsRQQuery queries = new ArrayOfHTNGStatisticsRQQuery();
	        //Create QueryList object
	        List<Query> queryList = new ArrayList<Query>();
	        //Create Query object
	        Query query = new Query();
	        //Create RequestParemeters object
	        ArrayOfHTNGKeyValueItemsTypeKeyValueItem requestParameters = new ArrayOfHTNGKeyValueItemsTypeKeyValueItem();
	        //Create KeyValueItem object
	        KeyValueItem keyValueItem = new KeyValueItem();
	        //Ceate KeyValueItem List
	        List<KeyValueItem> keyValueList = new ArrayList<KeyValueItem>();
	        //Create Comparison operator attribute
	        HTNGComparisonOperatorType comparisonOperator = HTNGComparisonOperatorType.IS_GREATER_THAN_OR_EQUAL_TO;
	        //Set attributes to KeyValue
	        keyValueItem.setKey("Group_Date");
	        keyValueItem.setOperator(comparisonOperator);
	        // Bussines Rule: Rest 90 days
	        
	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			
			Date dDate = dateFormat.parse(date);	
	        Calendar cal = Calendar.getInstance();
			cal.setTime(dDate);
			cal.add(Calendar.DATE, -90); //add 1 day
			dDate = cal.getTime();
	        
	        keyValueItem.setValue(dateFormat.format(dDate));
	        //Set Token
	        requestorID.setIDContext(token);        
	        //Integrate all objects        
	        keyValueList.add(keyValueItem);
	        requestParameters.getKeyValueItem().addAll(keyValueList);        
	        query.setQueryTrackingID(String.valueOf(Calendar.getInstance().getTimeInMillis()));
	        query.setStoredQueryName("Groups");
	        queryList.add(query);
	        queries.getQuery().addAll(queryList);
	        query.setRequestParameters(requestParameters);          
	        request.setHTNGStatisticsRQ(HTNGStatisticsRQ);
	        HTNGStatisticsRQ.setRequestorID(requestorID);    
	        //Set all queries
	        HTNGStatisticsRQ.setQueries(queries);
	        //Execute WS Query
	        ExecuteQueryResponse2 response = port.executeQuery(request);
	        //Get XML String from QueryResult raw
	        String xmlString = response.getHTNGStatisticsRS().getQueries().getQuery().get(0).getQueryResult();  
	        //Parse XML and Unmarshall it into a Java objects
	        inputStreamToMarshal = new ByteArrayInputStream(xmlString.getBytes());         
	        JAXBContext jaxbContext = JAXBContext.newInstance(Groups.class);
	        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
	        @SuppressWarnings("unchecked")
			Groups groups = (Groups) jaxbUnmarshaller.unmarshal(new InputStreamReader(inputStreamToMarshal, Charset.forName("UTF-8")));  
	        inputStreamToMarshal.close();
	                
	        return groups;
			
		} catch(Exception e) {
			System.out.println("**************** HSP Service:Error while getting Data *******************");
			e.printStackTrace();
			return null;
		}
		
		finally {
			
			if(inputStreamToMarshal!=null)
				try {
					inputStreamToMarshal.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println("**************** HSP Service:Error closing InputStream *******************");
					e.printStackTrace();
				}
		}
		
		
	}
	
	public List<ImportSegment> validateGroupData(List<Group> groups, int userId, int hotelId, Date dateFrom, Date dateTo) {
		System.out.println("*********** Start validateGroupData method : "+ new Date() +" *************");		
		List<ImportSegment> lstSegments = new ArrayList<ImportSegment>();
		
		for(int gi=0; gi<groups.size(); gi++) {
			
			Group group = groups.get(gi);
			String groupType = group.getGroup_Type();
			
			if(groupType != null) {
				
				//TODO Map vs RM3 segment
				ImportSegmentDAO segmentDAO = new ImportSegmentDAO();
				System.out.println("*********** validateGroupData -> get Segment Map: *************");	
				String RM3Segment = segmentDAO.mapHSPSegments(groupType);
				
				//Validate if RM3Segments != null
				if (RM3Segment != ""  || RM3Segment != null){	
					
					//Get GroupStatus
					String groupStatus = group.getGroup_Status() == null ? "" : group.getGroup_Status() ;
					//Validate if Status is Definitive or Tentative
					if(groupStatus.equals("Definite") || groupStatus.equals("Tentative")) {
						
						BigDecimal adrCalculated = new BigDecimal(0);
						System.out.println("*********** validateGroupData -> get Room Rate: *************");
						//Get Room_N_Rate
						BigDecimal room1_rate = group.getRoom_1_Rate();
						
						if (room1_rate == null) room1_rate = new BigDecimal(0);				
						else room1_rate.round(new MathContext(2));					
						
						BigDecimal room2_rate = group.getRoom_2_Rate();
						
						if ( room2_rate == null) room2_rate = new BigDecimal(0);
						else room1_rate.round(new MathContext(2));
						
						BigDecimal room3_rate = group.getRoom_3_Rate();
						if (room3_rate == null ) room3_rate = new BigDecimal(0);
						else room3_rate.round(new MathContext(2));
						
						BigDecimal room4_rate = group.getRoom_4_Rate();
						if(room4_rate == null) room4_rate = new BigDecimal(0);
						else room4_rate.round(new MathContext(2));
						
						BigDecimal room5_rate = group.getRoom_5_Rate();
						if(room5_rate == null) room5_rate = new BigDecimal(0);
						else room5_rate.round(new MathContext(2));
						
						System.out.println("*********** validateGroupData -> get Room Block: *************");
						//Get Room_N_Block
						int room1_block = group.getRoom_1_Block();										
						int room2_block = group.getRoom_2_Block();
						int room3_block = group.getRoom_3_Block();
						int room4_block = group.getRoom_4_Block();
						int room5_block = group.getRoom_5_Block();
						
						System.out.println("*********** validateGroupData -> get Room_N_Block: *************");
						int room_block_total = group.getRoom_Block_Total();
						
						
						System.out.println("*********** validateGroupData -> Sum Room_N_Rate * Room_N_Block: *************");
						//Sum all Room_N_Rate * Room_N_Block
						BigDecimal sumRates = new BigDecimal(0);
						sumRates = sumRates.add(room1_rate.multiply(new BigDecimal(room1_block)));
						sumRates = sumRates.add(room2_rate.multiply(new BigDecimal(room2_block)));
						sumRates = sumRates.add(room3_rate.multiply(new BigDecimal(room3_block)));
						sumRates = sumRates.add(room4_rate.multiply(new BigDecimal(room4_block)));
						sumRates = sumRates.add(room5_rate.multiply(new BigDecimal(room5_block)));
						
						//If sumRates = 0 and room_block_total = 0
						if(sumRates.compareTo(new BigDecimal(0)) == 0 && room_block_total == 0)
							adrCalculated = new BigDecimal(0);
						else{
							System.out.println("*********** validateGroupData -> Divide (rates, rommBlockTotal): *************");
							BigDecimal roomBlockTotal = new BigDecimal(room_block_total);
							adrCalculated = sumRates.divide(roomBlockTotal, 2,RoundingMode.HALF_EVEN);
						}
						
						//adrCalculated = adrCalculated.round(new MathContext(2));
						
						DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
						
						for(int di=0; di<group.getDays().getDayList().size(); di++) {
							
							Day day = group.getDays().getDayList().get(di);
							Date dayBlockDate = day.getDay_Block_Date();
							
							if (dayBlockDate != null ){
								System.out.println("*********** validateGroupData -> get valid dayBlock  : *************");
								if (dayBlockDate.compareTo(dateFrom) >= 0 && dayBlockDate.compareTo(dateTo) <= 0) {
								
									BigDecimal OCC = new BigDecimal(day.getDay_Block_Net_Block());
									BigDecimal  REV  =  adrCalculated.multiply(OCC);
									
									REV.setScale(2, RoundingMode.HALF_EVEN);
									
									ImportSegment iSegment = new ImportSegment(hotelId, userId,RM3Segment, df.format(dayBlockDate), "Group", "DP");
									
									if(groupStatus.equals("Definite")){
										iSegment.setDefADR(REV);
										iSegment.setDefOCC(OCC);
									}
									
									if(groupStatus.equals("Tentative")){
										iSegment.setTenADR(REV);
										iSegment.setTenOCC(OCC);
									}
									System.out.println("*********** validateGroupData -> Add segment to the list *************");
									lstSegments.add(iSegment);																					
								} 	
							} //if dayBlaockDate != null
						} // for Days
					} // if GroupStatus
			  } // if RM3Segmetns != null or ""
			} // if groupType != null
		} // for groups
		System.out.println("*********** invokeHSPImportRM3Api  return list of segments : "+ lstSegments.size() +" *************");
		System.out.println("*********** End invokeHSPImportRM3Api method : "+ new Date() +" *************");
		return lstSegments;
	}

		
	public int invokeHSPImportRM3Api (String importDataJson, int importSession){
		
		URL url = null;
		
		System.out.println("*********** Start invokeHSPImportRM3Api method : "+ new Date() +" *************");
		
		try {			
			System.out.println("***********invokeHSPImportRM3Api method -> Create URL: *************");
			
			url = new URL(URL_API_IMPORTHSP);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		HttpURLConnection conn = null;
		try {
			conn = (HttpURLConnection) url.openConnection();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 conn.setDoInput(true);
		 conn.setDoOutput(true);
		 try {
			conn.setRequestMethod("POST");
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
		 try {
			conn.connect();
		
		 
				 DataOutputStream output = null;
				 BufferedReader input = null;


			      
			     output = new DataOutputStream(conn.getOutputStream());
			    
			    System.out.println("*********** invokeHSPImportRM3Api method  -> Generate the parameters  *************"); 
			    String content = "importData="+importDataJson+"&importSession="+importSession;
			                
		        output.writeBytes(content);
		        output.flush();
		        output.close();		        				
		        
		        // Get response data.
		        String responseValue = null;
		        
		        input = new BufferedReader(new InputStreamReader(conn.getInputStream()));//new DataInputStream (conn.getInputStream());		        
		        
		        System.out.println("*********** invokeHSPImportRM3Api method  -> Get response data  *************");
		        while (null != ((responseValue = input.readLine()))) {
		            System.out.println("*********** invokeHSPImportRM3Api method  -> ImportSession value : " + responseValue + " *************");
		            System.out.println("*********** End invokeHSPImportRM3Api method : "+ new Date() +" *************");		            
		            return  Integer.parseInt(responseValue.replaceAll("^\"|\"$", ""));
		        }
		        input.close ();
        
		 } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}       
        //If Error
        return 0;		
	}
	
	
	public String moveImportRM3Api (int importSession, Date dateFrom, Date dateTo){
		
		URL url = null;
		
		System.out.println("*********** Start moveImportRM3Api method : "+ new Date() +" *************");
		
		try {			
			System.out.println("***********moveImportRM3Api method -> Create URL: *************");
			
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
			String content = "importSession="+importSession+"&dateFrom="+df.format(dateFrom)+"&dateTo="+df.format(dateTo)+"&occRev=1";
			url = new URL(URL_API_MOVEIMPORT_HSP+"?"+content);
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		HttpURLConnection conn = null;
		try {
			conn = (HttpURLConnection) url.openConnection();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			conn.setRequestMethod("GET");
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
		 
		try {
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 
		BufferedReader br;
		try {
			br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream())));
			String output;
			System.out.println("Output from Server .... \n");
			
			while ((output = br.readLine()) != null) {				
				 System.out.println("*********** moveImportRM3Api method  -> Result value : " + output + " *************");
		         System.out.println("*********** End moveImportRM3Api method : "+ new Date() +" *************");
		         //remove double quotes start and end 
		         output.trim();
				return output.substring(1, output.length()-1);								
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}				
 
		conn.disconnect();
		       
        //If Error
        return "Error";		
	}

	
	public static void main(String args[]) throws java.lang.Exception {
		
		//Indicate hotelId
		String clientId = "400016-C";
		//Indicate date
		String date = "2013-07-01";
		//Create the ImportService proxy obect to call HSP services.
		HSPImportService hspService = new HSPImportService();
		//Get HSP Token
		String token = hspService.getHSPToken(clientId, DEFAULT_CLIENT_SECRET);
		System.out.println("**************** HSP Token " + token + " ***********************");		
		//Invoke getData service using Token generated
		List<Group> groups = hspService.getHSPData(token, date).getGroups();
		//Do what you need with the data
		if(groups != null)
			System.out.println("**************** A total of " + groups.size() + " Group elements ready to import ***********************");
		else
			System.out.println("**************** No Group elements returned by HSP:executeQuery method ***********************");
		
		//Execute validations
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
		
		Date dateFrom = df.parse("2013-07-03");
		Date dateTo =  df.parse("2013-07-03");
		
		List<ImportSegment> segmentsToImport = hspService.validateGroupData(groups, 1, 30, dateFrom, dateTo);
		
		// create a new Gson instance
		 Gson gson = new Gson();
		 // convert your list to json
		 String importDataList = gson.toJson(segmentsToImport);
		 // print your generated json
		 System.out.println("Import Data in Json: " + importDataList);
		 
		 //Invoke RM3 API services
		   //to Test
		 int importSession = 61;
		 importSession = hspService.invokeHSPImportRM3Api(importDataList, importSession);		 		
            	        		          	        			     		
	}
	
	
}

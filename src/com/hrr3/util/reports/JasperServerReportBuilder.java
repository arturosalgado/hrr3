package com.hrr3.util.reports;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.naming.NamingException;

import org.apache.commons.io.IOUtils;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Filedownload;

import com.hrr3.services.ReportServiceProvider;

public class JasperServerReportBuilder {
	
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	public void buildExecutionURL (String reportURI, String outputFormat, List<JasperServerReportParameter>inputParameters, boolean withCustomDownload) {
		
		String userName = getJasperUserName();
		String passWord = getJasperPassWord();
		String jasperURL = getJasperURL();
		
		StringBuilder executionURL = new StringBuilder(jasperURL);
		executionURL.append(reportURI);
		executionURL.append("."+ outputFormat);
		executionURL.append("?j_username=" + userName);
		executionURL.append("&j_password=" + passWord);
		
		for(int i=0; i < inputParameters.size(); i++) {
			//Include the parameter as long as it comes with a value
			if(inputParameters.get(i).getValue() != null)
				executionURL.append("&" + inputParameters.get(i).getName() + "=" + inputParameters.get(i).getValue());
			else
				executionURL.append("&" + inputParameters.get(i).getName() + "=" + "0");
		}
		
		executionURL.append("&userLocale=en_US");
		
		if(withCustomDownload)
			downloadJasperReport(executionURL.toString(), reportURI, outputFormat);
	}
	
	public void buildExecutionURLWithDiffReportID (String reportURI, String reportName, String outputFormat, List<JasperServerReportParameter>inputParameters, boolean withCustomDownload) {
		
		String userName = getJasperUserName();
		String passWord = getJasperPassWord();
		String jasperURL = getJasperURL();
		
		StringBuilder executionURL = new StringBuilder(jasperURL);
		executionURL.append(reportURI);
		executionURL.append("."+ outputFormat);
		executionURL.append("?j_username=" + userName);
		executionURL.append("&j_password=" + passWord);
		
		for(int i=0; i < inputParameters.size(); i++) {
			//Include the parameter as long as it comes with a value
			if(inputParameters.get(i).getValue() != null)
				executionURL.append("&" + inputParameters.get(i).getName() + "=" + inputParameters.get(i).getValue());
			else
				executionURL.append("&" + inputParameters.get(i).getName() + "=" + "0");
		}
		
		executionURL.append("&userLocale=en_US");
		
		if(withCustomDownload)
			downloadJasperReport(executionURL.toString(), reportName, outputFormat);
	}
	
	private void downloadJasperReport(String reportExecutionURL, String reportName, String outputFormat) {
		
		InputStream is = null;
		byte[] fileBytes = null;    
		String contentType =outputFormat.equalsIgnoreCase(JasperServerReportsConfig.XLS_FORMAT) ? "msexcel" : outputFormat;
		try {
			URL url = new URL(reportExecutionURL);
			is = url.openStream ();
			fileBytes = IOUtils.toByteArray(is);
		
			if (is != null && fileBytes.length > 0){		
				ByteArrayOutputStream baosObj = new ByteArrayOutputStream();
				baosObj.write(fileBytes);
				String filename = dateFormat.format(Calendar.getInstance().getTime()) + "-" + reportName +  "." + outputFormat;
				Sessions.getCurrent().setAttribute(ReportServiceProvider.FILE_TYPE,  "application/" + contentType);
				Sessions.getCurrent().setAttribute(ReportServiceProvider.FILE_HEADER, "filename=" + filename);
				Sessions.getCurrent().setAttribute(ReportServiceProvider.REPORT_STREAM, baosObj);
				Executions.getCurrent().sendRedirect("/reportserviceprovider/" + filename, "_blank");
			}
		} catch (Exception e) {
		  e.printStackTrace ();
		}
		
		finally {
  		  if (is != null) { try {
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} }
  		}
		
	}
	
	private static String getJasperUserName () {
		
		String userName = null;
		try {
			userName = JasperServerConnectionFactory.getJasperServerUser();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 return userName;
	}
	
	private static String getJasperPassWord () {
		
		String passWord = null;
		try {
			passWord = JasperServerConnectionFactory.getJasperServerUser();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 return passWord;
	}
	
	private static String getJasperURL () {
		
		String jasperURL = null;
		try {
			jasperURL = JasperServerConnectionFactory.getJasperServerURL();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 return jasperURL;
	}
	
	

}

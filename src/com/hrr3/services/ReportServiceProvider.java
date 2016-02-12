package com.hrr3.services;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class ReportServiceProvider extends GenericServlet {
	
	private static final long serialVersionUID = 1L;	 
	private static final Logger log = Logger.getLogger( ReportServiceProvider.class.getName() );
	public static final String REPORT_STREAM = "report-stream";
	public static final String FILE_TYPE = "file-type";
	public static final String FILE_HEADER = "file-header";

	public void service(ServletRequest request, ServletResponse response)
	{
		try {
			
			HttpServletRequest httpRequest = (HttpServletRequest)request;
			HttpServletResponse httpResponse = (HttpServletResponse)response;
			ByteArrayOutputStream baos = (ByteArrayOutputStream)httpRequest.getSession().getAttribute(REPORT_STREAM);
			if (baos != null) {
				byte[] data = baos.toByteArray();
				httpResponse.setHeader("Content-disposition", (String)httpRequest.getSession().getAttribute(FILE_HEADER));
				httpResponse.setContentType((String)httpRequest.getSession().getAttribute(FILE_TYPE));
				ServletOutputStream out = httpResponse.getOutputStream();
				out.write(data);
				
				
			} else {
				log.info("No input found.");
			}
			
		}catch(Exception exc) {exc.printStackTrace();}
				
		finally {
			
			HttpServletRequest httpSr = (HttpServletRequest)request;			
			httpSr.getSession().setAttribute(REPORT_STREAM, null);
			httpSr.getSession().setAttribute(FILE_TYPE, null);
			httpSr.getSession().setAttribute(FILE_HEADER, null);
		}
	}

}
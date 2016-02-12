package com.hrr3.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hrr3.authentication.AuthenticationServiceHRR3Impl;
import com.hrr3.entity.Customer;
import com.hrr3.entity.User;
import com.hrr3.entity.ssr.SSRInputData;
import com.hrr3.model.RM3DataSourceConnectionFactory;
import com.hrr3.model.SSRInputDAO;

import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;


import com.hrr3.services.AuthenticationService;



/**
 * Servlet implementation class SsrController
 */
@WebServlet("/ssrcontrollerajax")
public class SsrControllerAjax extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private ListModel<SSRInputData> ssrDataRows;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SsrControllerAjax() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		
		String col = request.getParameter("col");
		String id = request.getParameter("update_id");
		String value = request.getParameter("value");
		String objectToReturn = "";
		if (value==null)
		{
			System.out.println("value us null");
			System.out.println("setting to empty string ");
			value="";
			
		}
		
		System.out.println("ajax called received updateing: ");
		System.out.println("cols is "+col);
		System.out.println("id is "+id);
		System.out.println("val is ["+value+"]");
		String field = getColumn(col);
		String table = getTable(col);
		System.out.println("Field to update is ["+field+"]");
		int Id = Integer.parseInt(id);
		update((long)Id,field,value,table,col);
		//comment or isAction
		if (Integer.parseInt(col)==-1 || Integer.parseInt(col)==0)
		{
			// don't update lrr
			return;
		}
		
		String lrr = getUpdateLRRValue(Id, Integer.parseInt(col), value);
		System.out.println("LRR to update is ["+lrr+"]");
		
		if (!table.equals("RM3SSRData"))
		return;
		
		update((long)Id,"lrr",lrr,table,col);
		
		// the case when the dot is removed and LRR should go back to blank;
		if (value.equals("") && Integer.parseInt(col)<=11 && Integer.parseInt(col)>=1)
		{	
			lrr = getUpdateLRRValue((long)Id,Integer.parseInt(col), value);
			System.out.println("Next Availble value for LRR is ["+lrr+"]");
			update((long)Id,"lrr",lrr,table,col);	
			//lrr="";
		}
		
		response.setContentType("application/xml");
	    PrintWriter writer = response.getWriter();
	 	writer.print(xml(lrr));
		writer.flush();
		
		
	}
	private String getUpdateLRRValue(long id, int col, String value)
	{
		System.out.println("Finding the next horizontal value ");
		
		int NotEmptyColumn = Integer.MAX_VALUE;
		
		
		
		
		String lrr = "";
		if (value.equals(""))// deleting items, lets find the next no empty
		{
			NotEmptyColumn = getNextNonEmptyCell(id);
			System.out.println("not empty column:: "+NotEmptyColumn);
			if (NotEmptyColumn>0 && NotEmptyColumn <11)
			{
				lrr = readOne("lrr"+NotEmptyColumn,id);
			}
			
			return lrr;
		}
		
		
		switch(col)
		{
			case 1: 
				lrr="Closed";
			break;
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:
			case 9:	
				lrr= getLRR(id, col, value);
			break;
		}
		return lrr;
		
	}
	
	private String getLRR(long id, int col, String value)
	{
		if (col>1 && col < 10)
		{
			//get the next to the left 
			col--;
		}
		if (col == 11) // hp2
		{
			String field = "lrr7";
			float floatValue = readOneFloat(field,id);
			System.out.print("Found LRR float value is "+floatValue);
			return Float.toString(floatValue);
		}
		String field = "lrr"+Integer.toString(col);
		float floatValue = readOneFloat(field,id);
		System.out.print("Found LRR float value is "+floatValue);
		return Float.toString(floatValue);
	}
	private String readOne(String field, long id )
	{
		Connection conn = null;
		Statement stmt2 = null;
		ResultSet rs2 = null;
		String value = "";
		
		
		try{
			conn = RM3DataSourceConnectionFactory.getHRR3Connection();
			stmt2 = conn.createStatement();
			String sql = "SELECT "+field+" FROM RM3SSRData WHERE ssr_id = "+id;
			rs2 = stmt2.executeQuery(sql);
			System.out.println("Executing statement <"+ sql+"> id ="+id );
			while (rs2.next()){
				value = rs2.getString(field);
				System.out.println(value + " <- value found  \n");
			}
		}
		catch(NamingException| SQLException e){
			System.out.println("returning empty string ");	
			System.out.println("given an exception  ... "+e.getMessage());
		}
		
		close(rs2, stmt2, conn);
		
		return value;
		
		
	}
	private int readOneInt(String field, long id )
	{
		Connection conn = null;
		Statement stmt2 = null;
		ResultSet rs2 = null;
		int value=0;
		
		
		try{
			conn = RM3DataSourceConnectionFactory.getHRR3Connection();
			stmt2 = conn.createStatement();
			String sql = "SELECT "+field+" FROM RM3SSRData WHERE ssr_id = "+id;
			rs2 = stmt2.executeQuery(sql);
			System.out.println("Executing statement <"+ sql+"> id ="+id );
			while (rs2.next()){
				value = rs2.getInt(field);
				System.out.println(value + " <- value found  \n");
			}
		}
		catch(NamingException| SQLException e){
			System.out.println("returning empty string ");	
			System.out.println("given an exception  ... "+e.getMessage());
		}
		
		close(rs2, stmt2, conn);
		
		return value;
		
		
	}
	private float readOneFloat(String field, long id )
	{
		Connection conn = null;
		Statement stmt2 = null;
		ResultSet rs2 = null; 
		float value = 0.0f;
		
		try{
			conn = RM3DataSourceConnectionFactory.getHRR3Connection();
			stmt2 = conn.createStatement();
			rs2 = stmt2.executeQuery("SELECT "+field+" FROM RM3SSRData WHERE ssr_id = "+id);
			while (rs2.next()){
				value = rs2.getFloat(field);
				System.out.println(value + " <- value found  \n");
			}
		}
		catch(NamingException| SQLException e){
			System.out.println("returning empty string ");	
			System.out.println("given an exception  ... "+e.getMessage());
		}
		finally{
		close(rs2, stmt2, conn);
		}
		
		return value;
		
		
	}
	
	String getTable(String colNumber)
	{
		int col = Integer.parseInt(colNumber);
		if (col>=-1 && col < 12)
			return "RM3SSRData";
		else if (col>=12 && col <= 19)
			return "RM3SSRSegmentData";
		else
			return "";
		
	}
	
	private String getColumn(String colNumber)
	{
		
		System.out.println("Col number:: ["+colNumber+"]");
		
		if (colNumber.equals("-1") )//commments
		{
			System.out.println("comments  ");
			return "comments";
		}
		
		if (colNumber.equals("0") )//exceptiov
		{
			System.out.println("is exception   ");
			return "isException";
		}
		
		if (colNumber.equals("12") )//commments
		{
			System.out.println("is trans   ");
			return "tot_occ";
		}
		
		if (colNumber.equals("13") )//commments
		{
			System.out.println("is trans   ");
			return "tot_occ";
		}
		
		//segment columns
		int NumericCol = Integer.parseInt(colNumber);
		if (NumericCol>=14 && NumericCol<=18)
			return "tot_occ";
		
		
		String field = "ratecat"+colNumber;
		int col = Integer.parseInt(colNumber);
		if (col>=1 && col<10)
			return field;
		else if (col==10)
			return "hp";
		else if (col==11)
			return "hp2";
		return "";
		
	}
	private String xml(String lrr)
	{
		String output = "";
		output+="<?xml version=\"1.0\"?>";
	    output+="<info>";
	    output+="<lrr>"+lrr+"</lrr>";
	    output+="</info>";
		return output;
	}
	
	
	protected void update(long ssr_id, String field, String value, String table,String col)
	{	
		ResultSet rs = null;
		Connection conn = null;
		PreparedStatement ps = null;
		Statement s = null;
		
		if (table.equals("RM3SSRSegmentData"))
		{
			update2(ssr_id,field,value,table,col);
			return;
			
		}
		
		
		//check is Action Y or N translate to 1 or 0
		if (field.equals("isException"))
		{
			if (value.equals("Y"))
			{
				value = "1";
			}
			else{
				value = "0";
			}
		}
		
		try{
			conn = RM3DataSourceConnectionFactory.getHRR3Connection();
			String sql  = "UPDATE "+table+" set `"+field+"` =  '"+value+"' WHERE ssr_id = "+ssr_id+" limit 1 ";
			System.out.println("statemet is ["+sql+"]");
			System.out.println("new value  is ["+value+"]");
			
			s = conn.createStatement();
			
			s.executeUpdate(sql);
			System.out.println("Final Excecuted Query  ["+sql+"]");
		}
		catch (NamingException | SQLException e)
		{
			// TODO Auto-generated catch block
			   e.printStackTrace();
			//return null;
		}
		finally { close(rs, ps, conn); }
		
	}
	protected void update2(long ssr_id, String field, String value, String table, String col)
	{	
		ResultSet rs = null;
		Connection conn = null;
		PreparedStatement ps = null;
		Statement s = null;
		
		int hotel_id = readOneInt("hotel_id",(long)ssr_id);
		String date = readOne("statdate",(long)ssr_id);
		
		int segment = 0;
		switch(col)
		{
			case "12":
			segment = 1;
			break;
		
			case "13":
			segment = 2;
			break;
			case "14":
			segment = 3;
			break;
			case "15":
				segment = 4;
				break;
			case "16":
				segment = 5;
				break;
			case "17":
				segment = 6;
				break;
			case "18":
				segment = 7;
				break;
				
		
		}
		
		System.out.println("Field tp update is "+field);
		System.out.println("Hotel found is "+hotel_id);
		System.out.println("Statdate  found is "+date);
		System.out.println("Segment is "+segment);
		
		
		//check is Action Y or N translate to 1 or 0
		if (field.equals("isException"))
		{
			if (value.equals("Y"))
			{
				value = "1";
			}
			else{
				value = "0";
			}
		}
		
		try{
			conn = RM3DataSourceConnectionFactory.getHRR3Connection();
			String sql  = "UPDATE "+table+" set `"+field+"` =  '"+value+"' WHERE statdate = '"+date+"' "
					+ "and hotel_id = "+hotel_id+" "
					+ "and segment_id = "+segment+" "
					+ "limit 1 ";
			System.out.println("statemet is ["+sql+"]");
			System.out.println("new value  is ["+value+"]");
			
			s = conn.createStatement();
			
			s.executeUpdate(sql);
			System.out.println("Final Excecuted Query  ["+sql+"]");
		}
		catch (NamingException | SQLException e)
		{
			// TODO Auto-generated catch block
			   e.printStackTrace();
			//return null;
		}
		finally { close(rs, ps, conn); }
		
	}
	
	protected void close(ResultSet rs, PreparedStatement ps, Connection conn) {
		  
		  System.out.println("****** Closing rs/ps/conn objects ******");
		  
	    try {
	      if (rs != null) {
	        rs.close();
	      }

	      if (ps != null) {
	        ps.close();
	      }

	      if (conn != null) {
	    	  conn.close();
	      }
	    } catch (Exception e) {

	    }
	  }
	protected void close(ResultSet rs, Statement ps, Connection conn) {
		  
		  System.out.println("****** Closing rs/ps/conn objects ******");
		  
	    try {
	      if (rs != null) {
	        rs.close();
	      }

	      if (ps != null) {
	        ps.close();
	      }

	      if (conn != null) {
	    	  conn.close();
	      }
	    } catch (Exception e) {

	    }
	  }
	
	 public static Date parseDate(String date) {
	     try {
	         return new SimpleDateFormat("yyyy-mm-dd").parse(date);
	     } catch (ParseException e) {
	         return null;
	     }
	  }
	 /*
	  * searches horizontally to find the next non empty cell
	  * when the cell is deleted 
	  * */
	 public int  getNextNonEmptyCell(long id)
	 {
		 int  t = 0;
		 String result= "";
		 for(int j = 1; j<10; j++)
		 {
			 
			result =  readOne("ratecat"+j,id);
			if (result!=null && !result.equals("")){
				t = j;
				break;
			}
		 }
		 
		 return t;
	 }
	 

}

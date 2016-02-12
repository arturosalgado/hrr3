package com.hrr3.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hrr3.authentication.AuthenticationServiceHRR3Impl;
import com.hrr3.entity.Hotel;
import com.hrr3.entity.User;
import com.hrr3.entity.ssr.SSRInputData;
import com.hrr3.model.SSRInputDAO;

import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;


import com.hrr3.services.AuthenticationService;

/**
 * Servlet implementation class SsrController
 */
@WebServlet("/ssrcontroller")
public class SsrController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private ListModel<SSRInputData> ssrDataRows;
	
	private String site_url = "";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SsrController() {
        super();
        // TODO Auto-generated constructor stub
        
      
        
    }
    public static String getURLWithContextPath(HttpServletRequest request) {
    	   return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	site_url = this.getURLWithContextPath(request);	
	request.setAttribute("site_url", site_url);
	
	RequestDispatcher view = request.getRequestDispatcher("WEB-INF/views/ssr.jsp");
	view.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		site_url = this.getURLWithContextPath(request);
		String date1 = request.getParameter("date1");
		String date2 = request.getParameter("date2");
		
		request.setAttribute("site_url", site_url);
		
		String []date1a = date1.split("\\/");
		String month1 = date1a[0];
		String day1 = date1a[1];
		String year1 = date1a[2];
		
		String []date2a = date2.split("\\/");
		String month2 = date2a[0];
		String day2 = date2a[1];
		String year2 = date2a[2];
		
		String finalDate1 = year1+"-"+month1+"-"+day1;
		String finalDate2 = year2+"-"+month2+"-"+day2;
		System.out.print(date1);
		System.out.print(date2);
		request.setAttribute("date1", date1);
		request.setAttribute("date2", date2);
		
		HttpSession session  = request.getSession();
		
		User user = (User)session.getAttribute("myUserData");
		
		if (user==null)
		{
			
			response.sendRedirect("/login.zul");
		}
		else{
			Hotel hotel = user.getCurrentHotel();
			
			if (hotel==null || user.getUserId()==null)
				response.sendRedirect("/login.zul");	
		}
		
		
		Date dateFrom =null;
		Date dateTo = null;		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateInString = finalDate1;
		String dateInString2 = finalDate2;
		
		try{
		 dateFrom = sdf.parse(dateInString);
		 dateTo = sdf.parse(dateInString2);
		}
		catch(Exception e){};
		
		if (user==null || user.getUserId()==null)
			response.sendRedirect("/login.zul");
		
		int userId = user.getUserId();
		int customerId = user.getCurrentCustomer().getCustomerId();
		
		SSRInputDAO ssrDAO = new SSRInputDAO(user.getCurrentHotel());

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		List<SSRInputData> tdrList = ssrDAO.getSSRInputData(
				dateFormat.format(dateFrom), dateFormat.format(dateTo), userId,
				customerId);

		
		
		
		System.out.println("user id "+userId);
		System.out.println("customer id "+customerId);
			
		
		request.setAttribute("tdrList", tdrList);
		
		System.out.println("User is "+user);
		request.setAttribute("user", user);
		request.getRequestDispatcher("WEB-INF/views/ssr.jsp").forward(request, response);
		
		
	}
	
	 public static Date parseDate(String date) {
	     try {
	         return new SimpleDateFormat("yyyy-mm-dd").parse(date);
	     } catch (ParseException e) {
	         return null;
	     }
	  }

}

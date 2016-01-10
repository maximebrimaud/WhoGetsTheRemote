

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class First
 */
@WebServlet("/First")
public class First extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public First() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
    	// Set refresh, autoload time as 30 seconds
    	response.setIntHeader("Refresh", 30);

    	// Set response content type
    	response.setContentType("text/html");

    	// Get current time
    	Calendar calendar = new GregorianCalendar();
    	String am_pm;
    	int hour = calendar.get(Calendar.HOUR);
    	int minute = calendar.get(Calendar.MINUTE);
    	int second = calendar.get(Calendar.SECOND);
    	if(calendar.get(Calendar.AM_PM) == 0)
    		am_pm = "AM";
    	else
    		am_pm = "PM";

    	String CT = hour+":"+ minute +":"+ second +" "+ am_pm;

    	PrintWriter out = response.getWriter();
    	String title = "Rafraichissement de la page pour connaitre l'etat des votes";
    	String docType =
    			"<!doctype html public \"-//w3c//dtd html 4.0 " +
    					"transitional//en\">\n";
    	out.println(docType +
    			"<html>\n" +
    			"<head><title>" + title + "</title></head>\n"+
    			"<body bgcolor=\"#f0f0f0\">\n" + 
    			"<img src= '//Users/Max Hark/Documents/Workspace/WhoGetsTheRemote/img/logo.png' alt='logo'>  </img>" +
    			"<h1 align=\"center\">" + title + "</h1>\n" +
    			"<p>il est actuellement : " + CT + "</p>\n");
    }
    
    // Method to handle POST method request.
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
    	doGet(request, response);
    }
}

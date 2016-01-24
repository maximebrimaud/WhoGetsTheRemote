package database;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UpdateNomFilm
 */
@WebServlet("/UpdateNomFilm")
public class UpdateNomFilm extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UpdateNomFilm() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */

	public void doGet(HttpServletRequest req, HttpServletResponse res)  throws IOException, ServletException    
	{ 
		res.setContentType("text/html"); 
		PrintWriter out = res.getWriter(); 
		PrintWriter pwinsert = res.getWriter(); 
		//  PrintWriter pwdelete = res.getWriter(); 

		Connection con = null; 
		PreparedStatement ps = null; 
		ResultSet rs = null; 
		Statement st = null; 


		out.println("<html>"); 
		out.println("<head>"); 
		out.println("<title>Film Data</title>"); 
		out.println("</head>"); 

		out.println("<body>"); 
		out.println("<center><u><h1>Film Data</h1></u>"); 

		out.println("<form name='form' >"); 

		
		out.println("<table> "); 
		out.println("<tr>"); 
		out.println("<td> Nom </td>"); 
		out.println("<td> Description </td>"); 
		out.println("<td> Director </td>"); 
		out.println("<td> Date </td>"); 
		out.println("<td> Rating </td>");
		out.println("</tr>"); 


		String nom = req.getParameter("name"); 
		String des = req.getParameter("description"); 
		String dir = req.getParameter("director"); 
		String date = req.getParameter("date"); 
		String rating = req.getParameter("idmbRating");

		req.setAttribute("NAME", nom);
		req.setAttribute("DESCRIPTION", des);
		req.setAttribute("DIRECTOR", dir);
		req.setAttribute("DATE", date);
		req.setAttribute("IMDBRATING", rating);
		req.setAttribute("EDIT", "Y");

		//req.getRequestDispatcher("/index.jsp").forward(req, res);

	}


	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException    {

		res.setContentType("text/html"); 
		PrintWriter out = res.getWriter(); 
		
		//PrintWriter pwinsert = res.getWriter(); 
		//  PrintWriter pwdelete = res.getWriter(); 

		Connection con = null; 
		

		try 
		{
			Class.forName("org.apache.derby.jdbc.ClientDriver");
			con = DriverManager.getConnection("jdbc:odbc:ordersdb", "user", "passwd"); 

			// Turn on transactions
		    con.setAutoCommit(false);

		    Statement stmt = con.createStatement();
		    
		    String nom = req.getParameter("name"); 
			String date = req.getParameter("date"); 
			String dir = req.getParameter("director"); 
			
			

			stmt.executeUpdate("update film set name="+ nom + " where director=" + dir +" and date=" + date);

			con.commit();
			
			out.println("Film name updated succesfully!");
		
			
		} 
		catch(Exception e)  
		{ 
			
			 try 
			 {
			       con.rollback();
			 }
			 catch (SQLException ignored) { }
			 
			     out.println("Failed to update film name");
	    }
		
		
		finally
		{
			// Clean up.
			 try 
			 {
			        if (con != null) con.close();
			 }
			 catch (SQLException ignored) { }
		}
			
			
		
	}
}


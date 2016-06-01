package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.sun.tools.xjc.reader.xmlschema.bindinfo.BIConversion.User;

import ejb.UserEjb;
//import models.User;

/**
 * Servlet implementation class UpdateUser
 */
@WebServlet("/UpdateUser")
public class UpdateUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@EJB UserEjb bean;
	
	@Inject DataSource ds;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateUser() { 
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		//Users user = em.find(Users.class, 1);
		////em.createQuery("UPDATE Users set firstName= '" + request.getParameter("firstName") +  "' where id=1").executeUpdate();
		//em.getTransaction().begin();
		//user.setFirstName(request.getParameter("firstName"));
		//user.setLastName(request.getParameter("lastName"));
		//user.setEmail(request.getParameter("email"));
		//em.getTransaction().commit();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		 res.setContentType("text/plain");
		    PrintWriter out = res.getWriter();

		    Connection con = null;
		    try 
		    {
		      con = ds.getConnection();
		      Statement s = con.createStatement();
		      
		      // Turn on transactions
		      con.setAutoCommit(false);

		      Statement stmt = con.createStatement();
		     /* ResultSet rs = stmt.executeQuery("select * from Film where id=1");
		      rs.next();
		      String a = rs.getString("");
		      
		      String up = "nameMustChange";*/
		      
		      stmt.executeUpdate("UPDATE Users SET lastName = " + req.getParameter("lastName")+ ", firstName = " + req.getParameter("firstName")+ ", email = " + req.getParameter("email")+ " where id = 1");
		      

		      con.commit();
		      out.println("Update successful!");
		      
		    }
		    catch (Exception e) {
		      // Any error is grounds for rollback
		      try {
		        con.rollback();
		      }
		      catch (SQLException ignored) { }
		      out.println("Update failed");
		    }
		    finally {
		      // Clean up.
		      try {
		        if (con != null) con.close();
		      }
		      catch (SQLException ignored) { }
		    }
	}

}

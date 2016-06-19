package servlet;

import java.io.IOException;

import javax.servlet.http.*;
import javax.annotation.Resource;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.sql.DataSource;

import models.User;

@WebServlet("/Profile")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	@Resource private DataSource myDataSource;
	
    public UserServlet() 
    {
        super();
    }  

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{	
		try 
		{
			//Setting up database connection
	        Connection myConnection = myDataSource.getConnection("APP","APP");			
	        System.out.println("i am in doGet UserServlet, Got Connection!");
			
	        HttpSession session;
			session = request.getSession();
	        int idUser = (Integer)session.getAttribute("sessionId");
	        System.out.println("i am in doGet UserServlet, preparing  session");
	        
	        //Statement myStatement =	myConnection.createStatement();
	        PreparedStatement userStatement = myConnection.prepareStatement("SELECT * from USERS WHERE ID_USER = " + idUser + "");
			System.out.println("i am in doGet UserServlet, Got Statement!");

			 //Authentication
			ResultSet myResultSet = userStatement.executeQuery();
			System.out.println("i am in doGet UserServlet, Executed Statement!");
			 
			while (myResultSet.next()) {				
				System.out.println("i am in doGet UserServlet, in the Result set");
				int id = myResultSet.getInt("ID_USER");
				String nom = myResultSet.getString("NOM_USER");
				String prenom = myResultSet.getString("PRENOM_USER");
				String sexee = myResultSet.getString("SEXE");
				String usernamee = myResultSet.getString("USERNAME");
				String passwordd = myResultSet.getString("PASSWORD_USER");
				String emaill =  myResultSet.getString("EMAIL_USER");
				String Bday =  myResultSet.getString("DATE_OF_BIRTH");
				String creationDatee =  myResultSet.getString("USER_CREATION_DATE");
				String modificationDatee =  myResultSet.getString("USER_MODIFICATION_DATE");
				String addresss =  myResultSet.getString("ADDRESS_USER");
				String imagee =  myResultSet.getString("IMAGE_USER");
				User currentUser = new User(id,prenom,nom,usernamee,passwordd,emaill,Bday, sexee, addresss, imagee, modificationDatee, creationDatee);
				
				session.setAttribute("userLogged", null);	
				session.setAttribute("userLogged", currentUser);		
				
				
				System.out.println("value set.. redirecting to home");
				
				System.out.println("redirecting to user page");
				request.getRequestDispatcher("/UserProfile.jsp").forward(request, response);
            }
			
			myConnection.close();
			System.out.println("i am out of doGet UserServlet");
		} 		
		catch (SQLException e) 
		{ 
			System.out.println(e.getMessage());
			e.printStackTrace();
		}			 
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{				
		System.out.println("i am in do POST UserServlet");
		response.getWriter().append("Served at: ").append(request.getContextPath());
		System.out.println("i am out of do POST UserServlet");	
	}
}

package servlet;

import java.io.IOException;

import javax.servlet.http.*;
import javax.annotation.Resource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import models.Film;
import models.User;

@WebServlet("/Login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	@Resource private DataSource myDataSource;
	
    public LoginServlet() 
    {
        super();
    }  

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{		
		System.out.println("i am in do GET Login");
		response.getWriter().append("Served at: ").append(request.getContextPath());
		System.out.println("i am out of do GET Login");				 
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{							
		try 
		{
			//getting Request data
			String user = request.getParameter("LoginUsername");
	        String pass = request.getParameter("LoginPassword");
	        System.out.println("i am in do POST Login, user: " + user + " and password: " + pass);
	        
	        //Setting up database connection
	        Connection myConnection = myDataSource.getConnection("APP","APP");			
	        System.out.println("i am in do POST Login, Got Connection!");
			
	        //Statement myStatement =	myConnection.createStatement();
	        PreparedStatement myStatement = myConnection.prepareStatement("SELECT * from USERS WHERE USERNAME = '" + user + "' AND PASSWORD_USER = '" + pass + "' ");
			System.out.println("i am in do POST Login, Got Statement!");

			 //Authentication
			ResultSet myResultSet = myStatement.executeQuery();
			 System.out.println("i am in do POST Login, Executed Statement!");
			 
			if (myResultSet.next()) 
			{				
				System.out.println("i am in do POST Login, in the Result set");
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
				                    
				//Creer un httpsession pour mettre l'objet user
				HttpSession session;
				session = request.getSession();
				session.setAttribute("sessionId", currentUser.getId());
				session.setAttribute("userLogged", currentUser);		
				
				System.out.println("username is: " + usernamee);	
				if (usernamee == "Admin" || usernamee.equals("Admin")){			
					System.out.println("setting value for servlet");			
					session.setAttribute("UserType", "Admin");			
				}			
				else{			
					session.setAttribute("UserType", "User");			
				}			
				System.out.println("value set.. redirecting to home");
				
				System.out.println("redirecting to home servlet");
				request.getRequestDispatcher("/Home").forward(request, response);
			} 
            else 
            {
            	request.setAttribute("loginMessage", "Invalid Username and Password combination!");
                request.getRequestDispatcher("/LoginPage.jsp").forward(request, response);
            	System.out.println("Incorrect login credentials");
            }
			
			myConnection.close();
			System.out.println("i am out of do POST Login");
		} 		
		catch (SQLException e) 
		{ 
			request.setAttribute("loginMessage", "Error in connection!");	
			request.getRequestDispatcher("/LoginPage.jsp").forward(request, response);			
			System.out.println("Error in connection!");
			System.out.println(e.getMessage());
			e.printStackTrace();
		}	
	}
}

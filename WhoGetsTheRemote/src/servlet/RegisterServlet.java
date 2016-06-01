package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.User;

import javax.sql.DataSource;

import com.sun.xml.rpc.processor.modeler.j2ee.xml.string;

import javafx.scene.control.Alert;

import java.io.*;
import javax.servlet.http.*;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.*;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@Resource private DataSource myDataSource;
    public RegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		System.out.println("i am in do GET Register");
		response.getWriter().append("Served at: ").append(request.getContextPath());
		System.out.println("i am out of do GET Register");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		try 
		{
			//getting Request data
			String firstName = request.getParameter("FirstName");
			String lastName = request.getParameter("LastName");
			String email = request.getParameter("email");
			String dateOfBirth = request.getParameter("dateOfBirth");
			String sexe = request.getParameter("Sexe");								
			String user = request.getParameter("Username");
	        String pass = request.getParameter("password");
	        String address = request.getParameter("Address");
	        
	        System.out.println("i am in do POST Login, user: " + user + " and password: " + pass);
	        //Setting up database connection
	        Connection myConnection = myDataSource.getConnection("APP","APP");			
	        System.out.println("i am in do POST Login, Got Connection!");
			//PreparedStatement myStatement = myConnection.prepareStatement("SELECT * from USERS WHERE USERNAME = '" + user + "' AND PASSWORD_USER = '" + pass + "' ");
	        
	        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // HH:mm:ss
	        Date date = new Date();
	         //2014-08-06   //15:59:48
	        String s = dateFormat.format(date);
	        System.out.println("Creation time is : " + s);
	        
	        PreparedStatement myStatement = myConnection.prepareStatement("INSERT INTO USERS (NOM_USER, PRENOM_USER, EMAIL_USER, DATE_OF_BIRTH, SEXE, USERNAME, PASSWORD_USER, ADDRESS_USER, USER_CREATION_DATE, USER_MODIFICATION_DATE) VALUES ('" +firstName+ "','"+ lastName + "','"+ email + "','"+ dateOfBirth + "','"+ sexe + "','"+ user + "','"+ pass + "','"+ address + "','" + s + "','" + s + "')");
	        																				//"('" +firstName+ "','"+ lastName + "','"+ email + "','"+ dateOfBirth + "','"+ sexe + "','"+ user + "','"+ pass + "','"+ address + "','" + s + "')"
	        System.out.println("i am in do POST Login, Got Statement!");
			//Authentication
			int myResultSet = myStatement.executeUpdate();  //executeQuery();
			 System.out.println("i am in do POST Register, Executed Update Statement!");
			//ResultSet myResultSet = myStatement.executeQuery("SELECT * from Users WHERE Username = \"" + user+ "\" AND Password = \"" + pass + "\" ");
			
			if (myResultSet == 1) 
			{				
				
				PreparedStatement myStatement2 = myConnection.prepareStatement("SELECT * from USERS WHERE USERNAME = '" + user + "' AND PASSWORD_USER = '" + pass + "' ");
				ResultSet myResultSet2 = myStatement2.executeQuery();
				if (myResultSet2.next())
				{
					int id = myResultSet2.getInt("ID_USER");
					String nom = myResultSet2.getString("NOM_USER");
					String prenom = myResultSet2.getString("PRENOM_USER");
					String sexe2 = myResultSet2.getString("SEXE");
					String username = myResultSet2.getString("USERNAME");
					String password = myResultSet2.getString("PASSWORD_USER");
					String email2 =  myResultSet2.getString("EMAIL_USER");
					String Bday =  myResultSet2.getString("DATE_OF_BIRTH");
					String CreationDate =  myResultSet2.getString("USER_CREATION_DATE");
					User currentUser = new User(id,prenom,nom,username,password,email,CreationDate) ;
					String fullName = prenom+ " " +nom;
				
				
					request.setAttribute("user", fullName);		
					request.setAttribute("CurrentUser", currentUser);
					request.getRequestDispatcher("/Home.jsp").forward(request, response);
					System.out.println("Correct login credentials, with username: " + username + " and userId: " + id + " and sexe: " + sexe2);
				}
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
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

}

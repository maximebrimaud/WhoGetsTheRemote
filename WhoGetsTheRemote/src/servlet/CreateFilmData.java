package servlet;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import models.Film;
import models.FilmCategorie;
import models.User;

/**
 * Servlet implementation class CreateFilmData
 */
@WebServlet("/CreateFilmData")
public class CreateFilmData extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Resource private DataSource myDataSource;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateFilmData() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		try 
		{
			 System.out.println("i am in update User, Got Connection!");
				        
	        //Setting up database connection	        
	        Connection myConnection = myDataSource.getConnection("APP","APP");			
	        System.out.println("i am in update User, Got Connection!");
			
	        //Statement myStatement =	myConnection.createStatement();
	        PreparedStatement myStatement = myConnection.prepareStatement("SELECT ID_FILM_CATEGORIE, LIBELLE_CATEGORIE from FILM_CATEGORIE ORDER BY LIBELLE_CATEGORIE"); //Integer.parseInt(UserId)
			System.out.println("i am in update User, Got user statment!");

			 //Authentication
			ResultSet myResultSet = myStatement.executeQuery();
			System.out.println("i am in update User, Executed get user Statement!");
				
			ArrayList<FilmCategorie> CategorieList = new ArrayList<FilmCategorie>();	
			while(myResultSet.next()) 
			{
				int categorieId = myResultSet.getInt("ID_FILM_CATEGORIE");				
				String categorieLibelle = myResultSet.getString("LIBELLE_CATEGORIE");	
				
				FilmCategorie newFC = new FilmCategorie(categorieId, categorieLibelle);
				
				CategorieList.add(newFC);
			}
			
						
			/*if (myResultSet.next()) 
			{*/							        		      								
				request.setAttribute("CategorieList" , CategorieList);						
				request.getRequestDispatcher("/MoviesCreate.jsp").forward(request, response);																			
			/*}	
			else
			{	
				request.setAttribute("CreateMessage" , "Error while loading page!");						
				request.getRequestDispatcher("/MoviesCreate.jsp").forward(request, response);															
			}*/
		}
		catch (SQLException e) 
		{
			request.setAttribute("CreateMessage" , "Error while loading page!");
			request.getRequestDispatcher("/MoviesCreate.jsp").forward(request, response);			
			System.out.println("redirected to UserProfile");
        	System.out.println("Error in connection!");
			System.out.println(e.getMessage());
			e.printStackTrace();
		} 
		
		
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
	}

}

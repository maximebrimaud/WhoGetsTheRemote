package servlet;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import models.FilmCategorie;
import models.User;

/**
 * Servlet implementation class CreateFilm
 */           
@WebServlet("/CreateFilm")
public class CreateFilm extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Resource private DataSource myDataSource;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateFilm() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try 
		{
			System.out.println("in create film get");
	        //Setting up database connection	        
	        Connection myConnection = myDataSource.getConnection("APP","APP");				        
			
	        //Statement myStatement =	myConnection.createStatement();
	        PreparedStatement myStatement = myConnection.prepareStatement("SELECT ID_FILM_CATEGORIE, LIBELLE_CATEGORIE from FILM_CATEGORIE ORDER BY LIBELLE_CATEGORIE"); //Integer.parseInt(UserId)			

			 //Authentication
			ResultSet myResultSet = myStatement.executeQuery();			
				
			ArrayList<FilmCategorie> CategorieList = new ArrayList<FilmCategorie>();	
			while(myResultSet.next()) 
			{
				int categorieId = myResultSet.getInt("ID_FILM_CATEGORIE");				
				String categorieLibelle = myResultSet.getString("LIBELLE_CATEGORIE");					
				FilmCategorie newFC = new FilmCategorie(categorieId, categorieLibelle);				
				CategorieList.add(newFC);
			}
			System.out.println("iredirecting to filmServlet");
			request.setAttribute("CategorieList" , CategorieList);						
			request.getRequestDispatcher("/MoviesCreate.jsp").forward(request, response);
			/*request.getRequestDispatcher("/FilmServlet").forward(request, response);*/
		}
		catch (SQLException e) 
		{
			request.setAttribute("CreateMessage" , "Error while loading page!");
			request.getRequestDispatcher("/Movies").forward(request, response);			
			System.out.println("redirected to UserProfile");
        	System.out.println("Error in connection!");
			System.out.println(e.getMessage());
			e.printStackTrace();
		} 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{		
		try 
		{
			//getting Request data
			String Title = request.getParameter("Title");
			String Director = request.getParameter("Director");
			String Language = request.getParameter("Language");
			String Cast = request.getParameter("Cast"); 
			String ReleaseDate = request.getParameter("ReleaseDate");								
			String Categorie = request.getParameter("Categorie");
	        String Duration = request.getParameter("Duration");
	        String PG = request.getParameter("PG");
	    	String Description = request.getParameter("Description"); 
			String Rating = request.getParameter("Rating");								
			String PictureBrowse = request.getParameter("PictureBrowse");
	        String Trailer = request.getParameter("Trailer");
	        String Movie = request.getParameter("Movie");
	        	      
	        //Setting up database connection
	        Connection myConnection = myDataSource.getConnection("APP","APP");			
	        System.out.println("i am in do POST Login, Got Connection!");
			//PreparedStatement myStatement = myConnection.prepareStatement("SELECT * from USERS WHERE USERNAME = '" + user + "' AND PASSWORD_USER = '" + pass + "' ");
	        
	        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // HH:mm:ss
	        Date date = new Date();
	         //2014-08-06   //15:59:48
	        String creationDate = dateFormat.format(date);
	        System.out.println("Creation time is : " + creationDate);
	        String imageRelativePath = "img/Movies/" + Title + ".jpg"; 
	        System.out.println("imageRelativePath is : " + imageRelativePath);
	        PreparedStatement myStatement = myConnection.prepareStatement("INSERT INTO FILM (FILM_IMAGE, FILM_LANGUAGE, FILM_CAST, FILM_DIRECTOR, FILM_PG_LEVEL, FILM_DURATION, FILM_MODIFICATION_DATE, FILM_CREATION_DATE, ID_CATEGORIE_FILM, FILM_LINK, TRAILER_FILM_LINK, NOTATION_FILM, DATE_RELEASED, DESCRIPTION_FILM, NOM_FILM) VALUES ('" + imageRelativePath + "','"+ Language + "','"+ Cast + "','"+ Director + "',"+ PG + ",'"+ Duration + "','"+ creationDate + "','"+ creationDate + "'," + Categorie + ",'" + Movie + "','"+ Trailer + "',"+ Rating + ",'"+ ReleaseDate + "','"+ Description + "','"+ Title + "')");
	      
	        System.out.println("INSERT INTO FILM (FILM_IMAGE, FILM_LANGUAGE, FILM_CAST, FILM_DIRECTOR, FILM_PG_LEVEL, FILM_DURATION, FILM_MODIFICATION_DATE, FILM_CREATION_DATE, ID_CATEGORIE_FILM, FILM_LINK, TRAILER_FILM_LINK, NOTATION_FILM, DATE_RELEASED, DESCRIPTION_FILM, NOM_FILM) VALUES ('" +imageRelativePath+ "','"+ Language + "','"+ Cast + "','"+ Director + "',"+ PG + ",'"+ Duration + "','"+ creationDate + "','"+ creationDate + "'," + Categorie + ",'" + Movie + "','"+ Trailer + "',"+ Rating + ",'"+ ReleaseDate + "','"+ Description + "','"+ Title + "')");
			
	        //Authentication
			int myResultSet = myStatement.executeUpdate();  //executeQuery();
			 System.out.println("i am in do POST Register, Executed Update Statement!");
			//ResultSet myResultSet = myStatement.executeQuery("SELECT * from Users WHERE Username = \"" + user+ "\" AND Password = \"" + pass + "\" ");
			
			if (myResultSet == 1) 
			{			
				// UPLOADING IMAGE
				BufferedImage newImage = ImageIO.read(new File(PictureBrowse));							
				
				String path = "img/Movies";
				String outputpath = this.getServletContext().getRealPath(path);
				System.out.println("testing upload... path: " + path);
				System.out.println("testing upload... outputpath: " + outputpath);
				
				ImageIO.write(newImage, "jpg", new File(outputpath + "/"+ Title + ".jpg"));
				System.out.println("image uploaded to: '" + outputpath + "/"+ Title + ".jpg'");
				// IMAGE UPLOADED DONE!!!
				
				
				request.setAttribute("ImagePath" , outputpath);
											
				System.out.println(">>>> Query result is true");
				//response.sendRedirect("./CreateFilmData");
				request.setAttribute("CreateMessage", "* Movie Created successfully !");
				//request.getRequestDispatcher("./CreateFilmData").forward(request, response);
				doGet(request, response);
            } 
            else 
            {            	
            	System.out.println(">>>> Query result is false");
            	request.setAttribute("CreateMessage", "* Movie Creation Failed!");
                //request.getRequestDispatcher("/MoviesCreate.jsp").forward(request, response);
                doGet(request, response);
            }
		} 		
		catch (SQLException e) 
		{
			System.out.println(">>>> function Exception");
			request.setAttribute("CreateMessage", "* Movie Creation Failed!");
            //request.getRequestDispatcher("/MoviesCreate.jsp").forward(request, response); 
            doGet(request, response);
			System.out.println(e.getMessage());
			e.printStackTrace();
		}		
	}
}

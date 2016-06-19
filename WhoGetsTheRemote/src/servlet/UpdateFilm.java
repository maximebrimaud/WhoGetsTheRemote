package servlet;

import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.sql.DataSource;

import models.Film;
import models.FilmCategorie;
import models.User;

@WebServlet("/UpdateFilm")
public class UpdateFilm extends HttpServlet 
{	private static final long serialVersionUID = 1L;
	@Resource private DataSource myDataSource;
	
	public UpdateFilm()
	{
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try 
		{
			
			System.out.println(">>>>>>>>>>>>>> in GET1!!!! ");
						
			
			
			String	filmId = request.getParameter("filmId");
			
			System.out.println(">>>>>>>>>>>>>> parameter film ID is = " + filmId);
			
			
		
			if (filmId == null)
			{
				System.out.println("fuck -- oke bel session");
				System.out.println(">>>>>>>>>>>>>> IT IS THE REFRESH");
				HttpSession session;
				session = request.getSession();
				String sessionFilmId = (String) session.getAttribute("filmId");
				
				System.out.println("session id film : " + sessionFilmId);
				filmId = sessionFilmId;
								
				System.out.println("jouwwaa");
			}
			else
			{
				System.out.println("fuck -- oke bel request");
				System.out.println(">>>>>>>>>>>>>> IT IS THE DETAILS");
			}
			
			
			
			
			System.out.println(">>>>>>>>>>>>>> in GET filmId = " + filmId);
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
			
			   //getting movie
	        PreparedStatement movieStatement = myConnection.prepareStatement("SELECT * from FILM where ID_FILM = " + filmId); //Integer.parseInt(UserId)			

			 //Authentication
			ResultSet movieResultSet = movieStatement.executeQuery();			
				
				
			while(movieResultSet.next()) 
			{	
				String Title = movieResultSet.getString("NOM_FILM");
				String Director = movieResultSet.getString("FILM_DIRECTOR");
				String Language = movieResultSet.getString("FILM_LANGUAGE");
				String Cast = movieResultSet.getString("FILM_CAST");
				String ReleaseDate = movieResultSet.getString("DATE_RELEASED");
				int Categorie = movieResultSet.getInt("ID_CATEGORIE_FILM");
				String Duration = movieResultSet.getString("FILM_DURATION");
			    int PG = movieResultSet.getInt("FILM_PG_LEVEL");
			    String Description = movieResultSet.getString("DESCRIPTION_FILM");
			    int Rating = movieResultSet.getInt("NOTATION_FILM");
			    String PictureBrowse = movieResultSet.getString("FILM_IMAGE");
			    String Trailer = movieResultSet.getString("TRAILER_FILM_LINK");
			    String Movie= movieResultSet.getString("FILM_LINK");
			    String creationDate = movieResultSet.getString("FILM_CREATION_DATE");
			    String modificationDate = movieResultSet.getString("FILM_MODIFICATION_DATE");
			    
				Film selectedMovie = new Film(Integer.parseInt(filmId), Title, Description, ReleaseDate, Rating, Trailer, Movie, Categorie, PictureBrowse, creationDate, modificationDate, Duration, PG, Director, Cast, Language);
				
				
				  PreparedStatement myStatement1 = myConnection.prepareStatement("SELECT * from FILM_CATEGORIE where ID_FILM_CATEGORIE = " + Categorie); //Integer.parseInt(UserId)			
				  ResultSet movieResultSet1 = myStatement1.executeQuery();			
					
				  FilmCategorie movieCategorie = null; 
					while(movieResultSet1.next()) 
					{
						int categorieId = movieResultSet1.getInt("ID_FILM_CATEGORIE");				
						String categorieLibelle = movieResultSet1.getString("LIBELLE_CATEGORIE");					
						movieCategorie = new FilmCategorie(categorieId, categorieLibelle);	
					}
					
				request.setAttribute("movie" , selectedMovie);	
				request.setAttribute("movieCategorie" , movieCategorie);
				request.setAttribute("CategorieList" , CategorieList);	
				System.out.println("redirecting from GET!!!! ");
				request.getRequestDispatcher("/MoviesUpdate.jsp").forward(request, response);
			}
		}
		catch (SQLException e) 
		{
			request.setAttribute("UpdateMessage" , "Error while loading page!");
			request.getRequestDispatcher("/MoviesUpdate.jsp").forward(request, response);			
			System.out.println("redirected to UserProfile");
        	System.out.println("Error in connection!");
			System.out.println(e.getMessage());
			e.printStackTrace();
		} 
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String movieId = null ;
		try 
		{

        	System.out.println("Any haaan");
        	
			String btnDeleteMovie = request.getParameter("btnDeleteMovie");
			String btnCreateEvent = request.getParameter("btnCreateEvent");
			
			if (btnDeleteMovie!=null)
			{
				System.out.println("ANA BEL btnDeleteMovie");
				// delete button was clicked
				movieId = request.getParameter("movieId");
				Connection myConnection = myDataSource.getConnection("APP","APP");			
				
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // HH:mm:ss
		        Date date = new Date();
		        String strDate = dateFormat.format(date);
		        System.out.println("Creation time is : " + strDate);
		        
		        System.out.println("query to check on delete = select * from GROUPE inner join FILM_CONTIENT_GROUPE on ID_GROUPE = ID_GROUPE_CONTIENT where ID_FILM_CONTIENT = " + movieId + " and WATCHING_DATE > '" + strDate + "'");
				String StatementStr1 = "select * from GROUPE inner join FILM_CONTIENT_GROUPE on ID_GROUPE = ID_GROUPE_CONTIENT where ID_FILM_CONTIENT = " + movieId + " and WATCHING_DATE > '" + strDate + "'";
				
				PreparedStatement myStatement1 = myConnection.prepareStatement(StatementStr1);
				ResultSet ResultSet1 = myStatement1.executeQuery();			
				
				
				if(ResultSet1.next()) 
				{
					System.out.println("ANA BEL btnDeleteMovie - RESULT = 1");	
					request.setAttribute("UpdateMessage" , "Can't delete Movie because of upcoming event!");
															
					HttpSession session;
					session = request.getSession();
					session.setAttribute("filmId", movieId);
					System.out.println("ABEL EL FORWARD ");										
					doGet(request, response);
				}
				else
				{					
			        String StatementStr = "UPDATE FILM SET FILM_DISABLE_DATE = '" + strDate+ "' WHERE ID_FILM = " + movieId;
															
					System.out.println("Film update statement is : " + StatementStr);
					
					PreparedStatement statement = myConnection.prepareStatement(StatementStr);
					System.out.println("prepared update statement object!");
					int result = statement.executeUpdate();
					System.out.println("update executed");
					if (result == 1)
					{				
						System.out.println("ANA BEL btnDeleteMovie - RESULT = 1");											
						RequestDispatcher rd = getServletContext().getRequestDispatcher("/Movies");
						System.out.println("ABEL EL FORWARD ");														
					
						rd.forward(request, response); 
					}
				}
			}
			else if (btnCreateEvent!=null)
			{
				movieId = request.getParameter("movieId");
								
				System.out.println("ANA BEL btnCreateEvent");											
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/CreateEvent?idMovie=" + movieId);
				System.out.println("ABEL EL FORWARD ");														
			
				rd.forward(request, response); 
			}
			else
			{
				System.out.println("ANA BEL btnUpdateMovie");
				// update button was clicked
				 System.out.println("i am in update film, Got Connection!");
				//getting Request data
				movieId = request.getParameter("movieId");
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
		        
		        //System.out.println("i am in do POST Login, user: " + user + " and password: " + pass);
		        
		        //Setting up database connection	        
		        Connection myConnection = myDataSource.getConnection("APP","APP");			
		        
				
		        //Statement myStatement =	myConnection.createStatement();
		        
		        String StatementStr = "UPDATE FILM SET NOM_FILM = '" + Title+ "', "
										        		+ "FILM_DIRECTOR ='"+ Director + "', "
									    				+ "FILM_LANGUAGE ='"+ Language + "', "
														+ "FILM_CAST ='"+ Cast + "', "
														+ "DATE_RELEASED ='"+ ReleaseDate + "', "
														+ "ID_CATEGORIE_FILM ="+ Categorie + ", "
														+ "FILM_DURATION ='"+ Duration + "', "
														+ "FILM_PG_LEVEL ="+ PG + ", "
														+ "DESCRIPTION_FILM ='"+ Description + "', "
														+ "NOTATION_FILM ="+ Rating + ", ";
														if (!PictureBrowse.trim().equals("")&& PictureBrowse != null)
														{
															String imageRelativePath = "img/Movies/" + Title + ".jpg"; 
															StatementStr = StatementStr+ "FILM_IMAGE ='"+ imageRelativePath + "', ";    
														}													
														StatementStr = StatementStr+ "TRAILER_FILM_LINK ='"+ Trailer + "', "
														+ "FILM_LINK ='"+ Movie + "'  "
														+ "WHERE ID_FILM = " + movieId;
														
				System.out.println("Film update statement is : " + StatementStr);
				
				PreparedStatement statement = myConnection.prepareStatement(StatementStr);
				System.out.println("prepared update statement object!");
				int result = statement.executeUpdate();
				System.out.println("update executed");
				if (result == 1)
				{				
					System.out.println("ANA BEL RESULT = 1");
							if (!PictureBrowse.trim().equals("") && PictureBrowse != null)
							{																						
								BufferedImage newImage = ImageIO.read(new File(PictureBrowse));							
								
								String path = "img/Movies";
								String outputpath = this.getServletContext().getRealPath(path);
								System.out.println("testing upload... path: " + path);
								System.out.println("testing upload... outputpath: " + outputpath);
								
								ImageIO.write(newImage, "jpg", new File(outputpath + "/"+ Title + ".jpg"));
								System.out.println("image uploaded to: '" + outputpath + "/"+ Title + ".jpg'");							
							}
							//request.setAttribute("ImagePath" , outputpath);
											
							
						//	request.setAttribute("UpdateMessage" , "Movie Updated successfully");
							//request.setAttribute("filmId",movieId);
							HttpSession session;
							session = request.getSession();
							session.setAttribute("filmId", movieId);
							
							//RequestDispatcher rd = getServletContext().getRequestDispatcher("/UpdateFilm?filmId="+movieId);
							System.out.println("ABEL EL FORWARD ");
							doGet(request, response);
							//response.sendRedirect("/UpdateFilm?filmId="+movieId);
						
							//rd.forward(request, response); 																				
				}	
				else
				{	
					System.out.println("ana bel ELSE");
					//request.setAttribute("UpdateMessage" , "Movie Updated FAILED!");
					//RequestDispatcher rd = getServletContext().getRequestDispatcher("/UpdateFilm?filmId="+movieId);
					HttpSession session;
					session = request.getSession();
					session.setAttribute("filmId", movieId);
					System.out.println("ABEL EL FORWARD ");
					doGet(request, response);
					//rd.forward(request, response); 
					// NOTHING HAS CHANGED, JUST SEND HIM BACK TO USER PROFILE PAGE
				}
			}
			 
		}
		catch (SQLException e) 
		{
			//HttpSession session;
			
			System.out.println("catch: redirecting to UpdateFilm");
			/*request.setAttribute("UpdateMessage" , "* Update failed!");
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/UpdateFilm?filmId=" + movieId);
			rd.forward(request, response);*/
			HttpSession session;
			session = request.getSession();
			session.setAttribute("filmId", movieId);
			doGet(request, response);
//			request.setAttribute("loginMessage", "Error in connection!");
//            request.getRequestDispatcher("/LoginPage.jsp").forward(request, response);
        	System.out.println("Error in connection!");
			System.out.println(e.getMessage());
			e.printStackTrace();
		} 
  }
}

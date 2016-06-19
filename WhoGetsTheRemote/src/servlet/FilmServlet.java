package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import models.Film;
import models.User;

/**
 * Servlet implementation class MoviesServlet
 */
@WebServlet("/Movies")
public class FilmServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Resource private DataSource myDataSource;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FilmServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		/*response.getWriter().append("Served at: ").append(request.getContextPath());*/
		System.out.println("in film servlet get");
		Connection myConnection;
		try 
		{
			myConnection = myDataSource.getConnection("APP","APP");
			int i = 0;
			PreparedStatement statementNewFilms = myConnection.prepareStatement("SELECT * FROM FILM WHERE (FILM_DISABLE_DATE is null) ORDER BY FILM_CREATION_DATE, NOM_FILM"); //WHERE FILM_DISABLE_DATE = ''
			ResultSet newMoviesResult = statementNewFilms.executeQuery();
			List<Film> listFilm = new ArrayList<Film>();
			while (newMoviesResult.next()) 
			{
				int filmId = newMoviesResult.getInt("ID_FILM");
				String Title = newMoviesResult.getString("NOM_FILM");
				String Director = newMoviesResult.getString("FILM_DIRECTOR");
				String Language = newMoviesResult.getString("FILM_LANGUAGE");
				String Cast = newMoviesResult.getString("FILM_CAST");
				String ReleaseDate = newMoviesResult.getString("DATE_RELEASED");
				int Categorie = newMoviesResult.getInt("ID_CATEGORIE_FILM");
				String Duration = newMoviesResult.getString("FILM_DURATION");
			    int PG = newMoviesResult.getInt("FILM_PG_LEVEL");
			    String Description = newMoviesResult.getString("DESCRIPTION_FILM");
			    int Rating = newMoviesResult.getInt("NOTATION_FILM");
			    String PictureBrowse = newMoviesResult.getString("FILM_IMAGE");
			    String Trailer = newMoviesResult.getString("TRAILER_FILM_LINK");
			    String Movie= newMoviesResult.getString("FILM_LINK");
			    String creationDate = newMoviesResult.getString("FILM_CREATION_DATE");
			    String modificationDate = newMoviesResult.getString("FILM_MODIFICATION_DATE");
			    String disableDate = newMoviesResult.getString("FILM_DISABLE_DATE");
				
			    Film selectedMovie = new Film(filmId, Title, Description, ReleaseDate, Rating, Trailer, Movie, Categorie, PictureBrowse, creationDate, modificationDate, Duration, PG, Director, Cast, Language);
								
			    listFilm.add(selectedMovie);
								
				i++;
				System.out.println("Film " + i + " : " + Title + " - AND DISABLE DATE = '" + disableDate + "'");
			} 
			request.setAttribute("listFilm", listFilm);
			System.out.println("redirecting to Movies Page");
			request.getRequestDispatcher("/MoviesPage.jsp").forward(request, response);
			
			System.out.println("redirected to Movies Page");			
		} 		
		catch (Exception e) 
		{
			System.out.println("i am in catch ALL Exception POST FriendsServlet, Got Connection!");
			request.setAttribute("loginMessage", "* Error in connection");
			request.getRequestDispatcher("/LoginPage.jsp").forward(request, response);
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
			String btnSearch = request.getParameter("btnSearch");
		 	
			if (btnSearch!=null)
			{
				System.out.println("i am in do POST");
				response.getWriter().append("Served at: ").append(request.getContextPath());
				System.out.println("i am out of do POST");	
				
					String SearchInput = request.getParameter("SearchInput");
					Connection myConnection = myDataSource.getConnection("APP","APP");				
					
					List<Film> MovieSearchList = new ArrayList<Film>();
					
					PreparedStatement statementMovieSearch = myConnection.prepareStatement("select * from FILM "
							+ "INNER JOIN FILM_CATEGORIE ON ID_CATEGORIE_FILM = ID_FILM_CATEGORIE "
							+ "WHERE FILM_DISABLE_DATE IS NULL and (NOM_FILM LIKE '%" + SearchInput + "%' "
									+ "or FILM_LANGUAGE LIKE '%" + SearchInput + "%'  "
											+ "or FILM_CAST LIKE '%" + SearchInput + "%'   "
													+ "or FILM_DIRECTOR LIKE '%" + SearchInput + "%' "															
																+ "or LIBELLE_CATEGORIE LIKE '%" + SearchInput + "%'  "
																		+ "or DESCRIPTION_FILM LIKE '%" + SearchInput + "%')");
				
					ResultSet newMovieSearch = statementMovieSearch.executeQuery();
					System.out.println("i am in do GET FriendsProfileServlet, preparing friends list");
					while(newMovieSearch.next())
					{
						int filmId = newMovieSearch.getInt("ID_FILM");
						String Title = newMovieSearch.getString("NOM_FILM");
						String Director = newMovieSearch.getString("FILM_DIRECTOR");
						String Language = newMovieSearch.getString("FILM_LANGUAGE");
						String Cast = newMovieSearch.getString("FILM_CAST");
						String ReleaseDate = newMovieSearch.getString("DATE_RELEASED");
						int Categorie = newMovieSearch.getInt("ID_CATEGORIE_FILM");
						String Duration = newMovieSearch.getString("FILM_DURATION");
					    int PG = newMovieSearch.getInt("FILM_PG_LEVEL");
					    String Description = newMovieSearch.getString("DESCRIPTION_FILM");
					    int Rating = newMovieSearch.getInt("NOTATION_FILM");
					    String PictureBrowse = newMovieSearch.getString("FILM_IMAGE");
					    String Trailer = newMovieSearch.getString("TRAILER_FILM_LINK");
					    String Movie= newMovieSearch.getString("FILM_LINK");
					    String creationDate = newMovieSearch.getString("FILM_CREATION_DATE");
					    String modificationDate = newMovieSearch.getString("FILM_MODIFICATION_DATE");					   
						
					    Film selectedMovie = new Film(filmId, Title, Description, ReleaseDate, Rating, Trailer, Movie, Categorie, PictureBrowse, creationDate, modificationDate, Duration, PG, Director, Cast, Language);
					    System.out.println("Adding film");
					    MovieSearchList.add(selectedMovie);								
					}
					request.setAttribute("SearchMovieList", MovieSearchList);
					doGet(request, response);						
			}
			else
			{
				System.out.println("in film servlet post");
				doGet(request, response);			
			}
		}
		catch (SQLException e) 
		{
			System.out.println("i am in catch SQL Exception POST FriendsServlet, Got Connection! " + e.getMessage());			
			doGet(request, response);
			
		}
		catch (Exception e) 
		{
			System.out.println("i am in catch ALL Exception POST FriendsServlet, Got Connection!");
			request.setAttribute("loginMessage", "* Error in connection");
			request.getRequestDispatcher("/LoginPage.jsp").forward(request, response);
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
}
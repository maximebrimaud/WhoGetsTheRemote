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
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}					
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		System.out.println("in film servlet post");
		// TODO Auto-generated method stub
		doGet(request, response);	
	}
}
package servlet;

import java.io.IOException;

import javax.servlet.http.*;
import javax.annotation.Resource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.sql.DataSource;

import models.Event;
import models.Film;
import models.User;

@WebServlet("/Events")
public class EventsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	@Resource private DataSource myDataSource;
	
    public EventsServlet() 
    {
        super();
    }  

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{		
		try 
		{
	        //Setting up database connection
	        Connection myConnection = myDataSource.getConnection("APP","APP");			
	        System.out.println("i am in do GET Login, Got Connection!");
			
	        HttpSession session;
			session = request.getSession();
			int id = (Integer)session.getAttribute("sessionId");
	        
			System.out.println("Getting the liste of events");
			//Liste des EVENTS
			PreparedStatement statementEvents;
			statementEvents = myConnection.prepareStatement(
					"(SELECT g.ID_GROUPE, g.ID_ADMIN, g.NOM_GROUPE, g.ADMIN_MESSAGE_GROUPE, g.GROUPE_CREATION_DATE, g.WATCHING_DATE, 'ADMIN' AS post, "
					+ "f.ID_FILM,f.NOM_FILM,f.DESCRIPTION_FILM,f.DATE_RELEASED,f.NOTATION_FILM,f.TRAILER_FILM_LINK,f.FILM_LINK,f.FILM_IMAGE,f.FILM_CREATION_DATE,f.FILM_MODIFICATION_DATE, "
					+ "'Upcoming' as Statut, "
					+ "u.ID_USER, u.NOM_USER, u.PRENOM_USER, u.EMAIL_USER, u.DATE_OF_BIRTH, u.SEXE, u.USERNAME, u.PASSWORD_USER, u.ADDRESS_USER, u.IMAGE_USER, u.USER_CREATION_DATE, u.USER_MODIFICATION_DATE "
					+ "FROM GROUPE g "
					+ "INNER JOIN FILM_CONTIENT_GROUPE fc ON fc.ID_GROUPE_CONTIENT = g.ID_GROUPE "
					+ "INNER JOIN FILM f ON f.ID_FILM = fc.ID_FILM_CONTIENT "
					+ "INNER JOIN USERS u ON u.ID_USER = g.ID_ADMIN "
					+ "WHERE g.WATCHING_DATE > CURRENT_DATE AND g.ID_ADMIN = " + id + ") "
					+ "UNION ALL "
					+ "(SELECT g.ID_GROUPE, g.ID_ADMIN, g.NOM_GROUPE, g.ADMIN_MESSAGE_GROUPE, g.GROUPE_CREATION_DATE, g.WATCHING_DATE, 'USER' AS post, "
					+ "f.ID_FILM,f.NOM_FILM,f.DESCRIPTION_FILM,f.DATE_RELEASED,f.NOTATION_FILM,f.TRAILER_FILM_LINK,f.FILM_LINK,f.FILM_IMAGE,f.FILM_CREATION_DATE,f.FILM_MODIFICATION_DATE, "
					+ "'Upcoming' as Statut, "
					+ "u.ID_USER, u.NOM_USER, u.PRENOM_USER, u.EMAIL_USER, u.DATE_OF_BIRTH, u.SEXE, u.USERNAME, u.PASSWORD_USER, u.ADDRESS_USER, u.IMAGE_USER, u.USER_CREATION_DATE, u.USER_MODIFICATION_DATE "
					+ "FROM USER_APPARTIENT_GROUPE u "
					+ "INNER JOIN GROUPE g ON g.ID_GROUPE = u.ID_GROUPE_APPARTIENT "
					+ "INNER JOIN FILM_CONTIENT_GROUPE fc ON fc.ID_GROUPE_CONTIENT = g.ID_GROUPE "
					+ "INNER JOIN FILM f ON f.ID_FILM = fc.ID_FILM_CONTIENT "
					+ "INNER JOIN USERS u ON u.ID_USER = g.ID_ADMIN "
					+ "WHERE g.WATCHING_DATE > CURRENT_DATE and u.ID_USER_APPARTIENT = " + id + ") "
					+ "UNION ALL "
					+ "(SELECT g.ID_GROUPE, g.ID_ADMIN, g.NOM_GROUPE, g.ADMIN_MESSAGE_GROUPE, g.GROUPE_CREATION_DATE, g.WATCHING_DATE, 'ADMIN' AS post, "
					+ "f.ID_FILM,f.NOM_FILM,f.DESCRIPTION_FILM,f.DATE_RELEASED,f.NOTATION_FILM,f.TRAILER_FILM_LINK,f.FILM_LINK,f.FILM_IMAGE,f.FILM_CREATION_DATE,f.FILM_MODIFICATION_DATE, "
					+ "'Past' as Statut, "
					+ "u.ID_USER, u.NOM_USER, u.PRENOM_USER, u.EMAIL_USER, u.DATE_OF_BIRTH, u.SEXE, u.USERNAME, u.PASSWORD_USER, u.ADDRESS_USER, u.IMAGE_USER, u.USER_CREATION_DATE, u.USER_MODIFICATION_DATE "
					+ "FROM GROUPE g  "
					+ "INNER JOIN FILM_CONTIENT_GROUPE fc ON fc.ID_GROUPE_CONTIENT = g.ID_GROUPE "
					+ "INNER JOIN FILM f ON f.ID_FILM = fc.ID_FILM_CONTIENT "
					+ "INNER JOIN USERS u ON u.ID_USER = g.ID_ADMIN "
					+ "WHERE g.WATCHING_DATE < CURRENT_DATE AND g.ID_ADMIN = " + id + ") "
					+ "UNION ALL "
					+ "(SELECT g.ID_GROUPE, g.ID_ADMIN, g.NOM_GROUPE, g.ADMIN_MESSAGE_GROUPE, g.GROUPE_CREATION_DATE, g.WATCHING_DATE, 'USER' AS post, "
					+ "f.ID_FILM,f.NOM_FILM,f.DESCRIPTION_FILM,f.DATE_RELEASED,f.NOTATION_FILM,f.TRAILER_FILM_LINK,f.FILM_LINK,f.FILM_IMAGE,f.FILM_CREATION_DATE,f.FILM_MODIFICATION_DATE, "
					+ "'Past' as Statut, "
					+ "u.ID_USER, u.NOM_USER, u.PRENOM_USER, u.EMAIL_USER, u.DATE_OF_BIRTH, u.SEXE, u.USERNAME, u.PASSWORD_USER, u.ADDRESS_USER, u.IMAGE_USER, u.USER_CREATION_DATE, u.USER_MODIFICATION_DATE "
					+ "FROM USER_APPARTIENT_GROUPE u "
					+ "INNER JOIN GROUPE g ON g.ID_GROUPE = u.ID_GROUPE_APPARTIENT "
					+ "INNER JOIN FILM_CONTIENT_GROUPE fc ON fc.ID_GROUPE_CONTIENT = g.ID_GROUPE "
					+ "INNER JOIN FILM f ON f.ID_FILM = fc.ID_FILM_CONTIENT "
					+ "INNER JOIN USERS u ON u.ID_USER = g.ID_ADMIN "
					+ "WHERE g.WATCHING_DATE < CURRENT_DATE and u.ID_USER_APPARTIENT = " + id + ") " );
			
			ResultSet eventsResult = statementEvents.executeQuery();
			List<Event> listUpcomingEvents = new ArrayList<Event>();
			List<Event> listPastEvents = new ArrayList<Event>();
			
			while (eventsResult.next()) 
			{
				System.out.println("Start looping through all events");
				String typeEvent = eventsResult.getString("Statut");
				
				int idEvent = eventsResult.getInt("ID_GROUPE");
				String nomEvent = eventsResult.getString("NOM_GROUPE");
				String adminMessage = eventsResult.getString("ADMIN_MESSAGE_GROUPE");
				int idAdmin = eventsResult.getInt("ID_ADMIN");
				String watchingDate = eventsResult.getString("WATCHING_DATE");
				String creationEventDate = eventsResult.getString("GROUPE_CREATION_DATE");
				
				int idFilm = eventsResult.getInt("ID_FILM");
				String nomFilm = eventsResult.getString("NOM_FILM");
				String descriptionFilm = eventsResult.getString("DESCRIPTION_FILM");
				String dateReleased = eventsResult.getString("DATE_RELEASED");
				int notationFilm = Integer.parseInt(eventsResult.getString("NOTATION_FILM"));
				String trailer = eventsResult.getString("TRAILER_FILM_LINK");
				String filmLink =  eventsResult.getString("FILM_LINK");
				String image =  eventsResult.getString("FILM_IMAGE");
				String creationDate =  eventsResult.getString("FILM_CREATION_DATE");
				String modificationDate =  eventsResult.getString("FILM_MODIFICATION_DATE");
				Film currentFilmEvent = new Film(idFilm,nomFilm,descriptionFilm,dateReleased,notationFilm,trailer,filmLink,image,creationDate,modificationDate) ;
				
				int idAdminn = eventsResult.getInt("ID_USER");
				String nomAdmin = eventsResult.getString("NOM_USER");
				String prenomAdmin = eventsResult.getString("PRENOM_USER");
				String sexeAdmin = eventsResult.getString("SEXE");
				String usernameAdmin = eventsResult.getString("USERNAME");
				String passwordAdmin = eventsResult.getString("PASSWORD_USER");
				String emailAdmin =  eventsResult.getString("EMAIL_USER");
				String BdayAdmin =  eventsResult.getString("DATE_OF_BIRTH");
				String creationDateAdmin =  eventsResult.getString("USER_CREATION_DATE");
				String modificationDateAdmin =  eventsResult.getString("USER_MODIFICATION_DATE");
				String addressAdmin =  eventsResult.getString("ADDRESS_USER");
				String imageAdmin =  eventsResult.getString("IMAGE_USER");
				User currentAdmin = new User(idAdminn,prenomAdmin,nomAdmin,usernameAdmin,passwordAdmin,emailAdmin,BdayAdmin, sexeAdmin, addressAdmin, imageAdmin, modificationDateAdmin, creationDateAdmin);
			
				Event currentEvent = new Event(idEvent, nomEvent,watchingDate, adminMessage,idAdmin,creationEventDate,currentFilmEvent,currentAdmin);
				
				System.out.println("Events : " + typeEvent);
				if (typeEvent.trim().equals("Upcoming")){
					listUpcomingEvents.add(currentEvent);
				}else if (typeEvent.trim().equals("Past")){
					listPastEvents.add(currentEvent);
				}
			} 
			
			session.setAttribute("userUpcomingEvents", listUpcomingEvents);
			session.setAttribute("userPastEvents", listPastEvents);		
			
			request.getRequestDispatcher("/EventsPage.jsp").forward(request, response);
			System.out.println("redirected to events page"); 
        
			myConnection.close();
			System.out.println("i am out of do GET Login");
		} 		
		catch (SQLException e) 
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
		}			 
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{	
		System.out.println("i am in do POST Login");
		doGet(request,response);
		System.out.println("i am out of do POST Login");	
	}
}

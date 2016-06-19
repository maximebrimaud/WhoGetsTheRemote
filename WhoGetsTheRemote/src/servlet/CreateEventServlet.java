package servlet;

import java.io.IOException;

import javax.servlet.http.*;
import javax.annotation.Resource;
import javax.servlet.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.sql.DataSource;

import models.Film;
import models.User;

@WebServlet("/CreateEvent")
public class CreateEventServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	@Resource private DataSource myDataSource;
	
    public CreateEventServlet() 
    {
        super();
    }  

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{		
		try 
		{
	        //Setting up database connection
	        Connection myConnection = myDataSource.getConnection("APP","APP");			
	        System.out.println("i am in do GET Event servlet, Got Connection!");
			
	        HttpSession session;
			session = request.getSession();
			int id = (Integer)session.getAttribute("sessionId");
			
			System.out.println("id user : " + id);
			
			String idChosenMovie = request.getParameter("idMovie");
			System.out.println("idChosenMovie : " + idChosenMovie);
		
			PreparedStatement statementMovie;
			statementMovie = myConnection.prepareStatement(
					"(SELECT f.ID_FILM,f.NOM_FILM,f.DESCRIPTION_FILM,f.DATE_RELEASED,f.NOTATION_FILM,f.TRAILER_FILM_LINK,f.FILM_LINK,f.FILM_IMAGE,f.FILM_CREATION_DATE,f.FILM_MODIFICATION_DATE "
					+ "FROM FILM f "
					+ "WHERE f.ID_FILM = " + idChosenMovie + " )");
			
			ResultSet movieResult = statementMovie.executeQuery();
			
			while (movieResult.next()) 
			{
				System.out.println("Collecting movie/invitations information");
				
				//Movie Info
				int idFilm = movieResult.getInt("ID_FILM");
				String nomFilm = movieResult.getString("NOM_FILM");
				String descriptionFilm = movieResult.getString("DESCRIPTION_FILM");
				String dateReleased = movieResult.getString("DATE_RELEASED");
				int notationFilm = Integer.parseInt(movieResult.getString("NOTATION_FILM"));
				String trailer = movieResult.getString("TRAILER_FILM_LINK");
				String filmLink =  movieResult.getString("FILM_LINK");
				String image =  movieResult.getString("FILM_IMAGE");
				String creationDate =  movieResult.getString("FILM_CREATION_DATE");
				String modificationDate =  movieResult.getString("FILM_MODIFICATION_DATE");
				Film chosenMovie = new Film(idFilm,nomFilm,descriptionFilm,dateReleased,notationFilm,trailer,filmLink,image,creationDate,modificationDate) ;
				
				session.setAttribute("chosenMovie", chosenMovie);
			    
				//get the list of friends not invited yet
				PreparedStatement statementFriends;
				statementFriends = myConnection.prepareStatement(
						"SELECT u.ID_USER, u.NOM_USER, u.PRENOM_USER, u.EMAIL_USER, u.DATE_OF_BIRTH, u.SEXE, u.USERNAME, u.PASSWORD_USER, u.ADDRESS_USER, u.IMAGE_USER, u.USER_CREATION_DATE, u.USER_MODIFICATION_DATE "
						+ "FROM FRIENDS f inner join USERS u on f.ID_USER_TWO = u.ID_USER "
						+ "WHERE f.ID_USER_ONE = " + id + " "
						+ "UNION "
						+ "SELECT u1.ID_USER, u1.NOM_USER, u1.PRENOM_USER, u1.EMAIL_USER, u1.DATE_OF_BIRTH, u1.SEXE, u1.USERNAME, u1.PASSWORD_USER, u1.ADDRESS_USER, u1.IMAGE_USER, u1.USER_CREATION_DATE, u1.USER_MODIFICATION_DATE "
						+ "FROM FRIENDS f1 inner join USERS u1 on f1.ID_USER_ONE = u1.ID_USER "
						+ "WHERE f1.ID_USER_TWO = " + id + " ");
				
				ResultSet friendsResult = statementFriends.executeQuery();
				List<User> listOfFriends = new ArrayList<User>();
				
				System.out.println("Start looping through not invited friends");
				while (friendsResult.next()) 
				{
					int idNotInvited = friendsResult.getInt("ID_USER");
					String nomNotInvited = friendsResult.getString("NOM_USER");
					String prenomNotInvited = friendsResult.getString("PRENOM_USER");
					String sexeNotInvited = friendsResult.getString("SEXE");
					String usernameNotInvited = friendsResult.getString("USERNAME");
					String passwordNotInvited = friendsResult.getString("PASSWORD_USER");
					String emailNotInvited =  friendsResult.getString("EMAIL_USER");
					String BdayNotInvited  =  friendsResult.getString("DATE_OF_BIRTH");
					String creationDateNotInvited =  friendsResult.getString("USER_CREATION_DATE");
					String modificationDateNotInvited =  friendsResult.getString("USER_MODIFICATION_DATE");
					String addressNotInvited =  friendsResult.getString("ADDRESS_USER");
					String imageNotInvited =  friendsResult.getString("IMAGE_USER");
					User CurrentFriend = new User (idNotInvited,prenomNotInvited,nomNotInvited,usernameNotInvited,passwordNotInvited,emailNotInvited,BdayNotInvited, sexeNotInvited, addressNotInvited, imageNotInvited, modificationDateNotInvited, creationDateNotInvited);
					listOfFriends.add(CurrentFriend);
				}
				session.setAttribute("listOfFriends", listOfFriends);
				session.setAttribute("idMovieToCreate", idFilm);
				
				request.getRequestDispatcher("/CreateEventPage.jsp").forward(request, response);
				System.out.println("redirected to create event page"); 
			
				myConnection.close();
				System.out.println("i am out of do GET Login");
			} 
		} 		
		catch (SQLException e) 
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
		}	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{	
		try 
		{	
	        System.out.println("i am in do POST Create Event Servlet, Got Connection!");
			//Check if it is coming from movie page, if so go to do get function
			String idMovie = request.getParameter("idMovie");
			if (idMovie == null){
				//Setting up database connection
		        Connection myConnection = myDataSource.getConnection("APP","APP");			
		        System.out.println("i am in do POST Create Event Servlet, Got Connection!");
				
		        HttpSession session;
				session = request.getSession();
				int id = (Integer)session.getAttribute("sessionId");
				
				String adminMessage = request.getParameter("adminMessage");
				System.out.println("message :" + adminMessage);

				String eventName = request.getParameter("eventName");
				System.out.println("message :" + eventName);

				String watchingDate = request.getParameter("watchingDate");
				System.out.println("message :" + watchingDate);
				
				//Create Event
				PreparedStatement statementCreateEvent;
				statementCreateEvent = myConnection.prepareStatement(
						"INSERT INTO GROUPE (NOM_GROUPE, ADMIN_MESSAGE_GROUPE, WATCHING_DATE, ID_ADMIN, GROUPE_CREATION_DATE)"
						+ "VALUES('" + eventName + "', '" + adminMessage + "', '" + watchingDate + "', " + id + ", CURRENT_DATE)");
				statementCreateEvent.executeUpdate();
				
				//Get the event id
				PreparedStatement statementEventID;
				statementEventID = myConnection.prepareStatement(
						"SELECT * FROM GROUPE "
						+ "WHERE NOM_GROUPE = '" + eventName + "' AND ADMIN_MESSAGE_GROUPE = '" + adminMessage + "' AND WATCHING_DATE = '" + watchingDate + "' AND ID_ADMIN = " + id + "");
				ResultSet groupResult = statementEventID.executeQuery();
				int idGroup = 0;
				while (groupResult.next()) {
					idGroup = groupResult.getInt("ID_GROUPE");
				}
				
				//Add film to group
				int idMovieToCreate = (Integer)session.getAttribute("idMovieToCreate");
				PreparedStatement statementFilmEvent;
				statementFilmEvent = myConnection.prepareStatement(
						"INSERT INTO FILM_CONTIENT_GROUPE (ID_FILM_CONTIENT, ID_GROUPE_CONTIENT) "
						+ "VALUES (" + idMovieToCreate + ", " + idGroup + ")");
				statementFilmEvent.executeUpdate();
				
				//Invite Friends
				String[] inviteFriendsId = request.getParameterValues("inviteFriends");
				for(String s : inviteFriendsId)
				{
					System.out.println("adding friends " + s + " to the event"); 
					
					PreparedStatement statementFriendInvite;
					statementFriendInvite = myConnection.prepareStatement(
							"INSERT INTO USER_APPARTIENT_GROUPE "
							+ "(ID_USER_APPARTIENT, ID_GROUPE_APPARTIENT, USER_ADDED_DATE) "
							+ "VALUES(" + s + ", " + idGroup + " , CURRENT_DATE)");
			
					statementFriendInvite.executeUpdate();
					
					System.out.println("out adding friends " + s + " to the event");
				}
				
				System.out.println("redirected to events page");
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Events");
				dispatcher.forward(request, response);
					    
				//request.getRequestDispatcher("/faces/EventsServlet").forward(request, response);
				
				myConnection.close();
				System.out.println("i am out of do POST Create Event Servlet");
			}else {
				System.out.println("redirect to doGet function in CreateEventServlet");
				doGet(request,response);
			}
	        
		} 		
		catch (SQLException e) 
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
		}	
	}
}

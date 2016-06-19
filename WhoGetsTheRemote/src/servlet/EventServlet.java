package servlet;

import java.io.IOException;

import javax.servlet.http.*;
import javax.annotation.Resource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.sql.DataSource;

import models.Event;
import models.Film;
import models.User;

@WebServlet("/Event")
public class EventServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	@Resource private DataSource myDataSource;
	
    public EventServlet() 
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
			
			String idChosenEvent = request.getParameter("idEvent");
			System.out.println("idChosenEvent : " + idChosenEvent);
		    Event currentEvent = new Event();
			if (idChosenEvent == null){
				System.out.println("IN");
				Event currentEventTest = new Event();
				currentEventTest = (Event)session.getAttribute("currentEvent");
				System.out.println("arrabit");
				idChosenEvent = String.valueOf(currentEventTest.getId());
				System.out.println("jouwwaa");
			}else {
				idChosenEvent = request.getParameter("idEvent");
			}

			System.out.println("mayallla n");
		
			PreparedStatement statementEvent;
			statementEvent = myConnection.prepareStatement(
					"(SELECT g.ID_GROUPE, g.ID_ADMIN, g.NOM_GROUPE, g.ADMIN_MESSAGE_GROUPE, g.GROUPE_CREATION_DATE, g.WATCHING_DATE, 'ADMIN' AS post, "
					+ "f.ID_FILM,f.NOM_FILM,f.DESCRIPTION_FILM,f.DATE_RELEASED,f.NOTATION_FILM,f.TRAILER_FILM_LINK,f.FILM_LINK,f.FILM_IMAGE,f.FILM_CREATION_DATE,f.FILM_MODIFICATION_DATE, "
					+ "u.ID_USER, u.NOM_USER, u.PRENOM_USER, u.EMAIL_USER, u.DATE_OF_BIRTH, u.SEXE, u.USERNAME, u.PASSWORD_USER, u.ADDRESS_USER, u.IMAGE_USER, u.USER_CREATION_DATE, u.USER_MODIFICATION_DATE "
					+ "FROM GROUPE g "
					+ "INNER JOIN FILM_CONTIENT_GROUPE fc ON fc.ID_GROUPE_CONTIENT = g.ID_GROUPE "
					+ "INNER JOIN FILM f ON f.ID_FILM = fc.ID_FILM_CONTIENT "
					+ "INNER JOIN USERS u ON u.ID_USER = g.ID_ADMIN "
					+ "WHERE g.ID_GROUPE = " + idChosenEvent + " )");
			
			ResultSet eventResult = statementEvent.executeQuery();
			
			while (eventResult.next()) 
			{
				System.out.println("Collecting event/admin/movie/invitations information");
				
				//Event Info
				int idEvent = eventResult.getInt("ID_GROUPE");
				String nomEvent = eventResult.getString("NOM_GROUPE");
				String adminMessage = eventResult.getString("ADMIN_MESSAGE_GROUPE");
				int idAdmin = eventResult.getInt("ID_ADMIN");
				String watchingDate = eventResult.getString("WATCHING_DATE");
				Date watchDate = eventResult.getDate("WATCHING_DATE");	
		        System.out.println("DATE String : " + watchingDate);		
		        System.out.println("DATE Date : " + watchDate);
				String creationEventDate = eventResult.getString("GROUPE_CREATION_DATE");
				
				//Movie Info
				int idFilm = eventResult.getInt("ID_FILM");
				String nomFilm = eventResult.getString("NOM_FILM");
				String descriptionFilm = eventResult.getString("DESCRIPTION_FILM");
				String dateReleased = eventResult.getString("DATE_RELEASED");
				int notationFilm = Integer.parseInt(eventResult.getString("NOTATION_FILM"));
				String trailer = eventResult.getString("TRAILER_FILM_LINK");
				String filmLink =  eventResult.getString("FILM_LINK");
				String image =  eventResult.getString("FILM_IMAGE");
				String creationDate =  eventResult.getString("FILM_CREATION_DATE");
				String modificationDate =  eventResult.getString("FILM_MODIFICATION_DATE");
				Film currentFilmEvent = new Film(idFilm,nomFilm,descriptionFilm,dateReleased,notationFilm,trailer,filmLink,image,creationDate,modificationDate) ;
				
				//Admin info
				User currentAdmin;
				if (id == eventResult.getInt("ID_USER")) {
					currentAdmin = (User)session.getAttribute("userLogged");
				}else {
					int idAdminn = eventResult.getInt("ID_USER");
					String nomAdmin = eventResult.getString("NOM_USER");
					String prenomAdmin = eventResult.getString("PRENOM_USER");
					String sexeAdmin = eventResult.getString("SEXE");
					String usernameAdmin = eventResult.getString("USERNAME");
					String passwordAdmin = eventResult.getString("PASSWORD_USER");
					String emailAdmin =  eventResult.getString("EMAIL_USER");
					String BdayAdmin =  eventResult.getString("DATE_OF_BIRTH");
					String creationDateAdmin =  eventResult.getString("USER_CREATION_DATE");
					String modificationDateAdmin =  eventResult.getString("USER_MODIFICATION_DATE");
					String addressAdmin =  eventResult.getString("ADDRESS_USER");
					String imageAdmin =  eventResult.getString("IMAGE_USER");
					currentAdmin = new User(idAdminn,prenomAdmin,nomAdmin,usernameAdmin,passwordAdmin,emailAdmin,BdayAdmin, sexeAdmin, addressAdmin, imageAdmin, modificationDateAdmin, creationDateAdmin);
				}
				
				//list of invitation
				PreparedStatement statementInvitation;
				statementInvitation = myConnection.prepareStatement(
						"SELECT u.*, ue.*, 'NoAnswer' AS statut FROM USER_APPARTIENT_GROUPE ue "
						+ "INNER JOIN USERS u ON u.ID_USER = ue.ID_USER_APPARTIENT "
						+ "WHERE ue.ID_GROUPE_APPARTIENT = " + idEvent + " ");
				ResultSet invitationsResult = statementInvitation.executeQuery();
				List<User> listNotComingUsers = new ArrayList<User>();
				List<User> listComingUsers = new ArrayList<User>();
				List<User> listNotAnsweringUsers = new ArrayList<User>();
				
				System.out.println("Start looping through all invitations");
				while (invitationsResult.next()) 
				{
					int idInvited = invitationsResult.getInt("ID_USER");
					String nomInvited = invitationsResult.getString("NOM_USER");
					String prenomInvited = invitationsResult.getString("PRENOM_USER");
					String sexeInvited = invitationsResult.getString("SEXE");
					String usernameInvited = invitationsResult.getString("USERNAME");
					String passwordInvited = invitationsResult.getString("PASSWORD_USER");
					String emailInvited =  invitationsResult.getString("EMAIL_USER");
					String BdayInvited  =  invitationsResult.getString("DATE_OF_BIRTH");
					String creationDateInvited =  invitationsResult.getString("USER_CREATION_DATE");
					String modificationDateInvited =  invitationsResult.getString("USER_MODIFICATION_DATE");
					String addressInvited =  invitationsResult.getString("ADDRESS_USER");
					String imageInvited =  invitationsResult.getString("IMAGE_USER");
					String userMessage =  invitationsResult.getString("USER_MESSAGE");
					User CurrentInvited = new User (idInvited,prenomInvited,nomInvited,usernameInvited,passwordInvited,emailInvited,BdayInvited, sexeInvited, addressInvited, imageInvited, modificationDateInvited, creationDateInvited, userMessage);
					
					String choice = invitationsResult.getString("USER_WATCHING");
					if (invitationsResult.wasNull()) {
						choice = "";
					}
					
					if (choice.equals("") || choice.equals(null)){
						listNotAnsweringUsers.add(CurrentInvited);
					}else if (choice.equals("NotComing")){
						listNotComingUsers.add(CurrentInvited);
					}else if (choice.equals("Coming")){
						listComingUsers.add(CurrentInvited);
					}		
				}
				currentEvent = new Event(idEvent, nomEvent,watchingDate, adminMessage,idAdmin,creationEventDate,currentFilmEvent,currentAdmin,listNotAnsweringUsers,listComingUsers,listNotComingUsers);
			    session.setAttribute("currentEvent", currentEvent);
			    
			    System.out.println("avant"); 
			    java.util.Date dateNow = (java.util.Date) Calendar.getInstance().getTime();
			    System.out.println("apres"); 
			    int res = watchDate.compareTo(dateNow);
			    //res = 0 if watchDate is equal to this dateNow; res < 0 if watchDate is before dateNow; res > 0 of watchDate is after dateNow
				if (res > 0 && id == idAdmin){
					//get the list of friends not invited yet
					PreparedStatement statementFriends;
					statementFriends = myConnection.prepareStatement(
							"SELECT u.ID_USER, u.NOM_USER, u.PRENOM_USER, u.EMAIL_USER, u.DATE_OF_BIRTH, u.SEXE, u.USERNAME, u.PASSWORD_USER, u.ADDRESS_USER, u.IMAGE_USER, u.USER_CREATION_DATE, u.USER_MODIFICATION_DATE "
							+ "FROM FRIENDS f inner join USERS u on f.ID_USER_TWO = u.ID_USER "
							+ "WHERE f.ID_USER_ONE = " + idAdmin + " AND f.ID_USER_TWO NOT IN "
							+ "(SELECT uu.ID_USER FROM USER_APPARTIENT_GROUPE ue "
							+ "INNER JOIN USERS uu ON uu.ID_USER = ue.ID_USER_APPARTIENT "
							+ "WHERE ue.ID_GROUPE_APPARTIENT = " + idEvent + ") "
							+ "UNION "
							+ "SELECT u1.ID_USER, u1.NOM_USER, u1.PRENOM_USER, u1.EMAIL_USER, u1.DATE_OF_BIRTH, u1.SEXE, u1.USERNAME, u1.PASSWORD_USER, u1.ADDRESS_USER, u1.IMAGE_USER, u1.USER_CREATION_DATE, u1.USER_MODIFICATION_DATE "
							+ "FROM FRIENDS f1 inner join USERS u1 on f1.ID_USER_ONE = u1.ID_USER "
							+ "WHERE f1.ID_USER_TWO = " + idAdmin + " AND f1.ID_USER_ONE NOT IN "
							+ "(SELECT uu1.ID_USER FROM USER_APPARTIENT_GROUPE ue1 "
							+ "INNER JOIN USERS uu1 ON uu1.ID_USER = ue1.ID_USER_APPARTIENT "
							+ "WHERE ue1.ID_GROUPE_APPARTIENT = " + idEvent + ")");
					
					ResultSet friendsResult = statementFriends.executeQuery();
					List<User> listNotInvitedFriends = new ArrayList<User>();
					
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
						User CurrentNotInvited = new User (idNotInvited,prenomNotInvited,nomNotInvited,usernameNotInvited,passwordNotInvited,emailNotInvited,BdayNotInvited, sexeNotInvited, addressNotInvited, imageNotInvited, modificationDateNotInvited, creationDateNotInvited);
						listNotInvitedFriends.add(CurrentNotInvited);
					}
					session.setAttribute("currentNotInvitedFriends", listNotInvitedFriends);
					
					request.getRequestDispatcher("/AdminEventPage.jsp").forward(request, response);
					System.out.println("redirected to admin event page"); 
				}else if (res > 0 && id != idAdmin){
					request.getRequestDispatcher("/EventPage.jsp").forward(request, response);
					System.out.println("redirected to event page"); 
				}else if (res < 0){
					request.getRequestDispatcher("/PastEventPage.jsp").forward(request, response);
					System.out.println("redirected to past event page"); 
				}
				myConnection.close();
				System.out.println("i am out of do GET EventServlet");
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
	        //Setting up database connection
	        Connection myConnection = myDataSource.getConnection("APP","APP");			
	        System.out.println("i am in do POST Event Servlet, Got Connection!");
			
	        HttpSession session;
			session = request.getSession();
		
			int id = (Integer)session.getAttribute("sessionId");
			Event currentEvent = (Event)session.getAttribute("currentEvent");
			int idChosenEvent = currentEvent.getId();
			
			String userMessage = request.getParameter("userMessage");
			System.out.println("message :" + userMessage);
			String[] inviteFriendsId = request.getParameterValues("inviteFriends");

			//User choice
			String action = request.getParameter("action");
			
			if (userMessage == null && action.equals("Invite")) {
				for(String s : inviteFriendsId)
				{
					System.out.println("adding friends " + s + " to the event"); 
					
					PreparedStatement statementFriendInvite;
					statementFriendInvite = myConnection.prepareStatement(
							"INSERT INTO USER_APPARTIENT_GROUPE "
							+ "(ID_USER_APPARTIENT, ID_GROUPE_APPARTIENT, USER_ADDED_DATE) "
							+ "VALUES(" + s + ", " + idChosenEvent + " , CURRENT_DATE)");
			
					statementFriendInvite.executeUpdate();
					
					System.out.println("out adding friends " + s + " to the event");
				}
			}else{
				if ("Going".equals(action)) {
				    action = "Coming";
				} else if ("NotGoing".equals(action)) {
				    action = "NotComing";
				}

				System.out.println("UPDATE USER_APPARTIENT_GROUPE "
						+ "SET USER_APPARTIENT_GROUPE.USER_MESSAGE = '" + userMessage + "', "
						+ "USER_APPARTIENT_GROUPE.USER_WATCHING = '" + action + "', "
						+ "USER_APPARTIENT_GROUPE.USER_DATE_MESSAGE = CURRENT_DATE "
						+ "WHERE USER_APPARTIENT_GROUPE.ID_GROUPE_APPARTIENT = " + idChosenEvent + " "
						+ "AND USER_APPARTIENT_GROUPE.ID_USER_APPARTIENT = " + id + " "); 
				
				PreparedStatement statementEvent;
				statementEvent = myConnection.prepareStatement(
						"UPDATE USER_APPARTIENT_GROUPE "
						+ "SET USER_APPARTIENT_GROUPE.USER_MESSAGE = '" + userMessage + "', "
						+ "USER_APPARTIENT_GROUPE.USER_WATCHING = '" + action + "', "
						+ "USER_APPARTIENT_GROUPE.USER_DATE_MESSAGE = CURRENT_DATE "
						+ "WHERE USER_APPARTIENT_GROUPE.ID_GROUPE_APPARTIENT = " + idChosenEvent + " "
						+ "AND USER_APPARTIENT_GROUPE.ID_USER_APPARTIENT = " + id + " "); 
		
				statementEvent.executeUpdate();
			}
			
			System.out.println("redirected to do Post function in EventServlet"); 
			request.setAttribute("idEvent", idChosenEvent);
			doGet(request, response);
			
			myConnection.close();
			System.out.println("i am out of do POST EventServlet");
		} 		
		catch (SQLException e) 
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
		}	
	}
}

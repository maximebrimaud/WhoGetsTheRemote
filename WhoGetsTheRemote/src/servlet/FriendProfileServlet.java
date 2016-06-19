package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.Column;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import models.Event;
import models.Film;
import models.Friend;
import models.User;

/**
 * Servlet implementation class FriendProfileServlet
 */
@WebServlet("/ManageFriends")
public class FriendProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Resource private DataSource myDataSource;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FriendProfileServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		/*// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());*/
		
		
		
		try 
		{
			String friendId = request.getParameter("friendId");
	        //Setting up database connection
	        Connection myConnection = myDataSource.getConnection("APP","APP");			
	        System.out.println("i am in do GET FriendsServlet, Got Connection!");

			HttpSession session;
			session = request.getSession();
	        int id = (Integer)session.getAttribute("sessionId");
	        System.out.println("i am in do GET FriendsProfileServlet, preparing  session");
	        
	     
	        
	      //**************************************** GETTING FRIENDS INFORMATION ******************************************************
	        PreparedStatement statementFriends = myConnection.prepareStatement("SELECT * from USERS where ID_USER =" + friendId);
	        	        	      
			ResultSet newFriendsResult = statementFriends.executeQuery();
			//List<Friend> friendsList = new ArrayList<Friend>();
			System.out.println("i am in do GET FriendsProfileServlet, preparing friends list");
			if(newFriendsResult.next())
			{
				System.out.println("i am in do GET FriendsProfileServlet, executed list friends ");
				Friend friendd = new Friend();
				
				int idFriend = newFriendsResult.getInt("ID_USER");
				String nomFriend = newFriendsResult.getString("NOM_USER");
				String prenomFriend = newFriendsResult.getString("PRENOM_USER");
				String sexeFriend = newFriendsResult.getString("SEXE");
				String usernameFriend = newFriendsResult.getString("USERNAME");
				String passwordFriend = newFriendsResult.getString("PASSWORD_USER");
				String emailFriend =  newFriendsResult.getString("EMAIL_USER");
				String BdayFriend =  newFriendsResult.getString("DATE_OF_BIRTH");
				String creationDateFriend =  newFriendsResult.getString("USER_CREATION_DATE");
				String modificationDateFriend =  newFriendsResult.getString("USER_MODIFICATION_DATE");
				String addressFriend =  newFriendsResult.getString("ADDRESS_USER");
				String imageFriend =  newFriendsResult.getString("IMAGE_USER");
				
				friendd.setId(idFriend);
				friendd.setFullName(prenomFriend + " " + nomFriend);
												
				User currentUserFriend = new User(idFriend,prenomFriend,nomFriend,usernameFriend,passwordFriend,emailFriend,BdayFriend, sexeFriend, addressFriend, imageFriend, modificationDateFriend, creationDateFriend);
				
				friendd.setUserr(currentUserFriend);
				
				
				//**************************************** GETTING FRIENDS IN COMMON ******************************************************
				
				 List<User> listFriendsCommon = new ArrayList<User>();
				
				//We have to retieve the liste of common friends not just the number
					PreparedStatement statementFriendsInCommon = myConnection.prepareStatement("SELECT * "
								+ "FROM (SELECT u.ID_USER, u.NOM_USER, u.PRENOM_USER, u.EMAIL_USER, u.DATE_OF_BIRTH, u.SEXE, u.USERNAME, u.PASSWORD_USER, u.ADDRESS_USER, u.IMAGE_USER, u.USER_CREATION_DATE, u.USER_MODIFICATION_DATE  "
								+ "FROM FRIENDS f inner join USERS u on f.ID_USER_TWO = u.ID_USER WHERE f.ID_USER_ONE = " + id + " "
								+ "UNION "
								+ "SELECT u1.ID_USER, u1.NOM_USER, u1.PRENOM_USER, u1.EMAIL_USER, u1.DATE_OF_BIRTH, u1.SEXE, u1.USERNAME, u1.PASSWORD_USER, u1.ADDRESS_USER, u1.IMAGE_USER, u1.USER_CREATION_DATE, u1.USER_MODIFICATION_DATE   "
								+ "FROM FRIENDS f1 inner join USERS u1 on f1.ID_USER_ONE = u1.ID_USER WHERE f1.ID_USER_TWO = " + id + ") as friends "
								+ "WHERE friends.ID_USER IN (SELECT uu.ID_USER "
								+ "FROM FRIENDS ff inner join USERS uu on ff.ID_USER_TWO = uu.ID_USER WHERE ff.ID_USER_ONE = " + idFriend + " "
								+ "UNION "
								+ "SELECT uu1.ID_USER "
								+ "FROM FRIENDS ff1 inner join USERS uu1 on ff1.ID_USER_ONE = uu1.ID_USER WHERE ff1.ID_USER_TWO = " + idFriend + ") ");
					
					ResultSet newFriendsInCommon = statementFriendsInCommon.executeQuery();
					int nbrFrCommon = 0;					
					while (newFriendsInCommon.next()){
						int idFriendCommon = newFriendsInCommon.getInt("ID_USER");
						String nomFriendCommon = newFriendsInCommon.getString("NOM_USER");
						String prenomFriendCommon = newFriendsInCommon.getString("PRENOM_USER");
						String sexeFriendCommon = newFriendsInCommon.getString("SEXE");
						String usernameFriendCommon = newFriendsInCommon.getString("USERNAME");
						String passwordFriendCommon = newFriendsInCommon.getString("PASSWORD_USER");
						String emailFriendCommon =  newFriendsInCommon.getString("EMAIL_USER");
						String BdayFriendCommon =  newFriendsInCommon.getString("DATE_OF_BIRTH");
						String creationDateFriendCommon =  newFriendsInCommon.getString("USER_CREATION_DATE");
						String modificationDateFriendCommon =  newFriendsInCommon.getString("USER_MODIFICATION_DATE");
						String addressFriendCommon =  newFriendsInCommon.getString("ADDRESS_USER");
						String imageFriendCommon =  newFriendsInCommon.getString("IMAGE_USER");
						
						nbrFrCommon ++;
						
						User FriendInCommon = new User(idFriendCommon,prenomFriendCommon,nomFriendCommon,usernameFriendCommon,passwordFriendCommon,emailFriendCommon,BdayFriendCommon, sexeFriendCommon, addressFriendCommon, imageFriendCommon, modificationDateFriendCommon, creationDateFriendCommon);
						listFriendsCommon.add(FriendInCommon);
					}
				
					friendd.setfriendsCommonNumber(nbrFrCommon);
					friendd.setListFriendsCommon(listFriendsCommon);
				//**************************************** GETTING EVENTS IN COMMON *******************************************************
				List<Event> listEventsCommon = new ArrayList<Event>();
			
				PreparedStatement statementEventsInCommon = myConnection.prepareStatement("select * from (SELECT * FROM GROUPE e "
																	+" inner join USER_APPARTIENT_GROUPE ug on ug.ID_GROUPE_APPARTIENT = e.ID_GROUPE"
																	+" inner join USERS u on ug.ID_USER_APPARTIENT = u.ID_USER"
																	+" inner join FILM_CONTIENT_GROUPE fc on fc.ID_GROUPE_CONTIENT = e.ID_GROUPE"
																	+" inner join FILM f on f.ID_FILM = fc.ID_FILM_CONTIENT"
																	+" WHERE u.ID_USER =  " + friendId +" ) as events"
																	+" WHERE events.ID_GROUPE IN "
																	+" (SELECT e.ID_GROUPE FROM GROUPE e "
																	+" inner join USER_APPARTIENT_GROUPE ug on ug.ID_GROUPE_APPARTIENT = e.ID_GROUPE"
																	+" inner join USERS u on ug.ID_USER_APPARTIENT = u.ID_USER"
																	+" WHERE u.ID_USER = " + id +")");
				
				
				ResultSet newEventsInCommonResult = statementEventsInCommon.executeQuery();
				System.out.println("i am in do GET FRIENDS in common, preparing friends list");
				while(newEventsInCommonResult.next())
				{
					
					int FilmId = newEventsInCommonResult.getInt("ID_FILM");
					String FilmImage = newEventsInCommonResult.getString("FILM_IMAGE");
					String FilmNom = newEventsInCommonResult.getString("NOM_FILM");
					String EventDate = newEventsInCommonResult.getString("WATCHING_DATE");
					String EventName = newEventsInCommonResult.getString("NOM_GROUPE");
					int EventId = newEventsInCommonResult.getInt("ID_GROUPE");
					
					Film newFilm = new Film(FilmId, FilmNom, FilmImage);
					
					Event newEvent = new Event(EventId,EventName,EventDate,newFilm);
										
					listEventsCommon.add(newEvent);					
				}
				
				
				
				friendd.setListEventsCommon(listEventsCommon);
				//**************************************** GETTING MOVIES HE HAS SEEN IF HE IS FRIEND *************************************							
				// >>>>>>>>>>>>>>> checking if he is a friends				
				PreparedStatement statementFriend = myConnection.prepareStatement("(SELECT * FROM FRIENDS f"   /*u.ID_USER, u.NOM_USER, u.PRENOM_USER, u.EMAIL_USER, u.DATE_OF_BIRTH, u.SEXE, u.USERNAME, u.PASSWORD_USER, u.ADDRESS_USER, u.IMAGE_USER, u.USER_CREATION_DATE, u.USER_MODIFICATION_DATE */
															+ "	inner join USERS u on f.ID_USER_TWO = u.ID_USER "
															+ "	WHERE f.ID_USER_ONE = " + id + " and f.ID_USER_TWO = "+ idFriend +")");   /*+" and f.FRIENDS_STATE='Accepted'	*/
															/*+ "	UNION "																	
															+ " (SELECT *  FROM FRIENDS f1"    u1.ID_USER, u1.NOM_USER, u1.PRENOM_USER, u1.EMAIL_USER, u1.DATE_OF_BIRTH, u1.SEXE, u1.USERNAME, u1.PASSWORD_USER, u1.ADDRESS_USER, u1.IMAGE_USER, u1.USER_CREATION_DATE, u1.USER_MODIFICATION_DATE
															+ " inner join USERS u1 on f1.ID_USER_ONE = u1.ID_USER "
															+ " WHERE f1.ID_USER_TWO = " + id + "  and f1.ID_USER_ONE = "+ idFriend +")");     and f1.FRIENDS_STATE='Accepted'*/
						
								
				ResultSet newFriendsResult2 = statementFriend.executeQuery();
				//List<Friend> friendsList = new ArrayList<Friend>();
				System.out.println("i am in do GET check if friends, preparing friends list");
				if(newFriendsResult2.next())
				{
					System.out.println("I asked to be friends with him");
					String FRIENDS_STATE = newFriendsResult2.getString("FRIENDS_STATE");									
					
					request.setAttribute("isFriend", FRIENDS_STATE);
				}
				else
				{
					System.out.println("He asked to be friends with me");
					PreparedStatement statementFriend22 = myConnection.prepareStatement(" (SELECT *  FROM FRIENDS f1"    
							+ " inner join USERS u1 on f1.ID_USER_ONE = u1.ID_USER "
							+ " WHERE f1.ID_USER_TWO = " + id + "  and f1.ID_USER_ONE = "+ idFriend +")");     


					ResultSet newFriendsResult22 = statementFriend22.executeQuery();
					//List<Friend> friendsList = new ArrayList<Friend>();
					System.out.println("i am in do GET checking req state, preparing friends list");
					if(newFriendsResult22.next())
					{
						System.out.println("i am in do GET checking req state, in next");
						String FRIENDS_STATE = newFriendsResult22.getString("FRIENDS_STATE");									
						System.out.println("Friends state is: " + FRIENDS_STATE);
						
						if (FRIENDS_STATE.equals("Pending"))
						{
							System.out.println("I didn't answere yet: " + FRIENDS_STATE);
							request.setAttribute("isFriend", "Confirme");
						}
						else 
						{
							System.out.println("I already answered: " + FRIENDS_STATE);
							request.setAttribute("isFriend", FRIENDS_STATE);
						}
					}
					else
					{
						System.out.println("i am in do GET checking req state, no next");
						request.setAttribute("isFriend", false);
					}																									
				}
				// >>>>>>>>>>>>>>> getting his movie list
				System.out.println("i am in do GET get F movie list, no next");
				List<Film> ListFriendsMovies = new ArrayList<Film>();
				PreparedStatement statementFriendsMovies = myConnection.prepareStatement("select * from (SELECT * FROM GROUPE e "
						+" inner join USER_APPARTIENT_GROUPE ug on ug.ID_GROUPE_APPARTIENT = e.ID_GROUPE"
						+" inner join USERS u on ug.ID_USER_APPARTIENT = u.ID_USER"
						+" inner join FILM_CONTIENT_GROUPE fc on fc.ID_GROUPE_CONTIENT = e.ID_GROUPE"
						+" inner join FILM f on f.ID_FILM = fc.ID_FILM_CONTIENT"
						+" WHERE u.ID_USER =  " + friendId +" ) as events");
				
				ResultSet newFriendsMovies = statementFriendsMovies.executeQuery();
				System.out.println("i am in do GET friends movie list, preparing friends list");
				while(newFriendsMovies.next())
				{					
					System.out.println("i am in do GET friends movie list,in loop");
					int FilmId = newFriendsMovies.getInt("ID_FILM");
					String FilmImage = newFriendsMovies.getString("FILM_IMAGE");
					String FilmNom = newFriendsMovies.getString("NOM_FILM");
					int FilmRating = newFriendsMovies.getInt("NOTATION_FILM");					
					
					Film newFilm = new Film(FilmId, FilmNom, FilmImage, FilmRating);													
					ListFriendsMovies.add(newFilm);			
				}
				System.out.println("i am in do GET get F movie list it is done!!");
				friendd.setListMovies(ListFriendsMovies);
				//**************************************** SETTING friend value in request *************************************													
				request.setAttribute("friend", friendd);
			}									
						  								
			System.out.println("redirecting to friends page");
			request.getRequestDispatcher("/AddFriend.jsp").forward(request, response);
			myConnection.close();
			System.out.println("i am out of do GET FriendsServlet");
		} 		
		catch (SQLException e) 
		{
			System.out.println("i i am in sql error !! " + e.getMessage());
			request.setAttribute("ModifyStateResult", "* Error while requesting change in Status");
			String friendId = request.getParameter("friendId");
			request.setAttribute("friendId", friendId);
			request.getRequestDispatcher("FriendsSrevlet").forward(request, response);
			
		}
		catch (Exception e) 
		{
			System.out.println("i i am in general exception !! " + e.getMessage());
			request.setAttribute("ModifyStateResult", "* Error while requesting change in Status");
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
			
	        System.out.println("i am in do POST FriendsServlet, Got Connection!");
			HttpSession session;
			session = request.getSession();
	        int id = (Integer)session.getAttribute("sessionId");
	        
			String friendId = request.getParameter("friendId");
			Connection myConnection = myDataSource.getConnection("APP","APP");		
			 
			String btnAddFriend = request.getParameter("btnAddFriend");
			String btnRemoveFriend = request.getParameter("btnRemoveFriend");
			String btnRequestPending = request.getParameter("btnRequestPending");
			
			String btnConfirme = request.getParameter("btnConfirme"); 
			String btnIgnore = request.getParameter("btnIgnore");
			 	
			if (btnAddFriend!=null)
			{
				System.out.println("i am in btnAddFriend POST FriendsServlet, Got Connection!");
				List<Film> ListFriendsMovies = new ArrayList<Film>();
				PreparedStatement statementFriendsMovies = myConnection.prepareStatement("select * from FRIENDS where (ID_USER_TWO = " + friendId + " and ID_USER_ONE = " + id + ") OR  (ID_USER_TWO = " + id + " and ID_USER_ONE = " + friendId+ ")");
				
				ResultSet newFriendsMovies = statementFriendsMovies.executeQuery();
				System.out.println("i am in do GET FriendsProfileServlet, preparing friends list");
				if(newFriendsMovies.next())
				{
					String IdFriends = newFriendsMovies.getString("ID_FRIENDS");				
					String StatementStr = "UPDATE FRIENDS SET FRIENDS_STATE = 'Pending' WHERE ID_FRIENDS = " + IdFriends;
					
					System.out.println("Film update statement is : " + StatementStr);
					
					PreparedStatement statement = myConnection.prepareStatement(StatementStr);
					System.out.println("prepared update statement object!");
					int result = statement.executeUpdate();
					System.out.println("update executed");
					if (result == 1)
					{
						request.setAttribute("friendId", friendId);
						doGet(request, response);
					}										
				}
				else
				{
					PreparedStatement myStatement = myConnection.prepareStatement("INSERT INTO FRIENDS (ID_USER_TWO, ID_USER_ONE, FRIENDS_DATE, FRIENDS_STATE) VALUES (" +friendId+ ", "+ id + ", CURRENT_DATE,'Pending')");			      
			        System.out.println("INSERT INTO FRIENDS (ID_USER_TWO, ID_USER_ONE, FRIENDS_DATE, FRIENDS_STATE) VALUES (" +friendId+ ", "+ id + ", currentdate,'Pending')");							        
					int myResultSet = myStatement.executeUpdate();  
					System.out.println("i am in do POST FRIENDS request, Executed Update Statement!");					
					
					if (myResultSet == 1) 
					{							
						request.setAttribute("friendId", friendId);
						doGet(request, response);
					}
				}
			}
			else if (btnRemoveFriend !=null)
			{
				System.out.println("i am in btnRemoveFriend POST FriendsServlet, Got Connection!");
				List<Film> ListFriendsMovies = new ArrayList<Film>();
				PreparedStatement statementFriendsMovies = myConnection.prepareStatement("select * from FRIENDS where (ID_USER_TWO = " + friendId + " and ID_USER_ONE = " + id + ") OR  (ID_USER_TWO = " + id + " and ID_USER_ONE = " + friendId+ ")");
				
				ResultSet newFriendsMovies = statementFriendsMovies.executeQuery();
				System.out.println("i am in do GET FriendsProfileServlet, preparing friends list");
				if(newFriendsMovies.next())
				{
					String IdFriends = newFriendsMovies.getString("ID_FRIENDS");				
					String StatementStr = "DELETE FROM FRIENDS WHERE ID_FRIENDS = " + IdFriends;
					//String StatementStr = "UPDATE FRIENDS SET FRIENDS_STATE = '' WHERE ID_FRIENDS = " + IdFriends;
					
					System.out.println("Film delete statement is : " + StatementStr);
					
					PreparedStatement statement = myConnection.prepareStatement(StatementStr);
					System.out.println("prepared delete statement object!");
					int result = statement.executeUpdate();
					System.out.println("delete executed");
					if (result == 1)
					{
						request.setAttribute("friendId", friendId);
						doGet(request, response);
					}										
				}
			}
			else if (btnRequestPending !=null)
			{
				System.out.println("i am in btnRequestPending POST FriendsServlet, Got Connection!");
				request.setAttribute("friendId", friendId);
				doGet(request, response);
			}
			else if (btnIgnore !=null)
			{
				System.out.println("i am in btnRemoveFriend POST FriendsServlet, Got Connection!");
				List<Film> ListFriendsMovies = new ArrayList<Film>();
				PreparedStatement statementFriendsMovies = myConnection.prepareStatement("select * from FRIENDS where ID_USER_TWO = " + id + " and ID_USER_ONE = " + friendId);
				
				ResultSet newFriendsMovies = statementFriendsMovies.executeQuery();
				System.out.println("i am in do GET FriendsProfileServlet, preparing friends list");
				if(newFriendsMovies.next())
				{
					String IdFriends = newFriendsMovies.getString("ID_FRIENDS");	
					String StatementStr = "DELETE FROM FRIENDS WHERE ID_FRIENDS = " + IdFriends;
					//String StatementStr = "UPDATE FRIENDS SET FRIENDS_STATE = 'Refused' WHERE ID_FRIENDS = " + IdFriends;
					
					System.out.println("Film update statement is : " + StatementStr);
					
					PreparedStatement statement = myConnection.prepareStatement(StatementStr);
					System.out.println("prepared update statement object!");
					int result = statement.executeUpdate();
					System.out.println("update executed");
					if (result == 1)
					{
						request.setAttribute("friendId", friendId);
						doGet(request, response);
					}										
				}
			}
			else if (btnConfirme !=null)
			{
				System.out.println("i am in btnRemoveFriend POST FriendsServlet, Got Connection!");
				List<Film> ListFriendsMovies = new ArrayList<Film>();
				PreparedStatement statementFriendsMovies = myConnection.prepareStatement("select * from FRIENDS where ID_USER_TWO = " + id + " and ID_USER_ONE = " + friendId);
				
				ResultSet newFriendsMovies = statementFriendsMovies.executeQuery();
				System.out.println("i am in do GET FriendsProfileServlet, preparing friends list");
				if(newFriendsMovies.next())
				{
					String IdFriends = newFriendsMovies.getString("ID_FRIENDS");				
					String StatementStr = "UPDATE FRIENDS SET FRIENDS_STATE = 'Accepted' WHERE ID_FRIENDS = " + IdFriends;
					
					System.out.println("Film update statement is : " + StatementStr);
					
					PreparedStatement statement = myConnection.prepareStatement(StatementStr);
					System.out.println("prepared update statement object!");
					int result = statement.executeUpdate();
					System.out.println("update executed");
					if (result == 1)
					{
						request.setAttribute("friendId", friendId);
						doGet(request, response);
					}										
				}
			}
			else 
			{
				System.out.println("i am in ELSE no button POST FriendsServlet, Got Connection!");
				request.setAttribute("friendId", friendId);
				doGet(request, response);
			}			
		}
		catch (SQLException e) 
		{
			System.out.println("i am in catch SQL Exception POST FriendsServlet, Got Connection!");
			request.setAttribute("ModifyStateResult", "* Error while requesting change in Status");
			String friendId = request.getParameter("friendId");
			request.setAttribute("friendId", friendId);
			doGet(request, response);
			
		}
		catch (Exception e) 
		{
			System.out.println("i am in catch ALL Exception POST FriendsServlet, Got Connection!");
			request.setAttribute("ModifyStateResult", "* Error while requesting change in Status");
			request.getRequestDispatcher("/LoginPage.jsp").forward(request, response);
			System.out.println(e.getMessage());
			e.printStackTrace();
		}		
	}
}

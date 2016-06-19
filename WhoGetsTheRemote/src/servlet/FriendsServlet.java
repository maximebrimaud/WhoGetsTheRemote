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

import models.Friend;
import models.User;

@WebServlet(urlPatterns={"/Friends"})
public class FriendsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	@Resource private DataSource myDataSource;
	
    public FriendsServlet() 
    {
        super();
    	System.out.println("i am in constructor");
    }  

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{		
		try 
		{
	        //Setting up database connection
	        Connection myConnection = myDataSource.getConnection("APP","APP");			
	        System.out.println("i am in do GET FriendsServlet, Got Connection!");

			HttpSession session;
			session = request.getSession();
	        int id = (Integer)session.getAttribute("sessionId");
	        System.out.println("i am in do GET FriendsServlet, preparing  session");
	        
	        //Liste des amis
			PreparedStatement statementFriends = 
					myConnection.prepareStatement("(SELECT u.ID_USER, u.NOM_USER, u.PRENOM_USER, u.EMAIL_USER, u.DATE_OF_BIRTH, u.SEXE, u.USERNAME, u.PASSWORD_USER, u.ADDRESS_USER, u.IMAGE_USER, u.USER_CREATION_DATE, u.USER_MODIFICATION_DATE FROM FRIENDS f inner join USERS u on f.ID_USER_TWO = u.ID_USER WHERE f.ID_USER_ONE = " + id + ") "
												+ "UNION "
												+ "(SELECT u1.ID_USER, u1.NOM_USER, u1.PRENOM_USER, u1.EMAIL_USER, u1.DATE_OF_BIRTH, u1.SEXE, u1.USERNAME, u1.PASSWORD_USER, u1.ADDRESS_USER, u1.IMAGE_USER, u1.USER_CREATION_DATE, u1.USER_MODIFICATION_DATE  FROM FRIENDS f1 inner join USERS u1 on f1.ID_USER_ONE = u1.ID_USER WHERE f1.ID_USER_TWO = " + id + ")");
			ResultSet newFriendsResult = statementFriends.executeQuery();
			List<Friend> friendsList = new ArrayList<Friend>();
			System.out.println("i am in do GET FriendsServlet, preparing friends list");
			while(newFriendsResult.next())
			{
				System.out.println("i am in do GET FriendsServlet, executed list friends ");
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
				
				//We have to retieve the liste of common friends not just the number
				PreparedStatement statementFriendsInCommon = 
						myConnection.prepareStatement(
								"SELECT * "
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
				List<User> friendsInCommonList = new ArrayList<User>();
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
					friendsInCommonList.add(FriendInCommon);
				}
				friendd.setfriendsCommonNumber(nbrFrCommon);
				friendd.setListFriendsCommon(friendsInCommonList);
				
				friendsList.add(friendd);	
			}
			session.setAttribute("friendsList", friendsList);		
			System.out.println("redirecting to friends page");
			request.getRequestDispatcher("/FriendsPage.jsp").forward(request, response);

			myConnection.close();
			System.out.println("i am out of do GET FriendsServlet");
		} 		
		catch (SQLException e) 
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
		}	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{			
		System.out.println("i am in do POST");
		response.getWriter().append("Served at: ").append(request.getContextPath());
		System.out.println("i am out of do POST");	
	}
}

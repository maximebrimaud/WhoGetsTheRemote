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

//import models.Counter;
import models.Film;
import models.Friend;
import models.User;

@WebServlet("/Home")
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	@Resource private DataSource myDataSource;
	
    public HomeServlet() 
    {
        super();
    }  

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{		
		System.out.println("i am in do GET HomeServlet");
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		doPost(request,response);
		System.out.println("i am out of do GET HomeServlet");				 
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{							
		try 
		{	//Setting up database connection
	        Connection myConnection = myDataSource.getConnection("APP","APP");			
	        System.out.println("i am in do POST HomeServlet, Got Connection!");
			
			//Liste des nouveaux films
			PreparedStatement statementNewFilms;
			statementNewFilms = myConnection.prepareStatement("SELECT * FROM FILM ORDER BY FILM_CREATION_DATE FETCH FIRST 8 ROWS ONLY");
			ResultSet newMoviesResult = statementNewFilms.executeQuery();

			int i = 0;
			List<Film> listF = new ArrayList<Film>();
			while (newMoviesResult.next()) 
			{
				int idFilm = newMoviesResult.getInt("ID_FILM");
				String nomFilm = newMoviesResult.getString("NOM_FILM");
				String descriptionFilm = newMoviesResult.getString("DESCRIPTION_FILM");
				String dateReleased = newMoviesResult.getString("DATE_RELEASED");
				int notationFilm = Integer.parseInt(newMoviesResult.getString("NOTATION_FILM"));
				String trailer = newMoviesResult.getString("TRAILER_FILM_LINK");
				String filmLink =  newMoviesResult.getString("FILM_LINK");
				String image =  newMoviesResult.getString("FILM_IMAGE");
				String creationDate =  newMoviesResult.getString("FILM_CREATION_DATE");
				String modificationDate =  newMoviesResult.getString("FILM_MODIFICATION_DATE");
				Film currentFilm = new Film(idFilm,nomFilm,descriptionFilm,dateReleased,notationFilm,trailer,filmLink,image,creationDate,modificationDate) ;
				listF.add(currentFilm);
				
				i++;
				System.out.println("Film " + i + " : " + nomFilm);
			}   
			
			HttpSession session;
			session = request.getSession();

			String idNotIn = String.valueOf(session.getAttribute("sessionId"));
			
			String query = "select uu.* from USERS uu "
					+ "WHERE uu.ID_USER NOT IN ( "
					+ "(SELECT u.ID_USER "
					+ "FROM FRIENDS f inner join USERS u on f.ID_USER_TWO = u.ID_USER WHERE f.ID_USER_ONE = " + idNotIn + " ) "
					+ "UNION "
					+ "SELECT u1.ID_USER "
					+ "FROM FRIENDS f1 inner join USERS u1 on f1.ID_USER_ONE = u1.ID_USER WHERE f1.ID_USER_TWO = " + idNotIn + ")"
					+ "AND uu.ID_USER <> " + idNotIn + " AND uu.USERNAME <> 'Admin' "
					+ "FETCH FIRST 8 ROWS ONLY";
			PreparedStatement statementFriends;
			statementFriends = myConnection.prepareStatement(query);
			ResultSet friendsResult = statementFriends.executeQuery();
			
			List<User> youMayKnowList = new ArrayList<User>();
			System.out.println("i am in do GET HomeServlet, preparing friends list");
			while(friendsResult.next())
			{
					
				System.out.println("i am in do GET HomeServlet, executed list pymk ");
				Friend someone = new Friend();
				
				int idFriend = friendsResult.getInt("ID_USER");
				String nomFriend = friendsResult.getString("NOM_USER");
				String prenomFriend = friendsResult.getString("PRENOM_USER");
				String sexeFriend = friendsResult.getString("SEXE");
				String usernameFriend = friendsResult.getString("USERNAME");
				String passwordFriend = friendsResult.getString("PASSWORD_USER");
				String emailFriend =  friendsResult.getString("EMAIL_USER");
				String BdayFriend =  friendsResult.getString("DATE_OF_BIRTH");
				String creationDateFriend =  friendsResult.getString("USER_CREATION_DATE");
				String modificationDateFriend =  friendsResult.getString("USER_MODIFICATION_DATE");
				String addressFriend =  friendsResult.getString("ADDRESS_USER");
				String imageFriend =  friendsResult.getString("IMAGE_USER");
				
				someone.setId(idFriend);
				someone.setFullName(prenomFriend + " " + nomFriend);
				User currentUserSomeone = new User(idFriend,prenomFriend,nomFriend,usernameFriend,passwordFriend,emailFriend,BdayFriend, sexeFriend, addressFriend, imageFriend, modificationDateFriend, creationDateFriend);
				youMayKnowList.add(currentUserSomeone);
			}
			

			//List<Counter> userCounter = new ArrayList<Counter>();
			//userCounter = PeopleYouMayKnow(myConnection,idNotIn);
			
			//for(Counter currentCounter:userCounter){
			//	System.out.println("ID : " + currentCounter.getId() + "  Counter : " + currentCounter.getCounter() + " ");
			//}
			
			//Creer un httpsession pour mettre la liste de fiilms hits

			session.setAttribute("listHits", listF);	
			session.setAttribute("youMayKnow", youMayKnowList);	

						
			System.out.println("redirecting to home");
			request.getRequestDispatcher("/Home.jsp").forward(request, response);
		
		
			myConnection.close();
			System.out.println("i am out of do POST HomeServlet");
		} 		
		catch (SQLException e) 
		{ 
			System.out.println(e.getMessage());
			e.printStackTrace();
		}	
	}
//	public ArrayList<Counter> PeopleYouMayKnow(Connection myConnection,String idNotIn){
//
//		/////Liste des People you may know
//		//Pour la recuperation des People you may know, on va se baser sur un compteur des amis en commun des chaque personne non ami
//		//// XXXXXX ce n'est plus le cas On va se limiter sur des amis de 3eme degree pour compter maximum sinon ca va etre lourd sur le serveur de glassfich
//
//		//On va utiliser la classe/object Counter pour enregistrer les compteur
//		List<Counter> userCounter = new ArrayList<Counter>(); //id = idUser et Counter = points de commun
//		
//		try
//		{
//			//Go through list of friends
//			String query =  "(SELECT u.ID_USER, u.PRENOM_USER FROM FRIENDS f inner join USERS u on f.ID_USER_TWO = u.ID_USER WHERE f.ID_USER_ONE = " + idNotIn + ") "
//					+ "UNION "
//					+ "(SELECT u1.ID_USER, u1.PRENOM_USER FROM FRIENDS f1 inner join USERS u1 on f1.ID_USER_ONE = u1.ID_USER WHERE f1.ID_USER_TWO = " + idNotIn + ")";
//					
//			PreparedStatement statementFriends;
//			statementFriends = myConnection.prepareStatement(query);
//			ResultSet friendsResult = statementFriends.executeQuery();
//			
//			//stocking friends id in an array to check if we are friends to count or not
//			ArrayList<Integer> friendsID = new ArrayList<Integer>();
//			while(friendsResult.next()){
//				int idFriend = friendsResult.getInt("ID_USER");
//				friendsID.add(idFriend);
//			}
//			PreparedStatement statementFriend;
//			statementFriend = myConnection.prepareStatement(query);
//			ResultSet friendResult = statementFriend.executeQuery();
//			
//			System.out.println("going through friends list");
//			while(friendResult.next()){
//				System.out.println("in first while");
//				int idUser = friendResult.getInt("ID_USER");
//				System.out.println("Friend Name : " + friendResult.getString("PRENOM_USER"));
//				idNotIn = idNotIn + ", " + idUser;
//				PreparedStatement statementFriends1;
//				statementFriends1 = myConnection.prepareStatement(
//						"(SELECT u.ID_USER, u.PRENOM_USER FROM FRIENDS f inner join USERS u on f.ID_USER_TWO = u.ID_USER WHERE f.ID_USER_ONE = " + idUser + " AND f.ID_USER_TWO NOT IN (" + idNotIn + ")) "
//						+ "UNION "
//						+ "(SELECT u1.ID_USER, u1.PRENOM_USER FROM FRIENDS f1 inner join USERS u1 on f1.ID_USER_ONE = u1.ID_USER WHERE f1.ID_USER_TWO = " + idUser + " AND f1.ID_USER_ONE NOT IN (" + idNotIn + "))");
//				ResultSet friendsResult1 = statementFriends1.executeQuery();
//				
//				//go through the list of friends of my friends
//				while(friendsResult1.next()){
//					int idUser1 = friendsResult1.getInt("ID_USER");
//					System.out.println("Friend of friend Name : " + friendsResult1.getString("PRENOM_USER"));
//					idNotIn = idNotIn + ", " + idUser1;
//					if (!friendsID.contains(idUser1)){
//						System.out.println("is not a friend");
//						//si il n'est pas amis, je dois incrementer son compteur dans la liste ou l'ajouter
//						if (userCounter.isEmpty()){
//							System.out.println("add first one to counter counter");
//							Counter temp = new Counter(idUser1,1);
//							userCounter.add(temp);
//						}else{
//							for(Counter currentCounter:userCounter){
//								if (currentCounter.getId() == idUser1){
//									System.out.println("already in counter");
//									currentCounter.setCounter(currentCounter.getCounter() + 1);
//								}else{
//									System.out.println("add to counter");
//									Counter temp = new Counter(idUser1,1);
//									userCounter.add(temp);
//								}
//							}	
//						}	
//					}
//				}
//			}
//		} 		
//		catch (SQLException e) 
//		{ 
//			System.out.println(e.getMessage());
//			e.printStackTrace();
//		}
////		idNotIn = idNotIn + ", " + idUser1;
////		//et s'il n'est pas ami je passe par ces amis pour voir le niveau de rlation avec ces amis
////		String newQuery = "(SELECT u.ID_USER, u.PRENOM_USER FROM FRIENDS f inner join USERS u on f.ID_USER_TWO = u.ID_USER WHERE f.ID_USER_ONE = " + idUser1 + " AND f.ID_USER_TWO NOT IN (" + idNotIn + ")) "
////				+ "UNION "
////				+ "(SELECT u1.ID_USER, u1.PRENOM_USER FROM FRIENDS f1 inner join USERS u1 on f1.ID_USER_ONE = u1.ID_USER WHERE f1.ID_USER_TWO = " + idUser1 + " AND f1.ID_USER_ONE NOT IN (" + idNotIn + "))";
////
////		System.out.println("(SELECT u.ID_USER, u.PRENOM_USER FROM FRIENDS f inner join USERS u on f.ID_USER_TWO = u.ID_USER WHERE f.ID_USER_ONE = " + idUser1 + " AND f.ID_USER_TWO NOT IN (" + idNotIn + ")) "
////				+ "UNION "
////				+ "(SELECT u1.ID_USER, u1.PRENOM_USER FROM FRIENDS f1 inner join USERS u1 on f1.ID_USER_ONE = u1.ID_USER WHERE f1.ID_USER_TWO = " + idUser1 + " AND f1.ID_USER_ONE NOT IN (" + idNotIn + "))");
////
////		PeopleYouMayKnow(friendsID, newQuery,myConnection,userCounter,idNotIn);
//		return (ArrayList<Counter>) userCounter;
//	}
}
	

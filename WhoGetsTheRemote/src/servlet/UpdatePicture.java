package servlet;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.sql.DataSource;

import models.User;

/**
 * Servlet implementation class UpdatePicture
 */
@WebServlet("/UpdatePicture")
@MultipartConfig(fileSizeThreshold=1024*1024*2, // 2MB
maxFileSize=1024*1024*10,      // 10MB
maxRequestSize=1024*1024*50)   // 50MB

public class UpdatePicture extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String PROFILE_IMG_DIR = "img/Profiles";
	@Resource private DataSource myDataSource;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdatePicture() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try 
		{							
			System.out.println("i am in update Picture, Got Connection!");						
			
			//***********************************************************************************************
		 /*   System.out.println(">>>>>>>>>>>>>>>>>>>>>>> testing new img path: ");
			// gets absolute path of the web application
		    String appPath = request.getServletContext().getRealPath("");
		    System.out.println(">>>>>>>>>>>>>>>>>>>>>>>  >>>>>>>>>>>> appPath: '" + appPath +"'");
		    // constructs path of the directory to save uploaded file
		    String savePath = appPath + File.separator + PROFILE_IMG_DIR;
		    System.out.println(">>>>>>>>>>>>>>>>>>>>>>>  >>>>>>>>>>>> savePath (appPath/PROFILE_IMG_DIR): '" + savePath +"'");
		    // creates the save directory if it does not exists
		    File fileSaveDir = new File(savePath);
		    System.out.println("created folder path");
		    if (!fileSaveDir.exists()) 
		    {
		        fileSaveDir.mkdir();
		    }
		    System.out.println("checked folder existance!");
		    for (Part part : request.getParts()) 
		    {
		        String fileName = extractFileName(part);
		        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>  >>>>>>>>>>>> fileName (extractFileName(part)): '" + fileName +"'");
		        
		        part.write(savePath + File.separator + fileName);		        		 					   
			    System.out.println(">>>>>>>>>>>>>>>>>>>>>>>  >>>>>>>>>>>>part write in(savePath/fileName): '" + savePath + File.separator + fileName + "'");
		    }*/

		   // request.setAttribute("message", "Upload has been done successfully!");
		   // getServletContext().getRequestDispatcher("/message.jsp").forward(request, response);
		
			//***********************************************************************************************
			
			
			//getting Request data			
	        String NImage = request.getParameter("PictureBrowse"); System.out.println("NEW NImage IS: " + NImage);
	        String UserId = request.getParameter("userId"); System.out.println("NEW NUserId IS: " + UserId);
	        
	        
	        //System.out.println("i am in do POST Login, user: " + user + " and password: " + pass);
	        
	        //Setting up database connection	        
	        Connection myConnection = myDataSource.getConnection("APP","APP");			
	        System.out.println("i am in update User, Got Connection!");
			
	        //Statement myStatement =	myConnection.createStatement();
	        PreparedStatement myStatement = myConnection.prepareStatement("SELECT * from USERS WHERE ID_USER = " + UserId + ""); //Integer.parseInt(UserId)
			System.out.println("i am in update User, Got user statment!");

			 //Authentication
			ResultSet myResultSet = myStatement.executeQuery();
			 System.out.println("i am in update User, Executed get user Statement!");
			//ResultSet myResultSet = myStatement.executeQuery("SELECT * from Users WHERE Username = \"" + user+ "\" AND Password = \"" + pass + "\" ");
			
			if (myResultSet.next()) 
			{					
				 System.out.println("i am in myResultSet after execute!");
		        //int OUserId = myResultSet.getInt("ID_USER");		        		      
				String OSexe = myResultSet.getString("SEXE");				
				String OPass = myResultSet.getString("PASSWORD_USER");
				String OEmail =  myResultSet.getString("EMAIL_USER");
				String ODOB =  myResultSet.getString("DATE_OF_BIRTH");
				String OCreationDate =  myResultSet.getString("USER_CREATION_DATE");
				String OModificationDate =  myResultSet.getString("USER_MODIFICATION_DATE");
				String OAddress =  myResultSet.getString("ADDRESS_USER");
				String OImage =  myResultSet.getString("IMAGE_USER");								
				String ONom = myResultSet.getString("NOM_USER");
				String OPrenom = myResultSet.getString("PRENOM_USER");				
				String OUsername = myResultSet.getString("USERNAME");																				        		       
				System.out.println("i got values after execute!");
				int count=0; 
				String StatementStr = "UPDATE USERS ";
				
				if (!NImage.trim().equals("")&& NImage != null && !NImage.equals(OImage)) 
				{																								
					String path = "img/Profiles/" + OUsername + ".jpg";
					String outputpath = this.getServletContext().getRealPath(path);																	
					
					count++;
					if (count ==1)
					{
						StatementStr =StatementStr + " Set IMAGE_USER = 'img/Profiles/" + OUsername + ".jpg'";
					}
					else
					{
						StatementStr =StatementStr + ", IMAGE_USER =  'img/Profiles/" + OUsername + ".jpg'";
					}					
				}

				if (count>0)
				{			
					StatementStr = StatementStr + " WHERE ID_USER = " + UserId + "";
					
					System.out.println("--> Finale statement = " + StatementStr);
					System.out.println("--> something changed, count = " + count);
					
					
					
					PreparedStatement statement = myConnection.prepareStatement(StatementStr);
					System.out.println("prepared update statement object!");
					int result = statement.executeUpdate();
					System.out.println("update executed");
					if (result == 1)
					{
						User currentUser ;
						if (!NImage.trim().equals("") && NImage != null)
						{
							//----------------------------------------------------
							 PreparedStatement myStatement2 = myConnection.prepareStatement("SELECT * from USERS WHERE ID_USER = " + UserId + ""); //Integer.parseInt(UserId)
								System.out.println("i am in update User, Got user statment!");

								 //Authentication
								ResultSet myResultSet2 = myStatement2.executeQuery();
								 System.out.println("i am in update User, Executed get user Statement!");
								//ResultSet myResultSet = myStatement.executeQuery("SELECT * from Users WHERE Username = \"" + user+ "\" AND Password = \"" + pass + "\" ");
								
								if (myResultSet2.next()) 
								{					
									 System.out.println("i am in myResultSet after execute!");
							        //int OUserId = myResultSet.getInt("ID_USER");		        		      
									String _Sexe = myResultSet2.getString("SEXE");				
									String _Pass = myResultSet2.getString("PASSWORD_USER");
									String _Email =  myResultSet2.getString("EMAIL_USER");
									String _DOB =  myResultSet2.getString("DATE_OF_BIRTH");
									String _CreationDate =  myResultSet2.getString("USER_CREATION_DATE");
									String _ModificationDate =  myResultSet2.getString("USER_MODIFICATION_DATE");
									String _Address =  myResultSet2.getString("ADDRESS_USER");
									String _Image =  myResultSet2.getString("IMAGE_USER");								
									String _Nom = myResultSet2.getString("NOM_USER");
									String _Prenom = myResultSet2.getString("PRENOM_USER");				
									String _Username = myResultSet2.getString("USERNAME");																				        		       
									System.out.println("i got values after execute!");
									
									System.out.println("i created new user! Nimg = " + NImage);
									currentUser = new User(Integer.parseInt(UserId),_Prenom,_Nom,_Username,_Pass,_Email,_DOB, _Sexe, _Address, _Image, _ModificationDate, _CreationDate);
									System.out.println("testing upload...");
								}								
								else
								{
									System.out.println("i created new user! Nimg = " + NImage);
									currentUser = new User(Integer.parseInt(UserId),OPrenom,ONom,OUsername,OPass,OEmail,ODOB, OSexe, OAddress, NImage, OModificationDate, OCreationDate);
									System.out.println("testing upload...");
								}
							//----------------------------------------------------
							
							
							/*System.out.println("i created new user! Nimg = " + NImage);
							currentUser = new User(Integer.parseInt(UserId),OPrenom,ONom,OUsername,NPass,NEmail,NDOB, NSexe, NAddress, NImage, OModificationDate, OCreationDate);
							System.out.println("testing upload...");
							*/
							
							BufferedImage newImage = ImageIO.read(new File(NImage));							
							//ImageIO.write(newImage, "jpg", new File(".\\testingSave.jpg"));
							
							String path = "img/Profiles";///" + OUsername + ".jpg";
							//String path2 = "./img/Profiles/" + OUsername + ".jpg";
							String outputpath = this.getServletContext().getRealPath(path);
							//String outputpath2 = this.getServletContext().getRealPath(path);
							System.out.println("testing upload... path: " + path);
							//System.out.println("testing upload... path2: " + path2);
							System.out.println("testing upload... outputpath: " + outputpath);
							//System.out.println("testing upload... outputpath2: " + outputpath2);
							
							
							ImageIO.write(newImage, "jpg", new File(outputpath + "/"+ OUsername + ".jpg"));
					 		//this.getServletContext().getRealPath(path);
							//InputStream is = p1.getInputStream();
						    //createPhoto(is, outputpath);
							System.out.println("image uploaded to: '" + outputpath + "/"+ OUsername + ".jpg'");
							
							request.setAttribute("ImagePath" , outputpath);
							
							//***********************************************************************************************
							
							/*// gets absolute path of the web application
						    String appPath = request.getServletContext().getRealPath("");
						    // constructs path of the directory to save uploaded file
						    String savePath = appPath + File.separator + PROFILE_IMG_DIR;
						     
						    // creates the save directory if it does not exists
						    File fileSaveDir = new File(savePath);
						    if (!fileSaveDir.exists()) {
						        fileSaveDir.mkdir();
						    }
						    
						    System.out.println(">>>>>>>>>>>>>>>>>>>>>>> testing new img path: ");
						    System.out.println(">>>>>>>>>>>>>>>>>>>>>>>  >>>>>>>>>>>> appPath: '" + appPath +"'");
						    System.out.println(">>>>>>>>>>>>>>>>>>>>>>>  >>>>>>>>>>>> savePath (appPath/PROFILE_IMG_DIR): '" + savePath +"'");
						     
						    for (Part part : request.getParts()) {
						        String fileName = extractFileName(part);
						        part.write(savePath + File.separator + fileName);
						        
						        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>  >>>>>>>>>>>> fileName (extractFileName(part)): '" + savePath +"'");						    
							    System.out.println(">>>>>>>>>>>>>>>>>>>>>>>  >>>>>>>>>>>>part write in(savePath/fileName): '" + savePath + File.separator + fileName + "'");
						    }

						   // request.setAttribute("message", "Upload has been done successfully!");
						   // getServletContext().getRequestDispatcher("/message.jsp").forward(request, response);
						*/
							//***********************************************************************************************
							//----------------------------------------------------------------------------------------------
							
							//String relativeWebPath = "/img/Profiles";
							//String absoluteDiskPath = getServletContext().getRealPath(relativeWebPath);
							//File file = new File(absoluteDiskPath, "imagetosave.jpg");
							
							//System.out.println("image absoluteDiskPath : '" + absoluteDiskPath + "'");				
							//File file1 = new File(absoluteDiskPath, ""+ OUsername + ".jpg");
							
							
							//String path = "img/Profiles/" + OUsername + ".jpg";
							//String outputpath2 = this.getServletContext().getRealPath("./");	
							//System.out.println("image */ : '" + outputpath2 + "'");				
							
							
						/*	System.out.println("testing upload... '" + NImage + "'");	
							File newFile = new File(NImage);
							BufferedImage newImage1 = ImageIO.read(newFile);							
												
							String path1 = "img/Profiles/" + OUsername + ".jpg";
							String outputpath1 = this.getServletContext().getRealPath(path1);					
							
							ImageIO.write(newImage1, "jpg", new File(outputpath1));
					 		System.out.println("image uploaded to: '" + outputpath1 + "'");*/
							
							
							
							//-----------------------------------------------------------------------------------------
						}
						else							
						{
							System.out.println("i created new user! Oimg = " + OImage);
							currentUser = new User(Integer.parseInt(UserId),OPrenom,ONom,OUsername,OPass,OEmail,ODOB, OSexe, OAddress, OImage, OModificationDate, OCreationDate);
						}
						//saving photo
						//BufferedImage originalImage = ImageIO.read(new File("the full path"));
						//ImageIO.write(originalImage, "jpg", new File("c:\\image\\mypic_new.jpg")); 
						
						
						HttpSession session;
						session = request.getSession();
						session.setAttribute("sessionId", currentUser.getId());
						session.setAttribute("userLogged", currentUser);
						System.out.println("setting userLogged for html page! img = " + currentUser.getImage());
						//session.setAttribute("listHits", listF);		
						System.out.println("Success: redirecting to UserProfile");
						request.setAttribute("UpdateMessage" , "Update Successfull");
						request.setAttribute("UserImage" , currentUser.getImage());
						request.setAttribute("UserUsername" , currentUser.getUsername());
						request.setAttribute("UserImage2" , "img/Profiles/mike.jpg");
						request.setAttribute("UserImage3" , "/img/Profiles/mike.jpg");
						//response.sendRedirect(request.getContextPath() + "/UserProfile.jsp");
						request.getRequestDispatcher("/UserProfile.jsp").forward(request, response);						
						System.out.println("redirected to UserProfile");
					}
					else
					{
						
						System.out.println("Update failed!!!");
						request.setAttribute("UpdateMessage" , "* Update Failed!");
					}									
				}	
				else
				{	
					HttpSession session;
					session = request.getSession();
					User currentUser = new User(Integer.parseInt(UserId),OPrenom,ONom,OUsername,OPass,OEmail,ODOB, OSexe, OAddress, NImage, OModificationDate, OCreationDate);
					session.setAttribute("sessionId", currentUser.getId());
					session.setAttribute("userLogged", currentUser);
					//session.setAttribute("listHits", listF);		
					System.out.println("no changes: redirecting to UserProfile");
					request.setAttribute("UpdateMessage" , "");
					request.getRequestDispatcher("/UserProfile.jsp").forward(request, response);
					
					System.out.println("redirected to UserProfile");
					// NOTHING HAS CHANGED, JUST SEND HIM BACK TO USER PROFILE PAGE
				}
				
				
//				/*System.out.println("i am in do POST Login, in the Result set");
//				int id = myResultSet.getInt("ID_USER");
//				String nom = myResultSet.getString("NOM_USER");
//				String prenom = myResultSet.getString("PRENOM_USER");
//				String sexee = myResultSet.getString("SEXE");
//				String usernamee = myResultSet.getString("USERNAME");
//				String passwordd = myResultSet.getString("PASSWORD_USER");
//				String emaill =  myResultSet.getString("EMAIL_USER");
//				String Bday =  myResultSet.getString("DATE_OF_BIRTH");
//				String creationDatee =  myResultSet.getString("USER_CREATION_DATE");
//				String modificationDatee =  myResultSet.getString("USER_MODIFICATION_DATE");
//				String addresss =  myResultSet.getString("ADDRESS_USER");
//				String imagee =  myResultSet.getString("IMAGE_USER");
//				
//				//Liste des amis en commun
//				PreparedStatement statementFriends = 
//						myConnection.prepareStatement("(SELECT u.ID_USER, u.NOM_USER, u.PRENOM_USER, u.EMAIL_USER, u.DATE_OF_BIRTH, u.SEXE, u.USERNAME, u.PASSWORD_USER, u.ADDRESS_USER, u.IMAGE_USER, u.USER_CREATION_DATE, u.USER_MODIFICATION_DATE FROM FRIENDS f inner join USERS u on f.ID_USER_TWO = u.ID_USER WHERE f.ID_USER_ONE = " + id + ") "
//													+ "UNION "
//													+ "(SELECT u1.ID_USER, u1.NOM_USER, u1.PRENOM_USER, u1.EMAIL_USER, u1.DATE_OF_BIRTH, u1.SEXE, u1.USERNAME, u1.PASSWORD_USER, u1.ADDRESS_USER, u1.IMAGE_USER, u1.USER_CREATION_DATE, u1.USER_MODIFICATION_DATE  FROM FRIENDS f1 inner join USERS u1 on f1.ID_USER_ONE = u1.ID_USER WHERE f1.ID_USER_TWO = " + id + ")");
//				ResultSet newFriendsResult = statementFriends.executeQuery();
//				List<Friend> friendsInCommon = new ArrayList<Friend>();
//				System.out.println("i am in do POST Login, preparing friends in commun");
//				while(newFriendsResult.next())
//				{
//					System.out.println("i am in do POST Login, executed friends in commun");
//					Friend friendd = new Friend();
//					//Getting all the rows
//					int idFriend = newFriendsResult.getInt("ID_USER");
//					String nomFriend = newFriendsResult.getString("NOM_USER");
//					String prenomFriend = newFriendsResult.getString("PRENOM_USER");
//					String sexeFriend = newFriendsResult.getString("SEXE");
//					String usernameFriend = newFriendsResult.getString("USERNAME");
//					String passwordFriend = newFriendsResult.getString("PASSWORD_USER");
//					String emailFriend =  newFriendsResult.getString("EMAIL_USER");
//					String BdayFriend =  newFriendsResult.getString("DATE_OF_BIRTH");
//					String creationDateFriend =  newFriendsResult.getString("USER_CREATION_DATE");
//					String modificationDateFriend =  newFriendsResult.getString("USER_MODIFICATION_DATE");
//					String addressFriend =  newFriendsResult.getString("ADDRESS_USER");
//					String imageFriend =  newFriendsResult.getString("IMAGE_USER");
//					
//					friendd.setId(idFriend);
//					friendd.setFullName(prenomFriend + " " + nomFriend);
//					
//					//We have to retieve the liste of common friends not just the number
//					friendd.setfriendsCommonNumber(10);
//					//friendd.setListFriendsCommon();
//					
//					User currentUserFriend = new User(idFriend,prenomFriend,nomFriend,usernameFriend,passwordFriend,emailFriend,BdayFriend, sexeFriend, addressFriend, imageFriend, modificationDateFriend, creationDateFriend);
//					friendd.setUserr(currentUserFriend);
//					
//					friendsInCommon.add(friendd);	
//				}
//
//				User currentUser = new User(id,prenom,nom,usernamee,passwordd,emaill,Bday, sexee, addresss, imagee, modificationDatee, creationDatee,friendsInCommon);
//				                            
//				
//				//Liste des nouveaux films
//				PreparedStatement statementNewFilms = myConnection.prepareStatement("SELECT * FROM FILM ORDER BY FILM_CREATION_DATE FETCH FIRST 8 ROWS ONLY");
//				ResultSet newMoviesResult = statementNewFilms.executeQuery();
//				List<Film> listF = new ArrayList<Film>();
//				while (newMoviesResult.next()) 
//				{
//					int idFilm = newMoviesResult.getInt("ID_FILM");
//					String nomFilm = newMoviesResult.getString("NOM_FILM");
//					String descriptionFilm = newMoviesResult.getString("DESCRIPTION_FILM");
//					String dateReleased = newMoviesResult.getString("DATE_RELEASED");
//					int notationFilm = Integer.parseInt(newMoviesResult.getString("NOTATION_FILM"));
//					String trailer = newMoviesResult.getString("TRAILER_FILM_LINK");
//					String filmLink =  newMoviesResult.getString("FILM_LINK");
//					String image =  newMoviesResult.getString("IMAGE_FILM");
//					String creationDate =  newMoviesResult.getString("FILM_CREATION_DATE");
//					String modificationDate =  newMoviesResult.getString("FILM_MODIFICATION_DATE");
//					Film currentFilm = new Film(idFilm,nomFilm,descriptionFilm,dateReleased,notationFilm,trailer,filmLink,image,creationDate,modificationDate) ;
//
//					listF.add(currentFilm);
//					
//					int i = 0;
//					i++;
//					System.out.println("Film " + i + " : " + nomFilm);
//				}   
//				
//				//Liste des People you may know
//				
//				//Creer un httpsession pour mettre l'objet user
//				HttpSession session;
//				session = request.getSession();
//				session.setAttribute("sessionId", currentUser.getId());
//				session.setAttribute("userLogged", currentUser);
//				session.setAttribute("listHits", listF);		
//				System.out.println("redirecting to home");
//				request.getRequestDispatcher("/Home.jsp").forward(request, response);
//				
//				System.out.println("redirected to home");
//			} 
//            else 
//            {
//            	request.setAttribute("loginMessage", "Invalid Username and Password combination!");
//                request.getRequestDispatcher("/LoginPage.jsp").forward(request, response);
//            	System.out.println("Incorrect login credentials");
//            }
//			
//			myConnection.close();
//			System.out.println("i am out of do POST Login");*/
		} 
		}
		catch (SQLException e) 
		{
			HttpSession session;
			
			System.out.println("catch: redirecting to UserProfile");
			request.setAttribute("UpdateMessage" , "* Update failed!");
			request.getRequestDispatcher("/UserProfile.jsp").forward(request, response);
			
			System.out.println("redirected to UserProfile");
			
			
//			request.setAttribute("loginMessage", "Error in connection!");
//            request.getRequestDispatcher("/LoginPage.jsp").forward(request, response);
        	System.out.println("Error in connection!");
			System.out.println(e.getMessage());
			e.printStackTrace();
		} 
	}
	
	
	


/**
 * Extracts file name from HTTP header content-disposition
 */
private String extractFileName(Part part) {
    String contentDisp = part.getHeader("content-disposition");
    String[] items = contentDisp.split(";");
    for (String s : items) {
        if (s.trim().startsWith("filename")) {
            return s.substring(s.indexOf("=") + 2, s.length()-1);
        }
    }
    return "";
}
}

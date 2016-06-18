package servlet;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
//import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.sql.Statement;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Calendar;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
//import javax.ejb.EJB;
//import javax.inject.Inject;
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

//import models.Film;
//import models.Friend;
import models.User;

@WebServlet("/UpdateUser") // UpdateUser
public class UpdateUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String PROFILE_IMG_DIR = "img/Profiles";
	
	@Resource private DataSource myDataSource;
	
    public UpdateUser() { 
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		System.out.println("i am in update User, Got Connection!");
		System.out.println("i am in do GET UpdateUser");
		response.getWriter().append("Served at: ").append(request.getContextPath());
		System.out.println("i am out of do GET Login");			
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		try 
		{
			 System.out.println("i am in update User, Got Connection!");
			//getting Request data
			String NEmail = request.getParameter("UserEmail"); System.out.println("NEW EMAIL IS: " + NEmail);
	        String NDOB = request.getParameter("UserDOB"); System.out.println("NEW NDOB IS: " + NDOB);
			String NSexe = request.getParameter("Sexe"); System.out.println("NEW NSexe IS: " + NSexe);
	        String NAddress = request.getParameter("UserAddress"); System.out.println("NEW NAddress IS: " + NAddress);
			String NPass = request.getParameter("UserPass"); System.out.println("NEW NPass IS: " + NPass);
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
				
				if (!NImage.trim().equals("")&& NImage != null && !NImage.equals(OImage)) // NImage != OImage)
				{																								
					//String path = "img/Profiles/" + OUsername + ".jpg";
					//String outputpath = this.getServletContext().getRealPath(path);																	
					
					count++;
					if (count ==1)
					{
						//StatementStr =StatementStr + " Set IMAGE_USER = '" + NImage + "'";
						StatementStr =StatementStr + " Set IMAGE_USER = 'img/Profiles/" + OUsername + ".jpg'";
					}
					else
					{
						//StatementStr =StatementStr + ", IMAGE_USER = '" + NImage + "'";
						StatementStr =StatementStr + ", IMAGE_USER =  'img/Profiles/" + OUsername + ".jpg'";
					}					
				}

				//System.out.println("will it enter in pass  = old pass");	
				if (!NPass.trim().equals("") && !NPass.equals(OPass)) //!= OPass)
				{
					//System.out.println("yes it entered");
					count++;
					if (count ==1)
					{
						StatementStr =StatementStr + " Set PASSWORD_USER = '" + NPass + "'";
					}
					else
					{
						StatementStr =StatementStr + ", PASSWORD_USER = '" + NPass + "'";
					}
					
				}
			//	System.out.println("did it?");
				if (!NAddress.trim().equals("") && !NAddress.equals(OAddress)) //!= OAddress)
				{
					count++;
					if (count ==1)
					{
						StatementStr =StatementStr + " Set ADDRESS_USER = '" + NAddress + "'";
					}
					else
					{
						StatementStr =StatementStr + ", ADDRESS_USER = '" + NAddress + "'";
					}
					
				}
				
				if (!NSexe.trim().equals("") && !NSexe.equals(OSexe))// OSexe)
				{
					count++;
					if (count ==1)
					{
						StatementStr =StatementStr + " Set SEXE = '" + NSexe + "'";
					}
					else
					{
						StatementStr =StatementStr + ", SEXE = '" + NSexe + "'";
					}
					
				}
				System.out.println("new email: '" + NEmail + "' , old email: '" + OEmail+"'");
				if (!NEmail.trim().equals("") && !NEmail.equals(OEmail))// != OEmail)
				{System.out.println("did itgo in?");
					count++;
					if (count ==1)
					{
						StatementStr =StatementStr + " Set EMAIL_USER = '" + NEmail + "'";
					}
					else
					{
						StatementStr =StatementStr + ", EMAIL_USER = '" + NEmail + "'";
					}
					
				}
				
				if (!NDOB.trim().equals("") && !NDOB.equals(ODOB))// != ODOB)
				{
					count++;
					if (count ==1)
					{
						StatementStr =StatementStr + " Set DATE_OF_BIRTH = '" + NDOB + "'";
					}
					else
					{
						StatementStr =StatementStr + ", DATE_OF_BIRTH = '" + NDOB + "'";
					}
					
				}
			
				
				
			
				if (count>0)
				{			
					/*java.util.Date dateNow = (java.util.Date) Calendar.getInstance().getTime();
					//string strDateNow = dateNow.getYear() + "-" + dateNow.getMonth() + "-" + dateNow.getDay();
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					java.util.Date date = format.parse(dateNow.toString());
					System.out.println("--> modification date is: "+date.toString());*/
					//SimpleDateFormat df = new SimpleDateFormat("yyyy");
					//year = df.format(date);
					
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
									currentUser = new User(Integer.parseInt(UserId),OPrenom,ONom,OUsername,NPass,NEmail,NDOB, NSexe, NAddress, NImage, OModificationDate, OCreationDate);
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
							currentUser = new User(Integer.parseInt(UserId),OPrenom,ONom,OUsername,NPass,NEmail,NDOB, NSexe, NAddress, OImage, OModificationDate, OCreationDate);
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
					User currentUser = new User(Integer.parseInt(UserId),OPrenom,ONom,OUsername,NPass,NEmail,NDOB, NSexe, NAddress, NImage, OModificationDate, OCreationDate);
					session.setAttribute("sessionId", currentUser.getId());
					session.setAttribute("userLogged", currentUser);
					//session.setAttribute("listHits", listF);		
					System.out.println("no changes: redirecting to UserProfile");
					request.setAttribute("UpdateMessage" , "");
					request.getRequestDispatcher("/UserProfile.jsp").forward(request, response);
					
					System.out.println("redirected to UserProfile");
					// NOTHING HAS CHANGED, JUST SEND HIM BACK TO USER PROFILE PAGE
				}
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
}

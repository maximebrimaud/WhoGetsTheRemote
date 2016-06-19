package servlet;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import models.User;

@WebServlet("/ProfileUpdated")
public class UpdateUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String PROFILE_IMG_DIR = "img/Profiles";
	
	@Resource private DataSource myDataSource;
	
    public UpdateUser() { 
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		System.out.println("i am in update User, Got Connection!");
		System.out.println("i am in do GET UpdateUser");
		response.getWriter().append("Served at: ").append(request.getContextPath());
		System.out.println("i am out of do GET Login");			
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		try 
		{
			 System.out.println("i am in update User");
			//getting Request data
			String NEmail = request.getParameter("UserEmail"); System.out.println("NEW EMAIL IS: " + NEmail);
	        String NDOB = request.getParameter("UserDOB"); System.out.println("NEW NDOB IS: " + NDOB);
			String NSexe = request.getParameter("Sexe"); System.out.println("NEW NSexe IS: " + NSexe);
	        String NAddress = request.getParameter("UserAddress"); System.out.println("NEW NAddress IS: " + NAddress);
			String NPass = request.getParameter("UserPass"); System.out.println("NEW NPass IS: " + NPass);
	        String NImage = request.getParameter("PictureBrowse"); System.out.println("NEW NImage IS: " + NImage);
	        String UserId = request.getParameter("userId"); System.out.println("NEW NUserId IS: " + UserId);
	        
	        //Setting up database connection	        
	        Connection myConnection = myDataSource.getConnection("APP","APP");			
	        System.out.println("i am in update User, Got Connection!");
			
	        //Statement myStatement =	myConnection.createStatement();
	        PreparedStatement myStatement = myConnection.prepareStatement("SELECT * from USERS WHERE ID_USER = " + UserId + ""); //Integer.parseInt(UserId)
			System.out.println("i am in update User, Got user statment!");

			 //Authentication
			ResultSet myResultSet = myStatement.executeQuery();
			 System.out.println("i am in update User, Executed get user Statement!");
			
			if (myResultSet.next()) 
			{					
				System.out.println("i am in myResultSet after execute!");		        		      
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

				if (!NPass.trim().equals("") && !NPass.equals(OPass))
				{
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
				if (!NAddress.trim().equals("") && !NAddress.equals(OAddress)) 
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
				if (!NEmail.trim().equals("") && !NEmail.equals(OEmail))
				{
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
				
				if (!NDOB.trim().equals("") && !NDOB.equals(ODOB))
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
					DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
			        Date date = new Date();
			        String s = dateFormat.format(date);
					StatementStr = StatementStr + " , USER_MODIFICATION_DATE = '" + s + "' WHERE ID_USER = " + UserId + "";
					
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
							 PreparedStatement myStatement2 = myConnection.prepareStatement("SELECT * from USERS WHERE ID_USER = " + UserId + ""); //Integer.parseInt(UserId)
								System.out.println("i am in update User, Got user statment!");

								 //Authentication
								ResultSet myResultSet2 = myStatement2.executeQuery();
								System.out.println("i am in update User, Executed get user Statement!");
								
								if (myResultSet2.next()) 
								{					
									System.out.println("i am in myResultSet after execute!");	        		      
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
									currentUser = new User(Integer.parseInt(UserId),OPrenom,ONom,OUsername,NPass,NEmail,NDOB, NSexe, NAddress, NImage, s, OCreationDate);
									System.out.println("testing upload...");
								}
							
							BufferedImage newImage = ImageIO.read(new File(NImage));							
							
							String path = "img/Profiles";
							String outputpath = this.getServletContext().getRealPath(path);
							System.out.println("testing upload... path: " + path);
							System.out.println("testing upload... outputpath: " + outputpath);
							
							ImageIO.write(newImage, "jpg", new File(outputpath + "/"+ OUsername + ".jpg"));
							System.out.println("image uploaded to: '" + outputpath + "/"+ OUsername + ".jpg'");
							
							request.setAttribute("ImagePath" , outputpath);
						}
						else							
						{
							System.out.println("i created new user! Oimg = " + OImage);
							currentUser = new User(Integer.parseInt(UserId),OPrenom,ONom,OUsername,NPass,NEmail,NDOB, NSexe, NAddress, OImage, s, OCreationDate);
						}
												
						HttpSession session;
						session = request.getSession();
						session.setAttribute("sessionId", currentUser.getId());
						session.setAttribute("userLogged", currentUser);
						System.out.println("setting userLogged for html page! img = " + currentUser.getImage());	
						System.out.println("Success: redirecting to UserProfile");
						request.setAttribute("UpdateMessage" , "Update Successfull");
						request.setAttribute("UserImage" , currentUser.getImage());
						request.setAttribute("UserUsername" , currentUser.getUsername());
						request.setAttribute("UserImage2" , "img/Profiles/mike.jpg");
						request.setAttribute("UserImage3" , "/img/Profiles/mike.jpg");
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
					System.out.println("no changes: redirecting to UserProfile");
					request.setAttribute("UpdateMessage" , "");
					request.getRequestDispatcher("/UserProfile.jsp").forward(request, response);
					
					System.out.println("redirected to UserProfile");
				}
			} 
		}
		catch (SQLException e) 
		{
			System.out.println("catch: redirecting to UserProfile");
			request.setAttribute("UpdateMessage" , "* Update failed!");
			request.getRequestDispatcher("/UserProfile.jsp").forward(request, response);
			
			System.out.println("redirected to UserProfile");
        	System.out.println("Error in connection!");
			System.out.println(e.getMessage());
			e.printStackTrace();
		} 
	}
}

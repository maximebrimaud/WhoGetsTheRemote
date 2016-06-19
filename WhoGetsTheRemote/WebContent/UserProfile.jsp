<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.io.*,java.util.*,javax.servlet.*" %>
<%
	if (session.getAttribute("sessionId") == null || session.getAttribute("sessionId").equals("")){
		System.out.println("not logged in!");
		response.sendRedirect("LoginPage.jsp");
	}
%>

<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="icon" href="logo.png">

    <title>${userLogged.getFullName()} Profile</title>

    <!-- Bootstrap core CSS -->
    <link href="Bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
  </head>

  <body>
    <nav class="navbar navbar-inverse navbar-fixed-top">
      <div class="container-fluid">
        <div class="navbar-header">
          <a class="navbar-brand" href="${pageContext.request.contextPath}/Home">Who Gets The Remote</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
          <ul class="nav navbar-nav navbar-left">
            <li><a href="${pageContext.request.contextPath}/Movies">Movies</a></li>
            <li><a href="${pageContext.request.contextPath}/Friends">Friends</a></li>
            <li><a href="${pageContext.request.contextPath}/Events">Events</a></li>
            <li id="btnAdmin" style="display: none; "><a href="${pageContext.request.contextPath}/Admin">Administrator</a></li>
          </ul>
		   <ul class="nav navbar-nav navbar-right">
            <li><a href="${pageContext.request.contextPath}/Profile">${userLogged.getFullName()}</a></li>
            <li><a href="LogoutPage.jsp">LogOut</a></li>
          </ul>
        </div>
      </div>
    </nav>

    <div class="container-fluid">
      <div class="row">
        <div class="col-sm-10 col-sm-offset-3 col-md-10 col-md-offset-1 main">
	        <br/>
	        <h1 class="page-header">${userLogged.getFullName()} - ${userLogged.getUsername()}</h1>                                      	        
        	<div class="container-fluid">
      			<div class="row">
        			<div class="col-sm-10 col-sm-offset-3 col-md-10 col-md-offset-1 main">		       
	        			<div class="row placeholders">
					  		<div class='col-xs-6 col-sm-3 col-md-3 placeholder'>
						        <div align='center'>						        	
						        	<img src='${userLogged.getImage()}' width='190' height='190' class='img-responsive' alt='Unable to load image!'>							       
								</div>
						        <h4 align='center'>${userLogged.getFullName()}</h4>   
					        </div>
					        <form role="form" action="ProfileUpdated" onsubmit="return ValidateInputs()" method="post">  
					      		<input name="userId" id="userId" style="display: none;" value="${userLogged.getId()}"/>
						        <div class='col-xs-6 col-sm-9 col-md-9 placeholder' style="padding-top: 45px;">					 	       
									<div class="row">
										<div class="col-xs-12 col-sm-3 col-md-3" style="text-decoration: underline; text-align: right;  font-size: large;  font-weight: bold;  ">
											Email :
										</div> 
										<div id="ViewModeEmail" class="col-xs-6 " style="text-align: left;  font-size: large; ">
											${userLogged.getEmail()}
										</div>
										<div id="EditModeEmail" class="col-xs-6" style="text-align: left; display: none;  font-size: large; ">
											<input id="UserEmail" type="text" style="color: black;" name="UserEmail" value="${userLogged.getEmail()}"  />		
											<label id="EmailValidator" style="color: red; font-weight:normal;"></label>
										</div>
									</div>
									<div class="row">
										<div class="col-xs-12 col-sm-5 col-md-3" style="text-decoration: underline;  font-weight: bold; font-size: large; text-align: right;">
											Date of Birth :
										</div>
										<div id="ViewModeDOB" class="col-xs-6 " style="text-align: left; font-size: large; ">
											${userLogged.getDob()}
										</div>
										<div id="EditModeDOB" class="col-xs-6" style="text-align: left; display: none; font-size: large; ">
											<input id="UserDOB" type="text" name="UserDOB" style="color: black;" class="myDateFormat" value="${userLogged.getDob()}" />	
											<label id="DOBValidator" style="color: red; font-weight:normal;"></label>
										</div>
									</div>
									<div class="row">
										<div class="col-xs-12 col-sm-3 col-md-3" style="text-decoration: underline; font-weight: bold; text-align: right; font-size: large; ">
											Sexe : 
										</div>								 
										<div id="ViewModeSexe" class="col-xs-6 " style="text-align: left; font-size: large; ">
											${userLogged.getSexe()}
										</div>
										<div id="EditModeSexe" class="col-xs-6 " style="text-align: left; display: none; font-size: large; ">
											<select name="Sexe" id="Sexe" value="F"  class="form-control">
												<option value="M">Male</option>
												<option value="F">Female</option>							    
											</select>	
											<label id="SexeValidator" style="color: red; font-weight:normal;"></label>
										</div>
									</div>
									<div class="row">
										<div class="col-xs-12 col-sm-3 col-md-3" style="text-decoration: underline;  font-size: large; font-weight: bold;  text-align: right;">
											Address :
										</div>								 
										<div  id="ViewModeAddress"  class="col-xs-6 " style="text-align: left; font-size: large; ">
											${userLogged.getAddress()}										    	
										</div>
										<div  id="EditModeAddress"  class="col-xs-6 " style="text-align: left; display: none; font-size: large; ">
											<input id="UserAddress" type="text" style="color: black;" name="UserAddress" value="${userLogged.getAddress()}"/>
											<label id="AddressValidator" style="color: red; font-weight:normal;"></label>
										</div>
									</div>
									<div class="row">
										<div id="EditModePasslbl" class="col-xs-12 col-sm-3 col-md-3" style="text-decoration: underline; display: none;   font-size: large; font-weight: bold;  text-align: right;">
											Password :
										</div>								 										 
										<div  id="EditModePass"  class="col-xs-6 " style="text-align: left; display: none; font-size: large; ">
											<input id="UserPass" type="password" style="color: black;" name="UserPass" value="${userLogged.getPassword()}"/>
											<label id="PassValidator" style="color: red; font-weight:normal;"></label>
										</div> 
									</div>
									
									<div id="divChangePic" class="row" style="display: none;">
										<div class="col-xs-12 col-sm-3 col-md-3" style="text-align: left; font-size: large; text-align: right; " >
											<label  style="text-decoration: underline; font-weight: bold;  font-size: large;  ">Change Picture :</label>  
										</div>
										<div class="col-xs-12 col-sm-8 "  style=" font-size: large; " >
											${userLogged.getImage()}
											<input name="PictureBrowse" id="PictureBrowse" type="file" name="PictureBrowse" />
										</div>								 									  
									</div>
									
									<div class="row">									 
										<div class="col-xs-12 col-sm-3 col-md-4" style=" font-size: large; text-align: right; " >
											<input id="btnEditUser" type="button" onclick="ChangeToEditMode()" name="btnEditUser" value="Edit Profile" />
											<input id="btnCancelEdit" type="button" onclick="ChangeToViewMode()" name="btnCancelEdit" value="Cancel" style="display: none;"/>										  														  											  	
										</div>								 									  
										<div class="col-xs-12 col-sm-3 col-md-4" style=" font-size: large; text-align: left; "  >
											<input id="btnSavePicture" type="submit" name="btnSavePicture" value="Save Changes" style="display: none;" />
										</div>
									</div>	
									<div class="row">									 
										<div class="col-xs-12 col-sm-3 col-md-4" style=" font-size: large; text-align: right; color: red; " >
											${UpdateMessage}							  														  											  	
										</div>
									</div>				        
						        </div>
				       		</form>		  
			          	</div>
		          	</div>
		      	</div>
	    	</div>          	                   
        </div>
      </div>
    </div>

    <script>
	    console.log("doing on load script");			
		var btnAdmin = document.getElementById("btnAdmin");                                			
		console.log("got btnAdmin");			
		
		var UserType = '<%= session.getAttribute("UserType") %>';			
		console.log("user type = " + UserType);			
		if (UserType == "Admin"){			
		        btnAdmin.style.display = "block";			
		}			
		else{			
		        btnAdmin.style.display = "none";			
		}	
	
		var temp = '${userLogged.getSexe()}';
		var mySelect = document.getElementById('Sexe');
		for(var i, j = 0; i = mySelect.options[j]; j++) {
		    if(i.value == temp) {
		        mySelect.selectedIndex = j;
		        break;
		    }
		}	
	</script>
    
    <script type="text/javascript">
    	function ValidateInputs()
    	{
    		var valide = true;
    		var email =  document.getElementById("UserEmail").value.trim();
    		var dob = document.getElementById("UserDOB").value.trim();
    		var ddlSexe = document.getElementById('Sexe');
			var sexe = ddlSexe.options[ddlSexe.selectedIndex].value;    		
    		var addresse = document.getElementById("UserAddress").value.trim();
    		var pass = document.getElementById("UserPass").value.trim();
    	
    		if (email =="" || email == null)
    		{
    			valide = false;
    			EmailValidator.innerHTML = "* Mandatory Field";
    		}
    		else    		
    		{
    			EmailValidator.innerHTML = "";
    		}
    		if (pass =="" || pass == null)
    		{
    			valide = false;
    			PassValidator.innerHTML = "* Mandatory Field";
    		}
    		else
    		{
    			PassValidator.innerHTML = "";
    		}
    		if (dob =="" || dob == null)
    		{
    			valide = false;
    			DOBValidator.innerHTML = "* Mandatory Field";
    		}
    		else
    		{
    			DOBValidator.innerHTML = "";
    		}    		
    		if (sexe =="" || sexe == null)
    		{
    			valide = false;
    			SexeValidator.innerHTML = "* Mandatory Field";
    		}
    		else
    		{
    			SexeValidator.innerHTML = "";
    		}
    		if (addresse =="" || addresse == null)
    		{
    			valide = false;
    			AddressValidator.innerHTML = "* Mandatory Field";
    		}
    		else
    		{
    			AddressValidator.innerHTML = "";
    		}
    		
    		
    		var resultDOB = validateDOB();
    		if (resultDOB == false)
    		{
    			valide = false;
    		}
    		
    		return valide;
    	}
    
    	function validateDOB()
    	{
	   		console.log("starting validating ....." );
	   		var UserDOB = document.getElementById("UserDOB");     		
		    var value = UserDOB.value;
		    var DOBValidator = document.getElementById("DOBValidator");     				   
		    			  
		  	DOBValidator.innerHTML = "";			  

	     	var regEx2 = /^\d{4}-\d{2}-\d{2}$/;
		    DOBValidator.innerHTML = "";
		    if (value == null || value == "" )
		    {
		      	console.log("DOB null" );
			    DOBValidator.innerHTML = "* Mandatory Field";
			    return false; 
		    }
			if(!regEx2.test(value))
			{
			    console.log("DOB format not valide" );
			    DOBValidator.innerHTML = "* Invalid date of birth format (yyyy-mm-dd)";
			    return false;  // Invalid format
		    }
		      
			var d;
			if(!((d = new Date(value))|0))
		    {
				DOBValidator.innerHTML = "* Invalid date of birth (yyyy-mm-dd)";
			    console.log("DOB not valide" );
			    return false; 
			}
			 
			console.log("Comparing '"+ d.toISOString().slice(0,10) + "' with: '" + value + "'" );	
			console.log("comparaison result : " + (d.toISOString().slice(0,10) == value))
			 
			if(d.toISOString().slice(0,10) == value)
			{			  
				console.log("DOB valid");	
				return true;
			}
			else
			{
			  	DOBValidator.innerHTML = "* Invalid date of birth format (yyyy-mm-dd)";
			    console.log("DOB not valide (yyyy-mm-dd)" );
				return false; 
			}		
		}
    
    	function ChangeToEditMode()
    	{
    		var ViewEmail = document.getElementById("ViewModeEmail");    		    	
    		ViewEmail.style.display = "none";
    		var ViewDOB = document.getElementById("ViewModeDOB");    		    	
    		ViewDOB.style.display = "none";
    		var ViewSexe = document.getElementById("ViewModeSexe");    		    	
    		ViewSexe.style.display = "none";
    		var ViewAddress = document.getElementById("ViewModeAddress");    		    	
    		ViewAddress.style.display = "none";    		    	    		    	
    		    		    	
    		
    		var EditEmail = document.getElementById("EditModeEmail");    		    	
    		EditEmail.style.display = "block";
   			var UserEmail = document.getElementById("UserEmail");    	
    		UserEmail.value= '${userLogged.getEmail()}';
    		
    		var EditDOB = document.getElementById("EditModeDOB");    		    	
    		EditDOB.style.display = "block";
    		var UserDOB = document.getElementById("UserDOB");    	
    		UserDOB.value= '${userLogged.getDob()}';
    		
    		var EditSexe = document.getElementById("EditModeSexe");    		    	
    		EditSexe.style.display = "block";
    		var temp = '${userLogged.getSexe()}';
			var mySelect = document.getElementById('Sexe');			
			for(var i, j = 0; i = mySelect.options[j]; j++) {
			    if(i.value == temp) {
			        mySelect.selectedIndex = j;
			        break;
			    }
			}
    		
    		var EditAddress = document.getElementById("EditModeAddress");    		    	
    		EditAddress.style.display = "block";
    		var UserAddress = document.getElementById("UserAddress");    	
    		UserAddress.value= '${userLogged.getAddress()}'; 
    		
    		var EditPass = document.getElementById("EditModePass");    		    	
    		EditPass.style.display = "block";
    		var UserPass = document.getElementById("UserPass");    	
    		UserPass.value= '${userLogged.getPassword()}'; 
    		
    		var EditModePasslbl = document.getElementById("EditModePasslbl");    		    	
    		EditModePasslbl.style.display = "block";
    		
    		var divChangePic = document.getElementById("divChangePic");    		    	
    		divChangePic.style.display = "block";
    		var btnCancelEdit = document.getElementById("btnCancelEdit");    		    	
    		btnCancelEdit.style.display = "block";
    		var btnSavePicture = document.getElementById("btnSavePicture");    		    	
    		btnSavePicture.style.display = "block";
    		var btnEditUser = document.getElementById("btnEditUser");    		    	
    		btnEditUser.style.display = "none";    		    		    		      		   
    	}    	    
    	
    	function ChangeToViewMode()
    	{
    		var ViewEmail = document.getElementById("ViewModeEmail");    		    	
    		ViewEmail.style.display = "block";    		
   			var ViewDOB = document.getElementById("ViewModeDOB");    		    	
    		ViewDOB.style.display = "block";
   			var ViewSexe = document.getElementById("ViewModeSexe");    		    	
    		ViewSexe.style.display = "block";
    		var ViewAddress = document.getElementById("ViewModeAddress");    		    	
    		ViewAddress.style.display = "block";
    		
    		var EditEmail = document.getElementById("EditModeEmail");    		    	
    		EditEmail.style.display = "none";
    		var UserEmail = document.getElementById("UserEmail");    	
    		UserEmail.value= '${userLogged.getEmail()}';
    		
    		var EditPass = document.getElementById("EditModePass");    		    	
    		EditPass.style.display = "none";
    		var UserPass = document.getElementById("UserPass");    	
    		UserPass.value= '${userLogged.getPassword()}'; 
    		
    		var EditModePasslbl = document.getElementById("EditModePasslbl");    		    	
    		EditModePasslbl.style.display = "none";
    		
		    var EditDOB = document.getElementById("EditModeDOB");    		    	
    		EditDOB.style.display = "none";
    		var UserDOB = document.getElementById("UserDOB");    	
    		UserDOB.value= '${userLogged.getDob()}';
    		
    		var EditSexe = document.getElementById("EditModeSexe");    		    	
    		EditSexe.style.display = "none";
    		var temp = '${userLogged.getSexe()}';
			var mySelect = document.getElementById('Sexe');			
			for(var i, j = 0; i = mySelect.options[j]; j++) {
			    if(i.value == temp) {
			        mySelect.selectedIndex = j;
			        break;
			    }
			}
			
    		var EditAddress = document.getElementById("EditModeAddress");    		    	
    		EditAddress.style.display = "none";
    		var UserAddress = document.getElementById("UserAddress");    	
    		UserAddress.value= '${userLogged.getAddress()}';
    		
    		var divChangePic = document.getElementById("divChangePic");    		    	
    		divChangePic.style.display = "none";
    		var btnCancelEdit = document.getElementById("btnCancelEdit");    		    	
    		btnCancelEdit.style.display = "none";
    		var btnSavePicture = document.getElementById("btnSavePicture");    		    	
    		btnSavePicture.style.display = "none";
    		var btnEditUser = document.getElementById("btnEditUser");    		    	
    		btnEditUser.style.display = "block";   
    	}
    </script>
  </body>
</html>
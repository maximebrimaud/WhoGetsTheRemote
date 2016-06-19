<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.io.*,java.util.*,javax.servlet.*,models.Event;" %>
<%
	if (session.getAttribute("sessionId") == null || session.getAttribute("sessionId").equals("")){
		System.out.println("not logged in!");
		response.sendRedirect("LoginPage.jsp");
	}
%>

<html lang="en">
  <head>
    <meta>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <link rel="icon" href="Bootstrap/favicon.ico">

    <title>WGTR - Create Event Page</title>

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
          <br/><br/>          
			<form action="CreateEvent" onsubmit="return checkInputs()" method="post">
				<h2 class="page-header">${chosenMovie.getName()}</h2>
				<div class="row placeholders">
					<div class="col-sm-3 col-md-3">
						<div align="center">
							<img src="${chosenMovie.getImageFilm()}" width='190' height='190' class='img-responsive' alt='Generic placeholder thumbnail'>
						</div>
					</div>
					<div class="col-sm-9 col-md-9" style="font-weight: bold">
						<div align='left'>
							Choose a name for the event : <input type="text" class="form-control" name="eventName" id="eventName">
							<label id="eventNameValidator" style="color: red; font-weight:normal;"></label>
						</div><div align='left'>You will be the host of this event</div>
						<div align='left'>You will be watching <a href="MoviePage.jsp?idMovie=${chosenMovie.getId()}">${chosenMovie.getName()}</a></div>
						<div align='left'>
							What will be your guests message : <input type="text" class="form-control" name="adminMessage" id="adminMessage">
							<label id="userMessageValidator" style="color: red; font-weight:normal;"></label>
						</div>
						<div align='left'>
							Choose the date of your event : <input type="text" class="form-control" name="watchingDate" id="watchingDate">
							<label id="watchingDateValidator" style="color: red; font-weight:normal;"></label>
						</div>
					</div>
				</div>  
				    
				<h3 class='page-header'>Invite your friends </h3>
				<div class="row placeholders">
					<c:forEach items="${listOfFriends}" var="item">
						<div class='col-xs-6 col-sm-3 placeholder'>
							<div align='center'>
								<img class="img-circle" src="${item.getImage()}" width="130" height="130" class="img-responsive" alt="Generic placeholder thumbnail">
							</div>
							<h6 align='center'>${item.getFullName()}</h6>
							<div align='center'>
								<label><input type='checkbox' name='inviteFriends' value='${item.getId()}' onclick='handleClick(this);'> Invite</label>
							</div>
						</div>
					</c:forEach>
				</div>
				<br/><br/>
				<div class="row placeholders">
					<div align='center'>
						<label id="nbrSelected" style="color: black; font-weight:normal;"></label>
						<label id="selected" style="color: black; font-weight:normal;"> selected</label>
					</div>
					<div align='center'>
						<input id="idCreateEvent" type="submit" name="action" value="Create Event" />
						<label id="generalValidator" style="color: red; font-weight:normal;"></label>
					</div>
				</div>
			</form>
			
			
          <h3 class="page-header">Movie's information</h3>
          <h4 align="Left">${chosenMovie.getName()}</h4>
          <div class="row placeholders">
	          <div class="col-sm-9 col-md-9" style="font-weight: bold">
	          		<div align='left'>Director      : ${chosenMovie.getName()}</div>
	          		<div align='left'>Producer      : ${chosenMovie.getName()}</div>
	          		<div align='left'>Description   : ${chosenMovie.getDescription()}</div>
	          		<div align='left'>Released Date : ${chosenMovie.getDateReleased()}</div>
	          		<div align='left'>Film Link     : ${chosenMovie.getFilmLink()}</div>
	          		<div align='left'>Film Trailer  : ${chosenMovie.getTrailerFilm()}</div>
	          		<div align='left'>Film Rating   : ${chosenMovie.getNotationFilm()}</div>
	          </div>
          </div>
        </div>
      </div>
    </div>
    
    <script type="text/javascript">
	    function checkInputs()
	    {
	    	console.log("checking message inputs");

	    	var generalValidator = document.getElementById("generalValidator");
	    	generalValidator.innerHTML = "";
	    	
		    var userMessageValidator = document.getElementById('userMessageValidator');
		    userMessageValidator.innerHTML = "";
	      	
	    	var adminMessage = document.getElementById('adminMessage').value.trim();
		    
		    var valid = true;
		    
		    if (adminMessage.trim()=="")
	    	{
		    	userMessageValidator.innerHTML  ="* You have to insert a message for your friends";
		      	generalValidator.innerHTML = "* You have an error (go up)";
	    		valid = false;
	    		console.log("adminMessage valid = false");
	    	}
		    
		    var eventNameValidator = document.getElementById('eventNameValidator');
		    eventNameValidator.innerHTML = "";
	      	
	    	var eventName = document.getElementById('eventName').value.trim();
		    
		    if (eventName.trim()=="")
	    	{
		    	eventNameValidator.innerHTML  ="* You have to insert an event name";
		      	generalValidator.innerHTML = "* You have an error (go up)";
	    		valid = false;
	    		console.log("eventName valid = false");
	    	}
		    
		    var resultWatchDate = validateDate();
    		if (resultWatchDate == false)
    		{
    	      	generalValidator.innerHTML = "* You have an error (go up)";
    			valid = false;
    		}
    		
    		return valid;
	    }
	    
	    function validateDate()
		{
	   		console.log("starting date validating ....." );
	    	var WatchingDate = document.getElementById("watchingDate");  		
		    var value = WatchingDate.value.trim();  				   
			var watchingDateValidator = document.getElementById("watchingDateValidator");
	      	watchingDateValidator.innerHTML = "";
	  
	
	     	var regEx2 = /^\d{4}-\d{2}-\d{2}$/;
	     	watchingDateValidator.innerHTML = "";
		    if (value == null || value == "" )
		    {
		      	console.log("date null" );
		      	watchingDateValidator.innerHTML = "* You have to set a watching date";
			    return false; 
		    }
			if(!regEx2.test(value))
			{
			    console.log("date format not valid" );
			    watchingDateValidator.innerHTML = "* Invalid date format (yyyy-mm-dd)";
			    return false;  // Invalid format
		    }
		      
			var d;
			if(!((d = new Date(value))|0))
		    {
				watchingDateValidator.innerHTML = "* Invalid date format(yyyy-mm-dd)";
			    console.log("date not valid" );
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
				watchingDateValidator.innerHTML = "* Invalid date format (yyyy-mm-dd)";
			    console.log("watching date not valid (yyyy-mm-dd)" );
				return false; 
			}		
		}
    </script>
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
    	
    	console.log("IN ON LOAD" );
        document.getElementById("nbrSelected").style.visibility = "hidden";
        document.getElementById("selected").style.visibility = "hidden";
        document.getElementById("idCreateEvent").style.visibility = "hidden";
    	var n = 0;
    	var firstTime = 1;
    	console.log("n = " + n );
    	console.log("OUT OF ON LOAD" );
    	
    	function handleClick(cb) {
	    	console.log("IN CLICK FUNCTION");
	    	
	    	if (firstTime == 1){
		    	console.log("firstTime = 1");
	    		n = 0;
	    		firstTime = 2;
	    	}else{
		    	console.log("firstTime != 1");
		    	n = document.getElementById("nbrSelected").textContent;
		    	console.log("n from the function = " + n);
	    	}
	    	
	    	if (cb.checked == true){
	    		console.log("checked : " + n);
	    		n++;
	    		console.log("checked : " + n);
	    	}else if (cb.checked == false){
	    		console.log("not checked : " + n);
	    		n--;	
	    		console.log("not checked : " + n);
	    	}
	    	
	    	if (n == 0){
	            document.getElementById("nbrSelected").style.visibility = "hidden";
	            document.getElementById("selected").style.visibility = "hidden";
	            document.getElementById("idCreateEvent").style.visibility = "hidden";
	    	}else{
	    		document.getElementById("nbrSelected").style.visibility = "visible";
	            document.getElementById("selected").style.visibility = "visible";
	            document.getElementById("idCreateEvent").style.visibility = "visible";
	    	}
	    	
	    	document.getElementById("nbrSelected").innerHTML = '' + n + '';
	    }
    </script>
  </body>
</html>
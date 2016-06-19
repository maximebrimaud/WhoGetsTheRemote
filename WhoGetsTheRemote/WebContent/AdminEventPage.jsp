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

    <title>WGTR - Event Page</title>

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
          <h2 class="page-header">${currentEvent.getNameGroupe()}</h2>
          <div class="row placeholders">
          	<div class="col-sm-3 col-md-3">
          		<div align="center">
					<img src="${currentEvent.film.imageFilm}" width='190' height='190' class='img-responsive' alt='Generic placeholder thumbnail'>
				</div>
          	</div>
          	<div class="col-sm-9 col-md-9" style="font-weight: bold">
          		<div align='left'>You are the host of this event</div>
          		<div align='left'>You will be watching <a href="MoviePage.jsp?idMovie=${currentEvent.film.getName()}">${currentEvent.film.getName()}</a></div>
          		<form action="AdminEventServlet" onsubmit="return checkInputs()" method="post">
		          	<div align='left'>
		          		Your message is : <input type="text" class="form-control" name="adminMessage" id="adminMessage" value="${currentEvent.getAdminMessage()}" >
	          			<label id="userMessageValidator" style="color: red; font-weight:normal;"></label>
	          		</div>
	          		<div align='left'>
	          			Date : <input type="text" class="form-control" name="watchingDate" id="watchingDate" value="${currentEvent.getWatchingDate()}">
	          			<label id="watchingDateValidator" style="color: red; font-weight:normal;"></label>
	          		</div>
	          		<input id="idModifyMessage" type="submit" name="action" value="Modify and Save" />
          		</form>
          		<br/>
          		<br/>
          		<div align='left'>Event created in : ${currentEvent.getCreationDate()}</div>
          	</div>
          </div>  
          
          <h3 class="page-header">Invite other friends (${currentNotInvitedFriends.size()} not invited till now) </h3>
			<form action='Event' method='post'> 
			    <div class="row placeholders">
			        <c:forEach items="${currentNotInvitedFriends}" var="item">
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
				    	<input id="idInviteFriends" type="submit" name="action" value="Invite" />
				    </div>
			    </div>
			</form>
			
			
          <h3 class="page-header">Invitations (${currentEvent.getNbrOfPeopleInvited()} were invited to this movie)</h3>
          <h4 align="center">COMING (${currentEvent.getNbrOfPeopleComing()})</h4>
          <div class="row placeholders">
            <c:forEach items="${currentEvent.userComing}" var="item">
		  		<div class='col-xs-6 col-sm-3 placeholder'>
			        <div align='center'>
			        	<img class="img-circle" src="${item.getImage()}" width="130" height="130" class="img-responsive" alt="Generic placeholder thumbnail">
              		</div>
			        <h6 align='center'>${item.getFullName()}</h6>
			        <div align='center'>
			        	<span class='text-muted'>${item.getUserMessage()}</span>
			        </div>
		        </div>
		  	</c:forEach>
          </div>
          
          <h4 align="center">NOT COMING (${currentEvent.getNbrOfPeopleNotComing()})</h4>
          <div class="row placeholders">
            <c:forEach items="${currentEvent.userNotComing}" var="item">
		  		<div class='col-xs-6 col-sm-3 placeholder'>
			        <div align='center'>
			        	<img class="img-circle" src="${item.getImage()}" width="130" height="130" class="img-responsive" alt="Generic placeholder thumbnail">
              		</div>
			        <h6 align='center'>${item.getFullName()}</h6>
			        <div align='center'>
			        	<span class='text-muted'>${item.getUserMessage()}</span>
			        </div>
		        </div>
		  	</c:forEach>
          </div>
          <h4  align="center">NO ANSWER (${currentEvent.getNbrOfPeopleNotAnswering()})</h4>
          <div class="row placeholders">
            <c:forEach items="${currentEvent.userInvited}" var="item">
		  		<div class='col-xs-6 col-sm-3 placeholder'>
			        <div align='center'>
			        	<img class="img-circle" src="${item.getImage()}" width="130" height="130" class="img-responsive" alt="Generic placeholder thumbnail">
              		</div>
			        <h6 align='center'>${item.getFullName()}</h6>
			        <div align='center'>
			        	<span class='text-muted'>${item.getUserMessage()}</span>
			        </div>
		        </div>
		  	</c:forEach>
          </div>
        </div>
      </div>
    </div>
    
    <script type="text/javascript">
	    function checkInputs()
	    {
	    	console.log("checking message inputs");
	    
	    	
		    var userMessageValidator = document.getElementById('userMessageValidator');
		    userMessageValidator.innerHTML = "";
	      	
	    	var adminMessage = document.getElementById('adminMessage').value.trim();
		    
		    var valid = true;
		    
		    if (adminMessage.trim()=="")
	    	{
		    	userMessageValidator.innerHTML  ="* You have to insert a message for your friends";
	    		valid = false;
	    		console.log("adminMessage valid = false");
	    	}
		    
		    var resultWatchDate = validateDate();
    		if (resultWatchDate == false)
    		{
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
        document.getElementById("idInviteFriends").style.visibility = "hidden";
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
	            document.getElementById("idInviteFriends").style.visibility = "hidden";
	    	}else{
	    		document.getElementById("nbrSelected").style.visibility = "visible";
	            document.getElementById("selected").style.visibility = "visible";
	            document.getElementById("idInviteFriends").style.visibility = "visible";
	    	}
	    	
	    	document.getElementById("nbrSelected").innerHTML = '' + n + '';
	    }
    </script>
  </body>
</html>


	
	
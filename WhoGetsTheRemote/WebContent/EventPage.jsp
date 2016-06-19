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
          <h2 class="page-header">${currentEvent.getNameGroupe()}</h2>
          <div class="row placeholders">
          	<div class="col-sm-3 col-md-3">
          		<div align="center">
					<img src="${currentEvent.film.imageFilm}" width='190' height='190' class='img-responsive' alt='Generic placeholder thumbnail'>
				</div>
          	</div>
          	<div class="col-sm-5 col-md-5" style="font-weight: bold">
          		<div align='left'>This event will be hosted by <a href="SomeonePage.jsp?idFriend=${currentEvent.admin.getId()}">${currentEvent.admin.getFullName()}</a></div>
          		<div align='left'>You will be watching <a href="MoviePage.jsp?idMovie=${currentEvent.film.getName()}">${currentEvent.film.getName()}</a></div>
          		<div align='left'>Host's message : ${currentEvent.getAdminMessage()}</div>
          		<div align='left'>Date : ${currentEvent.getWatchingDate()}</div>
          		<div align='left'>Event created in : ${currentEvent.getCreationDate()}</div>
          	</div>
          	<div class="col-sm-4 col-md-4" style="font-weight: bold">
          		<form role="form" action="Event" onsubmit="" method="post">
          			<label>Are you willing to watch this movie with your friends?</label>	
					<input type="text" class="form-control" name="userMessage" id="userMessage" placeholder="Enter a message so your friends can see it...">
          			<br/>
          			<div class="row placeholders">
          				<div class="col-sm-2 col-md-2" style="font-weight: bold">
          					<input id="idGoing" type="submit" name="action" value="Going" /> 
          				</div>
          				<div class="col-sm-2 col-md-2" style="font-weight: bold">
          					<input id="idNotGoing" type="submit" name="action" value="NotGoing" />
          				</div>
          			</div>
          		</form>
          	</div>
          </div>  
          
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
    
    	
    	console.log("in load");
    	var tempGoing = "out";
    	var tempNotGoing = "out";
    	
    	var idGoing = document.getElementById('idGoing');
    	var idNotGoing = document.getElementById('idNotGoing');
    	
	    <c:forEach items="${currentEvent.userInvited}" var="item">
	        if ('${item.getId()}' == '${sessionId}'){
	        	idGoing.style.display = 'block';
	        	idNotGoing.style.display = 'block';
   	    		tempGoing = "in";
   	    		tempNotGoing = "in";
	        }
		</c:forEach>

	    <c:forEach items="${currentEvent.userComing}" var="item">
	        if ('${item.getId()}' == '${sessionId}'){
	        	idNotGoing.style.display = 'block';
   	    		tempGoing = "in";
	        }
		</c:forEach>

	    <c:forEach items="${currentEvent.userNotComing}" var="item">
	        if ('${item.getId()}' == '${sessionId}'){
	        	idGoing.style.display = 'block';
   	    		tempNotGoing = "in";
	        }
		</c:forEach>
		
		if (tempNotGoing == "in" && tempGoing == "out"){
			idNotGoing.style.display = 'none';
		}else if (tempGoing == "in" && tempNotGoing == "out"){
			idGoing.style.display = 'none';
		}
    </script>
  </body>
</html>


	
	
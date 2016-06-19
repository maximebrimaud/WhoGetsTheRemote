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
    <meta>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <link rel="icon" href="Bootstrap/favicon.ico">

    <title>WGTR - Home Page</title>

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
          <h1 class="page-header">New Movies</h1>
          <div class="row placeholders">
		  	<c:forEach items="${listHits}" var="item">
		  		<div class='col-xs-6 col-sm-3 placeholder'>
			        <div align='center'>
			        	<img src='${item.imageFilm}' width='190' height='190' class='img-responsive' alt='Generic placeholder thumbnail'>
					</div>
			        <h4 align='center'>${item.name}</h4>
			        <div align='center'>
			        	<span class='text-muted'>${item.name}</span>
			        </div>
		        </div>
		  	</c:forEach>
          </div>  
          
          <h2 class="page-header">People you may know</h2>
          <div class="row placeholders">
            <div class="col-xs-6 col-sm-3 placeholder">
              <div align="center">
              	<img class="img-circle" src="data:image/gif;base64,R0lGODlhAQABAIAAAHd3dwAAACH5BAAAAAAALAAAAAABAAEAAAICRAEAOw==" width="130" height="130" class="img-responsive" alt="Generic placeholder thumbnail">
              </div>
              <h4 align="center">Label</h4>
              <div align="center">
              	<span class="text-muted">10 friends in common</span>
              </div>
            </div>
            <div class="col-xs-6 col-sm-3 placeholder">
              <div align="center">
              	<img class="img-circle" src="data:image/gif;base64,R0lGODlhAQABAIAAAHd3dwAAACH5BAAAAAAALAAAAAABAAEAAAICRAEAOw==" width="130" height="130" class="img-responsive" alt="Generic placeholder thumbnail">
              </div>
              <h4 align="center">Label</h4>
              <div align="center">
              	<span class="text-muted">8 friends in common</span>
              </div>
            </div>
            <div class="col-xs-6 col-sm-3 placeholder">
              <div align="center">
              	<img class="img-circle" src="data:image/gif;base64,R0lGODlhAQABAIAAAHd3dwAAACH5BAAAAAAALAAAAAABAAEAAAICRAEAOw==" width="130" height="130" class="img-responsive" alt="Generic placeholder thumbnail">
              </div>
              <h4 align="center">Label</h4>
              <div align="center">
              	<span class="text-muted">6 friends in common</span>
              </div>
            </div>
            <div class="col-xs-6 col-sm-3 placeholder">
              <div align="center">
              	<img class="img-circle" src="data:image/gif;base64,R0lGODlhAQABAIAAAHd3dwAAACH5BAAAAAAALAAAAAABAAEAAAICRAEAOw==" width="130" height="130" class="img-responsive" alt="Generic placeholder thumbnail">
              </div>
              <h4 align="center">Label</h4>
              <div align="center">
              	<span class="text-muted">4 friends in common</span>
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
	</script>
  </body>
</html>


	
	
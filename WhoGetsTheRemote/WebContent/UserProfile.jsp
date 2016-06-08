<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%-- <%@taglib prefix="c" uri="http:java.sun.com/jsp/jstl.core" %> --%>
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
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <link rel="icon" href="Bootstrap/favicon.ico">

    <title>WGTR - Home Page</title>

    <!-- Bootstrap core CSS -->
    <link href="Bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <link href="Bootstrap/assets/css/ie10-viewport-bug-workaround.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="dashboard.css" rel="stylesheet">

    <!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
    <!--[if lt IE 9]><script src="Bootstrap/assets/js/ie8-responsive-file-warning.js"></script><![endif]-->
    <script src="Bootstrap/assets/js/ie-emulation-modes-warning.js"></script>
  </head>

  <body>

    <nav class="navbar navbar-inverse navbar-fixed-top">
      <div class="container-fluid">
        <div class="navbar-header">
          <a class="navbar-brand" href="Home.jsp">Who Gets The Remote</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
          <ul class="nav navbar-nav navbar-left">
            <li><a href="MoviesPage.jsp">Movies</a></li>
            <li><a href="FriendsPage.jsp">Friends</a></li>
            <li><a href="#">Events</a></li>
          </ul>
<!--           <form class="navbar-form navbar-right"> -->
<!--             <input type="text" class="form-control" placeholder="Search..."> -->
<!--           </form> -->
		   <ul class="nav navbar-nav navbar-right">
            <li><a href="#">${userLogged.getFullName()}</a></li>
            <li><a href="LogoutPage.jsp">LogOut</a></li>
          </ul>
        </div>
      </div>
    </nav>

    <div class="container-fluid">
      <div class="row">
<!--         <div class="col-sm-3 col-md-2 sidebar"> -->
<!--           <ul class="nav nav-sidebar"> -->
<!--             <li class="active"><a href="#">Overview <span class="sr-only">(current)</span></a></li> -->
<!--             <li><a href="#">Reports</a></li> -->
<!--             <li><a href="#">Analytics</a></li> -->
<!--             <li><a href="#">Export</a></li> -->
<!--           </ul> -->
<!--         </div> -->
        <div class="col-sm-10 col-sm-offset-3 col-md-10 col-md-offset-1 main">
	        </br>
	        <h1 class="page-header">${User.username}</h1>                                      
	        <div class="row placeholders">
	          	<div class='col-xs-6 col-sm-1 placeholder'>	    <!-- width=25% align="left" -->    		
	          		<img class="img-circle" alt="Profile Picture" width="130" height="130" class="img-responsive" src="img/Profiles/mike.jpg">
	          	</div>
	         
			  	<c:forEach items="${listHits}" var="item">
			  		<div class='col-xs-6 col-sm-3 placeholder'>
				        <div align='center'>
				        	<img src='data:image/gif;base64,R0lGODlhAQABAIAAAHd3dwAAACH5BAAAAAAALAAAAAABAAEAAAICRAEAOw==' width='190' height='190' class='img-responsive' alt='Generic placeholder thumbnail'>
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

    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script>window.jQuery || document.write('<script src="Bootstrap/assets/js/vendor/jquery.min.js"><\/script>')</script>
    <script src="Bootstrap/dist/js/bootstrap.min.js"></script>
    <!-- Just to make our placeholder images work. Don't actually copy the next line! -->
    <script src="Bootstrap/assets/js/vendor/holder.min.js"></script>
    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <script src="Bootstrap/assets/js/ie10-viewport-bug-workaround.js"></script>
  </body>
</html>


	
	strap/assets/js/ie10-viewport-bug-workaround.js"></script>
  </body>
</html>


	
	
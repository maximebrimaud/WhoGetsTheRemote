<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.io.*,java.util.*,javax.servlet.*" %>
<%
	if (session.getAttribute("sessionId") == null || session.getAttribute("sessionId").equals("")){
		response.sendRedirect("LoginPage.jsp");
	}
	
	if (session.getAttribute("UserType") != "Admin")
	{
		response.sendRedirect("Movies");
	}

%>

<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <link rel="icon" href="Bootstrap/docs/favicon.ico">

    <title>WGTR - Movie Categories</title>

    <!-- Bootstrap core CSS -->
    <link href="Bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">    
  </head>

  <body>

    <nav class="navbar navbar-inverse navbar-fixed-top">
      <div class="container-fluid">
        <div class="navbar-header">
          <a class="navbar-brand" href="Home.jsp">Who Gets The Remote</a>
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
        <div class="col-sm-2 col-md-2 sidebar">
          </br>
          <h2 class="page-header">Search</h2>
          <ul class="nav nav-sidebar">	
          	<li id="btnCreateMovie" style="display: none;"><a href="${pageContext.request.contextPath}/CreateFilm">Create Movie</a></li> <!-- MoviesCreate.jsp    ////   CreateFilmData -->
          	<li id="btnManageCategories" style=" display: none; "><a style="text-decoration: underline;" href="${pageContext.request.contextPath}/MovieCategories">Manage Categories</a></li>             
            <li ><a href="${pageContext.request.contextPath}/Movies" >Overview<!-- <span class="sr-only">(current)</span> --></a></li>                        
          </ul>
        </div>
        <div class="col-sm-10 col-md-10 main">
          </br>
          <h2 class="page-header">Movie Categories</h2>
          <div class="row placeholders">          	
          	<form role="form" action="MovieCategories" onsubmit="return ValidateInputs()" method="post">          	
	          	<div class="col-xs-6 col-sm-12 sidebar-offcanvas" id="sidebar">
		          <div class="list-group">	          
		          	<div align="center" class='col-xs-6 col-sm-5 placeholder'>
		          		<a><input style="width: 100%; "  class="list-group-item" name="txtCategorieLibelle" id="txtCategorieLibelle" placeholder="Create new Category"></a>		          	
		          		<!-- <input style="padding-bottom: 20px;" class="list-group-item" id="txtCategorieLibelle" placeholder="Insert new Category"> -->
			          	<c:forEach items="${listCategorie}" var="item">			  				       
				        	 <a class="list-group-item">${item.getLibelleFilmCategorie()}</a>
				  		</c:forEach>
		         	</div>
		          	<div align="left" class='col-xs-6 col-sm-5 placeholder'>
		          		<!-- <input style="padding-bottom: 20px;" class="form-control" id="txtCategorieLibelle" placeholder="Select Categorie"> -->
		          		<input class="btn btn-primary" type="submit"  value="Create">
		          		<label id="CategorieValidator" style="color: red; font-weight:normal;"></label>
		          	</div>
		          	<div class="col-xs-12 col-sm-3 col-md-4" style=" font-size: large; text-align: right; color: red; " >
						${CreateMessage}							  														  											  	
					</div>
		          </div>
		        </div>
          	</form>
          </div>  
        </div>
      </div>
    </div>

	
    <br/><br/><br/>
    <div style="background-color:black; color:white; clear:both; text-align:center; padding:5px; padding-top:5px"> 
    	Who Gets The Remote - The Place for movie lovers
    </div>
    
    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script>window.jQuery || document.write('<script src="Bootstrap/docs/assets/js/vendor/jquery.min.js"><\/script>')</script>
    <script src="Bootstrap/dist/js/bootstrap.min.js"></script>
    <!-- Just to make our placeholder images work. Don't actually copy the next line! -->
    <script src="Bootstrap/docs/assets/js/vendor/holder.min.js"></script>
    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <script src="Bootstrap/docs/assets/js/ie10-viewport-bug-workaround.js"></script>
   
    <script>
	    console.log("doing on load script");
// 	    var btnAdmin = document.getElementById("btnAdmin"); 
		var btnCreateMovie = document.getElementById("btnCreateMovie");    		    	
		var btnManageCategories = document.getElementById("btnManageCategories");  
		console.log("got btnAdmin");
		var UserType = '<%= session.getAttribute("UserType") %>';
		console.log("user type = " + UserType);
		
		if (UserType == "Admin")
		{
// 			btnAdmin.style.display = "block";
			btnCreateMovie.style.display = "block";
			btnManageCategories.style.display = "block";
		}
		else
		{
// 			btnAdmin.style.display = "none";
			btnManageCategories.style.display = "none";
			btnCreateMovie.style.display = "none";
		}
		
		
		$(document).ready(function () {
		  $('[data-toggle="offcanvas"]').click(function () {
		    $('.row-offcanvas').toggleClass('active')
		  });
		});		
	
</script>

<script type="text/javascript">
function ValidateInputs()
{
	 var txtCategorieLibelle = document.getElementById("txtCategorieLibelle"); 
	 var CategorieValidator =  document.getElementById("CategorieValidator");
	 if (txtCategorieLibelle == null || txtCategorieLibelle == "")
	 {
	 	CategorieValidator.innerHTML = "* Mandatory Field";
	 	return false;
	 }
	 else
	 {
		CategorieValidator.innerHTML = "";
		return true;
	 }   
}

</script>

	
	
  </body>
</html>



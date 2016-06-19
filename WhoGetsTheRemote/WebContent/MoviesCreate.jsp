<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.io.*,java.util.*,javax.servlet.*" %>
<%
	if (session.getAttribute("sessionId") == null || session.getAttribute("sessionId").equals("")){
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

    <title>WGTR - Create Movie</title>

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
        <div class="col-sm-2 col-md-2 sidebar">
          </br>
          <h2 class="page-header">Search</h2>
          <ul class="nav nav-sidebar">
            <li class="active"><a href="#" style="text-decoration: underline;">Create Movie<!-- <span class="sr-only">(current)</span> --></a></li>
            <li><a href="MoviesPage.jsp">Overview</a></li>
            <li><a href="#">Reports</a></li>
            <li><a href="#">Analytics</a></li>
            <li><a href="#">Export</a></li>
          </ul>
        </div>
        <div class="col-sm-10 col-md-10 main">
          </br>
          <h2 class="page-header"><%-- ${Movie.getName()} <span style="font-size:large; color: gray;">(${Movie.getDateReleased()})</span> --%>Create Movie</h2>
          <div class="row placeholders">		  			  	
		  	<div class="container-fluid">
      			<div class="row">
        			<div class="col-sm-10 col-sm-offset-3 col-md-10 col-md-offset-1 main">		       
	        			<div class="row placeholders">				  						  		
					  		<%-- <div class='col-xs-6 col-sm-3 col-md-3 placeholder'>
						        <div align='center'>						        	
						        	<img src='${Movie.getImage()}' width='190' height='190' class='img-responsive' alt='Unable to load image!'>	
								</div>
						        <h4 align='center'>${Movie.getFullName()}</h4>   
					        </div> --%>
					       <form role="form" action="CreateFilm" onsubmit="return ValidateInputs()" method="post">  
					      		 <input name="userId" id="userId" style="display: none;" value="${Movie.getId()}"/>
						        <div class='col-xs-6 col-sm-9 col-md-9 placeholder' style="padding-top: 45px;">
						        					 	       
									<div class="row">
										  <div class="col-xs-12 col-sm-3 col-md-3" style="text-decoration: underline; text-align: right;  font-size: large;  font-weight: bold;  ">Title:</div> <!--  padding-right: 0px; -->										  
									  		<div id="EditModeEmail" class="col-xs-6" style="text-align: left;  font-size: large; ">
										  		<input id="Title" type="text" name="Title" placeholder="Title" />		
										  		<label id="TitleValidator" style="color: red; font-weight:normal;"></label>
								  		   </div>
									</div>
									<div class="row">
										  <div class="col-xs-12 col-sm-3 col-md-3" style="text-decoration: underline; text-align: right;  font-size: large;  font-weight: bold;  ">Director:</div> <!--  padding-right: 0px; -->										  
									  		<div id="EditModeEmail" class="col-xs-6" style="text-align: left;   font-size: large; ">
										  		<input id="Director" type="text" name="Director" placeholder="Director" />		
										  		<label id="DirectorValidator" style="color: red; font-weight:normal;"></label>
								  		   </div>
									</div>
									<div class="row">
										  <div class="col-xs-12 col-sm-3 col-md-3" style="text-decoration: underline; text-align: right;  font-size: large;  font-weight: bold;  ">Language:</div> <!--  padding-right: 0px; -->										  
									  		<div id="EditModeEmail" class="col-xs-6" style="text-align: left;   font-size: large; ">
										  		<input id="Language" type="text" name="Language" placeholder="Language" />		
										  		<label id="LanguageValidator" style="color: red; font-weight:normal;"></label>
								  		   </div>
									</div>
									<div class="row">
										  <div class="col-xs-12 col-sm-3 col-md-3" style="text-decoration: underline; text-align: right;  font-size: large;  font-weight: bold;  ">Cast:</div> <!--  padding-right: 0px; -->										  
									  		<div id="EditModeEmail" class="col-xs-6" style="text-align: left;   font-size: large; ">
										  		<input id="Cast" type="text" name="Cast" placeholder="Cast" />		
										  		<label id="CastValidator" style="color: red; font-weight:normal;"></label>
								  		   </div>
									</div>
									
									<div class="row">
										  <div class="col-xs-12 col-sm-5 col-md-3" style="text-decoration: underline;  font-weight: bold; font-size: large; text-align: right;">Release Date:</div><!--  padding-right: 0px; -->
										  <div id="EditModeDOB" class="col-xs-6" style="text-align: left;  font-size: large; ">
										  		<input id="ReleaseDate" type="text" name="ReleaseDate" placeholder="yyyy-mm-dd"/>	
										  		<label id="ReleaseDateValidator" style="color: red; font-weight:normal;"></label>
								  		   </div>										
									</div>
									
									<div class="row">
										  <div class="col-xs-12 col-sm-5 col-md-3" style="text-decoration: underline;  font-weight: bold; font-size: large; text-align: right;">Category:</div><!--  padding-right: 0px; -->
										  <div id="EditModeDOB" class="col-xs-6" style="text-align: left;  font-size: large; ">
										  		<select id="Categorie" name="Categorie">
										  			<option value="-1">Select Category</option>
													<c:forEach items="${CategorieList}" var="item">
													    <option value="${item.getId()}">${item.getLibelleFilmCategorie()}</option>
													</c:forEach>
												</select>
										  		<label id="CategorieValidator" style="color: red; font-weight:normal;"></label>
								  		   </div>										
									</div>
									
									<div class="row">
										  <div class="col-xs-12 col-sm-3 col-md-3" style="text-decoration: underline;  font-size: large; font-weight: bold;  text-align: right;">Duration:</div>	<!--  padding-right: 0px; -->								 										  
										  <div  id="EditModeAddress"  class="col-xs-6 " style="text-align: left;  font-size: large; ">
										  		<input id="Duration" type="text" name="Duration" placeholder="hh:mm"/>
										  		<label id="DurationValidator" style="color: red; font-weight:normal;"></label>
										  </div>
									</div>
									<div class="row">
										  <div class="col-xs-12 col-sm-5 col-md-3" style="text-decoration: underline;  font-weight: bold; font-size: large; text-align: right;">PG Level:</div><!--  padding-right: 0px; -->
										  <div id="EditModeDOB" class="col-xs-6" style="text-align: left;  font-size: large; ">
										  		<select id="PG" name="PG">													
													    <option value="-1">Select PG</option>
													    <option value="0">0</option>
													    <option value="1">1</option>
													    <option value="2">2</option>
													    <option value="3">3</option>
													    <option value="4">4</option>
													    <option value="5">5</option>
													    <option value="6">6</option>
													    <option value="7">7</option>
													    <option value="8">8</option>
													    <option value="9">9</option>
													    <option value="10">10</option>
													    <option value="11">11</option>
													  	<option value="12">12</option>
													    <option value="13">13</option>
													    <option value="14">14</option>
													    <option value="15">15</option>
													    <option value="16">16</option>
													    <option value="17">17</option>
													    <option value="18">18</option>													    										
												</select>	
										  		<label id="PGValidator" style="color: red; font-weight:normal;"></label>
								  		   </div>										
									</div>
									<div class="row">
										  <div class="col-xs-12 col-sm-3 col-md-3" style="text-decoration: underline; font-weight: bold; text-align: right; font-size: large; ">Description:</div>	<!--  padding-right: 0px; -->								 
							  		   	  <div id="EditModeDOB" class="col-xs-6" style="text-align: left;  font-size: large; ">
										  		<input id="Description" type="text" name="Description" placeholder="Description"/>	
										  		<label id="DescriptionValidator" style="color: red; font-weight:normal;"></label>
							  		   </div>	
									</div>
									<div class="row">
										  <div class="col-xs-12 col-sm-5 col-md-3" style="text-decoration: underline;  font-weight: bold; font-size: large; text-align: right;">IMDB Rating:</div><!--  padding-right: 0px; -->
										  <div id="EditModeDOB" class="col-xs-6" style="text-align: left;  font-size: large; ">
										  		<select id="Rating" name="Rating">													
													    <option value="-1">Select Rating</option>
													    <option value="0">0</option>
													    <option value="1">1</option>
													    <option value="2">2</option>
													    <option value="3">3</option>
													    <option value="4">4</option>
													    <option value="5">5</option>
													    <option value="6">6</option>
													    <option value="7">7</option>
													    <option value="8">8</option>
													    <option value="9">9</option>
													    <option value="10">10</option>													
												</select>	
										  		<label id="RatingValidator" style="color: red; font-weight:normal;"></label>
								  		   </div>										
									</div>													
									<div id="divChangePic" class="row" >
										  <div class="col-xs-12 col-sm-3 col-md-3" style="text-align: left; font-size: large; text-align: right; " >
												<label  style="text-decoration: underline; font-weight: bold;  font-size: large;  ">Image:</label>  
										  </div>
										  <div class="col-xs-12 col-sm-9"  style=" font-size: large; " >
										  		<input name="PictureBrowse" id="PictureBrowse" type="file" name="PictureBrowse" />
										  		<label id="PicValidator" style="color: red; font-weight:normal;"></label> 
										  </div>								 									  
									</div>
									<div class="row">
										  <div class="col-xs-12 col-sm-3 col-md-3" style="text-decoration: underline; font-weight: bold; text-align: right; font-size: large; ">Trailer Link:</div>	<!--  padding-right: 0px; -->								 
							  		   	  <div id="EditModeDOB" class="col-xs-6" style="text-align: left; font-size: large; ">
										  		<input id="Trailer" type="text" name="Trailer" placeholder="Trailer"/>	
										  		<label id="TrailerValidator" style="color: red; font-weight:normal;"></label>
							  		   </div>	
									</div>
									<div class="row">
										  <div class="col-xs-12 col-sm-3 col-md-3" style="text-decoration: underline; font-weight: bold; text-align: right; font-size: large; ">Movie Link:</div>	<!--  padding-right: 0px; -->								 
							  		   	  <div id="EditModeDOB" class="col-xs-6" style="text-align: left;  font-size: large; ">
										  		<input id="Movie" type="text" name="Movie" placeholder="Movie"/>	
										  		<label id="MovieValidator" style="color: red; font-weight:normal;"></label>
							  		   </div>	
									</div>
									<br/>
									<div class="row">									 
										  <div class="col-xs-12 col-sm-3 col-md-4" style=" font-size: large; text-align: right; " >										  		
										  		<input id="btnCancelCreate" type="reset" name="btnCancelCreate" value="Cancel"/>										  														  											  	
										  </div>								 									  
										  <div class="col-xs-12 col-sm-3 col-md-4" style=" font-size: large; text-align: left; "  >
										  		<input id="btnCreateFilm" type="submit" name="btnCreateFilm" value="Create" />
										  </div>
									</div>	
									<div class="row">									 
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
    <script>window.jQuery || document.write('<script src="Bootstrap/assets/js/vendor/jquery.min.js"><\/script>')</script>
    <script src="Bootstrap/dist/js/bootstrap.min.js"></script>
    <!-- Just to make our placeholder images work. Don't actually copy the next line! -->
    <script src="Bootstrap/assets/js/vendor/holder.min.js"></script>
    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <script src="Bootstrap/assets/js/ie10-viewport-bug-workaround.js"></script>
    
    <script>
    	function ValidateInputs()
    	{
    		console.log("Validating input .....");
    		var valide = true;
    		// Getting input values
    		var Title =  document.getElementById("Title").value.trim();
    		var Director = document.getElementById("Director").value.trim();    		    	
    		var Language = document.getElementById("Language").value.trim();
    		var Cast = document.getElementById("Cast").value.trim();
    		var ReleaseDate =  document.getElementById("ReleaseDate").value.trim();   
    		console.log("Validating input .....1"); 		
    		var ddlCategorie = document.getElementById("Categorie");
			var Categorie = ddlCategorie.options[ddlCategorie.selectedIndex].value;
			console.log("Validating input .....2");
    		var Duration = document.getElementById("Duration").value.trim();
    		var ddlPG = document.getElementById("PG");
    		console.log("Validating input .....3");
			var PG = ddlPG.options[ddlPG.selectedIndex].value;    		    	
    		var Description = document.getElementById("Description").value.trim();
    		console.log("Validating input .....4");
    		var ddlRating = document.getElementById("Rating");
			var Rating = ddlRating.options[ddlRating.selectedIndex].value;    
			console.log("Validating input .....5");
    		var PictureBrowse = document.getElementById("PictureBrowse").value.trim();
    		console.log("Validating input .....6");
    		var Trailer = document.getElementById("Trailer").value.trim(); 
    		var Movie = document.getElementById("Movie").value.trim();
    		console.log("Validating input .....7");
    		// Getting Inputs' Validators
    		var TitleValidator =  document.getElementById("TitleValidator");
    		var DirectorValidator = document.getElementById("DirectorValidator");    		    	
    		var LanguageValidator = document.getElementById("LanguageValidator");
    		var CastValidator = document.getElementById("CastValidator");
    		var ReleaseDateValidator =  document.getElementById("ReleaseDateValidator");    		
    		var CategorieValidator = document.getElementById("CategorieValidator");		
    		var DurationValidator = document.getElementById("DurationValidator");
    		var PGValidator = document.getElementById("PGValidator");			    		    	
    		var DescriptionValidator = document.getElementById("DescriptionValidator");    		
			var RatingValidator = document.getElementById("RatingValidator");    
    		var PictureValidator = document.getElementById("PicValidator");
    		var TrailerValidator = document.getElementById("TrailerValidator");
    		var MovieValidator = document.getElementById("MovieValidator");
    		    		    		    		    	
    		if (Title =="" || Title == null)
    		{
    			valide = false;
    			TitleValidator.innerHTML = "* Mandatory Field";
    		}
    		else    		
    		{
    			TitleValidator.innerHTML = "";
    		}
    		if (Director =="" || Director == null)
    		{
    			valide = false;
    			DirectorValidator.innerHTML = "* Mandatory Field";
    		}
    		else
    		{
    			DirectorValidator.innerHTML = "";
    		}
    		if (Language =="" || Language == null)
    		{
    			valide = false;
    			LanguageValidator.innerHTML = "* Mandatory Field";
    		}
    		else
    		{
    			LanguageValidator.innerHTML = "";
    		}    		
    		if (Cast =="" || Cast == null)
    		{
    			valide = false;
    			CastValidator.innerHTML = "* Mandatory Field";
    		}
    		else
    		{
    			CastValidator.innerHTML = "";
    		}
    		if (Categorie =="" || Categorie == null || Categorie == -1)
    		{
    			valide = false;
    			CategorieValidator.innerHTML = "* Mandatory Field";
    		}
    		else
    		{
    			CategorieValidator.innerHTML = "";
    		}
    		if (Duration =="" || Duration == null)
    		{
    			valide = false;
    			DurationValidator.innerHTML = "* Mandatory Field";
    		}
    		else
    		{
    			DurationValidator.innerHTML = "";
    		}
    		if (PG =="" || PG == null || PG ==-1)
    		{
    			valide = false;
    			PGValidator.innerHTML = "* Mandatory Field";
    		}
    		else
    		{
    			PGValidator.innerHTML = "";
    		}
    		if (Description =="" || Description == null)
    		{
    			valide = false;
    			DescriptionValidator.innerHTML = "* Mandatory Field";
    		}
    		else
    		{
    			DescriptionValidator.innerHTML = "";
    		}
    		if (Rating =="" || Rating == null || Rating == -1)
    		{
    			valide = false;
    			RatingValidator.innerHTML = "* Mandatory Field";
    		}
    		else
    		{
    			RatingValidator.innerHTML = "";
    		}
    		if (PictureBrowse =="" || PictureBrowse == null)
    		{
    			valide = false;
    			PictureValidator.innerHTML = "* Mandatory Field";
    		}
    		else
    		{
    			PictureValidator.innerHTML = "";
    		}
    		if (Trailer =="" || Trailer == null)
    		{
    			valide = false;
    			TrailerValidator.innerHTML = "* Mandatory Field";
    		}
    		else
    		{
    			TrailerValidator.innerHTML = "";
    		}
    		if (Movie =="" || Movie == null)
    		{
    			valide = false;
    			MovieValidator.innerHTML = "* Mandatory Field";
    		}
    		else
    		{
    			MovieValidator.innerHTML = "";
    		}
    		
    		
    		var resultDOB = validateDOB();
    		if (resultDOB == false)
    		{
    			valide = false;
    		}
    		console.log("Validating input result = " + valide);
    		return valide;
    	}
        
    
    	function validateDOB()
    	{
	   		console.log("starting validating ....." );
	   		var UserDOB = document.getElementById("ReleaseDate");     		
		    var value = UserDOB.value;
		    var DOBValidator = document.getElementById("ReleaseDateValidator");     				   
		    			  
		  	DOBValidator.innerHTML = "";			  

	     	var regEx2 = /^\d{4}-\d{2}-\d{2}$/;
		    DOBValidator.innerHTML = "";
		    if (value == null || value == "" )
		    {
		      	console.log("Date null" );
			    DOBValidator.innerHTML = "* Mandatory Field";
			    return false; 
		    }
			if(!regEx2.test(value))
			{
			    console.log("Date format not valide" );
			    DOBValidator.innerHTML = "* Invalid date of birth format (yyyy-mm-dd)";
			    return false;  // Invalid format
		    }
		      
			var d;
			if(!((d = new Date(value))|0))
		    {
				DOBValidator.innerHTML = "* Invalid date of birth (yyyy-mm-dd)";
			    console.log("Date not valide" );
			    return false; 
			}
			 
			console.log("Comparing '"+ d.toISOString().slice(0,10) + "' with: '" + value + "'" );	
			console.log("comparaison result : " + (d.toISOString().slice(0,10) == value))
			 
			if(d.toISOString().slice(0,10) == value)
			{			  
				console.log("Date valid");	
				return true;
			}
			else
			{
			  	DOBValidator.innerHTML = "* Invalid date of birth format (yyyy-mm-dd)";
			    console.log("Date not valide (yyyy-mm-dd)" );
				return false; 
			}		
		}
    </script>
  </body>
</html>


	
	
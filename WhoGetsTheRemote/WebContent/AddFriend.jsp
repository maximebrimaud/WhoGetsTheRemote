<<<<<<< HEAD
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
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
    <title>WGTR - Friends Page</title>
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
      	<form role="form" action="ManageFriends"  method="post">
	      	<div class="col-sm-10 col-sm-offset-3 col-md-12 col-md-offset-1 main">
	          </br>          
	          <input name="friendId" id="friendId" style="display: none;" value="${friend.getId()}"/>
	          <h1 class="page-header">${friend.getFullName()}</h1>
	          <div class="row placeholders">
	          	<div align='center' class='col-xs-6 col-sm-3 col-md-12  placeholder'>
			        <div align='center'>
			        	<img src='${friend.getUserr().getImage()}' width='190' height='190' class='img-responsive' alt='Generic placeholder thumbnail'>
					</div>
			        <h2 align='center'>${friend.getFullName()}</h2>		       
			        <h3 align='center'><span class='text-muted'>${friend.getUserr().getUsername()}</span></h3>		        
	          	</div>
	          	<div align='center' class='col-xs-6 col-sm-3 col-md-12  placeholder'>
	          		<input class="btn btn-primary"  name="btnAddFriend" id="btnAddFriend" type="submit" value="Add Friend">	          		
	          		<input class="btn btn-primary" name="btnRemoveFriend" id="btnRemoveFriend" type="submit" value="Remove Friend">
	          		<input class="btn"  name="btnRequestPending"id="btnRequestPending" onclick="return false;" type="button" value="Request Pending...">
	          		
	          		<div class='row'>
			          	<div class='col-sm-6 col-md-6' align='right'>
		          			<input class="btn btn-primary" name="btnConfirme" id="btnConfirme" type="submit" value="Confirme Request">	
			          	</div>
			          	
			          	<div class='col-sm-6 col-md-6' align='left'>
		          			<input class="btn " name="btnIgnore" id="btnIgnore" type="submit" value="Ignore Request">
			          	</div> 
	          		</div>         		
	          			  
	          		${ModifyStateResult}	
	          	</div>
	          	<div align='center' class='col-xs-6 col-sm-3 col-md-12  placeholder'>			        
	        	 	<h4 align='center'>Email: ${friend.getUserr().getEmail()}</h4> 
	        	 	<h4 id="lblDOB" align='center'>Date of Birth: ${friend.getUserr().getDob()}</h4> 
	        	 	<h4 id="lblAddress" align='center'>Address: ${friend.getUserr().getAddress()}</h4>  
	        	 	<h4 id="lblGender" align='center'>Gender: ${friend.getUserr().getSexe()}</h4>           	 	
			        <h4 align='center'><span style="font-style: italic;" class='text-muted'>Member since ${friend.getUserr().getCreationDate()}</span></h4>		        
	        	 	<%-- <h4 align='center'>Member since: ${friend.getCreationDate()}</h4>      --%>        	 	  	 				        			        
	          	</div>
	          </div>  
	        </div>
	        <div class="col-sm-10 col-sm-offset-3 col-md-10 col-md-offset-1 main">         
	          <h1 class="page-header">Friends In Common (${friend.getfriendsCommonNumber()} Friends)</h1>
	          <div class="row placeholders">
			  	<c:forEach items="${friend.getListFriendsCommon()}" var="item">
			  		<div class="col-xs-6 col-sm-3 placeholder">
		              <div align="center">
		              	<img class="img-circle" src='${item.getImage()}' width="150" height="150" class="img-responsive" alt="Generic placeholder thumbnail">
		              </div>
		              <h4 align="center"><a href="${pageContext.request.contextPath}/ManageFriends?friendId=${item.getId()}" >${item.fullName}</a></h4>		       
		            </div>
			  	</c:forEach>
	          </div>  
	        </div>
	        <div class="col-sm-10 col-sm-offset-3 col-md-10 col-md-offset-1 main">
	          </br>
	          <h1 class="page-header">Events In Common</h1>
	          <div class="row placeholders">
			  	<c:forEach items="${friend.getListEventsCommon()}" var="item">
			  		<div class="col-xs-6 col-sm-3 placeholder">
		              <div align="center">
		              	<img src="${item.getFilm().getImageFilm()}" width="130" height="130" class="img-responsive" alt="Generic placeholder thumbnail">	              	
		              </div>
		              <h4 align="center">${item.getNameGroupe()}</h4>
		              <div align="center">
		              	<span class="text-muted" style="font-size: small;">${item.getFilm().getName()} </span>
		              </div>
		              <div align="center">
		              	<span class="text-muted" style="font-style: italic;">${item.getWatchingDate()}</span>
		              </div>
		            </div>
			  	</c:forEach>
	          </div>  
	        </div>
	        <div id="divFriendMovies" class="col-sm-10 col-sm-offset-3 col-md-10 col-md-offset-1 main">
	          </br>
	          <h1 class="page-header">Movies ${friend.getUserr().getFirstName()} Has Seen</h1>
	          <div class="row placeholders">
			  	<c:forEach items="${friend.getListMovies()}" var="item">
			  		<div class="col-xs-6 col-sm-3 placeholder">
	              <div align="center">
	              	<img src='${item.getImageFilm()}' width="130" height="130" class="img-responsive" alt="Generic placeholder thumbnail">
	              </div>
	              <h4 align="center">${item.getName()}</h4>
	              <div align="center">
	              	<span class="text-muted">IMDB Rating: ${item.getNotationFilm()}/10</span>
	              </div>
	            </div>
			  	</c:forEach>
	          </div>  
	        </div>
		</form>      
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
    
    
    <script>    	
    	var isFriend = '${isFriend}'; 
    	console.log("doing on load scrip addFriend");	
    	console.log("var isFriend = " + isFriend); 
    	var btnAddFriend = document.getElementById('btnAddFriend');  	
    	var lblDOB = document.getElementById('lblDOB');
    	var lblAddress = document.getElementById('lblAddress'); 
    	var lblGender = document.getElementById('lblGender'); 
    	var btnRemoveFriend = document.getElementById('btnRemoveFriend'); 
    	var btnRequestPending = document.getElementById('btnRequestPending');
    	var divFriendMovies = document.getElementById('divFriendMovies');  
    	var btnConfirme = document.getElementById('btnConfirme');
    	var btnIgnore = document.getElementById('btnIgnore');
	    
	    if (isFriend == "Accepted")
	    {
	    	lblDOB.style.display = "block";
	    	lblAddress.style.display = "block";
	    	lblGender.style.display = "block";
	    	btnRemoveFriend.style.display = "block";
			divFriendMovies.style.display = "block";
			btnRequestPending.style.display = "none";
			btnAddFriend.style.display = "none";
			
			btnIgnore.style.display = "none";
			btnConfirme.style.display = "none";			
	    }
	    else if (isFriend == "Pending")
	    {
	    	lblDOB.style.display = "none";
	    	lblAddress.style.display = "none";
	    	lblGender.style.display = "none";
	    	btnRemoveFriend.style.display = "none";
	    	divFriendMovies.style.display = "none";
			btnAddFriend.style.display = "none"; 
			btnRequestPending.style.display = "block";
			
			btnIgnore.style.display = "none";
			btnConfirme.style.display = "none";
	    }
	    else if (isFriend == "Confirme")
	    {
	    	lblDOB.style.display = "none";
	    	lblAddress.style.display = "none";
	    	lblGender.style.display = "none";
	    	btnRemoveFriend.style.display = "none";
	    	divFriendMovies.style.display = "none";
			btnAddFriend.style.display = "none"; 
			btnRequestPending.style.display = "none";
			
			
			btnIgnore.style.display = "block";
			btnConfirme.style.display = "block";
	    }
	    else 
	    {
	    	lblDOB.style.display = "none";
	    	lblAddress.style.display = "none";
	    	lblGender.style.display = "none";
	    	btnRemoveFriend.style.display = "none";
	    	divFriendMovies.style.display = "none";
			btnAddFriend.style.display = "block"; 
			btnRequestPending.style.display = "none";
			
			btnIgnore.style.display = "none";
			btnConfirme.style.display = "none";
	    }	    
    </script>
  </body>
=======
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
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
    <title>WGTR - Friends Page</title>
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
      	<form role="form" action="ManageFriends"  method="post">
	      	<div class="col-sm-10 col-sm-offset-3 col-md-12 col-md-offset-1 main">
	          </br>          
	          <input name="friendId" id="friendId" style="display: none;" value="${friend.getId()}"/>
	          <h1 class="page-header">${friend.getFullName()}</h1>
	          <div class="row placeholders">
	          	<div align='center' class='col-xs-6 col-sm-3 col-md-12  placeholder'>
			        <div align='center'>
			        	<img src='${friend.getUserr().getImage()}' width='190' height='190' class='img-responsive' alt='Generic placeholder thumbnail'>
					</div>
			        <h2 align='center'>${friend.getFullName()}</h2>		       
			        <h3 align='center'><span class='text-muted'>${friend.getUserr().getUsername()}</span></h3>		        
	          	</div>
	          	<div align='center' class='col-xs-6 col-sm-3 col-md-12  placeholder'>
	          		<input style="background-color:activecaption;" name="btnAddFriend" id="btnAddFriend" type="submit" value="Add Friend">	          		
	          		<input style="background-color:activecaption;" name="btnRemoveFriend" id="btnRemoveFriend" type="submit" value="Remove Friend">
	          		<input style="background-color:activecaption;"  name="btnRequestPending"id="btnRequestPending" onclick="return false;" type="button" value="Request Pending...">
	          		
	          		<div class='row'>
			          	<div class='col-sm-6 col-md-6' align='right'>
		          			<input style="background-color:activecaption;" name="btnConfirme" id="btnConfirme" type="submit" value="Confirme Request">	
			          	</div>
			          	
			          	<div class='col-sm-6 col-md-6' align='left'>
		          			<input style="background-color:activecaption;" name="btnIgnore" id="btnIgnore" type="submit" value="Ignore Request">
			          	</div> 
	          		</div>         		
	          			  
	          		${ModifyStateResult}	
	          	</div>
	          	<div align='center' class='col-xs-6 col-sm-3 col-md-12  placeholder'>			        
	        	 	<h4 align='center'>Email: ${friend.getUserr().getEmail()}</h4> 
	        	 	<h4 id="lblDOB" align='center'>Date of Birth: ${friend.getUserr().getDob()}</h4> 
	        	 	<h4 id="lblAddress" align='center'>Address: ${friend.getUserr().getAddress()}</h4>  
	        	 	<h4 id="lblGender" align='center'>Gender: ${friend.getUserr().getSexe()}</h4>           	 	
			        <h4 align='center'><span style="font-style: italic;" class='text-muted'>Member since ${friend.getUserr().getCreationDate()}</span></h4>		        
	        	 	<%-- <h4 align='center'>Member since: ${friend.getCreationDate()}</h4>      --%>        	 	  	 				        			        
	          	</div>
	          </div>  
	        </div>
	        <div class="col-sm-10 col-sm-offset-3 col-md-10 col-md-offset-1 main">         
	          <h1 class="page-header">Friends In Common (${friend.getfriendsCommonNumber()} Friends)</h1>
	          <div class="row placeholders">
			  	<c:forEach items="${friend.getListFriendsCommon()}" var="item">
			  		<div class="col-xs-6 col-sm-3 placeholder">
		              <div align="center">
		              	<img class="img-circle" src='${item.getImage()}' width="150" height="150" class="img-responsive" alt="Generic placeholder thumbnail">
		              </div>
		              <h4 align="center"><a href="${pageContext.request.contextPath}/AddFriend?friendId=${item.getId()}" >${item.fullName}</a></h4>
		             <%--  <div align="center">
		              	<span class="text-muted">${item.friendsCommonNumber} friends in common</span>
		              </div> --%>
		            </div>
			  	</c:forEach>
	          </div>  
	        </div>
	        <div class="col-sm-10 col-sm-offset-3 col-md-10 col-md-offset-1 main">
	          </br>
	          <h1 class="page-header">Events In Common</h1>
	          <div class="row placeholders">
			  	<c:forEach items="${friend.getListEventsCommon()}" var="item">
			  		<div class="col-xs-6 col-sm-3 placeholder">
		              <div align="center">
		              	<img src="${item.getFilm().getImageFilm()}" width="130" height="130" class="img-responsive" alt="Generic placeholder thumbnail">	              	
		              </div>
		              <h4 align="center">${item.getNameGroupe()}</h4>
		              <div align="center">
		              	<span class="text-muted" style="font-size: small;">${item.getFilm().getName()} </span>
		              </div>
		              <div align="center">
		              	<span class="text-muted" style="font-style: italic;">${item.getWatchingDate()}</span>
		              </div>
		            </div>
			  	</c:forEach>
	          </div>  
	        </div>
	        <div id="divFriendMovies" class="col-sm-10 col-sm-offset-3 col-md-10 col-md-offset-1 main">
	          </br>
	          <h1 class="page-header">Movies ${friend.getUserr().getFirstName()} Has Seen</h1>
	          <div class="row placeholders">
			  	<c:forEach items="${friend.getListMovies()}" var="item">
			  		<div class="col-xs-6 col-sm-3 placeholder">
	              <div align="center">
	              	<img src='${item.getImageFilm()}' width="130" height="130" class="img-responsive" alt="Generic placeholder thumbnail">
	              </div>
	              <h4 align="center">${item.getName()}</h4>
	              <div align="center">
	              	<span class="text-muted">IMDB Rating: ${item.getNotationFilm()}/10</span>
	              </div>
	            </div>
			  	</c:forEach>
	          </div>  
	        </div>
		</form>      
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
    	var isFriend = '${isFriend}'; 
    	console.log("doing on load scrip addFriend");	
    	console.log("var isFriend = " + isFriend); 
    	var btnAddFriend = document.getElementById('btnAddFriend');  	
    	var lblDOB = document.getElementById('lblDOB');
    	var lblAddress = document.getElementById('lblAddress'); 
    	var lblGender = document.getElementById('lblGender'); 
    	var btnRemoveFriend = document.getElementById('btnRemoveFriend'); 
    	var btnRequestPending = document.getElementById('btnRequestPending');
    	var divFriendMovies = document.getElementById('divFriendMovies');  
    	var btnConfirme = document.getElementById('btnConfirme');
    	var btnIgnore = document.getElementById('btnIgnore');
	    
	    if (isFriend == "Accepted")
	    {
	    	lblDOB.style.display = "block";
	    	lblAddress.style.display = "block";
	    	lblGender.style.display = "block";
	    	btnRemoveFriend.style.display = "block";
			divFriendMovies.style.display = "block";
			btnRequestPending.style.display = "none";
			btnAddFriend.style.display = "none";
			
			btnIgnore.style.display = "none";
			btnConfirme.style.display = "none";			
	    }
	    else if (isFriend == "Pending")
	    {
	    	lblDOB.style.display = "none";
	    	lblAddress.style.display = "none";
	    	lblGender.style.display = "none";
	    	btnRemoveFriend.style.display = "none";
	    	divFriendMovies.style.display = "none";
			btnAddFriend.style.display = "none"; 
			btnRequestPending.style.display = "block";
			
			btnIgnore.style.display = "none";
			btnConfirme.style.display = "none";
	    }
	    else if (isFriend == "Confirme")
	    {
	    	lblDOB.style.display = "none";
	    	lblAddress.style.display = "none";
	    	lblGender.style.display = "none";
	    	btnRemoveFriend.style.display = "none";
	    	divFriendMovies.style.display = "none";
			btnAddFriend.style.display = "none"; 
			btnRequestPending.style.display = "none";
			
			
			btnIgnore.style.display = "block";
			btnConfirme.style.display = "block";
	    }
	    else 
	    {
	    	lblDOB.style.display = "none";
	    	lblAddress.style.display = "none";
	    	lblGender.style.display = "none";
	    	btnRemoveFriend.style.display = "none";
	    	divFriendMovies.style.display = "none";
			btnAddFriend.style.display = "block"; 
			btnRequestPending.style.display = "none";
			
			btnIgnore.style.display = "none";
			btnConfirme.style.display = "none";
	    }	    
    </script>
  </body>
>>>>>>> 4b630bc76fddd1ad377aac254b29668a780cdcf4
</html>	
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.io.*,java.util.*,javax.servlet.*"%>

<%
	session.setAttribute("sessionId","");
%>

<html lang="en">
	<head>
		<meta>
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
		<link rel="icon" href="Bootstrap/docs/favicon.ico">
		
		<title>WGTR - Login Page</title>
		
		<!-- Bootstrap core CSS -->
		<link href="Bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
		
		<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
		<link href="Bootstrap/docs/assets/css/ie10-viewport-bug-workaround.css"
			rel="stylesheet">
		
		<!-- Custom styles for this template -->
		<link href="Bootstrap/docs/examples/dashboard/dashboard.css"
			rel="stylesheet">
		
		<!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
		<!--[if lt IE 9]><script src="Bootstrap/assets/js/ie8-responsive-file-warning.js"></script><![endif]-->
		<script src="Bootstrap/docs/assets/js/ie-emulation-modes-warning.js"></script>
		<script src="MyBootstrap/jquery-1.12.4.min.js" type="text/javascript"></script>
		
		<link href="MyBootstrap/css/bootstrap.min.css" rel="stylesheet"
			type="text/css" />
		<script type="text/javascript" src="MyBootstrap/js/bootstrap.min.js"></script>
		
		<%-- vvvvv Cover Styiling vvvvv --%>
		<link href="MyBootstrap/css/ie10-viewport-bug-workaround.css"
			rel="stylesheet">
		<link href="MyBootstrap/css/cover.css" rel="stylesheet">
		<script src="MyBootstrap/js/ie-emulation-modes-warning.js"></script>
	</head>
	
	<body>
		<nav class="navbar navbar-inverse navbar-fixed-top">
			<div class="container-fluid">
				<div class="navbar-header">
					<a class="navbar-brand" href="Home.jsp">Who Gets The Remote</a>
				</div>
				<div id="navbar" class="navbar-collapse collapse">
					<ul class="nav navbar-nav navbar-right">
						<li><a href="javascript:void(0);" onclick="scrollToLogin();">Login</a></li>
						<li><a href="javascript:void(0);" onclick="scrollToSignup();">Register</a></li>
					</ul>
				</div>
			</div>
		</nav>
	
		<div class="container-fluid" style="padding-top: 50px;">
			<div class="row">
				<div class="col-sm-10 col-sm-offset-3 col-md-10 col-md-offset-1 main">
					<br />
					<table>
						<tr>
							<td style="width: 40%">
								<div class="inner cover">
									<h1 class="cover-heading">Don't wait anymore!</h1>
									<p class="lead">Sign in, or Register if you don't have an
										account yet, and start enjoying movie nights with your friends.</p>
									<p class="lead">
										<button type="button" onclick="scrollToLogin()"
											class="btn btn-default">Sign in</button>
										<button type="button" onclick="scrollToSignup()"
											class="btn btn-default">Register</button>
									</p>
								</div>
							</td>
							<td style="width: 60%;">
								<div id="LoginInnerContainer" class="inner cover">
									<h1 class="cover-heading">Login</h1>
									<form role="form" action="Login"
										onsubmit="return checkLoginInputs()" method="post">
										<div class="form-group">
											<div>
												<label for="exampleInputEmail1">Email address</label>
												<input type="text" class="form-control" name="LoginUsername"
													id="LoginUsername" placeholder="Enter email">
											</div>
											<div>
												<label id="LoginUserValidator"
													style="color: red; font-weight: normal;"></label>
											</div>
										</div>
										<div class="form-group">
											<div>
												<label for="exampleInputPassword1">Password</label> <input
													type="password" class="form-control" name="LoginPassword"
													id="LoginPassword" placeholder="Password">
											</div>
											<div>
												<label id="LoginPassValidator"
													style="color: red; font-weight: normal;"></label>
											</div>
										</div>
	
										<div id="LoginMessageDiv" style="color: red;">
											${loginMessage}</div>
										<div></div>
										<button type="submit" class="btn btn-default">Login</button>
										<div></div>
										<div></div>
										<div></div>
										<div></div>
									</form>
								</div>
	
								<div id="SignupInnerContainer" class="inner cover"
									style="display: none;">
									<h1 class="cover-heading">Register</h1>
									<form role="form" action="Register" onsubmit="return CheckRegisterInputs()" method="post">
										<table style="width: 100%;">
											<tr>
												<td width="50%" style="padding-right: 5px;">
													<div class="form-group">
														<label for="FirstName">First Name</label> <input
															type="text" class="form-control" name="FirstName"
															id="FirstName" placeholder="First Name"> <label
															id="FirstNameValidator"
															style="color: red; font-weight: normal;"></label>
													</div>
												</td>
												<td style="padding-left: 5px;">
													<div class="form-group">
														<label for="LastName">Last Name</label> <input type="text"
															class="form-control" name="LastName" id="LastName"
															placeholder="Last Name"> <label
															id="LastNameValidator"
															style="color: red; font-weight: normal;"></label>
													</div>
												</td>
											</tr>
											<tr>
												<td colspan="2">
													<div class="form-group">
														<label for="email">Email address</label> <input
															type="email" class="form-control" name="email" id="email"
															placeholder="Email"> <label id="EmailValidator"
															style="color: red; font-weight: normal;"></label>
													</div>
												</td>
											</tr>
											<tr>
												<td>
													<div class="form-group" style="padding-right: 5px;">
														<label for="UserDOB">Date of Birth</label> 
														<input type="text" class="form-control" name="UserDOB" id="UserDOB" placeholder="yyyy-mm-dd"> 
														<label id="DOBValidator" style="color: red; font-weight: normal;"></label>
													</div>
												</td>
												<td style="padding-left: 5px;">
													<div class="form-group">
														<label for="Sexe">Gender</label> 
														<select name="Sexe" id="Sexe" class="form-control">
															<option value="M">Male</option>
															<option value="F">Female</option>
														</select> 
														<label id="SexeValidator" style="color: red; font-weight: normal;"></label>
													</div>
												</td>
											</tr>
											<tr>
												<td>
													<div class="form-group">
														<label for="Username">Username</label> 
														<input type="text" class="form-control" name="Username" id="Username" placeholder="Username"> 
														<label id="UsernameValidator" style="color: red; font-weight: normal;"></label>
													</div>
												</td>
											</tr>
											<tr>
												<td>
													<div class="form-group" style="padding-right: 5px;">
														<label for="password">Password</label> 
														<input type="password" class="form-control" name="password" id="password" placeholder="Password"> 
														<label id="PassValidator" style="color: red; font-weight: normal;"></label>
													</div>
												</td>
												<td>
													<div class="form-group" style="padding-left: 5px;">
														<label for="password">Confirm Password</label> 
														<input type="password" class="form-control" name="confirmPass" id="confirmPass" placeholder="confirm Pass"> 
														<label id="ConfPassValidator" style="color: red; font-weight: normal;"></label>
													</div>
												</td>
											</tr>
											<tr>
												<td colspan="2">
													<div class="form-group">
														<label for="Address">Address</label> 
														<input type="text" class="form-control" name="Address" id="Address" placeholder="Address"> 
														<label id="AddressValidator" style="color: red; font-weight: normal;"></label>
													</div>
												</td>
											</tr>
										</table>
	
										<div style="color: red;">${RegisterMessage}</div>
										<div style="color: red;">
											<label id="RegistrationValidator" style="color: red; font-weight: normal;"></label>
										</div>
										<button type="submit" class="btn btn-default">Submit</button>
									</form>
								</div>
							</td>
						</tr>
					</table>
					<div class="mastfoot" style="width: 70%; background-color: #333;">
						<div class="inner">
							<p>
								Cover template for <a href="http://getbootstrap.com">Bootstrap</a>,
								by <a href="https://twitter.com/mdo">@mdo</a>.
							</p>
						</div>
					</div>
				</div>
			</div>
		</div>
	
		<!-- Bootstrap core JavaScript
	    ================================================== -->
		<!-- Placed at the end of the document so the pages load faster -->
		<script
			src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
		<script>window.jQuery || document.write('<script src="Bootstrap/assets/js/vendor/jquery.min.js"><\/script>')</script>
		<script src="Bootstrap/dist/js/bootstrap.min.js"></script>
		<!-- Just to make our placeholder images work. Don't actually copy the next line! -->
		<script src="Bootstrap/docs/assets/js/vendor/holder.min.js"></script>
		<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
		<script src="Bootstrap/docs/assets/js/ie10-viewport-bug-workaround.js"></script>
	
		<script>window.jQuery || document.write('<script src="MyBootstrap/js/vendor/jquery.min.js"><\/script>')</script>
		<script src="MyBootstrap/js/bootstrap.min.js"></script>
		<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
		<script src="MyBootstrap/js/ie10-viewport-bug-workaround.js"></script>
	
	
		<script type="text/javascript">
	    	document.getElementById("LoginUsername").focus();
	    </script>
	
		<script type="text/javascript">
		    function scrollToLogin()
		    {     	
		    	document.getElementById("LoginUsername").focus();
		    	var toShowDiv = document.getElementById('LoginInnerContainer');
			    var toHideDiv = document.getElementById('SignupInnerContainer');
			    //changing style
			    toShowDiv.style.display = 'block';
			    toHideDiv.style.display = 'none';
				console.log("in scroll login");
		    }
		    
		    function scrollToSignup()
		    {
		    	document.getElementById("FirstName").focus();
			    var toShowDiv = document.getElementById('SignupInnerContainer');
			    var toHideDiv = document.getElementById('LoginInnerContainer');
			    //changing style
			    toShowDiv.style.display = 'block';
			    toHideDiv.style.display = 'none';
		    	//alert('in scroll signup');
		    	console.log("in scroll signup");
		    }
		    
		    function CheckRegisterInputs()
		    {      
		        console.log("checking inputs register ");
		      	var RegistrationValidator = document.getElementById('RegistrationValidator');
		      	
		      	var FirstNameValidator = document.getElementById('FirstNameValidator'); 
		      	var LastNameValidator = document.getElementById('LastNameValidator');
		      	var EmailValidator = document.getElementById('EmailValidator');
		      	var DOBValidator = document.getElementById('DOBValidator');
		      	var SexeValidator = document.getElementById('SexeValidator'); 
		      	var UsernameValidator = document.getElementById('UsernameValidator');
		      	var PassValidator = document.getElementById('PassValidator');
		      	var ConfPassValidator = document.getElementById('ConfPassValidator');
		      	var AddressValidator = document.getElementById('AddressValidator');
		      	console.log("got validators");
		    	var FirstName = document.getElementById('FirstName').value;
		    	var LastName = document.getElementById('LastName').value;
		    	var email = document.getElementById('email').value;
		    	console.log("got values before date");
		    	var dateOfBirth = document.getElementById('UserDOB').value;
		    	console.log("got values after date");
		    	var Sexe = document.getElementById('Sexe').value;
		    	var Username = document.getElementById('Username').value;
		    	var password = document.getElementById('password').value;
		    	var confirmPass = document.getElementById('confirmPass').value;
		    	var Address = document.getElementById('Address').value;
		    	
		    	console.log("got values");
		    	RegistrationValidator.innerHTML  ="";
		    	FirstNameValidator.innerHTML  ="";
		    	LastNameValidator.innerHTML  ="";
		    	EmailValidator.innerHTML  ="";
		    	DOBValidator.innerHTML  ="";
		    	SexeValidator.innerHTML  ="";
		    	UsernameValidator.innerHTML  ="";
		    	PassValidator.innerHTML  ="";
		    	ConfPassValidator.innerHTML  ="";
		    	AddressValidator.innerHTML  ="";
		    	
		    	var valid = true;
		    	console.log("starting validating ....." );
		    	if (FirstName.trim()=="")
		    	{
		    		FirstNameValidator.innerHTML  ="*";
		    		valid = false;
		    		console.log("fn valid= false");
		    	}
		    	if (LastName.trim()=="")
		    	{
		    		LastNameValidator.innerHTML  ="*";
		    		valid = false;
		    		console.log("ln valid= false");
		    	}
		    	if (email.trim()=="")
		    	{
		    		EmailValidator.innerHTML  ="*";
		    		valid = false;
		    		console.log("email valid= false");
		    	}
		    	if (dateOfBirth.trim()=="")
		    	{
		    		DOBValidator.innerHTML  ="*";
		    		valid = false;
		    		console.log("dob valid= false");
		    	}
		    	if (Sexe.trim()=="")
		    	{
		    		SexeValidator.innerHTML  ="*";
		    		valid = false;
		    		console.log("sexe valid= false");
		    	}
		    	if (Username.trim()=="")
		    	{
		    		UsernameValidator.innerHTML  ="*";
		    		valid = false;
		    		console.log("username valid= false");
		    	}
		    	if (password.trim()=="")
		    	{
		    		PassValidator.innerHTML  ="*";
		    		valid = false;
		    		console.log("pass valid= false");
		    	}
		    	if (confirmPass.trim()=="")
		    	{
		    		ConfPassValidator.innerHTML  ="*";
		    		valid = false;
		    		console.log("conf pass valid= false");
		    	}
		    	if (Address.trim()=="")
		    	{
		    		AddressValidator.innerHTML  ="*";
		    		valid = false;
		    		console.log("address valid= false");
		    	}
		    	
		    	var resultDOB = validateDOB();
		    	console.log("validateDOB=" + resultDOB);
				if (resultDOB == false)
				{
					valid = false;
				}
		    	console.log("done validating in main; Final Valid = " + valid);
		    	
		    	if (valid == false)
		    	{
		    		console.log("final valid =" + valid);
		    		return false;    		
		    	}    	
		    	else	
		    	{
		    	console.log("final valid =" + valid);
		    		return true;
		    	}
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
		    
		    function checkLoginInputs()
		    {
		    	console.log("checking login inputs");
		    
			    LoginPassValidator
			    LoginUserValidator
			    
			    var LoginPassValidator = document.getElementById('LoginPassValidator');
		      	var LoginUserValidator = document.getElementById('LoginUserValidator');
		      	var LoginMessageDiv = document.getElementById('LoginMessageDiv');
		      	LoginMessageDiv.innerHTML = "";
		      	
		      	LoginPassValidator.innerHTML = "";
		    	LoginUserValidator.innerHTML = "";
		    	
		      	
		    	var LoginPass = document.getElementById('LoginPassword').value;
			    var LoginUser = document.getElementById('LoginUsername').value;
			    
			    var valid = true;
			    
			    if (LoginPass.trim()=="")
		    	{
		    		LoginPassValidator.innerHTML  ="* Mandatory Field";
		    		valid = false;
		    		console.log("LoginPass valid= false");
		    	}
		    	if (LoginUser.trim()=="")
		    	{
		    		LoginUserValidator.innerHTML  ="* Mandatory Field";
		    		valid = false;
		    		console.log("LoginUser valid= false");
		    	}
			    
			    if (valid == false)
		    	{
		    		console.log("final valid =" + valid);
		    		console.log("login inputs are not valid");
		    		return false;    		
		    	}    	
		    	else	
		    	{
		    		console.log("login inputs are valid");
		    		return true;
		    	}
		    }
	    </script>
	</body>
</html>




<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create User</title>
</head>
<body>
<form>
	<table>
		<tr>		
			<td>First name:</td>
			<td> <input type="text"  name="firstName"></td>
		</tr>
		<tr>
			<td>Last name:</td>
			<td><input type="text"  name="lastName"></td>
		</tr>	
		<tr>
			<td>Email:</td>
			<td><input type="text"  name="email"></td>
		</tr>
		<tr>
			<td></td>
			<td><button type="submit" action="UpdateUser"></button></td>
		</tr>
	</table> 
</form>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>My Film Directory</title>
</head>
<body>
	<h1>This is my Film Directory</h1>	
	<div>
		<a href="http://localhost:8080/WhoGetsTheRemote/FilmServlet">liste des films</a>
	</div>
</body>
</html>

<script>
function goToFilmServlet()
{
	location.href = "/src/servlets/FilmServlet.java";
}
</script> 

package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ejb.FilmEjb;
import models.Film;



/**
 * Servlet implementation class FilmServlet
 */
@WebServlet("/FilmServlet")
public class FilmServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB FilmEjb bean;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FilmServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		//out.print("<html><body>");
		out.print("<table><tr>"
				+ "<td>"
				+"<strong>Id</strong>"
				+ "</td>"
				+ "<td>"
				+"<strong>Name</strong>"
				+ "</td>"
				+ "<td>"
				+"<strong>Description</strong>"
				+ "</td>"
				+ "<td>"
				+"<strong>Director</strong>"
				+ "</td>"
				+ "<td>"
				+"<strong>Date</strong>"
				+ "</td>"
				+ "<td>"
				+"<strong>IMDB Rating</strong>"
				+ "</td>"
				+ "</tr>");
		
		List<Film> myList = bean.getAllFilm();
		for (int i=0; i<myList.size(); i++)
		{
			out.print("<tr>"
					+ "<td>" + myList.get(i).getId() + "</td>"
					+ "<td>" + myList.get(i).getName() + "</td>"
					+ "<td>" + myList.get(i).getDescription() + "</td>"
					+ "<td>" + myList.get(i).getDirector() + "</td>"
					+ "<td>" + myList.get(i).getDate() + "</td>"
					+ "<td>" + myList.get(i).getIdmbRating() + "</td>"
					+ "</tr>");
		}
		out.print("</table>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

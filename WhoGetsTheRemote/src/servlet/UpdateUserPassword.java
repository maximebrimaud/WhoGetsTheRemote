package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ejb.UserEjb;
import models.Users;

/**
 * Servlet implementation class UpdateUserPassword
 */
@WebServlet("/UpdateUserPassword")
public class UpdateUserPassword extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB UserEjb bean;
	
	@PersistenceContext
	 private EntityManager em;
	
    /**
     * Default constructor. 
     */
    public UpdateUserPassword() {
        // TODO Auto-generated constructor stub
    	super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		response.getWriter().append("Served at: ").append(request.getContextPath());
		Users user = em.find(Users.class, 1);
		em.getTransaction().begin();
		user.setPassword(request.getParameter("password"));
		em.getTransaction().commit();	
	}

}

package ejb;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import models.User;

/**
 * Session Bean implementation class UserEjb
 */
@Stateless
@LocalBean
public class UserEjb implements UserEjbLocal {
	
	@PersistenceContext
	 private EntityManager em;
	
	//@Override
	public User createUser(String firstName, String lastName, String username, String password, String email, String creationDate)
	{
		User utilisateur = new User();
		
		int id = utilisateur.hashCode();
		
		utilisateur.setId(id);
		utilisateur.setFirstName(firstName);
		utilisateur.setLastName(lastName);
		utilisateur.setUsername(username);
		utilisateur.setPassword(password);
		utilisateur.setCreationDate(creationDate);
		utilisateur.setEmail(email);

		em.persist(utilisateur);
		
		return utilisateur;
	}

	//@Override
	public void edit(User utilisateur) {
		// TODO Auto-generated method stub
		
	}

	//@Override
	public void remove(User utilisateur) {
		// TODO Auto-generated method stub
		
	}

	//@Override
	public List<User> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}

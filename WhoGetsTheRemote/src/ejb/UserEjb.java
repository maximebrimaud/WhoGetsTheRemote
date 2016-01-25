package ejb;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import models.Users;

/**
 * Session Bean implementation class UserEjb
 */
@Stateless
@LocalBean
public class UserEjb implements UserEjbLocal {
	
	@PersistenceContext
	 private EntityManager em;
	
	@Override
	public Users createUser(String firstName, String lastName, String username, String password, String email,
			String creationDate)
	{
		Users utilisateur = new Users();
		
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

	@Override
	public void edit(Users utilisateur) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(Users utilisateur) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Users> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}

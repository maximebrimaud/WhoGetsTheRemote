package ejb;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

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
		utilisateur.etCreationDate(creationDate);
		utilisateur.setEmail(email);

		em.persist(utilisateur);
		
		return utilisateur;
	}

}

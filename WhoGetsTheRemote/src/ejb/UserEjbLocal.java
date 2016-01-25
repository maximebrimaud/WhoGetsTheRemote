package ejb;

import javax.ejb.Local;
import models.Users;

@Local
public interface UserEjbLocal 
{
	Users createUser(String firstName, String lastName, String username, String password, String email,
			String creationDate);

    void edit(Users utilisateur);

    void remove(Users utilisateur);

    List<Users> findAll();
}

package ejb;

import java.util.List;

import javax.ejb.Local;
import models.User;

@Local
public interface UserEjbLocal 
{
	User createUser(String firstName, String lastName, String username, String password, String email,
			String creationDate);

    void edit(User utilisateur);

    void remove(User utilisateur);

    List<User> findAll();
}

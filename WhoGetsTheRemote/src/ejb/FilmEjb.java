package ejb;

import java.util.List;

import javax.ejb.Asynchronous;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import models.Film;

/**
 * Session Bean implementation class FilmEjb
 */
@Stateless
@LocalBean
@Asynchronous
public class FilmEjb implements FilmEjbLocal {

    /**
     * Default constructor. 
     */
    public FilmEjb() {
        // TODO Auto-generated constructor stub
    }
    
    @Override
    public void addFilm(Film film)
    {
    	
    }
    
    @Override
    public void editFilm(Film film)
    {
    	
    }
    @Override
    public void deleteFilm(int filmId)
    {
    	
    }
    @Override
    public Film getFilm(int filmId)
    {
		return null;    	
    }
    @Override
    public List<Film> getAllFilm()
    {
    	List <Film> myList;
    	for (int i=0; i<3; i++)
    	{
    		
    	}
		return null;    	
    }
}

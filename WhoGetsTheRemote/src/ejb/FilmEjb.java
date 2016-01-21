package ejb;

import java.io.Console;
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

	
	List <Film> myList = null;
    /**
     * Default constructor. 
     */
    public FilmEjb() {
        // TODO Auto-generated constructor stub
    	System.out.println("i am in film ejb constructor");
    	for (int i=0; i<4; i++)
    	{
    		Film f= new Film (i, "Film"+i, "this is the Description" + i , "Director name"+i , "16/02/2001", 3);
    		myList.add(f);    		
    	}
    }
    
    @Override
    public void addFilm(Film film)
    {
    	myList.add(film);
    }
    
    @Override
    public void editFilm(Film film)
    {
    	//myList.
    }
    @Override
    public void deleteFilm(int filmId)
    {
    	myList.remove(filmId);
    }
    @Override
    public Film getFilm(int filmId)
    {
    	myList.get(filmId);
		return null;		
    }
    @Override
    public List<Film> getAllFilm()
    {    	    
		return myList;    	
    }
}

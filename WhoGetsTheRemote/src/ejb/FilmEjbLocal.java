package ejb;

import java.util.List;

import javax.ejb.Local;

import models.Film;

@Local
public interface FilmEjbLocal {

	void addFilm(Film film);

	void editFilm(Film film);

	void deleteFilm(int filmId);

	Film getFilm(int filmId);

	List<Film> getAllFilm();

}

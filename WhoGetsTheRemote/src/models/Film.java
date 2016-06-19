package models;

import java.io.Serializable;
import java.util.ArrayList;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table
@SessionScoped
@Named
public class Film implements Serializable
{	
   /* Title TitleValidator
     * Director DirectorValidator
     * Language LanguageValidator
     * Cast CastValidator
     * ReleaseDate ReleaseDateValidator
     * Categorie CategorieValidator
     * Duration DurationValidator
     * PG PGValidator
     * Description DescriptionValidator
     * Rating RatingValidator
     * PictureBrowse PicValidator
     * Trailer TrailerValidator
     * Movie MovieValidator    
	*/
	@Id
	@SequenceGenerator(name="FilmId", sequenceName="FilmIdSequence", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator ="FilmId")
	@Column
	//OK
	private int id;
	@Column
	//OK
	private String name;
	@Column
	//OK
	private String description;
	@Column
	//OK
	private String dateReleased;
	@Column
	//OK
	private int notationFilm;
	@Column
	private String trailerFilm;
	@Column
	private String filmLink;
	@Column
	//OK
	private int categorieId;
	@Column
	//OK
	private String imageFilm;
	@Column
	//NO
	private String creationDate;
	@Column
	//NO
	private String modificationDate;
	@Column
	//OK
	private String duration;
	@Column
	//PG
	private int pgLevel;
	@Column
	//OK
	private String director;
	@Column
	//OK
	private String cast;
	@Column
	//OK
	private String language;
	//@Column
	//private int viewers;
	
	//private ArrayList<Film> listeHits = new ArrayList();
	//********* Constructor
	public Film() 
	{
		super();
	}
	
	public Film(int id, String name, String imageFilm) 
	{
		super();
		this.id = id;
		this.name = name;		
		this.imageFilm = imageFilm;		
	}
	
	
	public Film(int id, String name, String imageFilm, int rating) 
	{
		super();
		this.id = id;
		this.name = name;		
		this.imageFilm = imageFilm;		
		this.notationFilm = rating;
	}

	public Film(int id, String name, String description, String dateReleased, int notationFilm, String trailerFilm, String filmLink, int categorieId, String imageFilm, String creationDate, String modificationDate,String duration,int pgLevel,String director,String cast,String language) 
	{
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.dateReleased = dateReleased;
		this.notationFilm = notationFilm;
		this.trailerFilm = trailerFilm;
		this.filmLink = filmLink;
		this.categorieId = categorieId;
		this.imageFilm = imageFilm;
		this.creationDate = creationDate;
		this.modificationDate = modificationDate;
		this.duration = duration;
		this.pgLevel = pgLevel;
		this.director = director;
		this.cast = cast;
		this.language = language;
	}
	
	public Film(int id, String name, String description, String dateReleased, int notationFilm, String trailerFilm, String filmLink,  String imageFilm, String creationDate, String modificationDate) 
	{
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.dateReleased = dateReleased;
		this.notationFilm = notationFilm;
		this.trailerFilm = trailerFilm;
		this.filmLink = filmLink;
		this.imageFilm = imageFilm;
		this.creationDate = creationDate;
		this.modificationDate = modificationDate;

	}

 /* public Film(int id, String name, String description, String dateeleased, int notationFilm, String trailerFilm, String filmLink, int categorieId, String imageFilm, String creationDate, String modificationDate, String duration,int pgLevel,String director,String cast,String language, int viewers) 
	{
		super();
		this.id = id;		
		this.name = name;
		this.description = description;
		this.dateReleased = dateeleased;
		this.notationFilm = notationFilm;
		this.trailerFilm = trailerFilm;
		this.filmLink = filmLink;
		this.categorieId = categorieId;
		this.imageFilm = imageFilm;
		this.creationDate = creationDate;
		this.modificationDate = modificationDate;
		this.viewers = viewers;
		this.duration = duration;
		this.pgLevel = pgLevel;
		this.director = director;
		this.cast = cast;
		this.language = language;
	}*/

	
	/*
	 *ViewModeTitle
	 * ViewModeDirector
	 * ViewModeLanguage
	 * ViewModeCast
	 * ViewModeReleaseDate
	 * ViewModeCategorie
	 * ViewModeDuration
	 * ViewModePG
	 * ViewModeDescription
	 * ViewModeRating
	 * divChangePic /// in edit mode only
	 * ViewModeTrailer
	 * ViewModeMovie
	 * 
	 * btnEditFilm /// in view mode only
	 * 
	 * btnCancelEdit /// in edit mode only
	 * btnUpdateFilm /// in edit mode only
	 */
	
	
	/*
	 *EditModeTitle
	 * EditModeDirector
	 * EditModeLanguage
	 * EditModeCast
	 * EditModeReleaseDate
	 * EditModeCategorie
	 * EditModeDuration
	 * EditModePG
	 * EditModeDescription
	 * EditModeRating
	 * divChangePic /// in edit mode only
	 * EditModeTrailer
	 * EditModeMovie
	 * */
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDateReleased() {
		return dateReleased;
	}
	public void setDateReleased(String dateReleased) {
		this.dateReleased = dateReleased;
	}
	public int getNotationFilm() {
		return notationFilm;
	}
	public void setNotationFilm(int notationFilm) {
		this.notationFilm = notationFilm;
	}
	public String getTrailerFilm() {
		return trailerFilm;
	}
	public void setTrailerFilm(String trailerFilm) {
		this.trailerFilm = trailerFilm;
	}
	public String getFilmLink() {
		return filmLink;
	}
	public void setFilmLink(String filmLink) {
		this.filmLink = filmLink;
	}
	public String getImageFilm() {
		return imageFilm;
	}
	public void setImageFilm(String imageFilm) {
		this.imageFilm = imageFilm;
	}
	public String getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
	public String getModificationDate() {
		return modificationDate;
	}
	public void setModificationDate(String modificationDate) {
		this.modificationDate = modificationDate;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public int getPG() {
		return pgLevel;
	}
	public void setPG(int pgLevel) {
		this.pgLevel = pgLevel;
	}
	public String getDirector() {
		return director;
	}
	public void setDirector(String director) {
		this.director = director;
	}
	public String getCast() {
		return cast;
	}
	public void setCast(String cast) {
		this.cast = cast;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public int getCategorieId() {
		return categorieId;
	}
	public void setCategorieId(int categorieId) {
		this.categorieId = categorieId;
	}
}

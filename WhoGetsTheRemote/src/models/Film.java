package models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Film implements Serializable
{
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column
	private int id;
	@Column
	private String name;
	@Column
	private String description;
	@Column
	private String director;
	@Column
	private String date;
	@Column
	private int idmbRating;
	
	//********* Constructor
	public Film() {}
	
	public Film(int id, String name, String description, String director, String date, int idmbRating) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.director = director;
		this.date = date;
		this.idmbRating = idmbRating;
	}
	
	//********* Getters and Setters
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
	public String getDirector() {
		return director;
	}
	public void setDirector(String director) {
		this.director = director;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getIdmbRating() {
		return idmbRating;
	}
	public void setIdmbRating(int idmbRating) {
		this.idmbRating = idmbRating;
	}
}

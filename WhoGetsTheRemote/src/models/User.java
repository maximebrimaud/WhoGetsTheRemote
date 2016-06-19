package models;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.sql.DataSource;

@Entity
@Table
public class User
{
	
	@Id
	@SequenceGenerator(name="UserId", sequenceName="UserIdSequence", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator ="UserId")

	@Resource private DataSource myDataSource;
	
	@Column(name="id_user")
	private int id;
	@Column
	private String firstName;
	@Column
	private String lastName;
	@Column
	private String username;
	@Column
	private String password;
	@Column
	private String email;
	@Column
	private String dob;
	@Column
	private String sexe;
	@Column
	private String address;
	@Column
	private String image;
	@Column
	private String modificationDate;
	@Column
	private String creationDate;
	@Column
	private String userMessage;
	
	private List<Friend> friendsInCommon= new ArrayList<Friend>();
	
	//********* Constructor
	public User() {
		super();
	}
		
	public User(int id, String firstName, String lastName, String username, String password, String email, String dob,
			String sexe, String address, String image, String modificationDate, String creationDate) throws SQLException {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		this.email = email;
		this.dob = dob;
		this.sexe = sexe;
		this.address = address;
		this.image = image;
		this.modificationDate = modificationDate;
		this.creationDate = creationDate;
	}
	
	public User(int id, String firstName, String lastName, String username, String password, String email, String dob,
			String sexe, String address, String image, String modificationDate, String creationDate,List<Friend> friendsInComm) throws SQLException {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		this.email = email;
		this.dob = dob;
		this.sexe = sexe;
		this.address = address;
		this.image = image;
		this.modificationDate = modificationDate;
		this.creationDate = creationDate;
		this.friendsInCommon = friendsInComm;
	}
	
	//********* Getters and Setters
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getFullName() {
		return firstName + " " + lastName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getSexe() {
		return sexe;
	}
	public void setSexe(String sexe) {
		this.sexe = sexe;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getModificationDate() {
		return modificationDate;
	}
	public void setModificationDate(String modificationDate) {
		this.modificationDate = modificationDate;
	}
	public List<Friend> getFriendsInCommon() {
		return friendsInCommon;
	}
	public void setFriendsInCommon(List<Friend> friendsInCommon) {
		this.friendsInCommon = friendsInCommon;
	}
	public String getUserMessage() {
		return userMessage;
	}
	public void setUserMessage(String userMessage) {
		this.userMessage = userMessage;
	}

	public User(int id, String firstName, String lastName, String username, String password, String email, String dob,
			String sexe, String address, String image, String modificationDate, String creationDate,
			String userMessage) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		this.email = email;
		this.dob = dob;
		this.sexe = sexe;
		this.address = address;
		this.image = image;
		this.modificationDate = modificationDate;
		this.creationDate = creationDate;
		this.userMessage = userMessage;
	}
}
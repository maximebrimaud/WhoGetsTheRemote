package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;

public class Friend {
	
	@Column(name="id_user")
	private int id;
	@Column
	private String fullName;
	@Column
	private User userr;
	@Column
	private int friendsCommonNumber;
	@Column
	private List<User> listFriendsCommon = new ArrayList<User>();
	@Column
	private List<Event> listEventsCommon = new ArrayList<Event>();
	@Column
	private List<Film> listMovies = new ArrayList<Film>();
	
	public Friend() {
		super();
	}
	
	public Friend(int idd, User userr, String FullName, List<User> inCommon,int nbrCommon) {
		super();
		this.id = idd;
		this.fullName = FullName;
		this.userr = userr;
		this.listFriendsCommon = inCommon;
		this.friendsCommonNumber = nbrCommon;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int idd) {
		this.id = idd;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String FullName) {
		this.fullName = FullName;
	}
	public User getUserr() {
		return userr;
	}
	public void setUserr(User userrr) {
		this.userr = userrr;
	}
	public int getfriendsCommonNumber() {
		return friendsCommonNumber;
	}
	public void setfriendsCommonNumber(int common) {
		this.friendsCommonNumber = common;
	}
	public List<User> getListFriendsCommon() {
		return listFriendsCommon;
	}
	public void setListFriendsCommon(List<User> frdsCommon) {
		this.listFriendsCommon = frdsCommon;
	}
	public List<Film> getListMovies() {
		return listMovies;
	}
	public void setListMovies(List<Film> movieList) {
		this.listMovies = movieList;
	}
	public List<Event> getListEventsCommon() {
		return listEventsCommon;
	}
	public void setListEventsCommon(List<Event> eventList) {
		this.listEventsCommon = eventList;
	}
	
	
}

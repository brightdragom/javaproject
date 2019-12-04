package model;

public class User {
	private String id;
	private String pw;
	private String nickname;
	private String dateOfBirth;
	private String name;
	private String email;
	private boolean manager;
	public User(String id, String pw, String nickname, String dateOfBirth, String name, String email, String manager) {
		this.id = id;
		this.pw = pw;
		this.nickname = nickname;
		this.dateOfBirth = dateOfBirth;
		this.name = name;
		this.email = email;
		this.manager = manager.equals("O") ? true : false;
	}
	public User() {
		this.id = null;
		this.pw = null;
		this.nickname = null;
		this.dateOfBirth = null;
		this.name = null;
		this.email = null;
		this.manager = false;	
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public boolean getManager() {
		return manager;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}

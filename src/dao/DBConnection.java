package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.User;

public class DBConnection {
	private String DB_name = "randomchat";
	private Connection conn = null;
	private Statement stmt = null;
	private ResultSet rs = null;
	private String sql;
	private Statement st = null;
	private User user;

	public DBConnection() {
		String DB_URL = "jdbc:mysql://localhost:3306/" + DB_name + "?serverTimezone=UTC";
		String DB_USER = "root";
		String DB_PW = "1234";

		System.out.print("User Table 접속 : ");
		try {
			// Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PW);
			if (conn != null) {
				System.out.println("성공");
			} else {
				System.out.println("false");
			}

		} catch (SQLException e) {
			System.out.println("SQLException > " + e.getMessage());
		}
		printValue();
	}

	// 테이블 내용 출력
	public void printValue() {
		sql = "select * from user";
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				String id = rs.getString("userId");
				String pw = rs.getString("userpw");
				System.out.println("id > " + id + "\npw> " + pw);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	// join_membership_sql
	public void join_membership_method(String id, char[] password, String userName, 
			String nickName, String dateOfBirth, String email) {

		sql = "insert into user values (?,?,?,?,?,?,?)";
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(sql);
			st.setString(1, id);
			st.setString(2, new String(password));
			st.setString(3, nickName);
			st.setString(4, dateOfBirth);
			st.setString(5, userName);
			st.setString(6, email);
			st.setString(7, "X");
			st.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} catch (NullPointerException e) {
			System.out.println(e.getMessage());
		}

	}

	// login_sql
	public User login(String id, char[] pw_ary) {
		boolean login_success = false;
		String pw = new String(pw_ary);
		sql = "select * from user";
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				String id_db = rs.getString("userid");
				String pw_db = rs.getString("userpw");
				if (id.length() == 0 || pw.length() == 0) {
					System.out.println("ID || PW를 입력하세요");
					break;
				} else if (id.equals(id_db)) {
					if (pw.equals(pw_db)) {
						login_success = true;
						user = new User(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
								rs.getString(5), rs.getString(6), rs.getString(7));
						break;
					} else {
						System.out.println("Password가 틀림");
						break;
					}
				}
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		if (login_success)
			return user;
		else
			return null;
	}
	public boolean overlabId(String id) {
		sql = "select * from user";
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				String id_db = rs.getString("userid");
				if (id.equals(id_db)) {
					return false;//같은게 존재하면 false
				}
			}
		}catch(SQLException e) {
			return true;
		}
		return true;
	}
	
	public String findId(String email, String dateOfBrith) {
		sql = "select * from user";
		String id = null;
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				String id_db = rs.getString("userid");
				String e = rs.getString("email");
				String d = rs.getString("dateOfBirth");
				System.out.println("Log :: input email : "+email+"\tintput date : "+dateOfBrith+"\tget_email"+e+"\tget_date"+d);
				if (email.equals(e) && d.equals(dateOfBrith)) {
					id = id_db;
					break;
				}
			}
		}catch(SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}catch(NullPointerException e) {
			return null;
		}
		return id;
	}
	public String findPw(String email, String dateOfBrith) {
		sql = "select * from user";
		String pw = null;
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				String pw_db = rs.getString("userpw");
				String e = rs.getString("email");
				String d = rs.getString("dateOfBirth");
				System.out.println("Log :: input email : "+email+"\tintput date : "+dateOfBrith+"\tget_email"+e+"\tget_date"+d);
				if (email.equals(e) && d.equals(dateOfBrith)) {
					pw = pw_db;
					break;
				}
			}
		}catch(SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}catch(NullPointerException e) {
			return null;
		}
		return pw;
	}
}

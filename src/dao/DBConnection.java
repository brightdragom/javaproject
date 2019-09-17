package dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.User;

public class DBConnection {
	private String DB_name = "bpdbtest1";
	private int num;
	private Connection conn = null;
	private Statement stmt = null;
	private ResultSet rs = null;
	private String sql;
	private Statement st = null;
	private User user;
	public DBConnection() {
		connection();
	}
	//MySQL connection
	public void connection() {
		String DB_URL = "jdbc:mysql://localhost:3306/"+DB_name+"?serverTimezone=UTC";
		String DB_USER = "root";
		String DB_PW = "1234";

		System.out.print("User Table 접속 : ");
		try {
			//Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PW);
			if (conn != null) {
				System.out.println("성공");
			} else {
				System.out.println("false");
			}
		}/*catch(ClassNotFoundException e) {
			System.out.println("ClassNotFoundException > "+e.getMessage());
		}*/catch (SQLException e) {
			System.out.println("SQLException > " + e.getMessage());
		}
		
		sql = "select * from login_imformation";
		String n = "";
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()) {
				n = rs.getString("m_No");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		num = Integer.parseInt(n);
	}
	/* 테이블 내용 출력
	public void printValue() {
		sql = "select * from user";
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()) {
				String id = rs.getString("userId");
				String pw = rs.getString("userPassword");
				System.out.println("id > "+ id +"\npw> "+pw);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	*/
	
	//join_membership_sql
	public void join_membership_method(String id, char[] password, String name, String age, String phone_number, String address) {
		num++;
		sql = "insert into login_imformation values (?,?,?,?,?,?,?)";
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(sql);
			st.setString(1,num+"");
			st.setString(2,name);
			st.setString(3,age);
			st.setString(4,id);
			st.setString(5,new String(password));
			st.setString(6,address);
			st.setString(7,phone_number);
			
			st.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		sql = "insert into user_imformation values (?,?,?,?)";
		try {
			st = conn.prepareStatement(sql);
			st.setString(1,num+"");
			st.setString(2,name);
			st.setString(3,phone_number);
			st.setString(4,"");
			
			st.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	//login_sql
	public User login(String id, char[] pw_ary) {
		boolean login_success = false;
		String pw = new String(pw_ary);
		sql = "select * from login_imformation";
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()) {
				String id_db = rs.getString("m_ID");
				String pw_db = rs.getString("m_PW");
				if(id.length() == 0 || pw.length() == 0) {
					System.out.println("ID || PW를 입력하세요");
					break;
				}else if(id.equals(id_db)){
					if(pw.equals(pw_db)) {
						login_success = true;
						user = new User(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7));
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
		
		if(login_success)
			return user;
		else
			return null;
	}
	
	
	//getUserFriendList
	public String getUserFriend(int userNo) {
		sql = "select * from user_imformation";
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()) {
				int n = Integer.parseInt(rs.getString("userNo"));
				String friendList = rs.getString("userFriend");
				if(n == userNo) {
					return friendList;
				}
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return "";
	}
	
	//원하는 값 가져오기
	public String getValue(String no) {
		String str = null;
		sql = "select * from user_imformation where userNo = "+str;
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			str = rs.getString("userFriend");
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		
		System.out.println(str);
		return str;
	}
	
	//plus_friendList
	public void plusFriend(String userID) {
		String no = userID.replaceAll("[^0-9]", "");
		
		//UPDATE 테이블명 SET 필드명 = CONCAT(필드명,'영희') WHERE 조건들
		String str1 = getValue(no);	//추가 당한 놈
		String str2 = getValue(user.getUserNo()+"");	//추가 하는 놈
		sql = "UPDATE user_imformation SET userFriend = "+str1+","+userID+" WHERE userNo="+no;
		String sql2 = "UPDATE user_imformation SET userFriend = "+str2+","+userID+" WHERE userNo="+user.getUserNo();
		System.out.println(sql);
		System.out.println(sql2);
		try {
			st = conn.createStatement();
			st.executeUpdate(sql);
			st = conn.createStatement();
			st.executeUpdate(sql2);
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}
}

package controller;

import dao.DBConnection;
import view.LoginView;
import view.MainView;

public class Main {
	public static void main(String[] args) {
		DBConnection db = new DBConnection();
		new LoginView(db);
	}
}

package view;

import java.awt.Container;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import dao.DBConnection;
import model.User;

public class LoginView extends JFrame implements ActionListener {
	BufferedImage img = null;
	JTextField loginTextField;
	JPasswordField passwordField;
	JButton loginButton;
	JButton join_MembershipButton;
	DBConnection db;

	public LoginView(DBConnection db) {
		
		this.db = db;
		setTitle("LoginTest");
		setSize(1600, 938);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		Container contentPane = getContentPane();
		try {
			img = ImageIO.read(new File("img/login_view.jpg"));
		} catch (IOException e) {
			System.out.println("이미지 불러오기 실패");
			System.exit(0);
		}
		MyPanel background = new MyPanel();
		background.setBounds(0, 0, 1600, 900);

		// GridLayout grid = new GridLayout(3,2);
		// grid.setVgap(5);
		// contentPane.setLayout(grid);
		contentPane.setLayout(null);

		// JLabel id_label = new JLabel(" ID");
		// contentPane.add(id_label);
		loginTextField = new JTextField();
		loginTextField.setBounds(720, 400, 280, 40);
		loginTextField.setOpaque(false);
		contentPane.add(loginTextField);

		JLabel pw_label = new JLabel(" PassWord");
		contentPane.add(pw_label);
		passwordField = new JPasswordField();
		passwordField.setBounds(720, 500, 280, 40);
		passwordField.setOpaque(false);
		contentPane.add(passwordField);

		loginButton = new JButton("Login");
		loginButton.setBounds(720, 590, 104, 48);
		contentPane.add(loginButton);
		loginButton.setBorderPainted(false);
		loginButton.setFocusPainted(false);
		loginButton.setContentAreaFilled(false);
		loginButton.addActionListener(this);

		join_MembershipButton = new JButton("Join Membership");
		join_MembershipButton.setBounds(830, 590, 150, 48);
		contentPane.add(join_MembershipButton);
		join_MembershipButton.setBorderPainted(false);
		join_MembershipButton.setFocusPainted(false);
		join_MembershipButton.setContentAreaFilled(false);
		join_MembershipButton.addActionListener(this);

		contentPane.add(background);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		User user = db.login(loginTextField.getText(), passwordField.getPassword());
		if (e.getSource().equals(loginButton)) {
			if (user != null) {
				JOptionPane.showMessageDialog(this, "Login Success!!");
				new TestView2(db,user);
				dispose();
			} else {
				JOptionPane.showMessageDialog(this, "Login False!!");
			}
		} else if (e.getSource().equals(join_MembershipButton)) {
			new JoinMembershipView(db);
		}
	}

	class MyPanel extends JPanel {
		public void paint(Graphics g) {
			g.drawImage(img, 0, 0, null);
		}
	}

}

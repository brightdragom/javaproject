package view;

import java.awt.Container;
import java.awt.Graphics;
import java.awt.Toolkit;
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
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import dao.DBConnection;
import model.User;

public class LoginView extends JFrame implements ActionListener {
	BufferedImage img = null;
	DBConnection db;

	public LoginView(DBConnection db) {
		setIconImage(Toolkit.getDefaultToolkit().createImage("img/icon.jpg"));
		
		this.db = db;
		/*
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
		setVisible(true);*/
		
		setTitle("랜덤채팅 로그인");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 432, 164);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblId = new JLabel("ID");
		lblId.setHorizontalAlignment(SwingConstants.CENTER);
		lblId.setBounds(40, 30, 47, 15);
		contentPane.add(lblId);

		loginTextField = new JTextField();
		loginTextField.setText("");
		// sID = txtId.getText();
		loginTextField.setBounds(99, 27, 167, 21);
		contentPane.add(loginTextField);
		loginTextField.setColumns(10);

		JLabel lblPw = new JLabel("PW");
		lblPw.setHorizontalAlignment(SwingConstants.CENTER);
		lblPw.setBounds(40, 55, 47, 15);
		contentPane.add(lblPw);

		loginPasswordField = new JPasswordField();
		loginPasswordField.setText("");
		// sPW = txtId.getText();
		loginPasswordField.setBounds(99, 52, 167, 21);
		contentPane.add(loginPasswordField);
		loginPasswordField.setColumns(10);

		loginButton = new JButton("로그인");
		loginButton.addActionListener(this);
		loginButton.setBounds(274, 27, 102, 46);
		contentPane.add(loginButton);

		joinButton = new JButton("회원 가입");
		joinButton.addActionListener(this);
		joinButton.setBounds(274, 80, 102, 30);
		contentPane.add(joinButton);

		findIdButton = new JButton("ID 찾기");
		findIdButton.addActionListener(this);
		findIdButton.setBounds(40, 80, 109, 30);
		contentPane.add(findIdButton);

		findPwButton = new JButton("PW찾기");
		findPwButton.addActionListener(this);
		findPwButton.setBounds(152, 80, 118, 30);
		contentPane.add(findPwButton);
		setVisible(true);
	}
	private JPanel contentPane;
	private JTextField loginTextField;
	private JPasswordField loginPasswordField;
	private JButton loginButton, joinButton, findIdButton, findPwButton;
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(loginButton)) {
			User user = db.login(loginTextField.getText(), loginPasswordField.getPassword());
			if (user != null) {
				JOptionPane.showMessageDialog(this, "Login Success!!");
				new MainView(db,user);
				dispose();
			} else {
				JOptionPane.showMessageDialog(this, "Login False!!");
			}
		} else if (e.getSource().equals(joinButton)) {
			new JoinMembershipView(db);
		}else if(e.getSource().equals(findIdButton)) {
			new FindIDView(db);
		}else if(e.getSource().equals(findPwButton)) {
			new FindPWView(db);
		}
	}

	class MyPanel extends JPanel {
		public void paint(Graphics g) {
			g.drawImage(img, 0, 0, null);
		}
	}

}

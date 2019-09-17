package view;

import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import dao.DBConnection;


public class JoinMembershipView extends JFrame implements ActionListener{
	DBConnection db;
	BufferedImage img = null;
	
	JTextField userID_TextField;
	JPasswordField userPassword_Field;
	JTextField userName_TextField;
	JTextField userAge_TextField;
	JTextField userPhoneNumber_TextField;
	JTextField userAddress_TextField;
	JButton Cancel_Button;
	JButton join_MembershipButton;
	
	public JoinMembershipView(DBConnection db) {
		this.db = db;
		setTitle("Join_Membership_Service_View");
		setSize(542, 962);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		Container contentPane = getContentPane();
		try {
			img = ImageIO.read(new File("img/join.jpg"));
		} catch (IOException e) {
			System.out.println("이미지 불러오기 실패");
			System.exit(0);
		}
		MyPanel background = new MyPanel();
		background.setBounds(0, 0, 542, 962);
		contentPane.setLayout(null);
		Font font = new Font("돋움",Font.BOLD, 30);
		userID_TextField = new JTextField(/*"Please enter your using ID..."*/);
		userID_TextField.setBounds(220, 255, 250, 50);
		userID_TextField.setFont(font);
		userID_TextField.setOpaque(false);
		contentPane.add(userID_TextField);
		
		userPassword_Field = new JPasswordField(/*"Please enter your using PW..."*/);
		userPassword_Field.setBounds(220, 335, 250, 50);
		userPassword_Field.setOpaque(false);
		userPassword_Field.setFont(font);
		contentPane.add(userPassword_Field);
		
		userName_TextField = new JTextField(/*"Please enter your Name..."*/);
		userName_TextField.setBounds(220, 415, 250, 50);
		userName_TextField.setOpaque(false);
		userName_TextField.setFont(font);
		contentPane.add(userName_TextField);
		
		userAge_TextField = new JTextField(/*"Please enter your Age..."*/);
		userAge_TextField.setBounds(220, 495, 250, 50);
		userAge_TextField.setOpaque(false);
		userAge_TextField.setFont(font);
		contentPane.add(userAge_TextField);
		
		userPhoneNumber_TextField = new JTextField(/*"Please enter your PhoneNumber..."*/);
		userPhoneNumber_TextField.setBounds(220, 575, 250, 50);
		userPhoneNumber_TextField.setOpaque(false);
		userPhoneNumber_TextField.setFont(font);
		contentPane.add(userPhoneNumber_TextField);
		
		userAddress_TextField = new JTextField();
		userAddress_TextField.setBounds(220, 655, 250, 50);
		userAddress_TextField.setOpaque(false);
		userAddress_TextField.setFont(font);
		contentPane.add(userAddress_TextField);
		
		join_MembershipButton = new JButton("Join Membership");
		join_MembershipButton.setBounds(120, 755, 150, 48);
		contentPane.add(join_MembershipButton);
		join_MembershipButton.setBorderPainted(false);
		join_MembershipButton.setFocusPainted(false);
		join_MembershipButton.setContentAreaFilled(false);
		join_MembershipButton.addActionListener(this);
		
		Cancel_Button = new JButton("Cancel");
		Cancel_Button.setBounds(300, 755, 134, 48);
		contentPane.add(Cancel_Button);
		Cancel_Button.setBorderPainted(false);
		Cancel_Button.setFocusPainted(false);
		Cancel_Button.setContentAreaFilled(false);
		Cancel_Button.addActionListener(this);
		
		contentPane.add(background);
		setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource().equals(Cancel_Button)) {
			dispose();
		}else if(e.getSource().equals(join_MembershipButton)) {	//DB에 가입정보 추가
			String joinID = userID_TextField.getText();
			int joinPW = userPassword_Field.getPassword().length;
			String joinName = userName_TextField.getText();
			String joinAge = userAge_TextField.getText();
			String joinPhoneNumber = userPhoneNumber_TextField.getText();
			String joinAddress = userAddress_TextField.getText();
			
			if(joinID.length() == 0) {
				JOptionPane.showMessageDialog(this, "Please enter the ID!!");
			}else if(joinPW == 0) {
				JOptionPane.showMessageDialog(this, "Please enter the PW!!");
			}else if(joinName.length() == 0) {
				JOptionPane.showMessageDialog(this, "Please enter the Name!!");
			}else if(joinAge.length() == 0) {
				JOptionPane.showMessageDialog(this, "Please enter the Age!!");
			}else if(joinPhoneNumber.length() == 0) {
				JOptionPane.showMessageDialog(this, "Please enter the PhoneNumber!!");
			}else if(joinAddress.length() == 0) {
				JOptionPane.showMessageDialog(this, "Please enter the Address!!");
			}else {
				db.join_membership_method(userID_TextField.getText(), userPassword_Field.getPassword(), userName_TextField.getText(),
						userAge_TextField.getText(), userPhoneNumber_TextField.getText(), userAddress_TextField.getText());
				System.out.println("DB에 회원가입정보 입력 완료");
				dispose();
			}
		}
	}
	class MyPanel extends JPanel {
		public void paint(Graphics g) {
			g.drawImage(img, 0, 0, null);
		}
	}

}

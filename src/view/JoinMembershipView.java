package view;

import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import dao.DBConnection;

public class JoinMembershipView extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private DBConnection db;
	private BufferedImage img = null;
	private JTextField tfId, tfName, tfNickName,tfDateOfBirth, tfEmail;
	private JPasswordField pfPwd; // 비밀번호
	private JButton btnInsert, btnCancel, btnOverlap; // 가입, 취소
	private GridBagLayout gb;
	private GridBagConstraints gbc;
	private boolean overlab_check = false;
	private boolean overlab_check2 = false;
	
	public JoinMembershipView(DBConnection db) {
		this.db = db;
		createUI();
	}
	
	private void gbAdd(JComponent c, int x, int y, int w, int h) {
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.gridwidth = w;
		gbc.gridheight = h;
		gb.setConstraints(c, gbc);
		gbc.insets = new Insets(2, 2, 2, 2);
		add(c, gbc);
	}

	public void createUI() {
		this.setTitle("회원가입");
		gb = new GridBagLayout();
		setLayout(gb);
		gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;

		// 아이디
		JLabel inputIdBox = new JLabel("아이디 : ");
		tfId = new JTextField(20);
		// 그리드백에 붙이기
		gbAdd(inputIdBox, 0, 0, 1, 1);
		gbAdd(tfId, 1, 0, 3, 1);

		// 비밀번호
		JLabel inputPasswordBox = new JLabel("비밀번호 : ");
		pfPwd = new JPasswordField(20);
		gbAdd(inputPasswordBox, 0, 1, 1, 1);
		gbAdd(pfPwd, 1, 1, 3, 1);

		// 이름
		JLabel inputNameBox = new JLabel("이름 :");
		tfName = new JTextField(20);
		gbAdd(inputNameBox, 0, 2, 1, 1);
		gbAdd(tfName, 1, 2, 3, 1);

		JLabel inputNickNameBox = new JLabel("닉네임 :");
		tfNickName = new JTextField(20);
		gbAdd(inputNickNameBox, 0, 3, 1, 1);
		gbAdd(tfNickName, 1, 3, 3, 1);

		JLabel inputBirthBox = new JLabel("생년월일 :");
		tfDateOfBirth = new JTextField(20);
		gbAdd(inputBirthBox, 0, 4, 1, 1);
		gbAdd(tfDateOfBirth, 1, 4, 3, 1);

		JLabel inputEmailBox = new JLabel("Email :");
		tfEmail = new JTextField(20);
		gbAdd(inputEmailBox, 0, 5, 1, 1);
		gbAdd(tfEmail, 1, 5, 3, 1);

		// 버튼
		JPanel pButton = new JPanel();
		btnInsert = new JButton("가입");
		btnOverlap = new JButton("아이디 중복검사");
		btnCancel = new JButton("취소");
		pButton.add(btnInsert);
		pButton.add(btnOverlap);
		pButton.add(btnCancel);
		gbAdd(pButton, 0, 10, 4, 1);

		// 버튼에 감지기를 붙이자
		btnInsert.addActionListener(this);
		btnOverlap.addActionListener(this);
		btnCancel.addActionListener(this);

		setSize(350, 350);
		setVisible(true);
		// setDefaultCloseOperation(EXIT_ON_CLOSE); //System.exit(0) //프로그램종료
		setDefaultCloseOperation(DISPOSE_ON_CLOSE); // dispose(); //현재창만 닫는다.

	}// createUI

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource().equals(btnCancel)) {
			dispose();
		} else if (e.getSource().equals(btnInsert)) { // DB에 가입정보 추가
			if(overlab_check2 && overlab_check) {
				if(db.overlabId(tfId.getText())) {
					String joinID = tfId.getText();
					int joinPW = pfPwd.getPassword().length;
					String joinName = tfName.getText();
					String join_NickName = tfNickName.getText();
					String join_DateOfBirth = tfDateOfBirth.getText();
					String join_Email = tfEmail.getText();

					if (joinID.length() == 0) {
						JOptionPane.showMessageDialog(this, "Please enter the ID!!");
					} else if (joinPW == 0) {
						JOptionPane.showMessageDialog(this, "Please enter the PW!!");
					} else if (joinName.length() == 0) {
						JOptionPane.showMessageDialog(this, "Please enter the Name!!");
					} else if (join_NickName.length() == 0) {
						JOptionPane.showMessageDialog(this, "Please enter the Age!!");
					} else if (join_DateOfBirth.length() == 0) {
						JOptionPane.showMessageDialog(this, "Please enter the PhoneNumber!!");
					} else if (join_Email.length() == 0) {
						JOptionPane.showMessageDialog(this, "Please enter the Address!!");
					} else {
						db.join_membership_method(tfId.getText(), pfPwd.getPassword(),tfName.getText(), tfNickName.getText(), tfDateOfBirth.getText(),tfEmail.getText());

						System.out.println("DB에 회원가입정보 입력 완료");
						dispose();
					}
				}else {
					JOptionPane.showMessageDialog(this, "Please Duplicate inspection!!");
					overlab_check = false;
				}
			}else {
				JOptionPane.showMessageDialog(this, "Please Duplicate inspection!!");
				overlab_check = false;
			}
		}else if(e.getSource().equals(btnOverlap)) {
			overlab_check2 = db.overlabId(tfId.getText());
			JOptionPane.showMessageDialog(this, overlab_check2 ? "This ID is available.":"The ID already exists.");
			overlab_check = overlab_check2?true:false;
		}
	}

	class MyPanel extends JPanel {
		public void paint(Graphics g) {
			g.drawImage(img, 0, 0, null);
		}
	}

}

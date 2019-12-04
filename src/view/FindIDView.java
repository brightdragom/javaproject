package view;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import dao.DBConnection;

public class FindIDView extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//private BufferedImage img = null;
	private DBConnection db;
	private JPanel contentPane;
	private JTextField emailTextField, dateTextField;
	private JButton findIdButton;

	public FindIDView(DBConnection db) {
		this.db = db;
		setIconImage(Toolkit.getDefaultToolkit().createImage("img/icon.jpg"));
		setTitle("랜덤채팅 아이디찾기");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 432, 164);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel email_label = new JLabel("E-Mail");
		email_label.setHorizontalAlignment(SwingConstants.CENTER);
		email_label.setBounds(40, 30, 47, 15);
		contentPane.add(email_label);

		emailTextField = new JTextField();
		emailTextField.setText("");
		// sID = txtId.getText();
		emailTextField.setBounds(99, 27, 167, 21);
		contentPane.add(emailTextField);
		emailTextField.setColumns(10);

		JLabel date_label = new JLabel("DateOfBrith");
		date_label.setHorizontalAlignment(SwingConstants.CENTER);
		date_label.setBounds(40, 55, 47, 15);
		contentPane.add(date_label);

		dateTextField = new JTextField();
		dateTextField.setText("");
		// sPW = txtId.getText();
		dateTextField.setBounds(99, 52, 167, 21);
		contentPane.add(dateTextField);
		dateTextField.setColumns(10);

		findIdButton = new JButton("찾기");
		findIdButton.addActionListener(this);
		findIdButton.setBounds(274, 27, 102, 46);
		contentPane.add(findIdButton);

		setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(findIdButton)) {
			String answer = db.findId(emailTextField.getText(), dateTextField.getText());
			if(answer != null) {
				JOptionPane.showMessageDialog(this, "Your ID : "+answer);
				dispose();
			}else {
				JOptionPane.showMessageDialog(this, "No ID found.");
			}
		}
	}
}

package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import dao.DBConnection;

public class PlusFriendView extends JDialog implements ActionListener{
	private JTextField friendID;
	private JButton push;
	private DBConnection db;
	
	public PlusFriendView(DBConnection db) {
		this.db = db;
		setTitle("친구 추가");
		setSize(300, 300);
		setVisible(true);
		this.setBackground(Color.WHITE);
		/*
		 * Container c = new Container(); c.setLayout(null);
		 */
		setLayout(null);
		JLabel label_1 = new JLabel("친구 ID > ");
		label_1.setBounds(10, 10, 100, 50);
		label_1.setFont(new Font("돋움", Font.BOLD, 15));
		
		friendID = new JTextField();
		friendID.setBounds(100, 10, 120, 40);
		friendID.setOpaque(false);
		
		push = new JButton("추 가");
		push.setFont(new Font("돋움", Font.BOLD, 15));
		push.setBounds(210, 10, 80, 40);
		push.setBorderPainted(false);
		push.setFocusPainted(true);
		push.setContentAreaFilled(false);
		push.setOpaque(false);
		push.addActionListener(this);
		/*
		c.add(push);
		c.add(label_1);
		c.add(friendID);
		this.add(c);
		*/
		System.out.println();
		System.out.println();
		this.add(label_1);
		this.add(friendID);
		this.add(push);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(push)) {
			String id = friendID.getText();
			db.plusFriend(id);
			System.out.println("추가!");
		}
	}

}

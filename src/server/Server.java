package server;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class Server extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	private JTextArea ta = new JTextArea(40,25);
	private JTextField tf = new JTextField(25);
	private ServerBackground server = new ServerBackground();
	
	public Server() {
		add(ta, BorderLayout.CENTER);
		add(tf, BorderLayout.SOUTH);
		tf.addActionListener(this);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setBounds(200, 100, 400, 600);
		setTitle("¼­¹ö");
		
		//server.connection(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String msg = "Server : "+tf.getText()+"\n";
		ta.append(msg);
		server.sendMsg(msg);
		tf.setText("");
	}
	
	public void renewal(String msg) {
		ta.append(msg);
	}
}

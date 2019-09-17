package client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class Client extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	private JTextArea ta = new JTextArea(40,25);
	private JTextField tf = new JTextField(25);
	private ClientBackground client = new ClientBackground();
	private String nickName;
	
	public Client() {
		Scanner sc = new Scanner(System.in);
		add(ta, BorderLayout.CENTER);
		add(tf, BorderLayout.SOUTH);
		tf.addActionListener(this);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setBounds(200, 100, 400, 600);
		setTitle("클라이언트");
		System.out.print("Please enter your nick name > ");
		nickName = sc.next();
		client.setNickName(nickName);
		sc.close();
		client.connection(this);
	}
	public void appendMsg(String msg) {
		ta.append(msg);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String msg = nickName+" : " +tf.getText()+"\n";
		client.sendMsg(msg);
		tf.setText("");
	}
	
}

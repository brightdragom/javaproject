package client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import model.User;

public class chetClient extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	private JTextArea textArea = new JTextArea(40,25);
	private JTextField tf = new JTextField(25);
	private JPanel contentPane;
	private String ip=null;
	private String myRole=null;
	
	private DataInputStream dis;
	private DataOutputStream dos;
	private String port;
/*	
��	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					chetClient frame = new chetClient();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Create the frame.
	 */
	boolean stop = false;
	private User user;
	public chetClient(User user) {
		this.user = user;
		try {
			Socket socket = new Socket("127.0.0.1", 9090);
			BufferedReader bufReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			ip = bufReader.readLine();
			myRole = bufReader.readLine();
			port = bufReader.readLine();
			System.out.println(port);
			bufReader.close();
			socket.close();
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if(myRole.equals("server"))
		{
			ServerThread serverThread = new ServerThread(ip, Integer.parseInt(port));
			serverThread.setDaemon(true); //���� ������ ���� ����
			serverThread.start();			
		}
		else if(myRole.equals("client"))
		{
			ClientThread clientThread = new ClientThread(ip, Integer.parseInt(port));
			clientThread.setDaemon(true);
			clientThread.start();
		}
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		add(textArea, BorderLayout.CENTER);
		add(tf, BorderLayout.SOUTH);
		tf.addActionListener(this);

		//setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setBounds(200, 100, 400, 600);
		setTitle("�αٵα� ����ä��");
		
		tf.addKeyListener(new KeyAdapter() {

			//Ű���忡�� Ű �ϳ��� �������� �ڵ����� ����Ǵ� �޼ҵ�..: �ݹ� �޼ҵ�
			@Override
			public void keyPressed(KeyEvent e) {				
				super.keyPressed(e);
			//�Է¹��� Ű�� �������� �˾Ƴ���, KeyEvent ��ü�� Ű������ ���� ��������
				int keyCode = e.getKeyCode();
				switch(keyCode) {
				case KeyEvent.VK_ENTER:
					sendMessage();
					break;
				}
			}
		});		
		
		setVisible(true);
		tf.requestFocus();
		
		
	}

	

class ServerThread extends Thread {
	String ip;
	int port;
	public ServerThread(String ip, int port)
	{
		this.ip = ip;
		this.port = port;
	}
	
	@Override
	public void run() {			
		try {  //���� ���� ���� �۾�			
			ServerSocket serverSocket = new ServerSocket(this.port);
			Socket clientSocket = serverSocket.accept();
			textArea.append("������ ���ӵƽ��ϴ�.\n");
			dis = new DataInputStream(clientSocket.getInputStream());
			dos = new DataOutputStream(clientSocket.getOutputStream());
			
			while(true) {
				//������ ������ �����͸� �б�
				String msg = dis.readUTF();//������ ���������� ���
				textArea.append(" [���] : " + msg + "\n");
				textArea.setCaretPosition(textArea.getText().length());
			}				
		} catch (IOException e) {
			textArea.append("��밡 �������ϴ�.\n");
		}
	}

}

class ClientThread extends Thread {
	String ip;
	int port;
	public ClientThread(String ip, int port)
	{
		this.ip = ip;
		this.port = port;
		System.out.println("client->server"+ip);
	}
	@Override
	public void run() {
		try {
			Socket socket = new Socket(ip, this.port);
			textArea.append("������2 ���ӵƽ��ϴ�.\n");
			InputStream is = socket.getInputStream();
			OutputStream os = socket.getOutputStream();
			dis = new DataInputStream(is);
			dos = new DataOutputStream(os);	
			
			while(true) {//���� �޽��� �ޱ�
				String msg = dis.readUTF();
				textArea.append(" [���] : " + msg + "\n");
				textArea.setCaretPosition(textArea.getText().length());
			}
		} catch (UnknownHostException e) {
			textArea.append("ip �ּҰ� �̻��մϴ�.\n");
		} catch (IOException e) {
			textArea.append("���� ������ ������ϴ�.\n");
		}
	}
}

void sendMessage() {	

	final String msg = tf.getText(); //TextField�� ���ִ� �۾��� ������
	tf.setText(""); //�Է� �� ��ĭ����
	textArea.append(" [��] : " + msg + "\n");//1.TextArea(ä��â)�� ǥ��
	textArea.setCaretPosition(textArea.getText().length()); //��ũ�� ���󰡰�
	//2.����(Client)���� �޽��� �����ϱ�
	Thread t = new Thread() {
		@Override
		public void run() {
			try {
				dos.writeUTF(msg);
				dos.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	};		
	t.start();
}	

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}

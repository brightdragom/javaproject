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
¿	public static void main(String[] args) {

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
			serverThread.setDaemon(true); //메인 끝나면 같이 종료
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
		setTitle("두근두근 랜덤채팅");
		
		tf.addKeyListener(new KeyAdapter() {

			//키보드에서 키 하나를 눌렀을때 자동으로 실행되는 메소드..: 콜백 메소드
			@Override
			public void keyPressed(KeyEvent e) {				
				super.keyPressed(e);
			//입력받은 키가 엔터인지 알아내기, KeyEvent 객체가 키에대한 정보 갖고있음
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
		try {  //서버 소켓 생성 작업			
			ServerSocket serverSocket = new ServerSocket(this.port);
			Socket clientSocket = serverSocket.accept();
			textArea.append("상대방이 접속됐습니다.\n");
			dis = new DataInputStream(clientSocket.getInputStream());
			dos = new DataOutputStream(clientSocket.getOutputStream());
			
			while(true) {
				//상대방이 보내온 데이터를 읽기
				String msg = dis.readUTF();//상대방이 보낼때까지 대기
				textArea.append(" [상대] : " + msg + "\n");
				textArea.setCaretPosition(textArea.getText().length());
			}				
		} catch (IOException e) {
			textArea.append("상대가 나갔습니다.\n");
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
			textArea.append("상대방이2 접속됐습니다.\n");
			InputStream is = socket.getInputStream();
			OutputStream os = socket.getOutputStream();
			dis = new DataInputStream(is);
			dos = new DataOutputStream(os);	
			
			while(true) {//상대방 메시지 받기
				String msg = dis.readUTF();
				textArea.append(" [상대] : " + msg + "\n");
				textArea.setCaretPosition(textArea.getText().length());
			}
		} catch (UnknownHostException e) {
			textArea.append("ip 주소가 이상합니다.\n");
		} catch (IOException e) {
			textArea.append("상대와 연결이 끊겼습니다.\n");
		}
	}
}

void sendMessage() {	

	final String msg = tf.getText(); //TextField에 써있는 글씨를 얻어오기
	tf.setText(""); //입력 후 빈칸으로
	textArea.append(" [나] : " + msg + "\n");//1.TextArea(채팅창)에 표시
	textArea.setCaretPosition(textArea.getText().length()); //스크롤 따라가게
	//2.상대방(Client)에게 메시지 전송하기
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

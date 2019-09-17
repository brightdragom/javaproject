package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ServerBackground extends Thread{
	private ServerSocket s_socket;
	private int port_Num = 8888;
	private Socket socket;
	private Server server;
	private Map<String, DataOutputStream> uim = new HashMap<String, DataOutputStream>();
	@Override
	public void run() {
		while(true) {
			connection();
		}
	}
	
	public void connection(/*Server server*/) {
		//this.server = server;
		try {
			s_socket = new ServerSocket(port_Num);
			while(true) {
				System.out.println("Server에서 접속 대기중 ....");
				socket= s_socket.accept();
				System.out.println(s_socket.getLocalPort()+"포트에서 접속함! - S");
				Thread receive_Thread = new Thread(new Receive(socket));
				receive_Thread.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				socket.close();
				s_socket.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	public void sendMsg(String msg) {
		Iterator<String> it = uim.keySet().iterator();
		
		String key = null;
		
		while(it.hasNext()) {
			key = it.next();
			try {
				uim.get(key).writeUTF(msg);
			}catch(IOException e) {
				
			}
		}
	}
	public void addClient(String nick, DataOutputStream out) throws IOException {
        sendMsg(nick + "님이 접속하셨습니다.");
        uim.put(nick, out);
    }
 
    public void removeClient(String nick) {
    	sendMsg(nick + "님이 나가셨습니다.");
        uim.remove(nick);
    }
	class Receive implements Runnable{
		String msg;
		DataInputStream inputstream;
		DataOutputStream outputstream;
		private String nickName;
		
		public Receive(Socket socket){
			try {
				inputstream = new DataInputStream(socket.getInputStream());
				outputstream = new DataOutputStream(socket.getOutputStream());
				nickName = inputstream.readUTF();
				addClient(nickName, outputstream);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		@Override
		public void run() {
			try {
				while(inputstream != null) {
					msg = inputstream.readUTF();
					sendMsg(msg);
					server.renewal(msg);
				}
			}catch(IOException e) {
				removeClient(nickName);
			}
		}
		
	}
}

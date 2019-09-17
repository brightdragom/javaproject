package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ClientBackground {
	private Socket socket;
	private String ip = "127.0.0.1";
	private int port_Num = 8888;
	private DataInputStream inputstream;
	private DataOutputStream outputstream;
	private String nickName;
	
	public void connection(Client client) {
		
		String msg = null;
		try {
			socket = new Socket(ip, port_Num);
			System.out.println(ip+"|"+socket.getPort()+"로 접속 함! - C");
			inputstream = new DataInputStream(socket.getInputStream());
			outputstream = new DataOutputStream(socket.getOutputStream());
			outputstream.writeUTF(nickName);
			
			while(inputstream != null) {
				msg = inputstream.readUTF();
				client.appendMsg(msg);		
			}
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getNickName() {
		return nickName;
	}
	
	public void sendMsg(String msg) {
		try {
			outputstream.writeUTF(msg);
		}catch(IOException e) {
			
		}
	}
}

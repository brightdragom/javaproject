package client;

import java.io.ByteArrayOutputStream;
//클라이언트, 서버를 사용하는 소켓 선언
import java.io.IOException;
//네트워크 소켓 선언
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

//사운드 선언
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
//오디오 출력 소켓 선언
import javax.sound.sampled.TargetDataLine;

public class AudioClient{

	boolean stopaudioCapture = false; //오디오 멈춤
	private AudioFormat getFormat = getFormat(); //오디오 재생형식 지정
	ByteArrayOutputStream data; //바이트 데이터
	TargetDataLine targetDataLine; //오디오 출력
	private int port;

	public void setPort(int port) {
		this.port = port;
	}

	private AudioFormat getFormat() {
		float sampleRate = 8000;
		int sampleSizeInBits = 8;
		int channels = 1;
		boolean signed = true;
		boolean bigEndian = true;
		return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
	}

	public AudioClient(String ipAddress, int port){

		try {		
			final AudioFormat format = getFormat();
			DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
			TargetDataLine line;
			line = (TargetDataLine) AudioSystem.getLine(info);		
			line.open(format);
			line.start();
			Runnable sendRunner = new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					try{
						int bufferSize = (int) format.getSampleRate() * format.getFrameSize();
						System.out.println(bufferSize);
						byte buffer[] = new byte[bufferSize];
						DatagramSocket socket = new DatagramSocket(); //데이터 소켓 생성
						InetAddress ip = InetAddress.getByName(ipAddress); //전송할 ip번호	

						while(true)
						{							
							while(!stopaudioCapture){ //오디오 멈춤이 아니면
								int cnt = line.read(buffer, 0, buffer.length); //버퍼 길이 읽음
								if(cnt > 0){
									int trans = 0;
									int len = buffer.length;
									while(len-trans>=1024)
									{
										DatagramPacket sendpacket = new DatagramPacket(buffer, trans, 1024, ip, port);//(buffer, buffer.length, ip, port);
										socket.send(sendpacket);
										trans += 1024;
									}
									DatagramPacket sendpacket = new DatagramPacket(buffer, trans, len-trans, ip, port);//(buffer, buffer.length, ip, port);
									socket.send(sendpacket);
								}
							}
						}
					}catch(IOException e){
						e.printStackTrace();
					}
				}
			};

			Thread thread = new Thread(sendRunner);
			thread.start();

		} catch (LineUnavailableException e1) {
			e1.printStackTrace();
		}

	}
}

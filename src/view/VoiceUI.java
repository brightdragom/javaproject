package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import client.AudioClient;
import server.AudioServer;

public class VoiceUI extends JDialog {

	private Thread staySendThread;
	private AudioServer receiver;
	private JPanel contentPane;
	private JPanel panel_1;
	private JPanel panel_2;
	private HintTextField iptext;
	private JButton btnNewButton;
	private JPanel panel_3;
	private JLabel hint;
	private final int port = 10200;
	private boolean flag = false;	
	private Font font = new Font("돋움" , Font.BOLD,20);
	private static final Pattern PATTERN = Pattern.compile(
			"^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");
	
	/**
	 * Launch the application.
	 */
	/*
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VoiceUI frame = new VoiceUI();
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
	public VoiceUI() {
		setTitle("Voice Chat"); 
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setIconImage(Toolkit.getDefaultToolkit().createImage("img/micImage.png"));
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel northPanel = new JPanel();
		contentPane.add(northPanel, BorderLayout.NORTH);
		northPanel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel ipPanel = new JPanel();
		northPanel.add(ipPanel);
		ipPanel.setLayout(new BorderLayout(5, 10));
		
		iptext = new HintTextField("아이피를 입력해주세요.");
		ipPanel.add(iptext);
		iptext.setColumns(20);
		
		JPanel btnPanle = new JPanel();
		ipPanel.add(btnPanle, BorderLayout.EAST);
		btnPanle.setLayout(new BorderLayout(0, 0));
		
		JButton connectBtn = new JButton("Connect");

		btnPanle.add(connectBtn, BorderLayout.NORTH);
		
		JButton disconnectBtn = new JButton("Exit");
		disconnectBtn.setEnabled(false);
		btnPanle.add(disconnectBtn, BorderLayout.SOUTH);
		
		JPanel labelPanel = new JPanel();
		ipPanel.add(labelPanel, BorderLayout.SOUTH);
		labelPanel.setLayout(new BorderLayout(0, 0));
		
		JLabel stateLabel = new JLabel("State");
		stateLabel.setFont(new Font("굴림", Font.BOLD, 18));
		labelPanel.add(stateLabel, BorderLayout.SOUTH);
		
		JPanel statePanel = new JPanel();
		statePanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		contentPane.add(statePanel, BorderLayout.CENTER);
		statePanel.setLayout(new BoxLayout(statePanel, BoxLayout.Y_AXIS));
		
		connectBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String ip = iptext.getText();
				
				if(PATTERN.matcher(ip).matches())
				{
					statePanel.removeAll();
					connectBtn.setEnabled(false);
					disconnectBtn.setEnabled(true);
					iptext.setEditable(false);

					JLabel lblState = new JLabel();
					lblState.setFont(font);
					statePanel.add(lblState);
					statePanel.validate();
					statePanel.repaint();
					
					receiver = new AudioServer(ip, statePanel, port);
					Runnable receiveRunner = new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							receiver.receiveAudio();
						}
					};
					
					//client.setFlag(true);
					staySendThread = new Thread(receiveRunner);
					staySendThread.start();
					
					AudioClient client = new AudioClient(ip, port);
				}
				else
				{
					statePanel.removeAll();
					JLabel lblState = new JLabel();
					lblState.setText("Non IP.");
					lblState.setFont(font);
					statePanel.add(lblState);
					statePanel.validate();
					statePanel.repaint();
				}
			}
			
			
		});
		
		disconnectBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				connectBtn.setEnabled(true);
				disconnectBtn.setEnabled(false);
				iptext.setEditable(true);
				System.exit(-2);
				//staySendThread.interrupt();
			}
		});
		setSize(600,600);
        setResizable(false);
        setVisible(true);
	}

}

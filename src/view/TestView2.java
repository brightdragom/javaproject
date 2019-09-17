package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import client.Client;
import dao.DBConnection;
import model.User;
import server.ServerBackground;

public class TestView2 extends JFrame implements ActionListener {
	
	FriendView f;
	ChatListView c;
	BufferedImage img = null;
	JButton friendButton = null;
	JButton chatButton = null;
	JPanel panel2 = null;
	private boolean f_button = true;
	private boolean c_button = false;
	DBConnection db;
	User user;
	public TestView2(DBConnection db, User user) {
		super();
		this.db = db;
		this.user = user;
		setTitle("testView");
		setSize(365, 790);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBounds(0, 0, 350, 750);
		layeredPane.setLayout(null);
		
		// 좌측 매뉴바 패널
		JPanel panel1 = new JPanel();
		panel1.setLayout(null);
		panel1.setBounds(0, 0, 60, 650);
		panel1.setBackground(Color.lightGray);

		// 친구버튼
		friendButton = new JButton(new ImageIcon("img/friend_img2.PNG"));
		friendButton.setBounds(5, 50, 50, 50);
		friendButton.setBorderPainted(false);
		friendButton.setFocusPainted(true);
		friendButton.setContentAreaFilled(false);
		friendButton.setOpaque(false);
		friendButton.addActionListener(this);

		panel1.add(friendButton);

		// 채팅버튼
		chatButton = new JButton(new ImageIcon("img/chat.PNG"));
		chatButton.setBounds(5, 120, 50, 50);
		chatButton.setBorderPainted(false);
		chatButton.setFocusPainted(true);
		chatButton.setContentAreaFilled(false);
		chatButton.setOpaque(false);
		chatButton.addActionListener(this);

		panel1.add(chatButton);

		// 우측화면 패널
		panel2 = new JPanel();
		f = new FriendView();
		// 광고
		AdvertisingView adver = new AdvertisingView();
		adver.setLayout(null);
		adver.setBounds(0, 650, 350, 100);
		adver.setOpaque(false);
		new Thread(adver).start();
		
		
		// 레이아웃에 추가
		layeredPane.add(panel1);
		layeredPane.add(panel2);
		layeredPane.add(adver);
		add(layeredPane);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Icon friend1 = new ImageIcon("img/friend_img1.PNG");
		Icon friend2 = new ImageIcon("img/friend_img2.PNG");
		Icon chat1 = new ImageIcon("img/chat.PNG");
		Icon chat2 = new ImageIcon("img/chat2.PNG");
		if (e.getSource().equals(friendButton)) {
			if (!f_button && c_button) {
				chatButton.setIcon(chat1);
				friendButton.setIcon(friend2);
				c_button = false;
				f_button = true;
				ChangeView();
			}
		} else if (e.getSource().equals(chatButton)) {
			if (f_button && !c_button) {
				chatButton.setIcon(chat2);
				friendButton.setIcon(friend1);
				c_button = true;
				f_button = false;
				ChangeView();
			}
		}
	}
	// ----------------------------------------------------화면전환 해야댐
	//우측 패널 전환
	public void ChangeView() {
		if(f_button && !c_button) {
			panel2.removeAll();
			c = null;
			f = new FriendView();
			revalidate();
			repaint();
			System.out.println("chage c > f");
		}else {
			panel2.removeAll();
			f = null;
			c = new ChatListView();
			revalidate();
			repaint();
			System.out.println("chage f > c");
		}
	}

	// 광고
	class AdvertisingView extends JPanel implements Runnable {

		Image img[] = new Image[4];
		int i = 2;

		public AdvertisingView() {
			img[1] = Toolkit.getDefaultToolkit().createImage("img/AdvertisingView1.jpg");
			img[2] = Toolkit.getDefaultToolkit().createImage("img/AdvertisingView2.jpg");
			img[3] = Toolkit.getDefaultToolkit().createImage("img/AdvertisingView3.jpg");
			img[0] = img[1];
		}

		@Override
		public void paint(Graphics g) {
			super.paint(g);
			g.drawImage(img[0], 0, 0, this);
		}

		@Override
		public void run() {
			while (true) {
				try {
					Thread.sleep(3000);
					switch (i) {
					case 1:
						img[0] = img[i];
						i++;
						repaint();
						break;
					case 2:
						img[0] = img[i];
						i++;
						repaint();
						break;
					case 3:
						img[0] = img[i];
						i = 1;
						repaint();
						break;
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}

	//친구선택시
	class FriendView extends JPanel implements ActionListener, ListSelectionListener {

		Icon icon = new ImageIcon("img/plus_button.png");
		JButton plus_button = null;
		JList list;
		FriendView() {
			panel2.setLayout(null);
			panel2.setBounds(60, 0, 290, 650);
			panel2.setBackground(Color.WHITE);
			// 화면 명
			JLabel title = new JLabel("Friends!");
			title.setFont(new Font("돋움", Font.BOLD, 20));
			title.setBounds(5, 50, 100, 50);

			plus_button = new JButton(icon);
			plus_button.setBounds(250, 50, 30, 30);
			plus_button.setBorderPainted(false);
			plus_button.setFocusPainted(true);
			plus_button.setContentAreaFilled(false);
			plus_button.setOpaque(false);
			plus_button.addActionListener(this);
			
			String[] friend_list = db.getUserFriend(user.getUserNo()).split(",");
			/*
			JLabel a = new JLabel("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
			a.setBounds(5,100,280,60);
			a.setBackground(Color.black);
			panel2.add(a);
			*/
			
			
			JPanel f_list = new JPanel();
			f_list.setBounds(5,100,280,550);
			f_list.setBackground(Color.white);
			list = new JList(friend_list);
			list.setBounds(5,100,280,550);
			list.setFont(new Font("돋움", Font.BOLD, 18));
			list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

			list.addListSelectionListener(this);


			f_list.add(list);
			panel2.add(f_list);
			panel2.add(plus_button);
			panel2.add(title);
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(plus_button)) {
				//JOptionPane.showMessageDialog(this, "Select Plus Button!!");
				new PlusFriendView(db);
			}
		}
		int cnt = 0;
		@Override
		public void valueChanged(ListSelectionEvent e) {
			
			switch(list.getSelectedIndex()) {
			case 0:
				if(cnt == 1) {
					
					System.out.println("Select to 0");
					System.out.println(list.getSelectedValue());
					cnt = 0;
					break;
				}
				cnt=1;
				break;
			case 1:
				if(cnt == 1) {
					System.out.println("Select to 1");
					System.out.println(list.getSelectedValue());
					cnt = 0;
					break;
				}
				cnt=1;
				break;
			}
		}

	}

	//채팅선택시
	class ChatListView extends JPanel implements ActionListener {

		Icon icon = new ImageIcon("img/f_plus (1).png");
		JButton plus_button = null;
		
		ChatListView() {
			panel2.setLayout(null);
			panel2.setBounds(60, 0, 290, 650);
			panel2.setBackground(Color.WHITE);
			// 화면 명
			JLabel title = new JLabel("ChatList!");
			title.setFont(new Font("돋움", Font.BOLD, 20));
			title.setBounds(5, 50, 100, 50);

			plus_button = new JButton(icon);
			plus_button.setBounds(250, 50, 30, 30);
			plus_button.setBorderPainted(false);
			plus_button.setFocusPainted(true);
			plus_button.setContentAreaFilled(false);
			plus_button.setOpaque(false);
			plus_button.addActionListener(this);
			
			
			panel2.add(plus_button);
			panel2.add(title);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(plus_button)) {
				JOptionPane.showMessageDialog(this, "Select Plus Button!!");
				
			}
		}

	}

}

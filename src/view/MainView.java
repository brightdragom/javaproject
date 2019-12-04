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
import javax.swing.JPanel;

import client.chetClient;
import dao.DBConnection;
import model.User;

public class MainView extends JFrame implements ActionListener {

	//FriendView f;
	//ChatListView c;
	private User user;
	BufferedImage img = null;
	JButton friendButton = null;
	JButton chatButton = null;
	JPanel panel2 = null;
	private boolean f_button = true;
	private boolean c_button = false;
	DBConnection db;
	ImageIcon normalMikeIcon = new ImageIcon("img/nomalMikeIcon.png");
	ImageIcon rolloverMikeIcon = new ImageIcon("img/pressedMikeIcon.png");
	ImageIcon normalTextIcon = new ImageIcon("img/nomalTextIcon.png");
	ImageIcon rolloverTextIcon = new ImageIcon("img/pressedTextIcon.png");
	ImageIcon pressedIcon = new ImageIcon("img/starticon.png");
	
	public MainView(DBConnection db, User user) {
		
		super();
		this.db = db;
		this.user = user;
		setIconImage(Toolkit.getDefaultToolkit().createImage("img/icon.jpg"));
		setTitle("testView");
		setSize(350, 570);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBounds(0, 0, 350, 450);
		layeredPane.setLayout(null);

		// ���� �Ŵ��� �г�
		JPanel panel1 = new JPanel();
		panel1.setLayout(null);
		panel1.setBounds(0, 0, 60, 450);
		panel1.setBackground(Color.lightGray);

		// ģ����ư
		friendButton = new JButton(new ImageIcon("img/friend_img2.PNG"));
		friendButton.setBounds(5, 50, 50, 50);
		friendButton.setBorderPainted(false);
		friendButton.setFocusPainted(true);
		friendButton.setContentAreaFilled(false);
		friendButton.setOpaque(false);
		friendButton.addActionListener(this);

		panel1.add(friendButton);
		
		// ä�ù�ư
		chatButton = new JButton(new ImageIcon("img/chat.PNG"));
		chatButton.setBounds(5, 120, 50, 50);
		chatButton.setBorderPainted(false);
		chatButton.setFocusPainted(true);
		chatButton.setContentAreaFilled(false);
		chatButton.setOpaque(false);
		chatButton.addActionListener(this);

		panel1.add(chatButton);

		// ����ȭ�� �г�
		panel2 = new JPanel();
		panel2.setLayout(null);
		panel2.setBounds(45, 0, 290, 450);
		panel2.setBackground(Color.WHITE);
		JButton button = new JButton(normalMikeIcon);
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("push the button");
				new VoiceUI();
				// new Test();
			}

		});
		button.setBounds(15,50, 280, 200);
		button.setBorderPainted(false);
		button.setFocusPainted(true);
		button.setContentAreaFilled(false);
		button.setOpaque(false);
		button.setFont(new Font("����", Font.BOLD, 30));
		button.setPressedIcon(pressedIcon);
		button.setRolloverIcon(rolloverMikeIcon);
		panel2.add(button);
		JButton button2 = new JButton(normalTextIcon);
		button2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("push the button");
				new chetClient(user);
			}

		});
		button2.setBounds(15, 250, 280, 200);
		button2.setBorderPainted(false);
		button2.setFocusPainted(true);
		button2.setContentAreaFilled(false);
		button2.setOpaque(false);
		button2.setFont(new Font("����", Font.BOLD, 30));
		button2.setPressedIcon(pressedIcon);
		button2.setRolloverIcon(rolloverTextIcon);
		panel2.add(button2);
		// f = new FriendView();
		// ����
		AdvertisingView adver = new AdvertisingView();
		adver.setLayout(null);
		adver.setBounds(0, 450, 350, 100);
		adver.setOpaque(false);
		new Thread(adver).start();

		// ���̾ƿ��� �߰�
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
				//ChangeView();
			}
		} else if (e.getSource().equals(chatButton)) {
			if (f_button && !c_button) {
				chatButton.setIcon(chat2);
				friendButton.setIcon(friend1);
				c_button = true;
				f_button = false;
				//anfuy();
			}
		}
	}
}
	/*
	// ----------------------------------------------------ȭ����ȯ �ؾߴ�
	// ���� �г� ��ȯ
	public void ChangeView() {
		if (f_button && !c_button) {
			panel2.removeAll();
			c = null;
			f = new FriendView();
			revalidate();
			repaint();
			System.out.println("chage c > f");
		} else {
			panel2.removeAll();
			f = null;
			c = new ChatListView();
			revalidate();
			repaint();
			System.out.println("chage f > c");
		}
	}
	*/
	// ����
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
	/*
	// ģ�����ý�
	class FriendView extends JPanel implements ActionListener, ListSelectionListener {

		Icon icon = new ImageIcon("img/plus_button.png");
		JButton plus_button = null;
		JList list;

		FriendView() {
			panel2.setLayout(null);
			panel2.setBounds(60, 0, 290, 650);
			panel2.setBackground(Color.WHITE);
			// ȭ�� ��
			JLabel title = new JLabel("Friends!");
			title.setFont(new Font("����", Font.BOLD, 20));
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
			 * JLabel a = new JLabel("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
			 * a.setBounds(5,100,280,60); a.setBackground(Color.black); panel2.add(a);
			 */
/*
			JPanel f_list = new JPanel();
			f_list.setBounds(5, 100, 280, 550);
			f_list.setBackground(Color.white);
			list = new JList(friend_list);
			list.setBounds(5, 100, 280, 550);
			list.setFont(new Font("����", Font.BOLD, 18));
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
				// JOptionPane.showMessageDialog(this, "Select Plus Button!!");
				new PlusFriendView(db);
			}
		}

		int cnt = 0;

		@Override
		public void valueChanged(ListSelectionEvent e) {

			switch (list.getSelectedIndex()) {
			case 0:
				if (cnt == 1) {

					System.out.println("Select to 0");
					System.out.println(list.getSelectedValue());
					cnt = 0;
					break;
				}
				cnt = 1;
				break;
			case 1:
				if (cnt == 1) {
					System.out.println("Select to 1");
					System.out.println(list.getSelectedValue());
					cnt = 0;
					break;
				}
				cnt = 1;
				break;
			}
		}

	}

	// ä�ü��ý�
	class ChatListView extends JPanel implements ActionListener {

		Icon icon = new ImageIcon("img/f_plus (1).png");
		JButton plus_button = null;

		ChatListView() {
			panel2.setLayout(null);
			panel2.setBounds(60, 0, 290, 650);
			panel2.setBackground(Color.WHITE);
			// ȭ�� ��
			JLabel title = new JLabel("ChatList!");
			title.setFont(new Font("����", Font.BOLD, 20));
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
*/

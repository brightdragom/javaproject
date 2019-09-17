package view;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class TestView extends JFrame {
	BufferedImage img = null;

	public TestView() {
		super();
		setTitle("testView");
		setSize(1600, 900);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBounds(0, 0, 1600, 900);
		layeredPane.setLayout(null);
		ImageExample ie = new ImageExample();
		ie.setLayout(null);
		ie.setBounds(0, 0, 1600, 900);
		ie.setOpaque(false); // 배경이 투명처리
		new Thread(ie).start();

		layeredPane.add(ie);
		add(layeredPane);
		setVisible(true);
	}

	class MyPanel extends JPanel {
		public void paint(Graphics g) {
			g.drawImage(img, 0, 0, null);
		}
	}

	class ImageExample extends JPanel implements Runnable {
		Image img[] = new Image[4];
		int i = 2;

		public ImageExample() {
			img[1] = Toolkit.getDefaultToolkit().createImage("img/imageTest1.jpg");
			img[2] = Toolkit.getDefaultToolkit().createImage("img/imageTest2.jpg");
			img[3] = Toolkit.getDefaultToolkit().createImage("img/imageTest3.jpg");
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
}

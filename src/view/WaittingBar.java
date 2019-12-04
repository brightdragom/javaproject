package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Label;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class WaittingBar extends JPanel{
	JProgressBar p;
	Label status;
	public void make() {
		 setLayout(new BorderLayout());
         p = new JProgressBar();
         p.setMinimum(0);
         p.setMaximum(100);
         p.setValue(0);

         status = new Label("");

         add(p,"Center");
         add(status, "South");
         
	}
	public WaittingBar() {
		make();
    }
	
	public void go() {
		String[] str = {"대기중","대기중.","대기중..","대기중..."};
		int cnt = 0;
		try {
			for (int i = 0; i <= 100; i++) {
				p.setValue(i);
				Thread.sleep(50);
				status.setText(str[cnt++]);
				if(i == 100) {
					i = 0;
				}
				if(cnt == 4) {
					cnt = 0;
				}
			}
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());
		}
		
	}

	public Dimension getPreferredSize() {
		return new Dimension(300, 80);
	}
	
	
}

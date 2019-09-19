package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;

import javax.swing.JTextField;

public class HintTextField extends JTextField{

	private final String hint;
	public HintTextField(String hint)
	{
		this.hint = hint;
		this.setFont(new Font("µ¸¿ò",Font.PLAIN, 18));
	}
	
	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
		if(getText().length() == 0)
		{
			int h = getHeight();
			((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			Insets ins = getInsets();
			FontMetrics fm = g.getFontMetrics();

			g.setColor(Color.GRAY);
			g.setFont(new Font("µ¸¿ò",Font.ITALIC, 18));
			g.drawString(hint, ins.left, h/2 + fm.getAscent() / 2 - 2);
		}
		else
		{
			g.setColor(Color.BLACK);
		}
	}
	
}

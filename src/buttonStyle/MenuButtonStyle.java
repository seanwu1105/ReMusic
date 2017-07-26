package buttonStyle;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicButtonUI;

public class MenuButtonStyle extends BasicButtonUI
{
	@Override
	public void installUI(JComponent c)
	{
		super.installUI(c);
		final AbstractButton button = (AbstractButton) c;
		button.setOpaque(false);
		button.setBorder(new EmptyBorder(5, 15, 5, 15));
		button.setCursor(new Cursor(Cursor.HAND_CURSOR));
	}

	@Override
	public void paint(Graphics g, JComponent c)
	{
		final AbstractButton b = (AbstractButton) c;
		int buttonEventStyleNum;
		if (b.getModel().isPressed())
			buttonEventStyleNum = 1;
		else if (b.getModel().isRollover())
			buttonEventStyleNum = 2;
		else
			buttonEventStyleNum = 0; // Default style

		paintBackground(g, b, buttonEventStyleNum);
		super.paint(g, c);
	}

	private void paintBackground(Graphics g, JComponent c, int styleNumber)
	{
		final Dimension size = c.getSize();
		final Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		final int borderThickness = 5;

		int xInner[] = { borderThickness, borderThickness * 10, size.width - borderThickness,
				size.width - borderThickness, size.width - 10 * borderThickness, borderThickness };
		int yInner[] = { borderThickness * 2, borderThickness, borderThickness, size.height - borderThickness * 2,
				size.height - borderThickness, size.height - borderThickness };
		if (styleNumber == 1) // Button Pressed
		{
			g.setColor(c.getBackground().brighter());
			g.fillRect(0, 0, size.width, size.height);
			g.setColor(c.getBackground().darker());
			g.fillPolygon(xInner, yInner, 6);
		}
		else if (styleNumber == 2) // Button Hovered (On Rolled)
		{
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, size.width, size.height);
			g.setColor(c.getBackground());
			g.fillPolygon(xInner, yInner, 6);
		}
		else // Default style
		{
			g.setColor(c.getBackground().brighter());
			g.fillRect(0, 0, size.width, size.height);
			g.setColor(c.getBackground());
			g.fillPolygon(xInner, yInner, 6);
		}
	}
}
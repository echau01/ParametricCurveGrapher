package echau.gui.butterflycurve;

import javax.swing.JFrame;
import javax.swing.Timer;

import java.awt.Color;
import java.awt.Container;

@SuppressWarnings("serial")
public class Gui extends JFrame {
	/* Constants */
	private static final boolean IS_RESIZABLE = false;
	private static final Color BACKGROUND_COLOUR = Color.WHITE;
	
	private ButterflyCurve curve;
	private Screen screen;
	
	public Gui() {
		curve = new ButterflyCurve(0, 2 * Math.PI);
		screen = new Screen(curve);
		this.add(screen);
	}
	
	public void setUpGui() {
		Container c = this.getContentPane();
		c.setBackground(BACKGROUND_COLOUR);
		this.setResizable(IS_RESIZABLE);
		this.pack();
		this.setTitle("Butterfly Curve");
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
		new Timer(1, screen.getPainter()).start();
	}
	
	public ButterflyCurve getCurve() {
		return curve;
	}
	
	public Screen getScreen() {
		return screen;
	}
}

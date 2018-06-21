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
	
	/** 
	 * The amount that {@link ButterflyCurve#getCurrentT()} will be incremented by every time the
	 * Timer ticks (once per millisecond).
	 * 
	 * <br>
	 * <br>
	 * 
	 * Setting the value of this constant to be greater than the difference between the upper and lower
	 * bounds of the parametric curve's t value will cause problems. In other words do not make
	 * the value of this constant greater than 
	 * {@code this.getCurve().getTUpperBound() - this.getCurve().getTLowerBound()}
	 */
	public static final double T_INCREMENT = 0.003;
	
	public Gui() {
		// here, we subtract the preferred lower bound by T_LOWER_BOUND_EPSILON, and pass the
		// result into the ButterflyCurve constructor
		curve = new ButterflyCurve(0, 6.2835);
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

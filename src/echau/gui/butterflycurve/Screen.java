package echau.gui.butterflycurve;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

@SuppressWarnings("serial")
/**
 * The Screen renders the ParametricCurve.
 */
public class Screen extends JPanel {
	/* Size of the window depends on these constants */
	private static final int WIDTH = 1024;
	private static final int HEIGHT = 780;
	
	// The curve being drawn on the screen
	private ButterflyCurve curve;

	// The ActionListener that paints the screen
	private final ActionListener painter;

	/* 
	 * An integer from 0 to 2. Each time the curve changes direction, the cycle number increases.
	 * Every number corresponds to a certain colour with which the curve is drawn.
	 */
	private int currentCycle;

	/* Cycle numbers */
	private static final int RED_CYCLE = 0;
	private static final int GREEN_CYCLE = 1;
	private static final int BLUE_CYCLE = 2;

	// REMEMBER TO CHANGE THIS WHEN YOU ADD MORE CYCLES!
	private static final int NUMBER_OF_CYCLES = 3;
	
	/**
	 * Construct a Screen on which the specified ParametricCurve will be drawn.
	 */
	public Screen(ButterflyCurve curve) {
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.painter = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				repaint();
			}
		};

		this.curve = curve;
		this.currentCycle = 0;
	}

	@Override
	public void paintComponent(Graphics g) {
		curve.update();

		this.setCurveColour(g);
		
		if (curve.isChangingDirection()) {
			// Change cycle
			if (currentCycle == NUMBER_OF_CYCLES - 1) {
				currentCycle = 0;
			} else {
				currentCycle++;
			}
		}

		g.fillOval((int) (curve.getCurrentXCoord() * 100 + WIDTH * 0.5), (int) (curve.getCurrentYCoord() * -120 + HEIGHT * 0.6), 10, 10);
	}
	
	private void setCurveColour(Graphics g) {
		if (currentCycle == RED_CYCLE) {
			g.setColor(Color.RED);
		} else if (currentCycle == GREEN_CYCLE) {
			g.setColor(Color.GREEN);
		} else if (currentCycle == BLUE_CYCLE) {
			g.setColor(Color.BLUE);
		}
	}

	/**
	 * Returns the ActionListener that paints the Screen.
	 */
	public ActionListener getPainter() {
		return this.painter;
	}
}

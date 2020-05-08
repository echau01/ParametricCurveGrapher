package echau.gui.butterflycurve;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

/**
 * The Screen renders the ButterflyCurve.
 */
@SuppressWarnings("serial")
public class Screen extends JPanel {
	/* Size of the window depends on these constants */
	private static final int SCREEN_WIDTH = 1024;
	private static final int SCREEN_HEIGHT = 780;
	
	// The curve being drawn on the screen
	private ButterflyCurve curve;

	// The ActionListener that paints the screen
	private final ActionListener painter;

	// The colour that the curve is currently being drawn with
	private CurveColour curveColour;

	// All possible colours that the curve can be drawn with
	private CurveColour[] colours = CurveColour.values();

	private static final int CURVE_WIDTH = 10;
	private static final int CURVE_HEIGHT = 10;
	
	/**
	 * Construct a Screen on which the specified ButterflyCurve will be drawn.
	 */
	public Screen(ButterflyCurve curve) {
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.painter = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				repaint();
			}
		};

		this.curve = curve;
		curveColour = CurveColour.RED;
	}

	@Override
	public void paintComponent(Graphics g) {
		curve.update();

		this.setCurveColour(g);
		
		if (curve.willChangeDirection()) {
			if (curveColour == colours[colours.length - 1]) {
				curveColour = colours[0];
			} else {
				// Set curveColour to the next colour in the colours array
				curveColour = colours[curveColour.ordinal() + 1];
			}
		}

		g.fillOval((int) (curve.getCurrentXCoord() * 100 + SCREEN_WIDTH * 0.5),
				(int) (curve.getCurrentYCoord() * -120 + SCREEN_HEIGHT * 0.6),
				CURVE_WIDTH, CURVE_HEIGHT);
	}
	
	private void setCurveColour(Graphics g) {
		if (curveColour == CurveColour.RED) {
			g.setColor(Color.RED);
		} else if (curveColour == CurveColour.GREEN) {
			g.setColor(Color.GREEN);
		} else if (curveColour == CurveColour.BLUE) {
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

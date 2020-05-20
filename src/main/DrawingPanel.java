package main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;

import javax.swing.JPanel;

/**
 * The DrawingPanel renders the parametric curve.
 */
@SuppressWarnings("serial")
public class DrawingPanel extends JPanel {
	private static final int SCREEN_WIDTH = 1024;
	private static final int SCREEN_HEIGHT = 780;

	private static final Color BACKGROUND_COLOUR = Color.WHITE;

	// Width and height of the oval used to fill in each point on the curve
	private static final int CURVE_WIDTH = 10;
	private static final int CURVE_HEIGHT = 10;

	// The singleton instance of the drawing panel
	private static DrawingPanel instance;
	
	// The curve being drawn on the drawing panel
	private ParametricCurve curve;

	// The ActionListener that paints the drawing panel
	private final ActionListener painter;

	// The colour that the curve is currently being drawn with
	private CurveColour curveColour;

	// All possible colours that the curve can be drawn with
	private CurveColour[] colours = CurveColour.values();

	// A list of the curve's points in the increasing direction. May
	// contain null (to denote an undefined point).
	private ArrayList<ColouredPoint> increasingList;

	// A list of the curve's points in the decreasing direction. May
	// contain null (to denote an undefined point).
	private ArrayList<ColouredPoint> decreasingList;

	/* The current index of the list that we are currently adding
	 * ColouredPoints into. When a new parametric curve is just starting
	 * to be drawn, currentIndex is initially set to -1 because
	 * paintComponent() increments currentIndex if the curve is not changing
	 * direction. Thus, right before the curve starts to be drawn,
	 * currentIndex is incremented to 0 (its expected value).
	 */
	private int currentIndex;

	// Indicates whether one full cycle of the curve has been traced out
	// (i.e. whether the curve has been fully drawn in both the increasing
	// and decreasing t directions)
	private boolean oneCycleComplete;

	/**
	 * Represents a point drawn with a specific colour.
	 */
	private static class ColouredPoint {
		Point point;
		CurveColour colour;

		ColouredPoint(Point point, CurveColour colour) {
			this.point = point;
			this.colour = colour;
		}
	}

	private DrawingPanel() {
		curveColour = CurveColour.RED;
		this.setBackground(BACKGROUND_COLOUR);
		painter = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				repaint();
			}
		};
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));

		increasingList = new ArrayList<ColouredPoint>(ParametricCurve.APPROX_NUM_POINTS);
		decreasingList = new ArrayList<ColouredPoint>(ParametricCurve.APPROX_NUM_POINTS);

		// currentIndex is set to -1 because just before the curve starts being
		// drawn, paintComponent() will increment currentIndex by 1, making
		// currentIndex equal 0 as expected.
		currentIndex = -1;
	}

	/**
	 * @return the singleton instance of this DrawingPanel.
	 */
	public static DrawingPanel getInstance() {
		if (instance != null) {
			return instance;
		}
		instance = new DrawingPanel();
		return instance;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (curve == null) {
			return;
		}

		curve.update();

		if (curve.isChangingDirection()) {
			if (curveColour == colours[colours.length - 1]) {
				curveColour = colours[0];
			} else {
				// Set curveColour to the next colour in the colours array
				curveColour = colours[curveColour.ordinal() + 1];
			}
			currentIndex = 0;
		} else {
			currentIndex++;
		}

		// currentX and currentY are the actual screen coordinates that the points
		// are drawn at.
		int currentX = (int) (curve.getCurrentXCoord() * 100 + SCREEN_WIDTH * 0.5);
		int currentY = (int) (curve.getCurrentYCoord() * -100 + SCREEN_HEIGHT * 0.5);
		Point currentPoint = new Point(currentX, currentY);
		ColouredPoint listEntry;

		// Add the current point and its colour to the appropriate list (depending
		// on whether t is currently increasing or decreasing). Note that if the x
		// or y coordinate (actual coordinates; not screen coordinates) of the
		// current point is NaN, null is added to the appropriate list instead.
		if (Double.isNaN(curve.getCurrentXCoord())
				|| Double.isNaN(curve.getCurrentYCoord())) {
			listEntry = null;
		} else {
			listEntry = new ColouredPoint(currentPoint, curveColour);
		}

		if (oneCycleComplete) {
			// If one full cycle has been completed, we can override the point
			// in the appropriate list at currentIndex with our new listEntry.
			if (curve.isTIncreasing()) {
				increasingList.set(currentIndex, listEntry);
			} else {
				decreasingList.set(currentIndex, listEntry);
			}
		} else {
			if (curve.isTIncreasing()) {
				increasingList.add(listEntry);
			} else {
				decreasingList.add(listEntry);
			}
		}

		/* Below is the code for drawing points.
		 *
		 * At any given time, we want to draw the first currentIndex + 1 points of
		 * either increasingList or decreasingList (depending on whether t is increasing or
		 * decreasing). We also want to *exclude* the last currentIndex + 1 points of
		 * the other list from being drawn. For example, suppose increasingList has 998
		 * elements, decreasingList has 1000 elements, currentIndex is 400, and t is
		 * increasing. We want to draw the first 400 points of increasingList (as they are
		 * the most recently calculated points), and we want to exclude the last 400 points
		 * of decreasingList from being drawn (because the first 400 points of increasingList
		 * are drawn in place of the last 400 points of decreasingList).
		 */
		if (curve.isTIncreasing()) {
			for (int i = 0; i <= currentIndex; i++) {
				ColouredPoint colouredPoint = increasingList.get(i);
				if (colouredPoint != null) {
					this.setCurveColour(g, colouredPoint.colour);
					g.fillOval(colouredPoint.point.x, colouredPoint.point.y, CURVE_WIDTH, CURVE_HEIGHT);
				}
			}
			for (int i = 0; i <= decreasingList.size() - currentIndex - 1; i++) {
				ColouredPoint colouredPoint = decreasingList.get(i);
				if (colouredPoint != null) {
					this.setCurveColour(g, colouredPoint.colour);
					g.fillOval(colouredPoint.point.x, colouredPoint.point.y, CURVE_WIDTH, CURVE_HEIGHT);
				}
			}
		} else {
			for (int i = 0; i <= currentIndex; i++) {
				ColouredPoint colouredPoint = decreasingList.get(i);
				if (colouredPoint != null) {
					this.setCurveColour(g, colouredPoint.colour);
					g.fillOval(colouredPoint.point.x, colouredPoint.point.y, CURVE_WIDTH, CURVE_HEIGHT);
				}
			}
			for (int i = 0; i <= increasingList.size() - currentIndex - 1; i++) {
				ColouredPoint colouredPoint = increasingList.get(i);
				if (colouredPoint != null) {
					this.setCurveColour(g, colouredPoint.colour);
					g.fillOval(colouredPoint.point.x, colouredPoint.point.y, CURVE_WIDTH, CURVE_HEIGHT);
				}
			}
		}

		if (curve.isChangingDirection() && curve.isTIncreasing() && !oneCycleComplete) {
			oneCycleComplete = true;
		}
	}

	/**
	 * Set the colour being used to draw the curve according to the given
	 * CurveColour.
	 */
	private void setCurveColour(Graphics g, CurveColour colour) {
		if (colour == CurveColour.RED) {
			g.setColor(Color.RED);
		} else if (colour == CurveColour.GREEN) {
			g.setColor(Color.GREEN);
		} else if (colour == CurveColour.BLUE) {
			g.setColor(Color.BLUE);
		}
	}

	/**
	 * @return the ActionListener that paints the DrawingPanel.
	 */
	public ActionListener getPainter() {
		return this.painter;
	}

	/**
	 * Sets the parametric curve to be drawn on the DrawingPanel.
	 */
	public void setCurve(ParametricCurve curve) {
		this.curve = curve;
	}

	/**
	 * Clears the drawing panel the next time it is painted. The colour
	 * that the painter uses to draw the curve is also reset to the first
	 * colour in the CurveColour enum (i.e. CurveColours.values()[0]).
	 */
	public void clearPanel() {
		curveColour = colours[0];
		increasingList.clear();
		decreasingList.clear();

		// currentIndex is set to -1 because just before the curve starts being
		// drawn, paintComponent() will increment currentIndex by 1, making
		// currentIndex equal 0 as expected.
		currentIndex = -1;
		oneCycleComplete = false;
	}
}

package main;

import java.util.Queue;

/**
 * This class represents a parametric curve. This class tells the DrawingPanel where to
 * render the curve at a certain time. The main significant method in this class is {@link #update()}.
 */
public class ParametricCurve {
	// Note: setting APPROX_NUM_POINTS to anything more than 1000 makes the curve get traced
	// out remarkably slowly.
	/**
	 * The (approximate) number of points of the parametric curve that this program
	 * will plot (give or take a few points due to imprecision in floating-point
	 * arithmetic) each time the curve is traced out in one direction.
	 */
	public static final short APPROX_NUM_POINTS = 1000;

	/* Current x- and y-coordinates of the curve */
	private double xCoord;
	private double yCoord;
	
	/** t is the "time" variable in parametric equations. */
	private double t;

	private Queue<String> xPostfixQueue;
	private Queue<String> yPostfixQueue;

	/* Lower and upper bounds of t */
	private final double tLowerBound;
	private final double tUpperBound;

	// The amount by which the t field is incremented every time the Timer ticks.
	private final double tIncrement;

	// Used to compare the floating-point value of t to tLowerBound and tUpperBound.
	private final double epsilon;
	
	// Indicates whether the value of t is increasing
	private boolean tIncreasing;

	// Indicates whether t will encounter an upper or lower bound on the next clock tick.
	private boolean willChangeDirection;

	/* Indicates whether the curve is changing direction. In order for the curve to change
	 * direction, the following two conditions must be met:
	 * 1. The t variable is at an upper or lower bound.
	 * 2. The curve has been traced out completely in one direction. In other words, the
	 *    curve should not change direction when it is being traced out for the first time
	 *    (otherwise, since the initial value of t is tLowerBound, the curve will be stuck
	 *    at that lower bound, constantly changing direction).
	 *
	 * If willChangeDirection is false, changingDirection must be false.
	 */
	private boolean changingDirection;
	
	/**
	 * Creates a ParametricCurve with the x-coordinate and y-coordinate at time t being given
	 * by xExpression and yExpression, respectively. The t variable obeys the specified lower
	 * and upper bounds. The initial value of t is tLowerBound, and t will start off increasing.
	 *
	 * @throws IllegalArgumentException if xExpression or yExpression is invalid, or if
	 * tLowerBound > tUpperBound
	 */
	public ParametricCurve(String xExpression, String yExpression, double tLowerBound, double tUpperBound) {
		if (tLowerBound > tUpperBound) {
			throw new IllegalArgumentException("tLowerBound cannot be greater than tUpperBound");
		}
		this.tLowerBound = tLowerBound;
		this.tUpperBound = tUpperBound;
		t = tLowerBound;
		tIncreasing = true;
		willChangeDirection = false;
		changingDirection = false;
		tIncrement = (tUpperBound - tLowerBound) / APPROX_NUM_POINTS;
		epsilon = tIncrement / 2;

		xPostfixQueue = EquationParser.infixToPostfix(xExpression);
		yPostfixQueue = EquationParser.infixToPostfix(yExpression);
	}
	
	/**
	 * Updates the state of the curve. This method is called on every clock tick.
	 */
	public void update() {
		// Change the values of t, tIncreasing, and changingDirection according to
		// the current values of tIncreasing and willChangeDirection.
		// If t is sufficiently close to an upper or lower bound (such that t would
		// equal or exceed the bound on the next clock tick), then the value
		// of t is set to the value of that boundary.

		// The block of if statements below works with the updateWillChangeDirection()
		// method to guarantee that t stays in the range tLowerBound <= t <= tUpperBound.
		if (tIncreasing) {
			if (willChangeDirection) {
				tIncreasing = false;
				t = tUpperBound;
				changingDirection = true;
			} else {
				t += tIncrement;
				changingDirection = false;
			}
		} else {
			if (willChangeDirection) {
				tIncreasing = true;
				t = tLowerBound;
				changingDirection = true;
			} else {
				t -= tIncrement;
				changingDirection = false;
			}
		}

		updateWillChangeDirection();

		xCoord = EquationParser.evaluate(xPostfixQueue, t);
		yCoord = EquationParser.evaluate(yPostfixQueue, t);
	}
	
	/**
	 * Checks whether t will encounter an upper or lower bound on the next
	 * clock tick. This method changes the value of willChangeDirection
	 * accordingly.
	 */
	private void updateWillChangeDirection() {
		if (!changingDirection) {
			if (tIncreasing && t + tIncrement >= tUpperBound - epsilon) {
				willChangeDirection = true;
			} else if (!tIncreasing && t - tIncrement <= tLowerBound + epsilon) {
				willChangeDirection = true;
			}
		} else {
			willChangeDirection = false;
		}
	}
	
	/**
	 * @return the current value of the t variable
	 */
	public double getCurrentT() {
		return t;
	}
	
	/**
	 * @return the current x-coordinate of the curve
	 */
	public double getCurrentXCoord() {
		return xCoord;
	}
	
	/**
	 * @return the current y-coordinate of the curve
	 */
	public double getCurrentYCoord() {
		return yCoord;
	}
	
	/**
	 * @return the lower bound of the t variable
	 */
	public double getTLowerBound() {
		return tLowerBound;
	}
	
	/**
	 * @return the upper bound of the t variable
	 */
	public double getTUpperBound() {
		return tUpperBound;
	}
	
	/**
	 * @return true if the value of t is increasing; false otherwise
	 */
	public boolean isTIncreasing() {
		return tIncreasing;
	}

	/**
	 * @return true if t will be at an upper or lower bound on the next
	 * clock tick; false otherwise
	 */
	public boolean willChangeDirection() {
		return willChangeDirection;
	}

	/**
	 * @return true if t is at an upper or lower bound and is changing
	 * direction
	 */
	public boolean isChangingDirection() {
		return changingDirection;
	}
}

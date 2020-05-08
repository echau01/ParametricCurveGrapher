package echau.gui.butterflycurve;

/**
 * This class represents the actual butterfly curve. This class tells the Screen class where to
 * render the curve at a certain time. The main significant method in this class is {@link #update()}.
 * <br>
 * <br>
 * According to Wikipedia, the butterfly curve is a parametric function defined as:
 * <br>
 * <br>
 * {@code x(t) = [sin(t)][exp(cos(t)) - 2cos(4t) - (sin(t / 12))^5]}
 * <br>
 * {@code y(t) = [cos(t)][exp(cos(t)) - 2cos(4t) - (sin(t / 12))^5]}
 *
 * @see
 * <a href="https://en.wikipedia.org/wiki/Butterfly_curve_(transcendental)">
 *     https://en.wikipedia.org/wiki/Butterfly_curve_(transcendental)
 * </a>
 */
public class ButterflyCurve {
	/* Current x- and y-coordinates of the curve */
	private double xCoord;
	private double yCoord;
	
	/** t is the "time" variable in parametric equations. */
	private double t;
	
	/* Lower and upper bounds of t */
	private final double tLowerBound;
	private final double tUpperBound;

	// The (approximate) number of points of the butterfly curve that this program will plot (give or take a few points
	// due to imprecision in floating-point arithmetic) each time t goes from tLowerBound to tUpperBound (or vice versa).
	// CONSTRAINT: MUST BE POSITIVE.
	private static final int APPROX_NUM_POINTS = 5000;

	// The amount by which the t field is incremented every time the Timer ticks (once per millisecond).
	private final double tIncrement;

	// Used to compare the floating-point value of t to tLowerBound and tUpperBound.
	private final double epsilon;
	
	// Indicates whether the value of t is increasing
	private boolean tIncreasing;

	// Indicates whether the curve will change direction on the next clock tick.
	private boolean willChangeDirection;

	/* Represents whether the curve is in the process of changing direction.
	 * If willChangeDirection is false, changingDirection must be false.
	 *
	 * Note: It takes two clock ticks for the curve to fully change direction.
	 */
	private boolean changingDirection;
	
	/**
	 * Creates a ButterflyCurve with the specified lower and upper bound for t. This ButterflyCurve
	 * will have an initial t value of tLowerBound, and t will start off increasing.
	 */
	public ButterflyCurve(double tLowerBound, double tUpperBound) {
		this.tLowerBound = tLowerBound;
		this.tUpperBound = tUpperBound;
		t = tLowerBound;
		tIncreasing = true;
		willChangeDirection = false;
		changingDirection = false;
		tIncrement = (tUpperBound - tLowerBound) / APPROX_NUM_POINTS;
		epsilon = tIncrement / 2;
	}
	
	/**
	 * Updates the state of the curve. This method is called on every clock tick.
	 */
	public void update() {
		// Change the values of t, tIncreasing, and changingDirection according to
		// the current values of tIncreasing and willChangeDirection.
		// If t is close to a boundary (tLowerBound or tUpperBound), then the value
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
		double multiplier = Math.pow(Math.E, Math.cos(t)) - 2 * Math.cos(4 * t) - Math.pow(Math.sin(t / 12), 5);
		xCoord = Math.sin(t) * multiplier;
		yCoord = Math.cos(t) * multiplier;
	}
	
	/**
	 * Checks whether the curve will change direction on the next tick. This method changes the value of
	 * willChangeDirection accordingly.
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
	 * @return The current value of the t variable of the curve
	 */
	public double getCurrentT() {
		return t;
	}
	
	/**
	 * @return The current x-coordinate of the curve.
	 */
	public double getCurrentXCoord() {
		return xCoord;
	}
	
	/**
	 * @return The current y-coordinate of the curve.
	 */
	public double getCurrentYCoord() {
		return yCoord;
	}
	
	/**
	 * @return The lower bound of the t variable.
	 */
	public double getTLowerBound() {
		return tLowerBound;
	}
	
	/**
	 * @return The upper bound of the t variable.
	 */
	public double getTUpperBound() {
		return tUpperBound;
	}
	
	/**
	 * @return A boolean indicating whether the value of t is increasing
	 */
	public boolean isTIncreasing() {
		return tIncreasing;
	}

	/**
	 * @return A boolean indicating whether the curve will reverse its direction on the next tick.
	 */
	public boolean willChangeDirection() {
		return willChangeDirection;
	}
}

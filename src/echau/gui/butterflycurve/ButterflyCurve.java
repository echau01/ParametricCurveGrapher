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

	/* The amount by which the t field is incremented every time the Timer ticks (once per millisecond).
	 *
	 * <br>
	 * <br>
	 *
	 * Setting the value of this constant to be greater than the difference between the upper and lower
	 * bounds of the parametric curve's t value will cause problems. In other words do not make
	 * the value of this constant greater than tUpperBound() - tLowerBound().
	 */
	private static final double T_INCREMENT = 0.003;

	// Used to compare the floating-point value of t to tLowerBound and tUpperBound.
	private static final double EPSILON = T_INCREMENT;
	
	// The array storing all coordinates of the function within the domain T_LOWER_BOUND <= t <= T_UPPER_BOUND
	private double[][] coordinateArray;

	// Current index of the coordinate array being accessed
	private int currentIndex;
	
	// Indicates whether the value of t is increasing
	private boolean tIncreasing;
	
	// Indicates whether the curve is about to change direction; i.e. whether t is at a lower or upper bound
	private boolean changingDirection;
	
	/**
	 * Creates a ButterflyCurve with the specified lower and upper bound for t.
	 */
	public ButterflyCurve(double tLowerBound, double tUpperBound) {
		this.tLowerBound = tLowerBound;
		this.tUpperBound = tUpperBound;
		
		if (tUpperBound - tLowerBound < T_INCREMENT) {
			// Guarantees that the initial value of currentIndex is within the array
			this.t = (tUpperBound + tLowerBound) / 2;
		} else {
			this.t = tLowerBound + 0.01;
		}
		
		currentIndex = (int) ((t - tLowerBound) / T_INCREMENT);
		
		this.generateCoordinateArray();
		tIncreasing = true;
	}
	
	/**
	 * Updates the state of the curve. First, the values of t and currentIndex are updated, then the
	 * next x- and y-coordinates are fetched and assigned to the variables xCoord and yCoord.
	 */
	public void update() {
		updateTAndCurrentIndex();
		checkDirection();
		xCoord = coordinateArray[currentIndex][0];
		yCoord = coordinateArray[currentIndex][1];
	}
	
	/**
	 * Updates the values of t and currentIndex
	 */
	private void updateTAndCurrentIndex() {
		if (tIncreasing) {
			currentIndex++;
			t += T_INCREMENT;
		} else {
			currentIndex--;
			t -= T_INCREMENT;
		}
	}
	
	/**
	 * Checks whether the curve is about to change direction (i.e. whether {@code t} is at an upper or lower bound).
	 * This method changes the values of goingClockwise and changingDirection accordingly.
	 */
	private void checkDirection() {
		if (t <= tLowerBound + EPSILON || t >= tUpperBound - EPSILON) {
			tIncreasing = !tIncreasing;
			changingDirection = true;
			return;
		}
		changingDirection = false;
	}

	/**
	 * This method is to be used for pre-generating the coordinate arrays for efficiency.
	 */
	private void generateCoordinateArray() {
		// We add 10 extra spaces to the end of coordinateArray to prevent ArrayIndexOutOfBounds exceptions.
		// Floating-point arithmetic is dodgy
		coordinateArray = new double[10 + (int) ((tUpperBound - tLowerBound) / T_INCREMENT)][2];
		for (double i = tLowerBound; i <= tUpperBound; i += T_INCREMENT) {
			// This is where the parametric equation goes
			// Casting to an int always rounds down, so we add 0.001 to prevent floating-point arithmetic from screwing things up
			coordinateArray[(int) ((i - tLowerBound + 0.001) / T_INCREMENT)][0] = Math.sin(i) * (Math.pow(Math.E, Math.cos(i)) - 2 * Math.cos(4 * i) - Math.pow(Math.sin(i / 12), 5));
			coordinateArray[(int) ((i - tLowerBound + 0.001) / T_INCREMENT)][1] = Math.cos(i) * (Math.pow(Math.E, Math.cos(i)) - 2 * Math.cos(4 * i) - Math.pow(Math.sin(i / 12), 5));
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
	 * @return The current index of the coordinate array.
	 */
	public int getCurrentIndex() {
		return currentIndex;
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
	public boolean isChangingDirection() {
		return changingDirection;
	}
}

package echau.gui.butterflycurve;

import javax.swing.SwingUtilities;

/**
 * This project currently features a prototype parametric function - the butterfly function. Allowing
 * users to input their own parametric functions is the next step.
 */
public class Main {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new Gui().setUpGui();
			}
		});
	}
}

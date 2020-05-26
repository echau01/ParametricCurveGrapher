package ui;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings("serial")
public class Gui extends JFrame {
	/* Constants */
	private static final boolean IS_RESIZABLE = false;
	
	public Gui() {
		Container pane = this.getContentPane();

		this.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));

		pane.add(EquationPanel.getInstance());
		pane.add(DrawingPanel.getInstance());
	}
	
	public void setUpGui() {
		this.setResizable(IS_RESIZABLE);
		this.pack();
		this.setTitle("Parametric Curve Grapher");
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);

		new Timer(1, DrawingPanel.getInstance().getPainter()).start();
	}
}

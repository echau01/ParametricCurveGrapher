package ui;

import curve.ParametricCurve;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The EquationPanel is the area where the user inputs the
 * parametric equations for x and y, and sets the lower and
 * upper bounds for the t variable.
 */
public class EquationPanel extends JPanel {
    // The singleton instance of the equation panel
    private static EquationPanel instance;

    private GroupLayout layout;

    private JLabel xEqualsLabel;
    private JLabel yEqualsLabel;
    private JLabel tLowerBoundLabel;
    private JLabel tUpperBoundLabel;
    private JLabel errorLabel;

    private JTextField xTextField;
    private JTextField yTextField;
    private JTextField tLowerBoundTextField;
    private JTextField tUpperBoundTextField;

    private JButton drawCurveButton;

    private EquationPanel() {
        layout = new GroupLayout(this);
        this.setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        xEqualsLabel = new JLabel("x(t) = ");
        yEqualsLabel = new JLabel("y(t) = ");
        tLowerBoundLabel = new JLabel("t lower bound = ");
        tUpperBoundLabel = new JLabel("t upper bound = ");
        drawCurveButton = new JButton("Draw curve!");
        drawCurveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DrawingPanel drawingPanel = DrawingPanel.getInstance();
                try {
                    String xExpression = xTextField.getText();
                    String yExpression = yTextField.getText();
                    double tLowerBound = Double.parseDouble(tLowerBoundTextField.getText());
                    double tUpperBound = Double.parseDouble(tUpperBoundTextField.getText());

                    drawingPanel.setCurve(new ParametricCurve(xExpression, yExpression, tLowerBound, tUpperBound));
                    errorLabel.setVisible(false);
                    drawingPanel.clearPanel();
                } catch (IllegalArgumentException ex) {
                    errorLabel.setVisible(true);
                    drawingPanel.setCurve(null);
                }
            }
        });

        xTextField = new JTextField(10);
        yTextField = new JTextField(10);
        tLowerBoundTextField = new JTextField(10);
        tUpperBoundTextField = new JTextField(10);
        errorLabel = new JLabel("Check your inputs.");
        errorLabel.setVisible(false);

        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(xEqualsLabel)
                        .addComponent(yEqualsLabel)
                        .addComponent(tLowerBoundLabel)
                        .addComponent(tUpperBoundLabel)
                        .addComponent(drawCurveButton))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(xTextField)
                        .addComponent(yTextField)
                        .addComponent(tLowerBoundTextField)
                        .addComponent(tUpperBoundTextField)
                        .addComponent(errorLabel))
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(xEqualsLabel)
                        .addComponent(xTextField))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(yEqualsLabel)
                        .addComponent(yTextField))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(tLowerBoundLabel)
                        .addComponent(tLowerBoundTextField))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(tUpperBoundLabel)
                        .addComponent(tUpperBoundTextField))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(drawCurveButton)
                        .addComponent(errorLabel))
        );
    }

    public static EquationPanel getInstance() {
        if (instance != null) {
            return instance;
        }
        instance = new EquationPanel();
        return instance;
    }
}

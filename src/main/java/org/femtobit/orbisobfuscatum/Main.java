package org.femtobit.orbisobfuscatum;

import boofcv.abst.feature.associate.ScoreAssociateSad;
import org.femtobit.orbisobfuscatum.frame.OMFrame;
import org.femtobit.orbisobfuscatum.frame.PaintPanel;
import org.femtobit.orbisverto.canny.Canny;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;

public class Main {

    static Canny canny;

    static int[] sourceArr;
    static int sourceWidth;
    static int sourceHeight;

    static int[] destArr;
    static int destWidth;
    static int destHeight;
    static BufferedImage destImage;

    static int kernelSize = 3;
    static float deviation = 1;
    static int thresholdHigh = 10;
    static int thresholdLow = 1;
    static float scale = 1;
    static int offset = 0;

    private static JPanel parent = new JPanel();
    private static PaintPanel paint = new PaintPanel(0);

    private static GridBagLayout parentLayout = new GridBagLayout();
    private static GridBagConstraints parentConstraints = new GridBagConstraints();

    private static long timeMeasure;
    private static JTextField timeTaken = new JTextField(20);
    private static JLabel time = new JLabel("Time for detection:");

    private static JComboBox sizeBox = new JComboBox();

    private static ActionListener sizeListener = e ->
        kernelSize = ((JComboBox) e.getSource()).getSelectedIndex() + 3;

    private static JLabel sizeLabel = new JLabel("Kernel Size:");
    private static JLabel thetaLabel = new JLabel("Standard Deviation:");
    private static JLabel thresholdHighLabel = new JLabel("High threshold");
    private static JLabel thresholdLowLabel = new JLabel("Low Threshold:");
    private static JLabel scaleLabel = new JLabel("Scale");
    private static JLabel offsetLabel = new JLabel("Offset");
    private static JLabel infoLabel = new JLabel("Left click: draw, Right click: clear, Space: Canny");

    private static JTextField thetaValue = new JTextField(5);
    private static JTextField thresholdHighValue = new JTextField(5);
    private static JTextField thresholdLowValue = new JTextField(5);
    private static JTextField scaleValue = new JTextField(5);
    private static JTextField offsetValue = new JTextField(5);

    private static void initialiseLayout() {
        parent.setLayout(parentLayout);
        parent.setBackground(Color.white);

        //parentConstraints.weighty = 0.5;
        parentConstraints.gridx = 0;
        parentConstraints.gridy = 0;
        parentLayout.setConstraints(sizeLabel, parentConstraints);
        parent.add(sizeLabel);

        parentConstraints.gridx = 1;
        parentConstraints.gridy = 0;
        sizeBox.addItem("3x3");
        sizeBox.addItem("4x4");
        sizeBox.addItem("5x5");
        sizeBox.addItem("6x6");
        sizeBox.addItem("7x7");
        parentLayout.setConstraints(sizeBox, parentConstraints);
        parent.add(sizeBox);
        sizeBox.addActionListener(sizeListener);

        parentConstraints.gridx = 0;
        parentConstraints.gridy = 1;
        parentLayout.setConstraints(thetaLabel, parentConstraints);
        parent.add(thetaLabel);

        parentConstraints.gridx = 1;
        parentConstraints.gridy = 1;
        parentLayout.setConstraints(thetaValue, parentConstraints);
        thetaValue.setText("0.45");
        parent.add(thetaValue);

        parentConstraints.gridx = 0;
        parentConstraints.gridy = 2;
        parentLayout.setConstraints(thresholdHighLabel, parentConstraints);
        parent.add(thresholdHighLabel);

        parentConstraints.gridx = 1;
        parentConstraints.gridy = 2;
        parentLayout.setConstraints(thresholdHighValue, parentConstraints);
        thresholdHighValue.setText("10");
        parent.add(thresholdHighValue);

        parentConstraints.gridx = 0;
        parentConstraints.gridy = 3;
        parentLayout.setConstraints(thresholdLowLabel, parentConstraints);
        parent.add(thresholdLowLabel);

        parentConstraints.gridx = 1;
        parentConstraints.gridy = 3;
        parentLayout.setConstraints(thresholdLowValue, parentConstraints);
        thresholdLowValue.setText("1");
        parent.add(thresholdLowValue);

        parentConstraints.gridx = 0;
        parentConstraints.gridy = 4;
        parentLayout.setConstraints(scaleLabel, parentConstraints);
        parent.add(scaleLabel);

        parentConstraints.gridx = 1;
        parentConstraints.gridy = 4;
        parentLayout.setConstraints(scaleValue, parentConstraints);
        scaleValue.setText("1.0");
        parent.add(scaleValue);

        parentConstraints.gridx = 0;
        parentConstraints.gridy = 5;
        parentLayout.setConstraints(offsetLabel, parentConstraints);
        parent.add(offsetLabel);

        parentConstraints.gridx = 1;
        parentConstraints.gridy = 5;
        parentLayout.setConstraints(offsetValue, parentConstraints);
        offsetValue.setText("0");
        parent.add(offsetValue);

        parentConstraints.gridx = 0;
        parentConstraints.gridy = 6;
        parentLayout.setConstraints(infoLabel, parentConstraints);
        parent.add(infoLabel);

        parentConstraints.gridy = 7;
        parentLayout.setConstraints(paint, parentConstraints);
        parent.add(paint);
        paint.refresh();

        parent.repaint();

    }

    public static void main(String[] args) {
        initialiseLayout();
        SwingUtilities.invokeLater(() -> {
            OMFrame frame = new OMFrame("Orbis Obfuscatum");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(parent);

            frame.pack();
            frame.setVisible(true);
        });
    }

    public static void CannyImage() {
        /*
        timeMeasure = System.currentTimeMillis();

        try {
            scale = Float.valueOf(scaleValue.getText()).floatValue();
            offset = Integer.valueOf(offsetValue.getText()).intValue();
            deviation = Float.valueOf(thetaValue.getText()).floatValue();

            if(deviation < 0.4 || deviation > (kernelSize / 6)) {
                JOptionPane.showMessageDialog(null, "Standard Deviation must be between 0.4 < x < kernelSize / 6", "Error", JOptionPane.WARNING_MESSAGE);
            } else {

                thresholdHigh = Integer.valueOf(thresholdHighValue.getText()).intValue();
                thresholdLow = Integer.valueOf(thresholdLowValue.getText()).intValue();

                destArr = canny.process(sourceArr, sourceWidth, sourceHeight, kernelSize, deviation, thresholdLow, thresholdHigh, scale, offset);
                timeMeasure = System.currentTimeMillis() - timeMeasure;
                timeTaken.setText(new Long(timeMeasure).toString() + " millis");

                destImage = ImageConverter.convertImage(Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(destWidth, destHeight, destArr, 0, destWidth)));
                paint.setImage(destImage);


            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid parameters", "Error", JOptionPane.WARNING_MESSAGE);
        } */
    }
}

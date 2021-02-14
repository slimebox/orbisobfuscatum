package org.femtobit.orbisobfuscatum;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Orbis Obfuscatum");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JLabel label = new JLabel("sup beeb");
            frame.getContentPane().add(label);
            frame.pack();
            frame.setVisible(true);
        });
    }
}

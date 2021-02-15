package org.femtobit.orbisobfuscatum;

import org.femtobit.orbisobfuscatum.frame.OMFrame;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            OMFrame frame = new OMFrame("Orbis Obfuscatum");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
        });
    }
}

package org.femtobit.orbisobfuscatum.frame;

import javax.swing.*;
import java.awt.*;

public class OMFrame extends JFrame {

    public OMFrame(String name) {
        super(name);

        JLabel label = new JLabel("sup beeb");
        getContentPane().add(label);

        PaintPanel panel = new PaintPanel(0, this);
        getContentPane().add(panel);
    }

}

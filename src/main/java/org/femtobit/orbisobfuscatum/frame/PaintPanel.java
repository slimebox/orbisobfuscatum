package org.femtobit.orbisobfuscatum.frame;

import org.femtobit.orbisobfuscatum.Main;
import org.femtobit.orbisobfuscatum.shape.OMShape;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.Stack;

public class PaintPanel extends JPanel implements MouseListener, MouseMotionListener, KeyListener {

    BufferedImage canvas;
    Graphics2D graphics;

    private final int DOT = 0;
    private final int LINE = 1;
    private final int RECT = 2;
    private final int CIRCLE = 3;
    private final int TRI = 4;

    private Stack<OMShape> shapes;

    int x1, y1, x2, y2;

    private boolean dragged = false;

    private int panelWidth;
    private int panelHeight;

    public PaintPanel() {
        setBackground(Color.white);
        setBorder(BorderFactory.createLineBorder(Color.black));
        setPreferredSize(new Dimension(250, 250));
        shapes = new Stack<OMShape>();
    }

    public PaintPanel(int z) {

        panelWidth = 1775;
        panelHeight = 600;

        setSize(panelWidth, panelHeight);
        System.out.println("New height of " + panelWidth + " x " + panelHeight + " set");
        setPreferredSize(new Dimension(panelWidth, panelHeight));
        setLayout(null);
        setDoubleBuffered(true);
        setBackground(Color.white);
        setFocusable(true);
        requestFocus();

        addMouseListener(this);
        addMouseMotionListener(this);

        shapes = new Stack<OMShape>();
    }

    public void paintComponent(Graphics graph) {
        if(canvas == null) {
            canvas = new BufferedImage(panelWidth, panelHeight, BufferedImage.TYPE_INT_ARGB);
            graphics = canvas.createGraphics();
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            clear();
        }

        graph.drawImage(canvas, 0, 0, null);
        Graphics2D graphics2D = (Graphics2D) graph;

        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for(OMShape s : shapes) {
            graphics2D.setColor(Color.black);
            graphics2D.setStroke(new BasicStroke(2));

            switch(s.getShape()) {
                case DOT:
                    graphics2D.drawLine(s.getX1(), s.getY1(), s.getX2(), s.getY2());
                    break;
                case LINE:
                    graphics2D.drawLine(s.getX1(), s.getY1(), s.getX2(), s.getY2());
                    break;
                case RECT:
                    graphics2D.drawRect(s.getX1(), s.getY1(), s.getX2(), s.getY2());
                    break;
                case CIRCLE:
                    graphics2D.drawOval(s.getX1(), s.getY1(), s.getX2(), s.getY2());
                    break;

            }
        }
    }

    public void refresh() {
    }

    public void clear() {
        graphics.setPaint(Color.white);
        graphics.fillRect(0, 0, getSize().width, getSize().height);
        shapes.removeAllElements();
        repaint();
        graphics.setColor(Color.black);
    }

    public void setImage(BufferedImage image) {
        graphics.dispose();
        recreatePanel(image.getWidth(), image.getHeight());
        graphics.drawImage(image, 0, 0, null);
    }

    public void recreatePanel(int width, int height) {
        canvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        graphics = canvas.createGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        System.out.println("New height of " + width + " x " + height + " set");
        setSize(width, height);
        setPreferredSize(new Dimension(width, height));
        clear();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        x1 = e.getX();
        y1 = e.getY();

        dragged = true;

        shapes.push(new OMShape(x1, y1, x2, y2, LINE));
        repaint();
        x2 = x1;
        y2 = y1;
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON3) clear();
        System.out.println(e.getButton());
    }

    @Override
    public void mousePressed(MouseEvent e) {
        x2 = e.getX();
        y2 = e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_SPACE)
            Main.CannyImage();
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}

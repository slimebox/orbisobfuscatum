package org.femtobit.orbisobfuscatum.shape;

public class OMShape {

    private int x1, x2, y1, y2;

    private int group = 0;

    private int shape;

    public OMShape(int x1, int y1, int x2, int y2, int shape) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.shape = shape;
    }

    public OMShape(int x, int y, int shape) {
        x1 = x; x2 = x;
        y1 = y; y2 = y;
        this.shape = shape;
    }

    public int getShape() {
        return this.shape;
    }

    public int getX1() {
        return x1;
    }

    public int getX2() {
        return x2;
    }

    public int getY1() {
        return y1;
    }

    public int getY2() {
        return y2;
    }

    public int getGroup() {
        return group;
    }
}

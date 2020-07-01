package pacman.model;

import pacman.Updater;

public class Entity {
    private double x = 0;
    private double y = 0;

    private Updater updater = null;

    public static final int MAX_X = 15;
    public static final int MAX_Y = 19;

    public Entity(double x, double y) {
        if (x > MAX_X || x < 0 || y > MAX_Y || y < 0) {
            throw new IllegalArgumentException();
        }
        this.setX(x);
        this.setY(y);
    }

    public void notifyUpdater() {
        if (this.getUpdater() != null) {
            this.getUpdater().update();
        }
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Updater getUpdater() {
        return this.updater;
    }

    public void setUpdater(Updater updater) {
        this.updater = updater;
    }
}

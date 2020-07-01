package pacman.view;

import pacman.model.Entity;
import pacman.Updater;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

public abstract class Sprite extends JPanel implements Updater {
    private int x;
    private int y;

    private double angle;

    private final Entity entity;
    private Animation animation;

    private static final int WIDTH = 480;
    private static final int HEIGHT = 608;

    public Sprite(Entity entity, Animation animation, double angle) {
        this.entity = entity;
        this.angle = angle;
        if (entity != null) {
            this.entity.setUpdater(this);
            this.update();
        }
        this.animation = animation;
    }

    public Sprite(int x, int y, Animation animation, double angle) {
        this.entity = null;
        this.angle = angle;
        this.x = x;
        this.y = y;
        this.animation = animation;
    }

    public Animation getAnimation() {
        return this.animation;
    }

    public void setAnimation(Animation animation){
        this.animation = animation;
    }

    protected void setAngle(double angle){
        this.angle = angle;
    }

    protected Entity getEntity(){
        return this.entity;
    }

    @Override
    public void update() {
        if (this.entity != null) {
            this.x = (int) (this.entity.getX() * WIDTH / Entity.MAX_X);
            this.y = (int) (this.entity.getY() * HEIGHT / Entity.MAX_Y);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D graphic2d = (Graphics2D) g;
        this.draw(graphic2d);
    }

    public void draw(Graphics2D g) {
        Image image = this.animation.getCurrentFrame();
        AffineTransform old = g.getTransform();
        g.rotate(this.angle, this.x + image.getWidth(null) / 2.0, this.y + image.getHeight(null) / 2.0);
        g.drawImage(image, this.x, this.y, null);
        g.setTransform(old);
    }
}

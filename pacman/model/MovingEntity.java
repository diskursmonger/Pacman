package pacman.model;

import java.util.Timer;
import java.util.TimerTask;

public class MovingEntity extends Entity implements Runnable {
    private final Timer timer;
    private final FieldModel fieldModel;
    public static final double MOVE_DISTANCE = 0.125;

    private boolean frozen = false;

    private final int moveDelay;

    private DIRECTION direction;
    private DIRECTION nextDirection;

    public enum DIRECTION {
        UP,
        DOWN,
        LEFT,
        RIGHT;

        public boolean isNotOppositeTo(DIRECTION other) {
            switch (other) {
                case UP:
                    return this != DOWN;
                case DOWN:
                    return this != UP;
                case LEFT:
                    return this != RIGHT;
                case RIGHT:
                    return this != LEFT;
            }

            return true;
        }

    }

    public MovingEntity(double x, double y, DIRECTION direction, FieldModel fieldModel, double speed, Timer timer) {
        super(x, y);
        this.moveDelay = (int) ((MOVE_DISTANCE * 1000) / speed);
        this.fieldModel = fieldModel;
        this.direction = direction;
        this.nextDirection = this.direction;
        this.timer = timer;
    }

    public DIRECTION getDirection() {
        return direction;
    }

    public void setDirection(DIRECTION direction) {
        this.nextDirection = direction;
    }

    public FieldModel getFieldModel() {
        return this.fieldModel;
    }

    public void move() {

        if (this.getX() == MAX_X - 1.0 && this.direction == DIRECTION.RIGHT) {
            this.setX(0.0);
        }

        if (this.getX() == 0 && this.direction == DIRECTION.LEFT) {
            this.setX(MAX_X - 1 + MOVE_DISTANCE);
        }

        if (this.hasIntegerCoords()) {
            if (!this.isAgainstWall(this.nextDirection)) {
                this.direction = this.nextDirection;
            }

            if (this.isAgainstWall(this.direction)) {
                return;
            }
        }

        switch (this.getDirection()) {
            case RIGHT:
                this.setX(this.getX() + MOVE_DISTANCE);
                break;
            case UP:
                this.setY(this.getY() - MOVE_DISTANCE);
                break;
            case LEFT:
                this.setX(this.getX() - MOVE_DISTANCE);
                break;
            case DOWN:
                this.setY(this.getY() + MOVE_DISTANCE);
                break;
        }
        notifyUpdater();
    }

    public boolean hasIntegerCoords() {
        int x = (int) this.getX();
        int y = (int) this.getY();

        return x == this.getX() && y == this.getY();
    }

    public boolean isFrozen() {
        return this.frozen;
    }

    public boolean isAgainstWall(DIRECTION direction) {
        int x = (int) this.getX();
        int y = (int) this.getY();

        switch (direction) {
            case RIGHT:
                return (fieldModel.isWall(MAX_X * y + x + 1));
            case UP:
                return (fieldModel.isWall(MAX_X * (y - 1) + x));
            case LEFT:
                return (fieldModel.isWall(MAX_X * y + x - 1));
            case DOWN:
                return (fieldModel.isWall(MAX_X * (y + 1) + x));
            default:
                return false;
        }
    }

    public void freeze() {
        this.frozen = true;
    }

    public void go() {
        this.frozen = false;
    }

    @Override
    public void run() {
        this.timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!isFrozen()) {
                    MovingEntity.this.move();
                } else {
                    MovingEntity.this.notifyUpdater();
                }
            }
        }, this.moveDelay, this.moveDelay);
    }
}

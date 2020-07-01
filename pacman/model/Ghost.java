package pacman.model;

import java.util.Timer;

public class Ghost extends MovingEntity implements Runnable {
    private final String color;

    private static boolean pacmanBuffed = false;
    private static int scared = 0;

    private final AI ai;

    public Ghost(FieldModel fieldModel, double startX, double startY, String color, AI ai, Timer timer) {
        super(startX, startY, DIRECTION.RIGHT, fieldModel, 6, timer);
        this.ai = ai;
        this.ai.bind(this);
        this.color = color;
    }

    public String getColor() {
        return this.color;
    }

    public boolean isPacmanBuffed() {
        return this.pacmanBuffed;
    }

    public void setPacmanBuffed(boolean pacmanBuffed) {
        this.pacmanBuffed = pacmanBuffed;
    }

    public int getScaredCondition(){
        return scared;
    }
    public void setScaredCondition(int scaredCondition){
        scared = scaredCondition;
        this.notifyUpdater();
    }

    @Override
    public void move() {
        this.ai.setGhostDirection();
        super.move();
    }
}

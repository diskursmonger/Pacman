package pacman.model;

import pacman.controller.CollisionDetector;

import java.util.Timer;

public final class Pacman extends MovingEntity {
    private int score = 0;

    private String playerName;

    private int current_coins = 0;
    public static final int ALL_COINS = 121;

    private int lvl = 1;

    private static boolean alive = true;
    private static boolean buffed = false;

    private java.util.Timer timer;
    private CollisionDetector collisionDetector;

    public Pacman(FieldModel fieldModel, Timer timer) {
        super(7.0, 13.0, DIRECTION.RIGHT, fieldModel, 6, timer);
        this.timer = timer;
    }

    public int getScore() {
        return this.score;
    }

    public int getLvl() {
        return this.lvl;
    }

    public int getCurrent_coins(){
        return this.current_coins;
    }

    public void plusCoin(){
        this.current_coins++;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setLvl(int lvl) {
        this.lvl = lvl;
    }

    public void death() {
        alive = false;
    }

    public boolean isAlive() {
        return alive;
    }

    public boolean isBuffed() {
        return buffed;
    }

    public void setBuffed(boolean Buffed) {
        buffed = Buffed;
    }

    public void setCollisionDetector(CollisionDetector collisionDetector) {
        this.collisionDetector = collisionDetector;
    }

    public String getPlayerName() {
        return this.playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    @Override
    public void move() {
        this.collisionDetector.handleCollisions();
        super.move();
        notifyUpdater();
    }
}

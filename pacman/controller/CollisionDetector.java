package pacman.controller;

import pacman.model.Ghost;
import pacman.model.Pacman;
import pacman.model.ScoreBoard;

import java.io.IOException;
import java.util.TimerTask;

import static pacman.model.Entity.MAX_X;

public class CollisionDetector {
    private final Ghost[] ghosts;
    private final Pacman pacman;
    private final java.util.Timer timer;

    private static boolean alreadyScared = false;
    private static boolean neededUpdate = false;

    public CollisionDetector(Ghost[] ghosts, Pacman pacman, java.util.Timer timer) {
        this.ghosts = ghosts;
        this.pacman = pacman;
        this.timer = timer;
    }

    public void setAlreadyScared(boolean AlreadyScared) {
        alreadyScared = AlreadyScared;
    }

    public void setNeededUpdate(boolean NeededUpdate) {
        neededUpdate = NeededUpdate;
    }

    public void handleCollisions() {
        int x = (int) this.pacman.getX();
        int y = (int) this.pacman.getY();
        if (this.pacman.getFieldModel().isCoin(y * MAX_X + x) && this.pacman.hasIntegerCoords()) {
            this.pacman.getFieldModel().consumeCoin(y * MAX_X + x);
            this.pacman.setScore(this.pacman.getScore() + 10);
            this.pacman.plusCoin();
            if (this.pacman.getCurrent_coins() == Pacman.ALL_COINS) {
                this.pacman.setLvl(this.pacman.getLvl() + 1);
                this.timer.cancel();
                try {
                    ScoreBoard.addScoreEntry(new ScoreBoard.Entry(this.pacman.getPlayerName(),this.pacman.getScore()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (this.pacman.getFieldModel().isPill(y * MAX_X + x) ) {
            this.pacman.getFieldModel().consumePill(y * MAX_X + x);
            this.pacman.setScore(this.pacman.getScore() + 50);

            for (var ghost : this.ghosts) {
                ghost.setScaredCondition(1);
            }

            this.setNeededUpdate(true);

            if (CollisionDetector.this.pacman.isBuffed()) {
                CollisionDetector.this.setAlreadyScared(true);
                CollisionDetector.this.setNeededUpdate(false);
            }

            this.pacman.setBuffed(true);

            this.timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (neededUpdate) {
                        for (var ghost : CollisionDetector.this.ghosts) {
                            ghost.setScaredCondition(2);
                        }
                    }
                }
            }, 4000); //2000, 4000

            this.timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (neededUpdate) {
                        for (var ghost : CollisionDetector.this.ghosts) {
                            ghost.setScaredCondition(0);
                            ghost.setPacmanBuffed(false);
                            CollisionDetector.this.setAlreadyScared(false);
                            CollisionDetector.this.pacman.setBuffed(false);
                            if (ghost.isFrozen()){
                                ghost.setX(7.0);
                                ghost.setY(8.0);
                                ghost.go();
                            }
                        }
                    } else {
                        neededUpdate = true;
                    }
                }
            }, 6000);
        }

        for (var ghost : this.ghosts) {
            if (Math.abs(this.pacman.getX() - ghost.getX()) < 1 && Math.abs(this.pacman.getY() - ghost.getY()) < 1) {
                if (!this.pacman.isBuffed()) {
                    this.pacman.death();
                    this.timer.cancel();
                    try {
                        ScoreBoard.addScoreEntry(new ScoreBoard.Entry(this.pacman.getPlayerName(),this.pacman.getScore()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {
                    this.pacman.setScore(this.pacman.getScore() + 50);
                    ghost.freeze();
                    ghost.setX(7.0);
                    ghost.setY(10.0);
                }
            }
        }
    }
}

package pacman.view;

import pacman.model.MovingEntity;
import pacman.model.Pacman;

import java.util.HashMap;

public class PacmanView extends Sprite {
    private final Animation death;
    private Pacman pacman;
    private static final HashMap<MovingEntity.DIRECTION, Double> degrees = new HashMap<>() {{
        put(MovingEntity.DIRECTION.RIGHT, Math.toRadians(0));
        put(MovingEntity.DIRECTION.UP, Math.toRadians(270));
        put(MovingEntity.DIRECTION.LEFT, Math.toRadians(180));
        put(MovingEntity.DIRECTION.DOWN, Math.toRadians(90));
    }};

    public PacmanView(Pacman pacman, Animation alive, Animation death) {
        super(pacman, alive, 0);
        this.pacman = pacman;
        this.death = death;
    }

    @Override
    public void update() {
        if (!((Pacman) this.getEntity()).isAlive()) {
            this.setAngle(0);
            this.setAnimation(this.death);
        } else {
            this.setAngle(degrees.get(((Pacman) this.getEntity()).getDirection()));
        }
        super.update();
    }
}

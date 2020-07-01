package pacman.model;

import java.util.ArrayList;
import java.util.Random;

import static pacman.model.MovingEntity.DIRECTION.*;

public class RandomGhostAI extends AI {
    private final Random randomGenerator;

    public RandomGhostAI() {
        this.randomGenerator = new Random();
    }

    private ArrayList<MovingEntity.DIRECTION> availableDirections() {
        ArrayList<MovingEntity.DIRECTION> available = new ArrayList<>();

        if (!this.getGhost().isAgainstWall(UP) && UP.isNotOppositeTo(this.getGhost().getDirection())) {
            available.add(UP);
        }
        if (!this.getGhost().isAgainstWall(LEFT) && LEFT.isNotOppositeTo(this.getGhost().getDirection())) {
            available.add(LEFT);
        }
        if (!this.getGhost().isAgainstWall(RIGHT) && RIGHT.isNotOppositeTo(this.getGhost().getDirection())) {
            available.add(RIGHT);
        }
        if (!this.getGhost().isAgainstWall(DOWN) && DOWN.isNotOppositeTo(this.getGhost().getDirection())) {
            available.add(DOWN);
        }
        return available;
    }


    @Override
    public void setGhostDirection() {
        if (!this.getGhost().hasIntegerCoords()) {
            return;
        }
        var available = this.availableDirections();
        if (available.size() == 0){
            return;
        }
        this.getGhost().setDirection(available.get(this.randomGenerator.nextInt(available.size())));
    }
}

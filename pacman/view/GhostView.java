package pacman.view;

import pacman.model.Ghost;

public class GhostView extends Sprite {
    private final Animation right, up, down, left;
    private static Animation scared1, scared2;

    Ghost ghost;

    GhostView(Ghost ghost, Animation right, Animation up, Animation left, Animation down) {
        super(ghost, right, 0);
        this.ghost = ghost;
        this.right = right;
        this.up = up;
        this.left = left;
        this.down = down;
    }

    public static void setAnimationScared(Animation scared1, Animation scared2) {
        GhostView.scared1 = scared1;
        GhostView.scared2 = scared2;
    }

    @Override
    public void update() {
        switch (((Ghost) this.getEntity()).getDirection()) {
            case RIGHT:
                this.setAnimation(this.right);
                break;
            case UP:
                this.setAnimation(this.up);
                break;
            case LEFT:
                this.setAnimation(this.left);
                break;
            case DOWN:
                this.setAnimation(this.down);
                break;
        }
        if (this.ghost != null) {
            switch (this.ghost.getScaredCondition()) {
                case 1:
                    this.setAnimation(scared1);
                    break;
                case 2:
                    this.setAnimation(scared2);
                    break;
                case 0:
                    switch (((Ghost) this.getEntity()).getDirection()) {
                        case RIGHT:
                            this.setAnimation(this.right);
                            break;
                        case UP:
                            this.setAnimation(this.up);
                            break;
                        case LEFT:
                            this.setAnimation(this.left);
                            break;
                        case DOWN:
                            this.setAnimation(this.down);
                            break;
                    }
                    break;
            }
        }
        super.update();
    }
}

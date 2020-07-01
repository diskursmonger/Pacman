package pacman.model;

public abstract class AI {
    private Ghost ghost;

    public void bind(Ghost ghost) {
        this.ghost = ghost;
    }

    protected Ghost getGhost() {
        return this.ghost;
    }

    public abstract void setGhostDirection();
}
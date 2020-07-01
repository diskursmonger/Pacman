package pacman.controller;

import pacman.model.MovingEntity;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PlayerListener implements KeyListener {
    private final MovingEntity player;

    public PlayerListener(MovingEntity player) {
        this.player = player;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        var key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_UP:
                player.setDirection(MovingEntity.DIRECTION.UP);
                break;
            case KeyEvent.VK_DOWN:
                player.setDirection(MovingEntity.DIRECTION.DOWN);
                break;
            case KeyEvent.VK_LEFT:
                player.setDirection(MovingEntity.DIRECTION.LEFT);
                break;
            case KeyEvent.VK_RIGHT:
                player.setDirection(MovingEntity.DIRECTION.RIGHT);
                break;
            default:
                break;
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}
}

package pacman.view;

import pacman.InitPanel;

import javax.swing.*;
import java.awt.*;

public class GameOverPanel extends JLayeredPane implements InitPanel {
    private final MenuWindow mainMenuWindow;

    private static final int width = 480;
    private static final int height = 686;

    public GameOverPanel(MenuWindow menuWindow) {
        this.mainMenuWindow = menuWindow;
        this.init();
    }

    @Override
    public void init() {
        Background background = new Background(MenuWindow.GAME_CARD);
        background.setBounds(0, 0, width, height);
        this.add(background, JLayeredPane.DEFAULT_LAYER);

        JLabel gameOver = new JLabel("Game Over");
        gameOver.setFont(new Font("Copperplate", Font.CENTER_BASELINE, 22));
        gameOver.setBackground(new Color(200,200,200));
        this.add(gameOver);

        JButton backButton = new Button("Back", MenuWindow.HIGH_SCORES_CARD);
        backButton.addActionListener((actionEvent)->this.mainMenuWindow.showPanel(MenuWindow.MAIN_MENU_CARD));
        this.add(backButton,JLayeredPane.PALETTE_LAYER);
    }
}

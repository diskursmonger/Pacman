package pacman.view;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;


public class MenuWindow extends JFrame implements Runnable {
    private final CardLayout cardLayout;
    private final SettingsPanel settingsPanel;
    private final HighScoresPanel highScoresPanel;
    private final GamePanel gamePanel;
    private final GameOverPanel gameOverPanel;

    public static final String MAIN_MENU_CARD = "Main Menu";
    public static final String GAME_CARD = "Game";
    public static final String SETTINGS_CARD = "Settings Menu";
    public static final String HIGH_SCORES_CARD = "High Scores Menu";
    public static final String GAME_OVER = "Game Over";

    public MenuWindow() throws IOException {
        this.setTitle("Pac-Man");
        this.cardLayout = new CardLayout();
        this.gamePanel = new GamePanel(this);

        this.settingsPanel = new SettingsPanel(this);
        this.highScoresPanel = new HighScoresPanel(this);

        this.gameOverPanel = new GameOverPanel(this);

        this.addComponentsToPane(this.getContentPane());
        this.setSize(480, 686);
        this.setResizable(false);
        this.setFocusable(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void addComponentsToPane(Container pane) {
        pane.setLayout(this.cardLayout);
        pane.add(new MainMenuPanel(this), MAIN_MENU_CARD);
        pane.add(this.gamePanel, GAME_CARD);
        pane.add(this.settingsPanel, SETTINGS_CARD);
        pane.add(this.highScoresPanel, HIGH_SCORES_CARD);
        pane.add(this.gameOverPanel, GAME_OVER);
    }

    @Override
    public void run() {
        SwingUtilities.invokeLater(() -> this.setVisible(true));
    }

    public void showPanel(String panelName) {
        this.cardLayout.show(this.getContentPane(), panelName);
    }

    public void startGame() {
        this.showPanel(MenuWindow.GAME_CARD);
        this.gamePanel.run();
    }
}
package pacman.view;

import pacman.InitPanel;

import javax.swing.*;
import java.awt.event.WindowEvent;

public class MainMenuPanel extends JLayeredPane implements InitPanel {
    MenuWindow mainMenuWindow;
    private static final int weight = 515;
    private static final int height = 686;

    MainMenuPanel(MenuWindow menuWindow){
        this.mainMenuWindow = menuWindow;
        this.init();
    }

    @Override
    public void init(){
        Background background = new Background(MenuWindow.MAIN_MENU_CARD);
        background.setBounds(0,0,weight,height);
        this.add(background,JLayeredPane.DEFAULT_LAYER);

        JButton startButton = new Button("Start",MenuWindow.MAIN_MENU_CARD);
        startButton.addActionListener((actionEvent)->{ this.mainMenuWindow.startGame();
        });

        JButton settingsButton = new Button("Settings",MenuWindow.MAIN_MENU_CARD);
        settingsButton.addActionListener((actionEvent)->this.mainMenuWindow.showPanel(MenuWindow.SETTINGS_CARD));

        JButton highScoresButton = new Button("High Scores",MenuWindow.MAIN_MENU_CARD);
        highScoresButton.addActionListener((actionEvent)->this.mainMenuWindow.showPanel(MenuWindow.HIGH_SCORES_CARD));

        JButton exitButton = new Button("Exit",MenuWindow.MAIN_MENU_CARD);
        exitButton.addActionListener((actionEvent)->this.mainMenuWindow.dispatchEvent(new WindowEvent(this.mainMenuWindow,WindowEvent.WINDOW_CLOSING)));

        this.add(startButton,JLayeredPane.PALETTE_LAYER);
        this.add(settingsButton,JLayeredPane.PALETTE_LAYER);
        this.add(highScoresButton,JLayeredPane.PALETTE_LAYER);
        this.add(exitButton,JLayeredPane.PALETTE_LAYER);
    }
}

package pacman.view;

import pacman.InitPanel;

import javax.swing.*;

public class SettingsPanel extends JLayeredPane implements InitPanel {
    MenuWindow mainMenuWindow;
    private static final int weight = 515;
    private static final int height = 686;

    SettingsPanel(MenuWindow menuWindow) {
        this.mainMenuWindow = menuWindow;
        this.init();
    }

    @Override
    public void init() {
        Background background = new Background(MenuWindow.SETTINGS_CARD);
        background.setBounds(0, 0, weight, height);
        this.add(background, JLayeredPane.DEFAULT_LAYER);

        JButton backButton = new Button("Back", MenuWindow.SETTINGS_CARD);
        backButton.addActionListener((actionEvent)->this.mainMenuWindow.showPanel(MenuWindow.MAIN_MENU_CARD));
        this.add(backButton,JLayeredPane.PALETTE_LAYER);
    }
}

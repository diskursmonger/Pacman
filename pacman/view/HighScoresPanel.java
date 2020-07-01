package pacman.view;

import pacman.InitPanel;
import pacman.model.ScoreBoard;

import javax.swing.*;
import java.awt.*;

public class HighScoresPanel extends JLayeredPane implements InitPanel {
    private final MenuWindow mainMenuWindow;

    public HighScoresPanel(MenuWindow menuWindow) {
        this.mainMenuWindow = menuWindow;
        this.init();
    }

    @Override
    public void init() {
//        Background background = new Background(MenuWindow.HIGH_SCORES_CARD);
//        background.setBounds(0, 0, weight, height);
//        this.add(background, JLayeredPane.DEFAULT_LAYER);

        JButton backButton = new Button("Back", MenuWindow.HIGH_SCORES_CARD);
        backButton.addActionListener((actionEvent)->this.mainMenuWindow.showPanel(MenuWindow.MAIN_MENU_CARD));
        this.add(backButton,JLayeredPane.PALETTE_LAYER);


        this.setLayout(new BorderLayout());

        var infoPanel = new JPanel(new GridLayout(1, 2));

        JLabel jLabelName = new JLabel("Name");
        infoPanel.add(jLabelName);

        JLabel jLabelScore = new JLabel("Score");
        infoPanel.add(jLabelScore);
        this.add(infoPanel, BorderLayout.NORTH);

        JPanel mainList = new JPanel();
        mainList.setLayout(new BoxLayout(mainList, BoxLayout.Y_AXIS));

        this.add(new JScrollPane(
                mainList,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        ));

//        this.add(mainList, JLayeredPane.DEFAULT_LAYER);
//        mainList.setLayout(new BoxLayout(mainList, BoxLayout.Y_AXIS));

        var scores = ScoreBoard.getScoresList();

        for (var e : scores) {
            JPanel panel = new JPanel(new GridLayout(1, 2));

            JLabel player = new JLabel(e.playerName);
//            player.setForeground(Color.PINK);
//            panel.setBackground(new Color (4,4,4));
            panel.add(player);

            JLabel score = new JLabel(String.valueOf(e.score));
//            score.setForeground(Color.PINK);
            panel.add(score);
//            panel.setBackground(new Color (4,4,4));
            mainList.add(panel);
        }

    }
}

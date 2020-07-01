package pacman.view;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

import static javax.imageio.ImageIO.read;

public class Background extends JPanel {
    Image back;
    Background(String panelName){
        try {
            switch (panelName) {
                case "Main Menu":
                    back = read(new File("src/Sprite/menu.png"));
                    break;
                case "Game":
                    back = read(new File("src/Sprite/back.png"));
                    break;
                case "Settings Menu":
                    back = read(new File("src/Sprite/settings.png"));
                    break;
                case "High Scores Menu":
                    back = read(new File("src/Sprite/highScores.png"));
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void paint(Graphics g) {
        Graphics2D graphic2d = (Graphics2D) g;
        graphic2d.drawImage(back,0,0,null);
    }
}

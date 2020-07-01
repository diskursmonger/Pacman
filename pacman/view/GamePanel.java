package pacman.view;

import pacman.controller.CollisionDetector;
import pacman.model.*;
import pacman.controller.PlayerListener;
import pacman.StoppableRunnable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Timer;

import static javax.imageio.ImageIO.read;

public class GamePanel extends JPanel implements Runnable {
    private final Pacman pacman;

    public static GamePanel gamePanel;

    private final ArrayList<Runnable> runnables = new ArrayList<>();
    private final ArrayList<StoppableRunnable> stoppables = new ArrayList<>();
    private final ArrayList<Sprite> sprites = new ArrayList<>();

    private final Animation pacmanAnimation = createPacmanAnimation();

    MenuWindow mainMenuWindow;

    Image back, icon;

    java.util.Timer timer;

    javax.swing.Timer swingTimer;

    Field field;

    ArrayList<String> sequenceOfIcons;
    HashMap<String, Image> FiledIcons;

    FieldModel fieldModel;

    GamePanel(MenuWindow menuWindow) throws IOException {
        this.mainMenuWindow = menuWindow;

//        String playerName = JOptionPane.showInputDialog("Please, enter username: ");

        this.fieldModel = new FieldModel();
        this.timer = new Timer();

        Ghost[] ghosts = new Ghost[4];
        String[] colors = {"red", "pink", "blue", "yellow"};
        GhostView[] ghostsView = new GhostView[4];

        double startX = 0.0, startY = 0.0;
        for (int i = 0; i < 4; i++) {
            switch (i) {
                case 0: {
                    startX = 1.0;
                    startY = 1.0;
                    break;
                }
                case 1: {
                    startX = 13.0;
                    startY = 1.0;
                    break;
                }
                case 2: {
                    startX = 1.0;
                    startY = 17.0;
                    break;
                }
                case 3: {
                    startX = 13.0;
                    startY = 17.0;
                    break;
                }
            }

            ghosts[i] = new Ghost(this.fieldModel, startX, startY, colors[i], new RandomGhostAI(), this.timer);
            this.runnables.add(ghosts[i]);

            ghostsView[i] = new GhostView(ghosts[i], this.createGhostAnimation(ghosts[i].getColor(), "right"), this.createGhostAnimation(ghosts[i].getColor(), "up"),
                    this.createGhostAnimation(ghosts[i].getColor(), "left"), this.createGhostAnimation(ghosts[i].getColor(), "down"));
        }

        this.pacman = new Pacman(this.fieldModel, this.timer);
        this.runnables.add(pacman);

        this.pacman.setCollisionDetector(new CollisionDetector(ghosts, this.pacman, this.timer));

        var pacmanView = new PacmanView(this.pacman, this.createPacmanAnimation(), this.createPacmanAnimationDeath());

        this.sprites.add(pacmanView);

        this.sprites.addAll(Arrays.asList(ghostsView).subList(0, 4));
        GhostView.setAnimationScared(this.createGhostAnimationScaredFirst(), this.createGhostAnimationScaredSecond());

        this.swingTimer = new javax.swing.Timer(16, (actionEvent) -> this.repaint());

        menuWindow.addKeyListener(new PlayerListener(this.pacman));
        menuWindow.addKeyListener(new PlayerListener(ghosts[0]));

        setBackground();
        this.field = new Field();
        this.sequenceOfIcons = this.field.getSequenceOfIcons();
        this.FiledIcons = this.field.getFieldIcons();

        menuWindow.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                GamePanel.this.timer.cancel();
                GamePanel.this.swingTimer.stop();
                for (var f : stoppables) {
                    f.stop();
                }
            }
        });
        gamePanel = this;
        this.setVisible(true);
        this.setFocusable(true);
    }

    private Animation createPacmanAnimation() throws IOException {
        Animation animation;
        Image[] frameImages = new Image[4];
        for (int i = 0; i < frameImages.length; i++) {
            frameImages[i] = read(new File("src/Sprite/pacman/pacman" + i + ".png"));
        }
        animation = new Animation(frameImages, 8);
        this.runnables.add(animation);
        this.stoppables.add(animation);
        return animation;
    }

    private Animation createPacmanAnimationDeath() throws IOException {
        Animation animation;
        Image[] frameImages = new Image[11];
        for (int i = 0; i < frameImages.length; i++) {
            frameImages[i] = read(new File("src/Sprite/pacman/pacmanDeath" + i + ".png"));
        }
        animation = new Animation(frameImages, 4);
        this.runnables.add(animation);
        this.stoppables.add(animation);
        return animation;
    }

    private Animation createGhostAnimation(String color, String direction) throws IOException {
        Animation animation;
        Image[] frameImages = new Image[2];
        for (int i = 0; i < frameImages.length; i++) {
            frameImages[i] = read(new File("src/Sprite/ghost/" + color + "_" + direction + i + ".png"));
        }
        animation = new Animation(frameImages, 8);
        this.runnables.add(animation);
        this.stoppables.add(animation);
        return animation;
    }

    private Animation createGhostAnimationScaredFirst() throws IOException {
        Animation animation;
        Image[] frameImages = new Image[2];
        for (int i = 0; i < frameImages.length; i++) {
            frameImages[i] = read(new File("src/Sprite/ghost/scared_blue" + i + ".png"));
        }
        animation = new Animation(frameImages, 8);
        this.runnables.add(animation);
        this.stoppables.add(animation);
        return animation;
    }

    private Animation createGhostAnimationScaredSecond() throws IOException {
        Animation animation;
        Image[] frameImages = new Image[4];
        for (int i = 0; i < frameImages.length; i++) {
            if (i % 2 == 0){
                frameImages[i] = read(new File("src/Sprite/ghost/scared_white" + i % 2 + ".png"));
            } else {
                frameImages[i] = read(new File("src/Sprite/ghost/scared_blue" + i % 2 + ".png"));
            }
        }
        animation = new Animation(frameImages, 8);
        this.runnables.add(animation);
        this.stoppables.add(animation);
        return animation;
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D graphic2d = (Graphics2D) g;
        graphic2d.drawImage(this.back, 0, 0, null);
        drawBackground(graphic2d);
        drawScore(graphic2d);
        drawLVL(graphic2d);
        drawField(graphic2d);
        for (var f : this.sprites) {
            f.draw(graphic2d);
        }
    }

    private void setBackground() {
        try {
            back = read(new File("src/Sprite/back.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void drawBackground(Graphics2D g) {
        g.drawImage(back, 0, 0, null);
    }

    private void drawScore(Graphics2D g) {
        g.setColor(new Color(117, 4, 4));
        g.setFont(new Font("Copperplate", Font.BOLD, 18));
        g.drawString("Score: ", 50, 616);

        g.setColor(new Color(200, 200, 200));
        g.setFont(new Font("Copperplate", Font.BOLD, 18));
        g.drawString(Integer.toString(pacman.getScore()), 70, 630);
    }

    private void drawLVL(Graphics2D g) {
        g.setColor(new Color(117, 4, 4));
        g.setFont(new Font("Copperplate", Font.BOLD, 18));
        g.drawString("Level:", 385, 616);

        g.setColor(new Color(200, 200, 200));
        g.setFont(new Font("Copperplate", Font.BOLD, 18));
        g.drawString(Integer.toString(pacman.getLvl()), 410, 630);
    }

    private void drawField(Graphics2D g) {
        int iconX = 0;
        int iconY = 0;
        for (int i = 0; i < sequenceOfIcons.size(); i++) {
            if (this.fieldModel.isCoin(i)) {
                g.setColor(new Color(153, 98, 70));
                g.drawRect(iconX + 16, iconY + 16, 2, 2);
            }
            if (this.fieldModel.isPill(i)) {
                g.setColor(new Color(222, 199, 144));
                g.fillOval(iconX + 8, iconY + 14, 16, 16);
            }
            icon = FiledIcons.get(sequenceOfIcons.get(i));
            if ((i + 1) % 15 == 0) {
                g.drawImage(icon, iconX, iconY, null);
                iconX = 0;
                iconY += 32;
            } else {
                if (sequenceOfIcons.get(i).charAt(0) != '-') {
                    g.drawImage(icon, iconX, iconY, null);
                }
                iconX += 32;
            }
        }
    }

    @Override
    public void run() {
        SwingUtilities.invokeLater(() -> this.setVisible(true));
        String playerName = JOptionPane.showInputDialog("Please, enter username: ");
        this.pacman.setPlayerName(playerName);
        for (var f : runnables) {
            f.run();
        }
        this.swingTimer.start();
    }
}

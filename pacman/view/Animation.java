package pacman.view;

import pacman.StoppableRunnable;

import javax.swing.*;
import java.awt.*;

import static javax.imageio.ImageIO.read;

public class Animation implements StoppableRunnable {
    private final Image[] frames;
    private int frameIndex;
    private final Timer timer;

    public Animation(Image[] frameFiles, double speed) {
        if (speed < 0) {
            throw new IllegalArgumentException();
        }
        this.frameIndex = 0;
        this.frames = frameFiles;
        if (speed == 0) {
            this.timer = new Timer(0, null);
        } else {
            this.timer = new Timer((int) (1000 / speed), (actionEvent) ->
                    this.frameIndex = (this.frameIndex + 1) % this.frames.length);
        }
    }

//    public Animation(String frameFile) throws IOException {
//        this.frameIndex = 0;
//        this.frames = new Image[1];
//        this.frames[0] = read(new File(frameFile));
//        this.timer = new Timer(0, null);
//    }

    public Image getCurrentFrame() {
        return this.frames[this.frameIndex];
    }

    @Override
    public void run() {
        this.timer.start();
    }

    @Override
    public void stop() {
        this.timer.stop();
    }
}

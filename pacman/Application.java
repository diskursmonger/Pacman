package pacman;

import pacman.view.MenuWindow;

import java.io.IOException;

public class Application {
    public static void main(String[] args) throws IOException {
        MenuWindow menuWindow = new MenuWindow();
        menuWindow.run();
    }
}

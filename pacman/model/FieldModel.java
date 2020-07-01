package pacman.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class FieldModel {
    private final ArrayList<Boolean> walls = new ArrayList<>();
    private final ArrayList<String> wallTypes = new ArrayList<>();

    private final ArrayList<Boolean> coins = new ArrayList<>();
    private final ArrayList<Boolean> pills = new ArrayList<>();

    private java.util.Timer timer;
    public FieldModel() throws FileNotFoundException {
        loadFields();
    }

    private void loadFields() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("src/Sprite/field.txt"));
        String iconStr;
        char iconType;
        while (scanner.hasNext()) {
            iconStr = scanner.next();
            iconType = iconStr.charAt(0);
            switch (iconType) {
                case 'b':
                case 'w':
                    this.walls.add(true);
                    this.wallTypes.add(iconStr);
                    this.coins.add(false);
                    this.pills.add(false);
                    break;
                case '-':
                    iconType = iconStr.charAt(1);
                    switch (iconType) {
                        case '-':
                            this.walls.add(false);
                            this.wallTypes.add(null);
                            this.coins.add(false);
                            this.pills.add(false);
                            break;
                        case 'c':
                            this.walls.add(false);
                            this.wallTypes.add(null);
                            this.coins.add(true);
                            this.pills.add(false);
                            break;
                        case 't':
                            this.walls.add(false);
                            this.wallTypes.add(null);
                            this.coins.add(false);
                            this.pills.add(true);
                    }
                    break;
            }
        }
    }

    public boolean isCoin(int index) {
        return this.coins.get(index);
    }

    public void consumeCoin(int index) {
        this.coins.set(index, false);
    }

    public boolean isPill(int index){
        return this.pills.get(index);
    }

    public void consumePill(int index) {
        this.pills.set(index, false);
    }

    public boolean isWall(int coords) {
        return walls.get(coords);
    }
}

package pacman.view;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import static javax.imageio.ImageIO.read;

public class Field {
    HashMap<String, Image> FieldIcons;
    private final ArrayList<String> sequenceOfIcons;
    String nameIcon;
    Image icon;
    char typeOfIcon;
    int i = 0;

    Field() throws FileNotFoundException {
        FieldIcons = new HashMap<>();
        sequenceOfIcons = new ArrayList<>();
        Scanner scanner = new Scanner(new File("src/Sprite/field.txt"));
        while (scanner.hasNext()) {
            nameIcon = scanner.next();
            sequenceOfIcons.add(nameIcon);
            if (!FieldIcons.containsKey(nameIcon)){
                typeOfIcon = nameIcon.charAt(0);
                switch (typeOfIcon){
                    case 'b':
                        try {
                            icon = read(new File("src/Sprite/" + "border/" + nameIcon + ".png"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 'w':
                        try {
                            icon = read(new File("src/Sprite/" + "wall/" + nameIcon + ".png"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case '-':
                        break;
                }
                if (typeOfIcon =='w' || typeOfIcon =='b' ) {
                    FieldIcons.put(nameIcon,icon);
                }
            }
        }
    }

    public ArrayList<String> getSequenceOfIcons() {
        return this.sequenceOfIcons;
    }

    public HashMap<String, Image> getFieldIcons() {
        return FieldIcons;
    }
}

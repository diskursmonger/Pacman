package pacman.view;

import javax.swing.*;
import java.awt.*;

public class Button extends JButton {
    Font font;
    String nameButton, namePanel;

    Button(String name, String panelName){
        this.nameButton = name;
        this.namePanel = panelName;
        this.font = new Font("Copperplate", Font.CENTER_BASELINE, 22);
        this.setBorderPainted(false);
        this.setFocusPainted(false);
        this.setContentAreaFilled(false);
        this.setOpaque(true);

        this.setBackground(new Color(4,2,5));
        this.setForeground(new Color(200,200,200));
        this.setFont(font);
        this.setText(nameButton);

        switch (panelName){
            case "Main Menu":
                initMenuButtons();
                break;
            case "Settings Menu":
                initSettingsButtons();
                initBackButton();
                break;
            case "High Scores Menu":
                initBackButton();
                break;
        }
    }
    private void initMenuButtons(){
        this.setBackground(new Color(4,2,5));
        this.setForeground(new Color(200,200,200));
        this.setFont(font);
        this.setText(nameButton);

        switch (nameButton){
            case "Start":
                this.setBounds(135,343,200,50); //157
                break;
            case "Settings":
                this.setBounds(135,423,200,50);
                break;
            case "High Scores":
                this.setBounds(135,503,200,50);
                break;
            case "Exit":
                this.setBounds(135,583,200,50);
                break;
        }
    }
    private void initSettingsButtons(){
    }

    private void initBackButton(){
        if (this.namePanel.equals("Settings Menu")){
            this.setForeground(new Color(200,200,200));
        } else {
            this.setForeground(new Color(4,4,4));
        }
        this.setOpaque(false);
        this.setFont(font);
        this.setText(nameButton);
        this.setBounds(135,503,200,50);
    }
}

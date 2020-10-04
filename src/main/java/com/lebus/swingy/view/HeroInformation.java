package com.lebus.swingy.view;

import com.lebus.swingy.model.PlayerCharacter;

import java.awt.*;

public class HeroInformation {

    public static TextArea getTextHeroInfo(PlayerCharacter hero) {
        TextArea textField = new TextArea();
        String className = hero.getPlayerClass().name().substring(0, 1).toUpperCase() + hero.getPlayerClass().name().substring(1).toLowerCase();
        int level = hero.getLevel();
        int experience = hero.getExperience();
        int attack = hero.getAttack();
        int defense = hero.getDefense();
        int hitPoints = hero.getHitPoints();

        textField.setText("Class\t\t: " + className + "\n" + "Level\t\t: " + level + "\n" +
                "Experience\t\t: " + experience + "\n" + "Attack\t\t: " + attack + "\n" +
                "Defense\t\t: " + defense + "\n" + "Hit Points\t\t: " + hitPoints + "\n");
        textField.setEditable(false);
        return textField;
    }

    public static TextArea getDimachaeriInfo() {
        TextArea textField = new TextArea();
        String className = "Dimachaeri";
        int level = 1;
        int experience = 0;
        int attack = 35;
        int defense = 29;
        int hitPoints = 32;

        textField.setText("Class\t\t: " + className + "\n" + "Level\t\t: " + level + "\n" +
                "Experience\t\t: " + experience + "\n" + "Attack\t\t: " + attack + "\n" +
                "Defense\t\t: " + defense + "\n" + "Hit Points\t\t: " + hitPoints + "\n");
        textField.setEditable(false);
        return textField;
    }

    public static TextArea getGaulInfo() {
        TextArea textField = new TextArea();
        String className = "Gaul";
        int level = 1;
        int experience = 0;
        int attack = 31;
        int defense = 32;
        int hitPoints = 33;

        textField.setText("Class\t\t: " + className + "\n" + "Level\t\t: " + level + "\n" +
                "Experience\t\t: " + experience + "\n" + "Attack\t\t: " + attack + "\n" +
                "Defense\t\t: " + defense + "\n" + "Hit Points\t\t: " + hitPoints + "\n");
        textField.setEditable(false);
        return textField;
    }

    public static TextArea getMurmilloInfo() {
        TextArea textField = new TextArea();
        String className = "Murmillo";
        int level = 1;
        int experience = 0;
        int attack = 35;
        int defense = 34;
        int hitPoints = 27;

        textField.setText("Class\t\t: " + className + "\n" + "Level\t\t: " + level + "\n" +
                "Experience\t\t: " + experience + "\n" + "Attack\t\t: " + attack + "\n" +
                "Defense\t\t: " + defense + "\n" + "Hit Points\t\t: " + hitPoints + "\n");
        textField.setEditable(false);
        return textField;
    }

    public static TextArea getHoplomachusInfo() {
        TextArea textField = new TextArea();
        String className = "Hoplomachus";
        int level = 1;
        int experience = 0;
        int attack = 37;
        int defense = 30;
        int hitPoints = 29;

        textField.setText("Class\t\t: " + className + "\n" + "Level\t\t: " + level + "\n" +
                "Experience\t\t: " + experience + "\n" + "Attack\t\t: " + attack + "\n" +
                "Defense\t\t: " + defense + "\n" + "Hit Points\t\t: " + hitPoints + "\n");
        textField.setEditable(false);
        return textField;
    }

    public static TextArea getRetiariusInfo() {
        TextArea textField = new TextArea();
        String className = "Retiarius";
        int level = 1;
        int experience = 0;
        int attack = 29;
        int defense = 35;
        int hitPoints = 32;

        textField.setText("Class\t\t: " + className + "\n" + "Level\t\t: " + level + "\n" +
                "Experience\t\t: " + experience + "\n" + "Attack\t\t: " + attack + "\n" +
                "Defense\t\t: " + defense + "\n" + "Hit Points\t\t: " + hitPoints + "\n");
        textField.setEditable(false);
        return textField;
    }

    public static TextArea getThraexInfo() {
        TextArea textField = new TextArea();
        String className = "Thraex";
        int level = 1;
        int experience = 0;
        int attack = 32;
        int defense = 32;
        int hitPoints = 32;

        textField.setText("Class\t\t: " + className + "\n" + "Level\t\t: " + level + "\n" +
                "Experience\t\t: " + experience + "\n" + "Attack\t\t: " + attack + "\n" +
                "Defense\t\t: " + defense + "\n" + "Hit Points\t\t: " + hitPoints + "\n");
        textField.setEditable(false);
        return textField;
    }

}

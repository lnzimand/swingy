package com.lebus.swingy.controller;

import com.lebus.swingy.App;
import com.lebus.swingy.database.Database;
import com.lebus.swingy.model.PlayerCharacter;
import com.lebus.swingy.model.PlayerClasses;
import com.lebus.swingy.model.armoury.ArmouryInventory;
import com.lebus.swingy.model.armoury.armor.Armor;
import com.lebus.swingy.model.armoury.helm.Helmet;
import com.lebus.swingy.model.armoury.weapons.Weapons;
import com.lebus.swingy.util.ValidateInput;
import com.lebus.swingy.view.ConsoleUI;
import com.lebus.swingy.view.GuiPages;

import java.sql.SQLException;
import java.util.ArrayList;

@SuppressWarnings("StatementWithEmptyBody")
public class Controller {

    PlayerCharacter player = null;
    PlayerCharacter villain;
    Database database;
    private static GuiPages pages = null;

    public Controller() {
        database = new Database();
        database.setCreateTable();
    }

    public void toStartPage() {
        if (App.GUI) {
            if (pages == null)
                pages = new GuiPages(this);
            else GuiPages.showStartPage();
        }
        else ConsoleUI.showStartPage(this);
    }

    public void selectHeroPage() {
        if (App.GUI) {
            System.out.println("Select Hero Page");
        }
        else ConsoleUI.selectHero(this, database);
    }

    public void exitProgram() {
        System.exit(0);
    }

    public void selectedHero(String string, ArrayList<PlayerCharacter> heroes) {
        for (PlayerCharacter hero : heroes) {
            if (string.equals(hero.getName())) {
                setGame(hero);
                break;
            }
        }
    }

    public String  createHero(String name, String className, String info) {

        String[] string = info.split("\n");
        int level = Integer.parseInt(string[1].substring(string[1].indexOf(":") + 2));
        int experience = Integer.parseInt(string[2].substring(string[2].indexOf(":") + 2));
        int attack = Integer.parseInt(string[3].substring(string[3].indexOf(":") + 2));
        int defense = Integer.parseInt(string[4].substring(string[4].indexOf(":") + 2));
        int hitPoints = Integer.parseInt(string[5].substring(string[5].indexOf(":") + 2));

        PlayerClasses playerClasses = getClassName(className);
        ValidateInput validateInput = new ValidateInput(App.validator);

        ArrayList<String> strings;
        player = new PlayerCharacter(name, playerClasses, 100, attack, hitPoints, defense, level, experience);
        strings = validateInput.validate(player);
        if (strings.size() > 0)
            return strings.get(0);
        setArmoury(player);
        try {
            player.setName(name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase());
            database.insertHero(player);
            setGame(player);
        } catch (SQLException e) {
            player = null;
            if (App.GUI) {
                return e.getMessage();
            } else {
                System.out.println("\n------------------------------------------------------------------");
                System.out.println("\n\t\t   HERO ALREADY EXIST\n");
                System.out.println("------------------------------------------------------------------\n");
                ConsoleUI.createHero(this);
            }
        }
        return null;
    }

    private void setArmoury(PlayerCharacter player) {
        ArmouryInventory armoury = new ArmouryInventory();

        if(player.getLevel() < 3) {
            if (fiftyFifty()) player.setArmor(armoury.getArmor());
            if (fiftyFifty()) player.setWeapon(armoury.getWeapon());
            if (fiftyFifty()) player.setHelmet(armoury.getHelmet());
        } else {
            if (fiftyFifty()) player.setArmor(armoury.getAdvArmor());
            if (fiftyFifty()) player.setWeapon(armoury.getAdvWeapon());
            if (fiftyFifty()) player.setHelmet(armoury.getAdvHelmet());
        }
    }

    private void setGame(PlayerCharacter player) {
        this.player = player;
        this.player.setMap((player.getLevel()-1)*5+10-(player.getLevel()%2));
        this.player.setCoordinates();
        if (App.GUI) { }
        else setGameConsole(null);
    }

    private void setGameConsole(String events) {
        ConsoleUI.setGame(this, events);
    }

    private PlayerClasses getClassName(String className) {
        switch (className) {
            case "Dimachaeri":
                return PlayerClasses.DIMACHAERI;
            case "Gaul":
                return PlayerClasses.GAUL;
            case "Murmillo":
                return PlayerClasses.MURMILLO;
            case "Hoplomachus":
                return PlayerClasses.HOPLOMACHUS;
            case "Retiarius":
                return PlayerClasses.RETIARIUS;
            default:
                return PlayerClasses.THRAEX;
        }
    }

    public PlayerCharacter getPlayer() {
        return player;
    }

    public void movement(String action) {

        switch(action.toLowerCase()) {
            case "up":
                getPlayer().updatePosition("North");
                break;
            case "down":
                getPlayer().updatePosition("South");
                break;
            case "left":
                getPlayer().updatePosition("West");
                break;
            default:
                getPlayer().updatePosition("East");
                break;
        }

        if (player.getCoordinates().getX() < 0 || player.getCoordinates().getY() < 0 ||
                player.getCoordinates().getX() >= player.getMap().getSize() |
                        player.getCoordinates().getY() >= player.getMap().getSize()
        ) {
            winGame();
            return;
        }

        if (isSpaceOccupied())  {
            battleActions();
        } else {
            if (App.GUI) { }
            else setGameConsole("Hero at:\nX: " + player.getCoordinates().getX() + "\nY: " + player.getCoordinates().getY());
        }

    }

    private void battleActions() {
        boolean result;
        if (App.GUI) result = GuiPages.confirmationGui("VILLAIN ENCOUNTERED, you want to FIGHT?", "VILLAIN ENCOUNTERED");
        else result = ConsoleUI.confirmation("VILLAIN ENCOUNTERED\n1 -> FIGHT\n2 -> FLEE", "Enter 1 to FIGHT OR 2 to FLEE: ");
        if (!result) {
            if (fiftyFifty()) {
                battle();
                if (player.getHealth() < 1) {
                    if (App.GUI) GuiPages.gameOver("COULDN'T RUN FASTER AND THE VILLAIN CAUGHT YOU\nYOU LOST THE FIGHT");
                    else ConsoleUI.guiGameOverConsole("COULDN'T RUN FASTER AND THE VILLAIN CAUGHT YOU\nYOU LOST THE\nFIGHT", this, database);
                } else {
                    player.getMap().setCell(player.getCoordinates().getX(),
                            player.getCoordinates().getY(), false);
                    if (App.GUI) GuiPages.info("You couldn't outrun the villain and were forced to fight and WON\n" +
                            "Hero at:\nX: " + player.getCoordinates().getX() +
                            "\nY: " + player.getCoordinates().getY());
                    else setGameConsole("You couldn't outrun the villain and were forced to fight and WON\n" +
                            "Hero at:\nX: " + player.getCoordinates().getX() +
                            "\nY: " + player.getCoordinates().getY());
                }
            } else {
                player.revertBackToPrevPos();
                if (App.GUI) GuiPages.info("You have successfully fled away from the Villain\n" +
                        "Hero back at:\nX: " + player.getCoordinates().getX() +
                        "\nY: " + player.getCoordinates().getY());
                else setGameConsole("You have successfully fled away from the Villain\n" +
                        "Hero back at:\nX: " + player.getCoordinates().getX() +
                        "\nY: " + player.getCoordinates().getY());
            }
        } else {
            battle();
            if (player.getHealth() < 1) {
                if (App.GUI) GuiPages.gameOver();
                else ConsoleUI.guiGameOverConsole("YOU LOST", this, database);
            } else {
                player.getMap().setCell(player.getCoordinates().getX(),
                        player.getCoordinates().getY(), false);
                if (App.GUI) GuiPages.info("VILLAIN DEFEATED\n"
                        + "Hero at:\nX: " + player.getCoordinates().getX() +
                        "\nY: " + player.getCoordinates().getY());
                else setGameConsole("VILLAIN DEFEATED\n"
                        + "Hero at:\nX: " + player.getCoordinates().getX() +
                        "\nY: " + player.getCoordinates().getY());
            }
        }
    }

    private void winGame() {

        boolean result;

        if (player.getLevel() > 5) {
            if (App.GUI) result = GuiPages.confirmationGui("GAME COMPLETED, YOU WON!!!!!!!! Click Yes to restart and No to exit", "CONGRATULATIONS");
            else result = ConsoleUI.confirmation("GAME COMPLETED, YOU WON!!!!!!!!\n1 -> RESTART\n2 -> EXIT", "ENTER 1 to RESTART or 2 to EXIT: ");
            try {
                database.updatePInfo(player);
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (result) toStartPage();
            else System.exit(0);
        }
        else {
            if (App.GUI) result = GuiPages.confirmationGui(player.getName().toUpperCase() + " completed level " + player.getLevel(), "Enter OK to continue");
            else result = ConsoleUI.confirmation(player.getName().toUpperCase() + " completed level " + player.getLevel(), "Enter 1 to CONTINUE or 2 to go to MENU: ");
            int exp = player.getLevel() * 1000 + (int)Math.pow(player.getLevel() - 1, 2) * 450;
            player.setLevel(player.getLevel() + 1);
            int r = getRandomInRange(0, 2);
            ArmouryInventory armoury = new ArmouryInventory();
            switch(r) {
                case 0:
                    Weapons weapon;
                    if (player.getWeapon() == null) {
                        if (player.getLevel() > 2)
                            weapon = armoury.getAdvWeapon();
                        else weapon = armoury.getWeapon();
                    } else {
                        do {
                            if (player.getLevel() > 2) weapon = armoury.getAdvWeapon();
                            else weapon = armoury.getWeapon();
                        }
                        while (weapon.getName().equals(player.getWeapon().getName()));
                    }
                    if (App.GUI) {
                        if (GuiPages.confirmationGui("YOU WON A new WEAPON\nName: "
                                + weapon.getName() + "\nAttack: " + weapon.getAttack() + ", you want to keep it?", "NEW WEAPON"))
                            player.setWeapon(weapon);
                    } else if (ConsoleUI.confirmation("YOU WON A new WEAPON\nName: "
                            + weapon.getName() + "\nAttack: " + weapon.getAttack(), "ENTER 1 to CHANGE or 2 to DISCARD: "))
                        player.setWeapon(weapon);
                    break;
                case 1:
                    Armor armor;
                    if (player.getArmor() == null) {
                        if (player.getLevel() > 2)
                            armor = armoury.getAdvArmor();
                        else armor = armoury.getArmor();
                    } else {
                        do {
                            if (player.getLevel() > 2) armor = armoury.getAdvArmor();
                            else armor = armoury.getArmor();
                        }
                        while (armor.getName().equals(player.getArmor().getName()));
                    }
                    if (App.GUI) {
                        if (GuiPages.confirmationGui("YOU WON A new ARMOR\nName: "
                                + armor.getName() + "\nAttack: " + armor.getDefense() + ", you want to keep it?", "NEW ARMOR"))
                            player.setArmor(armor);
                    } else if (ConsoleUI.confirmation("YOU WON A new ARMOR\nName: "
                            + armor.getName() + "\nAttack: " + armor.getDefense(), "ENTER 1 to CHANGE or 2 to DISCARD: "))
                        player.setArmor(armor);
                    break;
                default:
                    Helmet helmet;
                    if (player.getHelmet() == null) {
                        if (player.getLevel() > 2)
                            helmet = armoury.getAdvHelmet();
                        else helmet = armoury.getHelmet();
                    } else {
                        do {
                            if (player.getLevel() > 2) helmet = armoury.getAdvHelmet();
                            else helmet = armoury.getHelmet();
                        }
                        while (helmet.getName().equals(player.getHelmet().getName()));
                    }
                    if (App.GUI) {
                        if (GuiPages.confirmationGui("YOU WON A new HELMET\nName: "
                                + helmet.getName() + "\nAttack: " + helmet.getHitPoints() + ", you want to keep it?", "NEW HELMET")) player.setHelmet(helmet);
                    } else if (ConsoleUI.confirmation("YOU WON A new HELMET\nName: "
                            + helmet.getName() + "\nAttack: " + helmet.getHitPoints(), "ENTER 1 to CHANGE or 2 to DISCARD: "))
                        player.setHelmet(helmet);
                    break;
            }
            try {
                player.setExperience(player.getExperience() + exp);
                player.setHealth(player.getLevel()*100);
                database.updatePInfo(player);
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            if (result) {
                try {
                    player = database.getHero(player.getName());
                    player.setMap((player.getLevel()-1)*5+10-(player.getLevel()%2));
                    player.setCoordinates();
                    if (App.GUI) System.out.println(player.getName() + " levelled up!!");
                    else setGameConsole(player.getName() + " levelled up!!");
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else toStartPage();
        }
    }

    private void battle() {
        villain = generateVillain();
        while (player.getHealth() > 0 && villain.getHealth() > 0) {
            player.fight(villain);
            villain.fight(player);
        }
        int addExp = player.getExperience() + villain.getAllAttack() + villain.getAllDefense() + villain.getHitPoints();
        player.setExperience(addExp);
    }

    public ArrayList<PlayerCharacter> getHeroes() {
        try {
            return database.getHeroes();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public PlayerCharacter generateVillain() {
        villain = new  PlayerCharacter("Gladiator", PlayerClasses.VILLIAN, player.getLevel() * 100, getRandomInRange(32, 28),
                getRandomInRange(32, 28), getRandomInRange(32, 28), player.getLevel(),
                getRandomInRange(32, 28));

        setArmoury(villain);
        return villain;
    }

    public static int getRandomInRange(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public static boolean fiftyFifty() {
        return Math.random() > 0.5;
    }

    private boolean isSpaceOccupied() {
        boolean[][] map = player.getMap().getMap();
        return map[player.getCoordinates().getY()][player.getCoordinates().getX()];
    }

}

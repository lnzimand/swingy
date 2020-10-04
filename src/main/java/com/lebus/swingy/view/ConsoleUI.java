package com.lebus.swingy.view;

import com.lebus.swingy.controller.Controller;
import com.lebus.swingy.database.Database;
import com.lebus.swingy.model.PlayerCharacter;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class ConsoleUI {
    private static Scanner scanner = getScanner();
    private static int sValue = -1;

    public static void showStartPage(Controller controller) {
        String[] arrayString = new String[2];
        arrayString[0] = "\t\t\tSWINGY | lnzimand\n\n\tENTER THE FOLLOWING COMMANDS TO PLAY";
        arrayString[1] = "\n\tCREATE:\tCreate a NEW HERO\n\tSELECT:\tSelect a previously created HERO";
        writeArray(arrayString);
        System.out.print("Command: ");

        String string = null;
        do {
            if (scanner.hasNext()) {
                string = scanner.nextLine().trim();
                if (isValidInput(string)) {
                    userInputMessagesError("\tERROR: WRONG COMMAND\n\tCORRECT COMMANDS: <CREATE> / <SELECT>\n\tPLEASE ENTER THE CORRECT COMMAND", "Command: ");
                    scanner = getScanner();
                }
            }
        } while (isValidInput(string));

        if (string.equalsIgnoreCase("create")) {
            createHero(controller);
        } else if (string.equalsIgnoreCase("select")) {
            controller.selectHeroPage();
        } else {
            scanner.close();
            System.out.println("Program exiting...");
            System.exit(0);
        }
    }

    private static void userInputMessagesError(String info, String userInput) {
        System.err.println("\n------------------------------------------------------------------");
        System.err.println(info);
        System.err.println("------------------------------------------------------------------");
        System.err.print(userInput);
    }

    public static void createHero(Controller controller) {
        userInputMessages("\tWARNING: Hero's name should be 3 characters long", "Enter hero's name: ");
        String name = getHeroName();
        String className = getClassName();
        controller.createHero(name, className.substring(0, 1).toUpperCase() + className.substring(1).toLowerCase(), getHeroInfo(className));
    }

    private static String getHeroName() {
        String name = null;
        do {
            assert scanner != null;
            if (scanner.hasNext()) {
                name = scanner.nextLine().trim();
                if (name.length() < 3) {
                    userInputMessagesError("\tWARNING: Hero's name should be 3 characters long", "Enter hero's name: ");
                    scanner = getScanner();
                }
            }
        } while (name.length() < 3);
        return name;
    }

    private static String getClassName() {
        String className = null;
        userInputMessages("\t\t\t   SELECT HERO CLASS\n\t\t\tAVAILABLE HERO CLASSES\n\tDimachaeri Gaul Murmillo Hoplomachus Retiarius Thraex", "Enter hero's class: ");
        do {
            assert scanner != null;
            if (scanner.hasNext()) {
                className = scanner.nextLine().trim();
                if (heroClassValid(className)) {
                    userInputMessagesError("\t\tAVAILABLE HERO CLASSES\n\tDimachaeri Gaul Murmillo Hoplomachus Retiarius Thraex", "Enter hero's class: ");
                    scanner = getScanner();
                }
            }
        } while (heroClassValid(className));
        return className;
    }

    private static String getHeroInfo(String className) {
        switch (className.toLowerCase()) {
            case "dimachaeri":
                return HeroInformation.getDimachaeriInfo().getText();
            case "gaul":
                return HeroInformation.getGaulInfo().getText();
            case "hoplomachus":
                return HeroInformation.getHoplomachusInfo().getText();
            case "thraex":
                return HeroInformation.getThraexInfo().getText();
            case "murmillo":
                return HeroInformation.getMurmilloInfo().getText();
            default:
                return HeroInformation.getRetiariusInfo().getText();
        }
    }

    private static void userInputMessages(String info, String userInput) {
        writeStringNewline("------------------------------------------------------------------");
        writeStringNewline(info);
        writeStringNewline("------------------------------------------------------------------");
        writeString(userInput);
    }

    private static boolean heroClassValid(String className) {
        switch (className.toLowerCase()) {
            case "dimachaeri":
            case "gaul":
            case "murmillo":
            case "hoplomachus":
            case "retiarius":
            case "thraex":
                return false;
            default:
                return true;
        }
    }

    private static boolean isValidInput(String string) {

        if (string.equalsIgnoreCase("create")) {
            return false;
        } else if (string.equalsIgnoreCase("select")) {
            return false;
        } else return !string.equalsIgnoreCase("exit");
    }

    public static void writeArray(String[] arrayString) {
        for (String s : arrayString) System.out.println(s);
    }

    public static void writeString(String string) {
        System.out.print(string);
    }

    public static void writeStringNewline(String string) {
        System.out.println(string);
    }

    public static Scanner getScanner() {
        try {
            return new Scanner(System.in);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static void setGame(Controller controller, String events) {
        printCharacter(controller.getPlayer());
        printMap(controller.getPlayer());
        if (events != null)
            System.out.println(events);
        controller.movement(navigations());
    }

    private static String navigations() {
        userInputMessages("UP\nDOWN\nLEFT\nRIGHT", "Enter direction: ");
        String direction = null;
        do {
            assert scanner != null;
            if (scanner.hasNext()) {
                direction = scanner.nextLine().trim();
                if (directionValid(direction)) {
                    userInputMessagesError("UP\nDOWN\nLEFT\nRIGHT", "Enter direction: ");
                    scanner = getScanner();
                }
            }
        } while (directionValid(direction));
        return direction;
    }

    private static boolean directionValid(String direction) {
        switch (direction.toLowerCase()) {
            case "up":
            case "down":
            case "left":
            case "right":
                return false;
            default:
                return true;
        }
    }

    private static void printCharacter(PlayerCharacter player) {
        System.out.println("NAME: " + player.getName() + "\tLEVEL: " + player.getLevel() + "\tHEALTH: " + player.getHealth() +
                "\nATTACK: " + player.getAttack() + "\tHIT POINTS: " + player.getHitPoints() + "\tEXPERIENCE: " +
                player.getHitPoints());
        if (player.getWeapon() != null)
            System.out.println("WEAPON: " + player.getWeapon().getName() + " +" + player.getWeapon().getAttack() + "\tALL ATTACK: " +
                    player.getAllAttack());
        if (player.getArmor() != null)
            System.out.println("ARMOR: " + player.getArmor().getName() + " +" + player.getArmor().getDefense() + "\tALL DEFENSE: " +
                    player.getAllDefense());
        if (player.getHelmet() != null)
            System.out.println("HELMET: " + player.getHelmet().getName() + " +" + player.getHelmet().getHitPoints());
    }

    private static void printMap(PlayerCharacter player) {
        boolean[][] map = player.getMap().getMap();
        for (int index = 0; index < player.getMap().getSize(); index++) {
            for (int index2 = 0; index2 < player.getMap().getSize(); index2++) {
                if (index == ((player.getLevel()-1)*5+10-(player.getLevel()%2)) / 2 && index2 == ((player.getLevel()-1)*5+10-(player.getLevel()%2)) / 2) {
                    if (player.getCoordinates().getX() == index2 && player.getCoordinates().getY() == index)
                        System.out.print("O");
                    else System.out.print("C");
                }
                else if (index == player.getCoordinates().getY() && index2 == player.getCoordinates().getX()) {
                    System.out.print("O");
                    if (map[index][index2])
                        System.out.print(" X");
                } else {
                    System.out.print("*");
                }
                System.out.print("\t");
            }
            System.out.println();
        }
    }

    public static void guiGameOverConsole(String message, Controller controller, Database database) {
        boolean result;
        result = confirmation(message, "Enter 1 to RESTART OR 2 to EXIT: ");
        database.removeHero(controller.getPlayer());
        if (result) controller.toStartPage();
        else controller.exitProgram();
    }

    public static boolean confirmation(String info, String userInput) {
        boolean result;
        userInputMessages(info, userInput);
        String answer = null;
        do {
            if (scanner.hasNext()) {
                answer = scanner.nextLine().trim();
                if (confirmationValid(answer)) {
                    userInputMessagesError(info, userInput);
                    scanner = getScanner();
                }
            }
        } while (confirmationValid(answer));
        result = Integer.parseInt(answer) == 1;
        return result;
    }

    private static boolean confirmationValid(String answer) {
        try {
            switch (Integer.parseInt(answer)) {
                case 1:
                case 2:
                    return false;
                default:
                    return true;
            }
        } catch (Exception e) {
            return true;
        }
    }

    public static void selectHero(Controller controller, Database database) {
        try {
            ArrayList<PlayerCharacter> heroes = database.getHeroes();
            if (heroes.size() == 0) {
                userInputMessagesError("No heroes available, create a new hero.", "");
                createHero(controller);
            } else getSelectedHero(controller, heroes);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void getSelectedHero(Controller controller, ArrayList<PlayerCharacter> heroes) {
        if (sValue == -1) sValue =  0;
        writeStringNewline("\n------------------------------------------------------------------");
        writeStringNewline("CURRENTLY SELECTED HERO");
        writeStringNewline("------------------------------------------------------------------\n");
        showSelectedHero(heroes.get(sValue));
        showOtherHeroes(heroes);
        String selectedHero = getUserInput(heroes);
        if (selectedHero.toLowerCase().equals("select")) {
            getHeroIndex(selectedHero, heroes);
            controller.selectedHero(heroes.get(sValue).getName(), heroes);
        } else {
            sValue = getHeroIndex(selectedHero, heroes);
            getSelectedHero(controller, heroes);
        }
    }

    private static int getHeroIndex(String heroName, ArrayList<PlayerCharacter> heroes) {
        int index = 0;
        for (PlayerCharacter hero: heroes
        ) {
            if (heroName.toLowerCase().equals(hero.getName().toLowerCase())) {
                return index;
            }
            index++;
        }
        return index;
    }

    private static String getUserInput(ArrayList<PlayerCharacter> heroes) {
        String result = null;
        userInputMessages("Enter hero's name to show hero's details or SELECT to SELECT\nthe currently selected HERO", "HERO's name or SELECT: ");
        do {
            if (scanner.hasNext()) {
                result = scanner.nextLine().trim();
                if (heroValid(result, heroes)) {
                    writeStringNewline("\n------------------------------------------------------------------");
                    writeStringNewline("CURRENTLY SELECTED HERO");
                    writeStringNewline("------------------------------------------------------------------\n");
                    showSelectedHero(heroes.get(sValue));
                    showOtherHeroes(heroes);
                    userInputMessagesError("Enter hero's name to show hero's details or SELECT to SELECT\nthe currently selected HERO", "HERO's name or SELECT: ");
                    scanner = getScanner();
                }
            }
        } while (heroValid(result, heroes));
        return result;
    }

    private static boolean heroValid(String name, ArrayList<PlayerCharacter> heroes) {
        if (name.toLowerCase().equals("select")) {
            return false;
        }
        for (PlayerCharacter hero: heroes
        ) {
            if (name.toLowerCase().equals(hero.getName().toLowerCase()))
                return false;
        }
        return true;
    }

    private static void showOtherHeroes(ArrayList<PlayerCharacter> heroes) {
        writeStringNewline("\n------------------------------------------------------------------");
        writeStringNewline("OTHER HEROES");
        writeStringNewline("------------------------------------------------------------------\n");
        int index = 0;
        for (PlayerCharacter hero : heroes) {
            System.out.print("NAME: " + hero.getName() + "  <||>  ");
            index++;
            if (index == 5) {
                System.out.println();
                index = 0;
            }
        }
        System.out.println();
    }

    private static void showSelectedHero(PlayerCharacter player) {
        printCharacter(player);
    }

}

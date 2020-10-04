package com.lebus.swingy.model;

import com.lebus.swingy.model.armoury.armor.Armor;
import com.lebus.swingy.model.armoury.helm.Helmet;
import com.lebus.swingy.model.armoury.weapons.Fists;
import com.lebus.swingy.model.armoury.weapons.Weapons;
import com.lebus.swingy.util.Coordinates;
import com.lebus.swingy.util.Map;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Random;

public @Data
class PlayerCharacter {
    @NotNull(message = "Please fill out the name")
    @Size(min = 2, max = 15, message = "Name should be 2 to 15 characters long")
    @Pattern(regexp = "^[a-zA-Z]*$", message = "Only alphabets allowed")
    private String name;
    private int health;
    private int attack;
    private int hitPoints;
    private int defense;
    private int level;
    private int experience;
    private Coordinates coordinates;
    private Coordinates lastCoordinates;
    private final PlayerClasses playerClass;
    private Map map;
    private Weapons weapon;
    private Armor armor;
    private Helmet helmet;

    public PlayerCharacter(String name, PlayerClasses className , int health, int attack, int hitPoints, int defense, int level, int experience) {
        this.name = name;
        this.playerClass = className;
        this.health = health;
        this.attack = attack;
        this.hitPoints = hitPoints;
        this.defense = defense;
        this.level = level;
        this.weapon = new Fists();
        this.armor = null;
        this.helmet = null;
        this.experience = experience;
    }

    public void setMap(int size) {
        map = new Map(size);
    }

    public Map getMap() {
        return map;
    }

    public void setCoordinates() {
        coordinates = new Coordinates((((this.getLevel()-1)*5+10-(this.getLevel()%2)) / 2), (((this.getLevel()-1)*5+10-(this.getLevel()%2)) / 2));
    }

    public void revertBackToPrevPos() {
        coordinates = lastCoordinates;
    }

    public void increaseXPos() {
        coordinates.setX(coordinates.getX() + 1);
    }

    public void increaseYPos() {
        coordinates.setY(coordinates.getY() + 1);
    }

    public void decreaseXPos() {
        coordinates.setX(coordinates.getX() - 1);
    }

    public void decreaseYPos() {
        coordinates.setY(coordinates.getY() - 1);
    }

    public void updatePosition(String direction) {
        lastCoordinates = new Coordinates(coordinates.getX(), coordinates.getY());
        switch (direction) {
            case "North":
                decreaseYPos();
                break;
            case "South":
                increaseYPos();
                break;
            case "West":
                decreaseXPos();
                break;
            default:
                increaseXPos();
                break;
        }
    }

    public void goToLastPos() {
        coordinates = lastCoordinates;
    }

    public void attack(PlayerCharacter character) {

        int damageTook = (this.getLuckAmount() + this.getAllAttack() + this.getHitPoints()) - (character.getLuckAmount() + character.getAllDefense() + character.getHitPoints());
        if (damageTook <= 0) return;
        character.setHealth(character.getHealth() - damageTook);
    }

    public void fight(PlayerCharacter character) {
        attack(character);
    }

    public int getLuckAmount() {
        return new Random().nextInt((level + 1) * 13);
    }

    public int getAllAttack() {
        if (weapon != null)
            return this.getAttack() + this.getWeapon().getAttack();
        return this.getAttack();
    }

    public int getAllDefense() {
        if (armor != null)
            return this.getDefense() + this.getArmor().getDefense();
        return this.getDefense();
    }

}

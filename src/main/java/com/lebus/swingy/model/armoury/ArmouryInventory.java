package com.lebus.swingy.model.armoury;

import com.lebus.swingy.controller.Controller;
import com.lebus.swingy.model.armoury.armor.*;
import com.lebus.swingy.model.armoury.helm.*;
import com.lebus.swingy.model.armoury.weapons.*;
import java.util.ArrayList;

public class ArmouryInventory {

    private final ArrayList<Armor> armors = new ArrayList<>();
    private final ArrayList<Weapons> weapons = new ArrayList<>();
    private final ArrayList<Helmet> helms = new ArrayList<>();
    private final ArrayList<Armor> advArmors = new ArrayList<>();
    private final ArrayList<Weapons> advWeapons = new ArrayList<>();
    private final ArrayList<Helmet> advHelms = new ArrayList<>();

    public ArmouryInventory() {
        addArmors();
        addWeapons();
        addHelmets();
    }

    private void addArmors() {
        armors.add(new BronzeShield());
        armors.add(new WoodShield());
        armors.add(new SilverShield());
        advArmors.add(new GoldShield());
        advArmors.add(new PlatinumShield());
    }

    public Armor getArmor() {
        int rand = Controller.getRandomInRange(0, 3);
        return armors.get(rand);
    }

    public Armor getAdvArmor() {
        int rand = Controller.getRandomInRange(0, 2);
        return advArmors.get(rand);
    }

    private void addWeapons() {
        weapons.add(new BronzeBow());
        weapons.add(new BronzeDagger());
        weapons.add(new BronzeSword());
        weapons.add(new BronzeBow());
        weapons.add(new SilverBow());
        weapons.add(new SilverDagger());
        weapons.add(new SilverSword());
        weapons.add(new WoodBow());
        weapons.add(new WoodDagger());
        weapons.add(new WoodSword());
        advWeapons.add(new CrossBow());
        advWeapons.add(new GoldBow());
        advWeapons.add(new GoldDagger());
        advWeapons.add(new GoldSword());
        advWeapons.add(new Halberd());
        advWeapons.add(new KnightlySword());
        advWeapons.add(new Panabas());
        advWeapons.add(new PlatinumBow());
        advWeapons.add(new PlatinumDagger());
        advWeapons.add(new PlatinumSword());
        advWeapons.add(new SpikedBallFlails());
    }

    public Weapons getWeapon() {
        int rand = Controller.getRandomInRange(0, 10);
        return weapons.get(rand);
    }

    public Weapons getAdvWeapon() {
        int rand = Controller.getRandomInRange(0, 11);
        return advWeapons.get(rand);
    }

    private void addHelmets() {
        helms.add(new BronzeHelmet());
        helms.add(new SilverHelmet());
        advHelms.add(new GoldHelmet());
        advHelms.add(new PlatinumHelmet());
    }

    public Helmet getHelmet() {
        int rand = Controller.getRandomInRange(0, 2);
        return helms.get(rand);
    }

    public Helmet getAdvHelmet() {
        int rand = Controller.getRandomInRange(0, 2);
        return advHelms.get(rand);
    }

}

package com.lebus.swingy.model.armoury.weapons;

import com.lebus.swingy.model.armoury.Armoury;
import com.lebus.swingy.model.armoury.ArmouryType;
import com.lebus.swingy.model.armoury.Inventory;
import lombok.Data;

public @Data
class Weapons extends Armoury implements Inventory {

    private int attack;

    public Weapons(String name) {
        super(name, ArmouryType.WEAPON);
    }

}

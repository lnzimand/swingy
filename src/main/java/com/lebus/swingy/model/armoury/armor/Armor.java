package com.lebus.swingy.model.armoury.armor;

import com.lebus.swingy.model.armoury.Armoury;
import com.lebus.swingy.model.armoury.ArmouryType;
import com.lebus.swingy.model.armoury.Inventory;
import lombok.Data;

public @Data
class Armor extends Armoury implements Inventory {

    private int defense;

    public Armor(String name) {
        super(name, ArmouryType.ARMOR);
    }

}

package com.lebus.swingy.model.armoury.helm;

import com.lebus.swingy.model.armoury.Armoury;
import com.lebus.swingy.model.armoury.ArmouryType;
import com.lebus.swingy.model.armoury.Inventory;
import lombok.Data;

public @Data
abstract class Helmet extends Armoury implements Inventory {

    private int hitPoints;

    public Helmet(String name) {
        super(name, ArmouryType.HELMET);
    }

}

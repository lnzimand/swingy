package com.lebus.swingy.model.armoury;

import lombok.Getter;
import lombok.Setter;

public abstract class Armoury {

    @Setter
    @Getter
    protected String name;
    protected ArmouryType type;

    public Armoury(String name, ArmouryType type) {
        this.name = name;
        this.type = type;
    }

}

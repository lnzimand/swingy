package com.lebus.swingy;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class Car {

    private Long Id;

    @NotNull
    @Size(min=4, max=50)
    private String name;

    @Min(value = 1000)
    @Max(value = 5000000, message="There is no such expensive car")
    private int price;

    public Car() {}

    public Car(String name, int price) {

        this.name = name;
        this.price = price;
    }
}


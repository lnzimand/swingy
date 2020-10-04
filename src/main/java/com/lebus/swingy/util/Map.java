package com.lebus.swingy.util;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Random;

public class Map {

    @Setter @Getter
    int size;
    @Getter
    private boolean map[][];
    Random random = new Random();

    public Map(int size) {
        this.size = size;
        map = new boolean[size][size];
        generateMap();
    }

    private void generateMap() {
        for (int index = 0; index < size; index++) {
            for (int index2 = 0; index2 < size; index2++) {
                if ((random.nextInt(size) < 4)) {
                    if (index == size / 2 && index2 == size / 2)
                        map[index][index2] = false;
                    else map[index][index2] = true;
                } else map[index][index2] = false;
            }
        }
    }

    public void setCell(int x, int y, boolean value) {
        map[y][x] = value;
    }

}

package ru.simul.service;

import ru.simul.createactions.FillEntity;
import ru.simul.world.Field;
import ru.simul.world.Universe;

import java.util.Properties;

public class InitWorld {

    public Universe createUniverse( Properties properties) {
        Field field = new Field(0, Integer.parseInt(properties.getProperty("field.x.max")),
                0, Integer.parseInt(properties.getProperty("field.y.max")));
        int totalCell = field.maxX() * field.maxY();
        Universe universe = new Universe();
        universe.setField(field);

        FillEntity fill = new FillEntity(universe, totalCell, properties);
        fill.create();
        return universe;
    }
}

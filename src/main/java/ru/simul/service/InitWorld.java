package ru.simul.service;

import ru.simul.createactions.FillEntity;
import ru.simul.world.Field;
import ru.simul.world.Universe;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class InitWorld {

    public Universe createUniverse() {
        Properties prop = new Properties();
        try (InputStream in = InitWorld.class.getClassLoader().getResourceAsStream("application.properties")) {
            prop.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Field field = new Field(0, Integer.parseInt(prop.getProperty("field.x.max")),
                0, Integer.parseInt(prop.getProperty("field.y.max")));
        int totalCell = field.maxX() * field.maxY();
        Universe universe = new Universe();
        universe.setField(field);

        FillEntity fill = new FillEntity(universe, totalCell, prop);
        fill.create();
        return universe;
    }
}

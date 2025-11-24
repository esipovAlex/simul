package ru.simul.createactions;

import ru.simul.world.Coordinate;
import ru.simul.world.Universe;

import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

public class RockCreateAction extends CoordinateGenerator {

    @Override
    public void create(int total, Universe universe, Properties properties) {
        int totalRock = (int) (total * Double.parseDouble(properties.getProperty("total.rock")));
        Set<Coordinate> rocks = getRandomCoordinates(universe, totalRock, Set.of()).collect(Collectors.toSet());
        universe.setRocks(rocks);
        universe.getFillsCells().addAll(rocks);
    }
}

package ru.simul.createactions;

import ru.simul.world.Coordinate;
import ru.simul.world.Universe;

import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

public class TreeCreateAction extends CoordinateGenerator {

    @Override
    public void create(int total, Universe universe, Properties properties) {
        int totalTree = (int) (total * Double.parseDouble(properties.getProperty("total.tree")));
        Set<Coordinate> trees = getRandomCoordinates(universe, totalTree, Set.of()).collect(Collectors.toSet());
        universe.setTrees(trees);
        universe.getFillsCells().addAll(trees);
    }
}

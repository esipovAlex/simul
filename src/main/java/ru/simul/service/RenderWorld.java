package ru.simul.service;

import ru.simul.world.Coordinate;
import ru.simul.world.Field;
import ru.simul.world.Names;
import ru.simul.world.Universe;

import java.util.Set;
import java.util.StringJoiner;

public class RenderWorld {

    public Names[][] prepare(Universe universe) {
        Field field = universe.getField();
        Set<Coordinate> rocks = universe.getRocks();
        Set<Coordinate> trees = universe.getTrees();
        Set<Coordinate> grasses = universe.getGrasses().keySet();
        Set<Coordinate> herbivores = universe.getHerbivore().keySet();
        Set<Coordinate> predators = universe.getPredators().keySet();
        Names[][] arrNames = new Names[field.maxX()][field.maxY()];
        for (int x = 0; x < field.maxX(); x++) {
            for (int y = 0;y < field.maxY(); y++) {
                Coordinate coordinate = new Coordinate(x, y);
                if (rocks.contains(coordinate)) {
                    arrNames[x][y] = Names.ROCK;
                } else if (trees.contains(coordinate)) {
                    arrNames[x][y] = Names.TREE;
                } else if (grasses.contains(coordinate)) {
                    arrNames[x][y] = Names.GRASS;
                } else if (herbivores.contains(coordinate)) {
                    arrNames[x][y] = Names.HERBIVORE;
                } else if (predators.contains(coordinate)) {
                    arrNames[x][y] = Names.PREDATOR;
                } else {
                    arrNames[x][y] = Names.CELL;
                }
            }
        }
        universe.setNames(arrNames);
        return arrNames;
    }

    public void render(Names[][] arrNames) {
        for (Names[] arrName : arrNames) {
            StringJoiner joiner = new StringJoiner("");
            for (Names names : arrName) {
                joiner.add(names.getText());
            }
            System.out.println(joiner);
        }
    }
}

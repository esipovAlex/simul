package ru.simul.createactions;

import ru.simul.world.Coordinate;
import ru.simul.world.Field;
import ru.simul.world.Universe;

import java.util.HashSet;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.stream.Stream;

public abstract class CoordinateGenerator implements CreateAction {

    private final Random random = new Random();

    protected Stream<Coordinate> getRandomCoordinates(Universe universe, int addedEntity, Set<Coordinate> target) {
        Field field = universe.getField();
        Set<Coordinate> fillsCells = universe.getFillsCells();
        Set<Coordinate> result = new HashSet<>();
        while (result.size() <= addedEntity) {
            int x = random.nextInt(field.maxX());
            int y = random.nextInt(field.maxY());
            Coordinate coordinate = new Coordinate(x, y);
            if (!fillsCells.contains(coordinate) && !isNearTarget(x, y, target)) {
                result.add(coordinate);
            }
        }
        return result.stream();
    }

    private boolean isNearTarget(int x, int y, Set<Coordinate> targets) {
        return targets.stream().anyMatch(c -> (Math.abs(x - c.x()) <= 1 && Math.abs(y - c.y()) <= 1));
    }

    @Override
    public void create(int total, Universe universe, Properties properties) {
    }
}

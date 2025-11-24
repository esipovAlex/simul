package ru.simul.createactions;

import ru.simul.models.Grass;
import ru.simul.world.Coordinate;
import ru.simul.world.Universe;

import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

public class GrassCreateAction extends CoordinateGenerator {
    @Override
    public void create(int total, Universe universe, Properties properties) {
        int totalGrass = (int) (total * Double.parseDouble(properties.getProperty("total.grass")));
        Map<Coordinate, Grass> grasses = getRandomCoordinates(universe, totalGrass, Set.of())
                .collect(Collectors.toMap(
                        a -> a,
                        Grass::new,
                        (a, b) -> a));
        universe.setGrasses(grasses);
        universe.getFillsCells().addAll(grasses.keySet());
    }
}

package ru.simul.createactions;

import ru.simul.models.Predator;
import ru.simul.world.Coordinate;
import ru.simul.world.Universe;

import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

public class PredatorCreateAction extends CoordinateGenerator {

    @Override
    public void create(int total, Universe universe, Properties properties) {
        int totalPredators = (int) (total * Double.parseDouble(properties.getProperty("total.predator")));
        Map<Coordinate, Predator> predators = getRandomCoordinates(universe, totalPredators, Set.of())
                .collect(Collectors.toMap(
                        a -> a,
                        a -> new Predator(6, a),
                        (a, b) -> a));
        universe.setPredators(predators);
        universe.getFillsCells().addAll(predators.keySet());
    }
}

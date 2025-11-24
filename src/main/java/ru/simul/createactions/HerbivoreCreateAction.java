package ru.simul.createactions;

import ru.simul.models.Herbivore;
import ru.simul.world.Coordinate;
import ru.simul.world.Universe;

import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

public class HerbivoreCreateAction extends CoordinateGenerator {

    @Override
    public void create(int total, Universe universe, Properties properties) {
        int totalHerbivore = (int) (total * Double.parseDouble(properties.getProperty("total.herbivore")));
        Map<Coordinate, Herbivore> herbivores = getRandomCoordinates(universe, totalHerbivore, Set.of())
                .collect(Collectors.toMap(
                        a -> a,
                        Herbivore::new,
                        (a, b) -> a));
        universe.setHerbivores(herbivores);
        universe.getFillsCells().addAll(herbivores.keySet());
    }
}

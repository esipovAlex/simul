package ru.simul.service;

import ru.simul.world.Coordinate;
import ru.simul.world.Field;
import ru.simul.world.Universe;
import ru.simul.models.Grass;
import ru.simul.models.Herbivore;
import ru.simul.models.Predator;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

public class InitWorld {

    private Random random = new Random();

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
        int totalRock = (int) (totalCell * Double.parseDouble(prop.getProperty("total.rock")));
        int totalTree = (int) (totalCell * Double.parseDouble(prop.getProperty("total.tree")));
        int totalGrass = (int) (totalCell * Double.parseDouble(prop.getProperty("total.grass")));
        int totalHerbivore = (int) (totalCell * Double.parseDouble(prop.getProperty("total.herbivore")));
        int totalPredator = (int) (totalCell * Double.parseDouble(prop.getProperty("total.predator")));
        Universe universe = new Universe();
        universe.setField(field);

        Set<Coordinate> rocks = getRandomCoordinates(field, Set.of(), totalRock, Set.of());
        Set<Coordinate> allCoordinate = new HashSet<>(rocks);
        universe.setRocks(rocks);

        Set<Coordinate> trees = getRandomCoordinates(field, allCoordinate, totalTree, Set.of());
        universe.setTrees(trees);
        allCoordinate.addAll(trees);

        Set<Coordinate> grasses = getRandomCoordinates(field, allCoordinate, totalGrass, Set.of());
        Map<Coordinate, Grass> mG = grasses.stream().collect(Collectors.toMap(a -> a, Grass::new, (a, b) -> a));
        universe.setGrasses(mG);
        allCoordinate.addAll(grasses);

        Set<Coordinate> herbivores = getRandomCoordinates(field, allCoordinate, totalHerbivore, Set.of());
        Map<Coordinate, Herbivore> mH =herbivores.stream().collect(Collectors.toMap(a -> a, Herbivore::new, (a, b) -> a));
        universe.setHerbivore(mH);
        allCoordinate.addAll(herbivores);

        Set<Coordinate> predators = getRandomCoordinates(field, allCoordinate, totalPredator, herbivores);
        Map<Coordinate, Predator> mP = predators.stream().collect(Collectors.toMap(a -> a, a ->
            new Predator(6, a), (a, b) -> a));
        universe.setPredators(mP);
        return universe;
    }

    private Set<Coordinate> getRandomCoordinates(Field field, Set<Coordinate> existsEntity, int addedEntity, Set<Coordinate> target) {
        Set<Coordinate> result = new HashSet<>();
        while (result.size() <= addedEntity) {
            int x = random.nextInt(field.maxX());
            int y = random.nextInt(field.maxY());
            Coordinate coordinate = new Coordinate(x, y);
            if (!existsEntity.contains(coordinate) && !isNearTarget(x, y, target)) {
                result.add(coordinate);
            }
        }
        return result;
    }

    private boolean isNearTarget(int x, int y, Set<Coordinate> targets) {
        return targets.stream().anyMatch(c -> (Math.abs(x - c.x()) <= 1 && Math.abs(y - c.y()) <= 1));
    }
}

package ru.simul.world;

import lombok.Getter;
import lombok.Setter;
import ru.simul.models.Grass;
import ru.simul.models.Herbivore;
import ru.simul.models.Predator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
public class Universe {

    private Field field;
    private Set<Coordinate> rocks = new HashSet<>();
    private Set<Coordinate> trees = new HashSet<>();
    private Map<Coordinate, Grass> grasses = new HashMap<>();
    private Map<Coordinate, Herbivore> herbivores = new HashMap<>();
    private Map<Coordinate, Predator> predators = new HashMap<>();
    private Names[][] names;
    private int stepId;

    public void refreshHerbivore(Coordinate first, NextStep nextStep) {
        if (nextStep.target().x() != -1) {
            grasses.remove(nextStep.target());
        }
        Herbivore herbivore = herbivores.remove(first);
        herbivores.put(nextStep.step(), herbivore);
    }

    public void refreshPredator(Coordinate first, NextStep nextStep) {
        if (nextStep.target().x() != -1) {
            herbivores.remove(nextStep.target());
        }
        Predator predator = predators.remove(first);
        predators.put(nextStep.step(), predator);
    }
}

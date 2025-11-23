package ru.simul.world;

import ru.simul.models.Grass;
import ru.simul.models.Herbivore;
import ru.simul.models.Predator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Universe {

    private Field field;
    private Set<Coordinate> rocks = new HashSet<>();
    private Set<Coordinate> trees = new HashSet<>();
    private Map<Coordinate, Grass> grasses = new HashMap<>();
    private Map<Coordinate, Herbivore> herbivores = new HashMap<>();
    private Map<Coordinate, Predator> predators = new HashMap<>();
    private Names[][] names;
    private int stepId;

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public Set<Coordinate> getRocks() {
        return rocks;
    }

    public void setRocks(Set<Coordinate> rocks) {
        this.rocks = rocks;
    }

    public Set<Coordinate> getTrees() {
        return trees;
    }

    public void setTrees(Set<Coordinate> trees) {
        this.trees = trees;
    }

    public Map<Coordinate, Grass> getGrasses() {
        return grasses;
    }

    public void setGrasses(Map<Coordinate, Grass> grasses) {
        this.grasses = grasses;
    }

    public Map<Coordinate, Herbivore> getHerbivore() {
        return herbivores;
    }

    public void setHerbivore(Map<Coordinate, Herbivore> herbivore) {
        this.herbivores = herbivore;
    }

    public Map<Coordinate, Predator> getPredators() {
        return predators;
    }

    public void setPredators(Map<Coordinate, Predator> predators) {
        this.predators = predators;
    }

    public Names[][] getNames() {
        return names;
    }

    public void setNames(Names[][] names) {
        this.names = names;
    }

    public int getStepId() {
        return stepId;
    }

    public void setStepId(int stepId) {
        this.stepId = stepId;
    }

    public void refreshHerbivore(Coordinate first, NextStep nextStep) {
        if (nextStep.target().x() != -1) {
            grasses.remove(nextStep.target());
        }
        Herbivore herbivore = herbivores.get(first);
        herbivores.remove(first);
        herbivores.put(nextStep.step(), herbivore);
    }

    public void refreshPredator(Coordinate first, NextStep nextStep) {
        if (nextStep.target().x() != -1) {
            herbivores.remove(nextStep.target());
        }
        Predator predator = predators.get(first);
        predators.remove(first);
        predators.put(nextStep.step(), predator);
    }
}

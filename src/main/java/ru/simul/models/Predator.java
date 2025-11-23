package ru.simul.models;

import ru.simul.service.RenderWorld;
import ru.simul.world.Coordinate;
import ru.simul.world.Names;
import ru.simul.world.NextStep;
import ru.simul.world.Universe;

public class Predator extends Creature {
    private static final Names target = Names.HERBIVORE;
    private final int ATTACK;

    public Predator(int attack, Coordinate coordinate) {
        ATTACK = attack;
        setCoordinate(coordinate);
    }

    @Override
    public Universe eatTargets(Universe universe, RenderWorld renderWorld) {
        NextStep nextStep = move(universe, coordinate, target);
        universe.refreshPredator(coordinate, nextStep);
        renderWorld.prepare(universe);
        setCoordinate(nextStep.step());
        return universe;
    }
}

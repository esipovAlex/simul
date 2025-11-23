package ru.simul.models;

import ru.simul.service.RenderWorld;
import ru.simul.world.Coordinate;
import ru.simul.world.Names;
import ru.simul.world.NextStep;
import ru.simul.world.Universe;

public class Herbivore extends Creature {

    private static final Names target = Names.GRASS;

    public Herbivore(Coordinate coordinate) {
        setCoordinate(coordinate);
    }

    @Override
    public Universe eatTargets(Universe universe, RenderWorld renderWorld) {
        NextStep nextStep = move(universe, coordinate, target);
        universe.refreshHerbivore(coordinate, nextStep);
        renderWorld.prepare(universe);
        setCoordinate(nextStep.step());
        return universe;
    }
}

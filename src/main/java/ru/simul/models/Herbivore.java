package ru.simul.models;

import ru.simul.world.Coordinate;
import ru.simul.world.Names;
import ru.simul.world.NextStep;
import ru.simul.world.Universe;
import ru.simul.service.RenderWorld;

import java.util.Map;

public class Herbivore extends Creature {

    private final Names name = Names.HERBIVORE;
    private final Names target = Names.GRASS;

    public Herbivore(Coordinate coordinate) {
        setCoordinate(coordinate);
    }

    public Universe eatGrasses(Universe universe, RenderWorld renderWorld) {
        return eatThis(universe,  coordinate, renderWorld);
    }

    private Universe eatThis(Universe universe, Coordinate first, RenderWorld renderWorld) {
        Map<Coordinate, Grass> targets = universe.getGrasses();
        NextStep nextStep = move(universe, first, targets.keySet(), target);
        Coordinate next = nextStep.step();
        Coordinate remove = nextStep.target();
        if (remove.x() != -1) {
            targets.remove(remove);
        }
        Map<Coordinate, Herbivore> hunters = universe.getHerbivore();
        Herbivore herbivore = hunters.get(first);
        hunters.remove(first);
        hunters.put(next, herbivore);
        universe.setHerbivore(hunters);
        universe.setGrasses(targets);
        renderWorld.prepare(universe);
        setCoordinate(next);
        return universe;
    }
}

package ru.simul.models;

import ru.simul.world.Coordinate;
import ru.simul.world.Names;
import ru.simul.world.NextStep;
import ru.simul.world.Universe;
import ru.simul.service.RenderWorld;

import java.util.Map;

public class Predator extends Creature {
    private static final Names name = Names.PREDATOR;
    private static final Names target = Names.HERBIVORE;
    private final int ATTACK;

    public Predator(int attack, Coordinate coordinate) {
        ATTACK = attack;
        setCoordinate(coordinate);
    }

    public Names getName() {
        return name;
    }

    public Universe eatRabbits(Universe universe, RenderWorld renderWorld) {
        return eatThis(universe, coordinate, renderWorld);
    }

    private Universe eatThis(Universe universe, Coordinate first, RenderWorld renderWorld) {
        Map<Coordinate, Herbivore> targets = universe.getHerbivore();
        NextStep nextStep = move(universe, first, targets.keySet(), target);
        Coordinate next = nextStep.step();
        Coordinate remove = nextStep.target();
        if (remove.x() != -1) {
            targets.remove(remove);
        }
        Map<Coordinate, Predator> hunters = universe.getPredators();
        Predator hunter = hunters.get(first);
        hunters.remove(first);
        hunters.put(next, hunter);
        universe.setPredators(hunters);
        universe.setHerbivore(targets);
        renderWorld.prepare(universe);
        setCoordinate(next);
        return universe;
    }
}

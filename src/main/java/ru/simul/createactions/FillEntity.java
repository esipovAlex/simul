package ru.simul.createactions;

import ru.simul.world.Universe;

import java.util.List;
import java.util.Properties;

public class FillEntity {

    private final int total;
    private final Universe universe;
    private final List<CreateAction> actions;
    private final Properties properties;

    public FillEntity(Universe universe, int total, Properties properties) {
        this.total = total;
        this.properties = properties;
        this.universe = universe;
        this.actions = List.of(
                new TreeCreateAction(),
                new RockCreateAction(),
                new GrassCreateAction(),
                new HerbivoreCreateAction(),
                new PredatorCreateAction()
        );
    }

    public void create() {
        actions.forEach(el -> el.create(total, universe, properties));
    }
}

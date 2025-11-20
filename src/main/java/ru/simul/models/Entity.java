package ru.simul.models;

import ru.simul.world.Coordinate;

public abstract class Entity {

    protected Coordinate coordinate;

    protected void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }
}

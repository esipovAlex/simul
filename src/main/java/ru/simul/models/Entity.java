package ru.simul.models;

import lombok.Getter;
import lombok.Setter;
import ru.simul.world.Coordinate;

@Getter
@Setter
public abstract class Entity {

    private Coordinate coordinate;
}

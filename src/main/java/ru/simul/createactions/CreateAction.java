package ru.simul.createactions;

import ru.simul.world.Universe;

import java.util.Properties;

public interface CreateAction {

    void create(int total, Universe universe, Properties properties);
}

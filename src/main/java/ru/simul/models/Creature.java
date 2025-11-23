package ru.simul.models;

import lombok.Getter;
import lombok.Setter;
import ru.simul.service.RenderWorld;
import ru.simul.world.*;
import ru.simul.world.*;

import java.util.*;

@Getter
@Setter
public abstract class Creature extends Entity{

    private static final int[][] DIRECTIONS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    protected int hp;

    /*protected int getHp() {
        return hp;
    }

    protected void setHp(int hp) {
        this.hp = hp;
    }*/

    protected NextStep move(Universe universe, Coordinate first, Names targetName) {
        Coordinate next;
        Coordinate remove = new Coordinate(-1, -1);

        Set<Coordinate> targets = coordsTargetByName(universe, targetName);

        if (isAdjacentToAnyTarget(first, targets)) {
            next = findAdjacentTarget(first, targets);
            remove = next;
        } else {
            next = step(universe, first, targetName);
        }
        return new NextStep(next, remove);
    }

    protected boolean isAdjacentToAnyTarget(Coordinate point, Set<Coordinate> targets) {
        return targets.stream()
                .anyMatch(coordinate ->
                        (Math.abs(point.x() - coordinate.x()) + Math.abs(point.y() - coordinate.y())) == 1);
    }

    protected Coordinate findAdjacentTarget(Coordinate point, Set<Coordinate> targets) {
        return targets.stream()
                .filter(target ->
                        Math.abs(point.x() - target.x()) + Math.abs(point.y() - target.y()) == 1)
                .findFirst().orElse(null);
    }

    protected Coordinate step(Universe universe, Coordinate start, Names targetName) {
        Field field = universe.getField();
        Set<Coordinate> targets = coordsTargetByName(universe, targetName);
        Names[][] arrNames = universe.getNames();
        int xMax = field.maxX();
        int yMax = field.maxY();
        boolean[][] visited = new boolean[xMax][yMax];
        Coordinate[][] prev = new Coordinate[xMax][yMax];
        Queue<Coordinate> queue = new LinkedList<>();

        queue.add(start);
        visited[start.x()][start.y()] = true;
        while (!queue.isEmpty()) {
            Coordinate current = queue.poll();
            for (int[] dir : DIRECTIONS) {
                int newX = current.x() + dir[0];
                int newY = current.y() + dir[1];
                if (isValidMove(field, arrNames, newX, newY, visited)) {
                    Coordinate next = new Coordinate(newX, newY);
                    visited[newX][newY] = true;
                    prev[newX][newY] = current;
                    queue.add(next);
                    // Если достигли клетки, соседствующей с одной из целей
                    if (isAdjacentToAnyTarget(next, targets)) {
                        return newStep(prev, start, next);
                    }
                }
            }
        }
        return start;
    }

    private Coordinate newStep(Coordinate[][] prev, Coordinate start, Coordinate end) {
        List<Coordinate> path = new ArrayList<>();
        Coordinate current = end;
        while (current != null) {
            path.add(current);
            current = prev[current.x()][current.y()];
        }
        Collections.reverse(path);
        return path.size() > 1 ? path.get(1) : start;
    }

    private boolean isValidMove(Field field, Names[][] arrNames, int x, int y, boolean[][] visited) {
        return (x >= 0 && x < field.maxX()) &&
                (y >= 0 && y < field.maxY()) &&
                arrNames[x][y].equals(Names.CELL) &&
                !visited[x][y];
    }

    private Set<Coordinate> coordsTargetByName(Universe universe, Names targetName) {
        Set<Coordinate> targets;
        switch (targetName) {
            case GRASS -> targets = universe.getGrasses().keySet();
            case HERBIVORE -> targets = universe.getHerbivores().keySet();
            default -> targets = Set.of();
        }
        return targets;
    }

    public abstract Universe eatTargets(Universe universe, RenderWorld renderWorld);
}

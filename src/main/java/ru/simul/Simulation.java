package ru.simul;

import ru.simul.models.Herbivore;
import ru.simul.models.Predator;
import ru.simul.service.InitWorld;
import ru.simul.service.ReadProperties;
import ru.simul.service.RenderWorld;
import ru.simul.world.Names;
import ru.simul.world.Universe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.lang.System.out;
import static ru.simul.consts.Messages.*;

public class Simulation {

    private static final AtomicBoolean IS_PAUSED = new AtomicBoolean(false);

    private final List<Names[][]> loop = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        Simulation simulation = new Simulation();
        InitWorld initWorld = new InitWorld();
        Universe universe = initWorld.createUniverse(new ReadProperties());
        simulation.fillLoop(universe.getField().maxX(), universe.getField().maxY());
        out.println(START.getText());
        Thread keyListener =  new Thread(new KeyListener());
        keyListener.setDaemon(true);
        keyListener.start();
        simulation.renderWithUniverse(universe, new RenderWorld());
        out.println(END.getText());
    }

    private void renderWithUniverse(Universe universe, RenderWorld renderWorld) throws InterruptedException {
        int stepId = 1;
        universe.setStepId(stepId++);
        Names[][] names = renderWorld.prepare(universe);
        printStatusLine(universe);
        renderWorld.render(names);
        while (!universe.getHerbivores().isEmpty()) {
            List<Herbivore> herbivores = new ArrayList<>(universe.getHerbivores().values());
            herbivores.stream()
                    .forEach(a ->
                            a.eatTargets(universe, renderWorld)
                    );
            List<Predator> predators = new ArrayList<>(universe.getPredators().values());
            predators.stream()
                    .forEach(a ->
                            a.eatTargets(universe, renderWorld)
                    );
            universe.setStepId(stepId++);
            names = renderWorld.prepare(universe);
            if (isLoopRender(names)) {
                break;
            }
            printStatusLine(universe);
            renderWorld.render(names);
            TimeUnit.MILLISECONDS.sleep(1000L);
            if (IS_PAUSED.get()) {
                out.println();
                out.println(ON_PAUSE.getText());
                out.println(PRESS_SPASE_ENTER.getText());
                while (IS_PAUSED.get()) {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
                out.println(CONTINUE.getText());
            }
        }
    }

    private void printStatusLine(Universe universe) {
        out.println(STEP_SEPARATOR.getText());
        String line = STATUS_LINE.getText().formatted(
                universe.getGrasses().size(),
                universe.getHerbivores().size(),
                universe.getStepId());
        out.println(line);
    }

    private boolean isLoopRender(Names[][] names) {
        loop.set(2, loop.get(1));
        loop.set(1, loop.get(0));
        loop.set(0, names);
        return Arrays.deepEquals(loop.get(0), loop.get(1))
                || Arrays.deepEquals(loop.get(0), loop.get(2));
    }

    private void fillLoop(int x, int y) {
        Names[][] arr = new Names[x][y];
        loop.add(arr);
        loop.add(arr);
        loop.add(arr);
    }

    static class KeyListener implements Runnable {

        @Override
        public void run() {
            try (Scanner scanner = new Scanner(System.in)) {
                while (true) {
                    String input = scanner.nextLine();
                    if (" ".equals(input)) {
                        IS_PAUSED.set(!IS_PAUSED.get());
                        if (IS_PAUSED.get()) {
                            out.println(COMMAND_PAUSE.getText());
                        } else {
                            out.println(COMMAND_CONTINUE.getText());
                        }
                    }
                }
            }
        }
    }
}

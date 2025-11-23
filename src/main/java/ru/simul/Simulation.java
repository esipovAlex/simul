package ru.simul;

import ru.simul.world.Names;
import ru.simul.world.Universe;
import ru.simul.models.Herbivore;
import ru.simul.models.Predator;
import ru.simul.service.InitWorld;
import ru.simul.service.RenderWorld;
import java.util.*;
import java.util.concurrent.TimeUnit;
import static java.lang.System.out;

public class Simulation {

    private static volatile boolean isPaused = false;

    private final List<Names[][]> loop = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        Simulation simulation = new Simulation();
        InitWorld initWorld = new InitWorld();
        RenderWorld renderWorld = new RenderWorld();
        Universe universe = initWorld.createUniverse();
        simulation.fillLoop(universe.getField().maxX(), universe.getField().maxY());
        out.println("===START SIMULATION===");
        Thread keyListener =  new Thread(new KeyListener());
        keyListener.setDaemon(true);
        keyListener.start();
        simulation.renderWithUniverse(universe, renderWorld);
        out.println("===END SIMULATION ===");
    }

    private void renderWithUniverse(Universe universe, RenderWorld renderWorld) throws InterruptedException {
        int stepId = 1;
        universe.setStepId(stepId++);
        Names[][] names = renderWorld.prepare(universe);
        printStatusLine(universe);
        renderWorld.render(names);
        while (!universe.getHerbivore().isEmpty()) {
            List<Herbivore> herbivores = new ArrayList<>(universe.getHerbivore().values());
            herbivores.stream()
                    .forEach(a ->
                            a.eatTargets(universe,renderWorld)
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
            if (isPaused) {
                out.println("\n>>> ПРИЛОЖЕНИЕ НА ПАУЗЕ <<<");
                out.println("Нажмите 'Пробел' и Enter для продолжения...");
                while (isPaused) {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
                out.println(">>> ПРОДОЛЖЕНИЕ РАБОТЫ <<<\n");
            }
        }
    }

    private void printStatusLine(Universe universe) {
        out.println("_________________________");
        String line = "трава: %d; кролики: %d; шаг = %d".formatted(
                universe.getGrasses().size(),
                universe.getHerbivore().size(),
                universe.getStepId());
        out.println(line);
    }

    private boolean isLoopRender(Names[][] names) {
        loop.set(2, loop.get(1));
        loop.set(1, loop.get(0));
        loop.set(0, names);
        return Arrays.deepEquals(loop.get(0), loop.get(1)) ||
                Arrays.deepEquals(loop.get(0), loop.get(2));
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
                        isPaused = !isPaused;
                        if (isPaused) {
                            out.println(">> Команда: ПАУЗА");
                        } else {
                            out.println(">> Команда: ПРОДОЛЖИТЬ");
                        }
                    }
                }
            }
        }
    }
}

package ru.simul;

import ru.simul.world.Names;
import ru.simul.world.Universe;
import ru.simul.models.Herbivore;
import ru.simul.models.Predator;
import ru.simul.service.InitWorld;
import ru.simul.service.RenderWorld;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class Simulation {

    private static volatile boolean isPaused = false;

    private final List<Names[][]> loop = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        Thread keyListener = new Thread(new KeyListener());
        keyListener.setDaemon(true);
        keyListener.start();
        System.out.println("===START SIMULATION===");
        Simulation simulation = new Simulation();
        InitWorld initWorld = new InitWorld();
        RenderWorld renderWorld = new RenderWorld();
        Universe universe = initWorld.createUniverse();
        simulation.fillLoop(universe.getField().maxX(), universe.getField().maxY());
        simulation.renderWithUniverse(universe, renderWorld);
        System.out.println("===END SIMULATION ===");
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
                            a.eatGrasses(universe,renderWorld)
                    );
            List<Predator> predators = new ArrayList<>(universe.getPredators().values());
            predators.stream()
                    .forEach(a ->
                            a.eatRabbits(universe, renderWorld)
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
                System.out.println("\n>>> ПРИЛОЖЕНИЕ НА ПАУЗЕ <<<");
                System.out.println("Нажмите 'Пробел' и Enter для продолжения...");
                while (isPaused) {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
                System.out.println(">>> ПРОДОЛЖЕНИЕ РАБОТЫ <<<\n");
            }
        }
    }

    private void printStatusLine(Universe universe) {
        System.out.println("_________________________");
        System.out.println("трава: " + universe.getGrasses().size()
                + "; кролики: " + universe.getHerbivore().size()
                + "; шаг = " + universe.getStepId());
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
                            System.out.println(">> Команда: ПАУЗА");
                        } else {
                            System.out.println(">> Команда: ПРОДОЛЖИТЬ");
                        }
                    }
                }
            }
        }
    }
}

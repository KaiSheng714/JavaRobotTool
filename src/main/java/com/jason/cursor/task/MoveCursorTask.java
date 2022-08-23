package com.jason.cursor.task;

import java.awt.*;
import java.awt.event.InputEvent;

public class MoveCursorTask implements Runnable {

    private final Robot robot;
    private boolean running;

    public MoveCursorTask() throws Exception {
        robot = new Robot();
    }

    public void terminate() {
        running = false;
    }

    @SuppressWarnings("all")
    @Override
    public void run() {
        running = true;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int centerX = screenSize.width / 2;
        int centerY = screenSize.height / 2;
        robot.mouseMove(centerX, centerY);

        int NUM_POINTS = 10;
        while (running) {
            try {
                PointerInfo point = MouseInfo.getPointerInfo();
                for (int radius = 100; radius < 300 && running; radius += 50) {
                    for (int i = 0; i < NUM_POINTS && running; i++) {
                        final double angle = Math.toRadians(((double) i / NUM_POINTS) * 360d);
                        int x = centerX + (int) (Math.cos(angle) * radius);
                        int y = centerY + (int) (Math.sin(angle) * radius);
                        robot.mouseMove(x, y);
                        robot.delay(500);
                        robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
                        robot.delay(10);
                        robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
                    }
                }
                Thread.sleep(5 *  60 * 1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
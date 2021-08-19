package com.jason.cursor.task;

import com.jason.cursor.view.MainForm;

import java.awt.*;
import java.time.LocalTime;

public class MoveCursorTask implements Runnable {

    private final MainForm form;
    private final Robot robot;

    public MoveCursorTask(MainForm form, Robot robot) {
        this.form = form;
        this.robot = robot;
    }

    private boolean isWorkTime() {
        LocalTime now = LocalTime.now();
        return now.isAfter(LocalTime.parse("09:00:00")) && now.isBefore(LocalTime.parse("11:58:00"))
                ||
                now.isAfter(LocalTime.parse("13:05:00")) && now.isBefore(LocalTime.parse("15:00:00"))
                ||
                now.isAfter(LocalTime.parse("15:30:00")) && now.isBefore(LocalTime.parse("18:00:00"));
    }

    @SuppressWarnings("all")
    @Override
    public void run() {
        while (true) {
            try {
                PointerInfo point = MouseInfo.getPointerInfo();
                if (point != null) {
                    robot.mouseMove((int) point.getLocation().getX() + 1, (int) point.getLocation().getY());
                } else {
                    // in lock screen, wait until unlock
                    continue;
                }

                if (isWorkTime()) {
                    Thread.sleep(200 * 1000);
                } else {
                    form.pause();
                    Thread.sleep(590 * 1000);
                    form.resume();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
package com.jason.cursor;

import com.jason.cursor.task.MoveCursorTask;
import lc.kra.system.keyboard.GlobalKeyboardHook;
import lc.kra.system.keyboard.event.GlobalKeyAdapter;
import lc.kra.system.keyboard.event.GlobalKeyEvent;

public class Main {
    private static boolean running = true;
    private static MoveCursorTask moveCursorTask;

    public static void main(String[] args) {
        GlobalKeyboardHook keyboardHook = new GlobalKeyboardHook(true); // use false here to switch to hook instead of raw input
        keyboardHook.addKeyListener(new GlobalKeyAdapter() {
            @Override
            public void keyPressed(GlobalKeyEvent event) {
                if (event.getVirtualKeyCode() == GlobalKeyEvent.VK_ESCAPE ||
                        event.getVirtualKeyCode() == GlobalKeyEvent.VK_SPACE ||
                        event.getVirtualKeyCode() == GlobalKeyEvent.VK_SHIFT) {
                    running = false;
                }
            }
            @Override
            public void keyReleased(GlobalKeyEvent event) { }
        });

        try {
            moveCursorTask = new MoveCursorTask();
            new Thread(moveCursorTask).start();
            while (running) {
                Thread.sleep(128);
            }
        } catch (Exception e) {

        } finally {
            moveCursorTask.terminate();
            keyboardHook.shutdownHook();
            System.out.println("[END]");
        }
    }
}

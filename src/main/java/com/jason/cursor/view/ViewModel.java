package com.jason.cursor.view;

import java.awt.*;

import static com.jason.cursor.view.MainForm.*;

public class ViewModel {

    private final MainForm mainForm;

    public ViewModel(MainForm mainForm) {
        this.mainForm = mainForm;
    }

    public void refreshView(String state) {
        switch (state) {
            case STATE_RUNNING:
                mainForm.statusLabel.setText("Running");
                mainForm.statusLabel.setForeground(new Color(0x00E200));
                mainForm.startButton.setEnabled(false);
                mainForm.stopButton.setEnabled(true);
                break;
            case STATE_WAITING:
                mainForm.statusLabel.setText("Not in working time, waiting...");
                mainForm.statusLabel.setForeground(new Color(0xB1B100));
                break;
            case STATE_STOP:
                mainForm.statusLabel.setText("Stop");
                mainForm.statusLabel.setForeground(new Color(0xD70404));
                mainForm.startButton.setEnabled(true);
                mainForm.stopButton.setEnabled(false);
                break;
            default:
                mainForm.statusLabel.setText("ERROR!");
                mainForm.statusLabel.setForeground(new Color(0xD70404));
                mainForm.startButton.setEnabled(true);
                mainForm.stopButton.setEnabled(false);
                break;
        }
    }
}

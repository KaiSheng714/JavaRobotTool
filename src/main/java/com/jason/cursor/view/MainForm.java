package com.jason.cursor.view;

import com.jason.cursor.task.MoveCursorTask;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Locale;

public class MainForm {
    private final JFrame frame;
    private JPanel panel;
    private Robot robot;
    private final ViewModel viewModel;
    private Thread taskThread;
    static final String STATE_RUNNING = "running";
    static final String STATE_WAITING = "waiting";
    static final String STATE_STOP = "stop";
    JButton startButton;
    JButton stopButton;
    JLabel statusLabel;

    public MainForm() {
        frame = new JFrame("Cursor");
        frame.setContentPane(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.addWindowStateListener(e -> {
            if (e.getNewState() == Frame.ICONIFIED) {
                frame.setVisible(false);
            } else {
                frame.setVisible(true);
            }
        });

        viewModel = new ViewModel(this);

        startButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                start();
            }
        });

        stopButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                stop();
            }
        });

        try {
            Image image = new ImageIcon(this.getClass().getResource("/icon.jpg")).getImage();
            PopupMenu popup = new PopupMenu();
            TrayIcon trayIcon = new TrayIcon(image, "Cursor", popup);
            trayIcon.addActionListener(listener -> {
                frame.setVisible(true);
                frame.setState(Frame.NORMAL);
            });

            MenuItem exitMenuItem = new MenuItem("Exit");
            exitMenuItem.addActionListener(event -> System.exit(0));
            popup.add(exitMenuItem);
            SystemTray.getSystemTray().add(trayIcon);

            robot = new Robot();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }

        start();
    }

    private void start() {
        viewModel.refreshView(STATE_RUNNING);
        taskThread = new Thread(new MoveCursorTask(this, robot));
        taskThread.start();
        frame.setExtendedState(Frame.ICONIFIED);
    }

    private void stop() {
        viewModel.refreshView(STATE_STOP);
        taskThread.interrupt();
    }

    public void pause() {
        viewModel.refreshView(STATE_WAITING);
    }

    public void resume() {
        viewModel.refreshView(STATE_RUNNING);
    }

    public void showError() {
        viewModel.refreshView("error");
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setFocusable(true);
        panel.setMaximumSize(new Dimension(1000, 1000));
        panel.setMinimumSize(new Dimension(540, 100));
        panel.setPreferredSize(new Dimension(540, 200));
        panel.setRequestFocusEnabled(true);
        panel.setVerifyInputWhenFocusTarget(false);
        startButton = new JButton();
        startButton.setAlignmentX(1.0f);
        startButton.setAlignmentY(1.0f);
        startButton.setAutoscrolls(false);
        startButton.setHorizontalAlignment(0);
        startButton.setHorizontalTextPosition(0);
        startButton.setText("Start");
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipadx = 5;
        gbc.ipady = 5;
        panel.add(startButton, gbc);
        final JLabel label1 = new JLabel();
        label1.setHorizontalAlignment(0);
        label1.setHorizontalTextPosition(0);
        label1.setText("Status: ");
        label1.setVerticalAlignment(0);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 0.5;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(label1, gbc);
        statusLabel = new JLabel();
        statusLabel.setAutoscrolls(false);
        statusLabel.setDoubleBuffered(false);
        Font statusLabelFont = this.$$$getFont$$$("Lucida Sans Typewriter", Font.BOLD, 16, statusLabel.getFont());
        if (statusLabelFont != null) statusLabel.setFont(statusLabelFont);
        statusLabel.setHorizontalAlignment(2);
        statusLabel.setHorizontalTextPosition(2);
        statusLabel.setMaximumSize(new Dimension(300, 200));
        statusLabel.setMinimumSize(new Dimension(200, 200));
        statusLabel.setPreferredSize(new Dimension(200, 40));
        statusLabel.setRequestFocusEnabled(true);
        statusLabel.setText("Stop");
        statusLabel.setVisible(true);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(statusLabel, gbc);
        stopButton = new JButton();
        stopButton.setEnabled(false);
        stopButton.setText("Stop");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipadx = 5;
        gbc.ipady = 5;
        panel.add(stopButton, gbc);
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel;
    }
}
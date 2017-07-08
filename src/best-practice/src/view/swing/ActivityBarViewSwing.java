/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.swing;

import controller.interfaces.ActivityBarController;

import javax.swing.*;

import view.interfaces.ActivityBarView;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Duration;
import java.util.*;
import java.util.Timer;

/**
 *
 * @author stephan
 */
public class ActivityBarViewSwing implements ActivityBarView
{
    private final JFrame frame;
    private ActivityBarController controller;

    private final JPanel pMain;
    private final JComboBox<String> cbProject;
    private final JComboBox<String> cbPhase;
    private final JButton btStart;
    private final JButton btStop;
    private final JLabel lbDuration;

    private final JTextField tfDescription;
    private final JTextField tfComment;

    private Duration duration;
    private TimerTask task;
    private Timer timer;



    public ActivityBarViewSwing(JFrame frame)
    {

        this.frame = frame;

        duration = Duration.ZERO;


        this.pMain = new JPanel(new GridLayout(1,5, 5, 5));
        this.cbProject = new JComboBox<>();
        this.cbPhase = new JComboBox<>();
        this.btStart = new JButton("Start Activity");
        this.btStop = new JButton("Stop Activity");
        this.lbDuration = new JLabel();
        btStart.setEnabled(false);
        btStop.setEnabled(false);

        tfDescription = new JTextField();
        tfComment = new JTextField();


        pMain.add(cbProject);
        pMain.add(cbPhase);
        pMain.add(btStart);
        pMain.add(btStop);
        pMain.add(lbDuration);


        resetTimer();

        setListeners();

    }

    @Override
    public void setActivityBarController(ActivityBarController controller)
    {
        this.controller = controller;
    }

    @Override
    public void show() {
        frame.add(pMain, BorderLayout.NORTH);
        update();
    }

    @Override
    public void enableStart() {
        btStart.setEnabled(true);
    }

    @Override
    public void disableStart() {
        btStart.setEnabled(false);
    }

    @Override
    public void enableStop() {
        btStop.setEnabled(true);
    }

    @Override
    public void disableStop() {
        btStop.setEnabled(false);
    }

    @Override
    public void setProjectPhases(ArrayList<String> phases)
    {
        cbPhase.removeAllItems();
        for(String phase : phases)
            cbPhase.addItem(phase);
    }

    @Override
    public void setProjects(ArrayList<String> projects) {
        cbProject.removeAllItems();
        for(String project : projects)
            cbProject.addItem(project);
    }

    @Override
    public void startTimer() {
        timer.scheduleAtFixedRate(task, 0, 1000);
    }

    @Override
    public void stopTimer() {

        resetTimer();

        duration = Duration.ZERO;
        long seconds = duration.getSeconds();
        lbDuration.setText(String.format("%d:%02d:%02d", seconds / 3600, (seconds % 3600) / 60, (seconds % 60)));
        lbDuration.revalidate();
        lbDuration.repaint();
    }


    @Override
    public void showError(String message) {
        JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void disableComboBoxes() {
        cbProject.setEnabled(false);
        cbPhase.setEnabled(false);
    }

    @Override
    public void showCommentDescriptionDialog() {
        JPanel dialogPanel = getDialogPanel();

        int selection = JOptionPane.showConfirmDialog(
                null, dialogPanel, "Input Form : "
                , JOptionPane.OK_CANCEL_OPTION
                , JOptionPane.PLAIN_MESSAGE);

        if (selection == JOptionPane.OK_OPTION)
        {
            String project = (String) cbProject.getSelectedItem();
            String phase = (String) cbPhase.getSelectedItem();
            controller.ActivityFinished(project, phase, tfDescription.getText(), tfComment.getText());
        }
        else if (JOptionPane.showConfirmDialog(null, "Are you sure to discard the activity?") == JOptionPane.CANCEL_OPTION)
        {
            showCommentDescriptionDialog();
        }
        else
            controller.discardActivity();
        tfComment.setText("");
        tfDescription.setText("");
    }

    @Override
    public void enableComboBoxes() {
        cbPhase.setEnabled(true);
        cbProject.setEnabled(true);
    }

    private void removeAll()
    {
        frame.remove(pMain);
    }

    private void update()
    {
        frame.revalidate();
        frame.repaint();
    }

    private void setListeners()
    {
        btStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                controller.StartClicked();
            }
        });

        btStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                controller.StopClicked();
            }
        });


        cbPhase.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        String selectedPhase = (String) cbPhase.getSelectedItem();
                        controller.PhaseSelected(selectedPhase);
                    }
                }
        );

        cbProject.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        String selectedProject = (String) cbProject.getSelectedItem();
                        controller.ProjectSelected(selectedProject);
                    }
                }
        );
    }

    private JPanel getDialogPanel() {
        JPanel basePanel = new JPanel();
        //basePanel.setLayout(new BorderLayout(5, 5));
        basePanel.setOpaque(true);
        basePanel.setBackground(Color.BLUE.darker());

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(2, 2, 5, 5));
        centerPanel.setBorder(
                BorderFactory.createEmptyBorder(5, 5, 5, 5));
        centerPanel.setOpaque(true);
        centerPanel.setBackground(Color.WHITE);

        JLabel lbDescription = new JLabel("Enter Activity Description : ");
        JLabel lbComment = new JLabel("Enter Activity Comment     : ");

        centerPanel.add(lbDescription);
        centerPanel.add(tfDescription);
        centerPanel.add(lbComment);
        centerPanel.add(tfComment);

        basePanel.add(centerPanel);

        return basePanel;
    }

    private void resetTimer()
    {
        timer.cancel();
        timer.purge();
        task.cancel();
        timer = new Timer();

        task = new TimerTask() {
            @Override
            public void run() {
                duration = duration.plusSeconds(1);
                long seconds = duration.getSeconds();
                lbDuration.setText(String.format("%d:%02d:%02d", seconds / 3600, (seconds % 3600) / 60, (seconds % 60)));
                lbDuration.revalidate();
                lbDuration.repaint();

            }};
    }
}

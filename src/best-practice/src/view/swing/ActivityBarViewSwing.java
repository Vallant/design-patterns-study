/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.swing;

import controller.interfaces.ActivityBarController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

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

    private final ActivityBarPanel pMain;


    private Duration duration;
    private TimerTask task;
    private Timer timer;



    public ActivityBarViewSwing(JFrame frame)
    {

        this.frame = frame;

        duration = Duration.ZERO;


        pMain = new ActivityBarPanel();
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
        frame.getContentPane().add(pMain, BorderLayout.NORTH);
        update();
    }

    @Override
    public void enableStart() {
        pMain.btStart.setEnabled(true);
    }

    @Override
    public void disableStart() {
        pMain.btStart.setEnabled(false);
    }

    @Override
    public void enableStop() {
        pMain.btStop.setEnabled(true);
    }

    @Override
    public void disableStop() {
        pMain.btStop.setEnabled(false);
    }

    @Override
    public void setProjectPhases(ArrayList<String> phases)
    {
        pMain.cbPhase.removeAllItems();
        for(String phase : phases)
            pMain.cbPhase.addItem(phase);
    }

    @Override
    public void setProjects(ArrayList<String> projects) {
        pMain.cbProject.removeAllItems();
        for(String project : projects)
            pMain.cbProject.addItem(project);
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
        pMain.lbDuration.setText(String.format("%d:%02d:%02d", seconds / 3600, (seconds % 3600) / 60, (seconds % 60)));
        pMain.lbDuration.revalidate();
        pMain.lbDuration.repaint();
    }


    @Override
    public void showError(String message) {
        JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void disableComboBoxes() {
        pMain.cbProject.setEnabled(false);
        pMain.cbPhase.setEnabled(false);
    }

    @Override
    public void showCommentDescriptionDialog() {
        ActivityBarDialogPanel dialogPanel = new ActivityBarDialogPanel();

        int selection = JOptionPane.showConfirmDialog(
                null, dialogPanel, "Input Form : "
                , JOptionPane.OK_CANCEL_OPTION
                , JOptionPane.PLAIN_MESSAGE);

        if (selection == JOptionPane.OK_OPTION)
        {
            String project = (String) pMain.cbProject.getSelectedItem();
            String phase = (String) pMain.cbPhase.getSelectedItem();
            controller.ActivityFinished(project, phase, dialogPanel.tfDescription.getText(), dialogPanel.tfComment.getText());
        }
        else if (JOptionPane.showConfirmDialog(null, "Are you sure to discard the activity?") == JOptionPane.CANCEL_OPTION)
        {
            showCommentDescriptionDialog();
        }
        else
            controller.discardActivity();
        dialogPanel.tfComment.setText("");
        dialogPanel.tfDescription.setText("");
    }

    @Override
    public void enableComboBoxes() {
        pMain.cbPhase.setEnabled(true);
        pMain.cbProject.setEnabled(true);
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
        pMain.btStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                controller.StartClicked();
            }
        });

        pMain.btStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                controller.StopClicked();
            }
        });


        pMain.cbPhase.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        String selectedPhase = (String) pMain.cbPhase.getSelectedItem();
                        controller.PhaseSelected(selectedPhase);
                    }
                }
        );

        pMain.cbProject.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        String selectedProject = (String) pMain.cbProject.getSelectedItem();
                        controller.ProjectSelected(selectedProject);
                    }
                }
        );
    }


    private void resetTimer()
    {
        if(timer != null)
        {
            timer.cancel();
            timer.purge();
            task.cancel();
        }

        timer = new Timer();

        task = new TimerTask() {
            @Override
            public void run() {
                duration = duration.plusSeconds(1);
                long seconds = duration.getSeconds();
                pMain.lbDuration.setText(String.format("%d:%02d:%02d", seconds / 3600, (seconds % 3600) / 60, (seconds % 60)));
                pMain.lbDuration.revalidate();
                pMain.lbDuration.repaint();

            }};
    }
}

package view.swing;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Created by stephan on 08.07.17.
 */
public class ActivityBarPanel extends JPanel
{

    final JPanel pMain;
    final JComboBox<String> cbProject;
    final JComboBox<String> cbPhase;
    final JButton btStart;
    final JButton btStop;
    final JLabel lbDuration;

    public ActivityBarPanel() {
        super(new FlowLayout(5));
        pMain = new JPanel(new GridLayout(1,5, 5, 5));
        setBorder(new EmptyBorder(5,5,5,5));

        this.cbProject = new JComboBox<>();
        this.cbPhase = new JComboBox<>();
        this.btStart = new JButton("Start Activity");
        this.btStop = new JButton("Stop Activity");
        this.lbDuration = new JLabel();
        btStart.setEnabled(false);
        btStop.setEnabled(false);



        pMain.add(cbProject);
        pMain.add(cbPhase);
        pMain.add(btStart);
        pMain.add(btStop);
        pMain.add(lbDuration);
        add(pMain);

    }
}

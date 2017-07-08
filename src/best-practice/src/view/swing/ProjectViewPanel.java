package view.swing;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * Created by stephan on 08.07.17.
 */
public class ProjectViewPanel extends JPanel {

    final JPanel pOwned;
    final JPanel pOwnedHeader;
    final JLabel lbOwnedTitle;
    final JPanel pOwnedButtons;
    final JButton btAddProject;
    final JButton btDeleteProject;
    final JScrollPane spOwned;
    final DefaultListModel<String> lstOwnedModel;
    final JList<String> lstOwned;

    final JPanel pInvolved;
    final JPanel pInvolvedHeader;
    final JLabel lbInvolvedTitle;
    final JPanel pInvolvedButtons;
    final JButton btLeaveProject;
    final JScrollPane spInvolved;
    final DefaultListModel<String> lstInvolvedModel;
    final JList<String> lstInvolved;

    public ProjectViewPanel() {

        super(new GridLayout(2,1,5,5));
        setBorder(new EmptyBorder(5,5,5,5));
        pOwned = new JPanel(new BorderLayout(5,5));
        pOwnedHeader = new JPanel(new BorderLayout(5,5));
        pOwnedButtons = new JPanel(new FlowLayout(5));
        lbOwnedTitle = new JLabel("Own Projects");
        btAddProject = new JButton("Add Project");
        btDeleteProject = new JButton("Delete Project");
        lstOwnedModel = new DefaultListModel<>();
        lstOwned = new JList<>(lstOwnedModel);
        spOwned = new JScrollPane();
        spOwned.setViewportView(lstOwned);

        lstOwned.setBorder(new LineBorder(Color.black, 1));

        add(pOwned);
        pOwned.add(pOwnedHeader, BorderLayout.NORTH);
        pOwnedHeader.add(pOwnedButtons, BorderLayout.EAST);
        pOwnedHeader.add(lbOwnedTitle, BorderLayout.CENTER);
        pOwnedButtons.add(btAddProject);
        pOwnedButtons.add(btDeleteProject);
        pOwned.add(spOwned, BorderLayout.CENTER);


        pInvolved = new JPanel(new BorderLayout(5,5));

        pInvolvedHeader = new JPanel(new BorderLayout(5,5));
        pInvolvedButtons = new JPanel(new FlowLayout(5));
        lbInvolvedTitle = new JLabel("Participating Projects");
        btLeaveProject = new JButton("Leave Project");
        lstInvolvedModel = new DefaultListModel<>();
        lstInvolved = new JList<>(lstInvolvedModel);
        lstInvolved.setBorder(new LineBorder(Color.black, 1));
        spInvolved = new JScrollPane();
        spInvolved.setViewportView(lstInvolved);

        add(pInvolved);
        pInvolved.add(pInvolvedHeader, BorderLayout.NORTH);
        pInvolvedHeader.add(pInvolvedButtons, BorderLayout.EAST);
        pInvolvedHeader.add(lbInvolvedTitle, BorderLayout.CENTER);
        pInvolvedButtons.add(btLeaveProject);
        pInvolved.add(spInvolved, BorderLayout.CENTER);
    }
}

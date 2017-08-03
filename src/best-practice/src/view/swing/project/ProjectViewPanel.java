package view.swing.project;

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
    final JPanel pFlowPanel1;
    final JScrollPane spOwned;
    final DefaultListModel<String> lstOwnedModel;
    final JList<String> lstOwned;

    final JPanel pInvolved;
    final JPanel pInvolvedHeader;
    final JLabel lbInvolvedTitle;
    final JPanel pInvolvedButtons;
    final JPanel pFlowPanel2;
    final JButton btLeaveProject;
    final JScrollPane spInvolved;
    final DefaultListModel<String> lstInvolvedModel;
    final JList<String> lstInvolved;

    public ProjectViewPanel() {

        super(new GridLayout(2,1,5,5));
        setBorder(new EmptyBorder(5,5,5,5));
        pOwned = new JPanel(new BorderLayout(5,5));
        pOwnedHeader = new JPanel(new BorderLayout(5,5));
        pFlowPanel1 = new JPanel(new FlowLayout(5));
        pOwnedButtons = new JPanel(new GridLayout(2,1,5,5));
        lbOwnedTitle = new JLabel("Own Projects");
        btAddProject = new JButton("Add Project");
        btAddProject.setPreferredSize(new Dimension(175,25));
        btDeleteProject = new JButton("Delete Project");
        btDeleteProject.setPreferredSize(new Dimension(175,25));
        lstOwnedModel = new DefaultListModel<>();
        lstOwned = new JList<>(lstOwnedModel);
        spOwned = new JScrollPane(lstOwned);

        lstOwned.setBorder(new LineBorder(Color.black, 1));

        add(pOwned);
        pOwned.add(pOwnedHeader, BorderLayout.NORTH);
        pOwned.add(pFlowPanel1, BorderLayout.EAST);
        pOwnedHeader.add(lbOwnedTitle, BorderLayout.CENTER);
        pOwnedButtons.add(btAddProject);
        pOwnedButtons.add(btDeleteProject);
        pFlowPanel1.add(pOwnedButtons);
        pOwned.add(spOwned, BorderLayout.CENTER);


        pInvolved = new JPanel(new BorderLayout(5,5));
        pFlowPanel2 = new JPanel(new FlowLayout(5));
        pInvolvedHeader = new JPanel(new BorderLayout(5,5));
        pInvolvedButtons = new JPanel(new GridLayout(1,1,5,5));
        lbInvolvedTitle = new JLabel("Participating Projects");
        btLeaveProject = new JButton("Leave Project");
        btLeaveProject.setPreferredSize(new Dimension(175,25));
        lstInvolvedModel = new DefaultListModel<>();
        lstInvolved = new JList<>(lstInvolvedModel);
        lstInvolved.setBorder(new LineBorder(Color.black, 1));
        spInvolved = new JScrollPane(lstInvolved);

        add(pInvolved);
        pInvolved.add(pInvolvedHeader, BorderLayout.NORTH);
        pInvolved.add(pFlowPanel2, BorderLayout.EAST);
        pInvolvedHeader.add(lbInvolvedTitle, BorderLayout.CENTER);
        pInvolvedButtons.add(btLeaveProject);
        pInvolved.add(spInvolved, BorderLayout.CENTER);
        pFlowPanel2.add(pInvolvedButtons);
    }
}

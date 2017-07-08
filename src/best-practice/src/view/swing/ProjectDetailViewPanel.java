package view.swing;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by stephan on 08.07.17.
 */
public class ProjectDetailViewPanel extends JPanel
{
    final JPanel pHeader;
    final JButton btBack;
    final JLabel lbProjectName;

    final JPanel pCenter;

    final JPanel pPhases;
    final JPanel pPhasesHeader;
    final JLabel lbPhases;

    final JPanel pPhasesButtons;
    final JButton btAddPhase;
    final JButton btDeletePhase;
    final DefaultListModel<String> lstPhasesModel;
    final JList<String> lstPhases;

    final JPanel pMembers;
    final JPanel pMembersHeader;
    final JLabel lbMembers;
    final JPanel pMembersButtons;
    final JButton btAddMember;
    final JButton btDeleteMember;
    final JButton btPromoteToAdmin;
    final JButton btDegradeToMember;

    final DefaultTableModel tblMemberModel;
    final JTable tblMembers;

    final JTextPane pFooter;


    public ProjectDetailViewPanel() {
        super(new BorderLayout(5,5));

        pCenter = new JPanel(new GridLayout(2,1,5,5));

        pHeader = new JPanel(new BorderLayout(5,5));
        btBack = new JButton("Back");
        lbProjectName = new JLabel("");
        pHeader.add(btBack, BorderLayout.WEST);
        pHeader.add(lbProjectName, BorderLayout.CENTER);

        pPhases = new JPanel(new BorderLayout(5,5));
        pPhasesHeader = new JPanel(new BorderLayout(5,5));
        lbPhases = new JLabel("Project Phases");
        btAddPhase = new JButton("Add Phase");
        btDeletePhase = new JButton("Delete Phase");
        lstPhasesModel = new DefaultListModel<>();
        lstPhases = new JList<>(lstPhasesModel);
        pPhasesButtons = new JPanel(new GridLayout(2,1,5,5));

        pPhases.add(pPhasesHeader, BorderLayout.NORTH);
        pPhasesHeader.add(lbPhases, BorderLayout.CENTER);
        pPhases.add(pPhasesButtons, BorderLayout.EAST);
        pPhasesButtons.add(btAddPhase);
        pPhasesButtons.add(btDeletePhase);
        pPhases.add(lstPhases, BorderLayout.CENTER);



        pMembers = new JPanel(new BorderLayout(5,5));
        pMembersHeader = new JPanel(new BorderLayout(5,5));
        lbMembers = new JLabel("Project Members");
        btAddMember = new JButton("Add Member");
        btDeleteMember = new JButton("Delete Member");
        btPromoteToAdmin = new JButton("Promote to admin");
        btDegradeToMember = new JButton("Degrade to member");
        tblMemberModel = new DefaultTableModel();
        tblMembers = new JTable(tblMemberModel);
        pMembersButtons = new JPanel(new GridLayout(4,1,5,5));

        pMembers.add(pMembersHeader, BorderLayout.NORTH);
        pMembersHeader.add(lbMembers, BorderLayout.CENTER);
        pMembers.add(pMembersButtons, BorderLayout.EAST);
        pMembersButtons.add(btAddMember);
        pMembersButtons.add(btDeleteMember);
        pMembersButtons.add(btPromoteToAdmin);
        pMembersButtons.add(btDegradeToMember);
        pMembers.add(tblMembers, BorderLayout.CENTER);

        pFooter = new JTextPane();

        pCenter.add(pPhases);
        pCenter.add(pMembers);
        add(pCenter, BorderLayout.CENTER);
        add(pFooter, BorderLayout.SOUTH);
        add(pHeader, BorderLayout.NORTH);
    }

    public void setProjectName(String projectName)
    {
        lbProjectName.setText(projectName);
    }

    public void setPhases(ArrayList<String> phases)
    {
        for(String s : phases)
            lstPhasesModel.addElement(s);
    }

    public void setMembers(ArrayList<String> members)
    {
        tblMemberModel.addColumn("Name", members.toArray());
    }

    public void setRoles(ArrayList<String> roles) {
        tblMemberModel.addColumn("Role", roles.toArray());
    }

    public void setDescription(String description) {
        pFooter.setText(description);
    }
}

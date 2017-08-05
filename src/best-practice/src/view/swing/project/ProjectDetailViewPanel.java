package view.swing.project;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
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

    final JPanel pFlowPanel1;
    final JPanel pPhases;
    final JPanel pPhasesHeader;
    //final JLabel lbPhases;
    final JPanel pPhasesButtons;
    final JButton btAddPhase;
    final JButton btDeletePhase;
    final DefaultListModel<String> lstPhasesModel;
    final JList<String> lstPhases;

    final JPanel pFlowPanel2;
    final JPanel pMembers;
    final JPanel pMembersHeader;
    //final JLabel lbMembers;
    final JPanel pMembersButtons;
    final JButton btAddMember;
    final JButton btDeleteMember;
    final JButton btPromoteToAdmin;
    final JButton btDegradeToMember;

    final DefaultListModel<String> lstMemberModel;
    final JList lstMembers;

    final JPanel pDescription;
    final JPanel pFlowPanel3;
    final JPanel pDescriptionButtons;
    final JTextArea taDescription;
    final JButton btUpdateDescription;


    public ProjectDetailViewPanel() {
        super(new BorderLayout(5,5));
        //setPreferredSize(new Dimension(700, 700));
        pCenter = new JPanel(new GridLayout(3,1,5,5));

        pHeader = new JPanel(new BorderLayout(5,5));
        btBack = new JButton("Back");
        lbProjectName = new JLabel("Detail for Project: ");
        pHeader.add(btBack, BorderLayout.WEST);
        pHeader.add(lbProjectName, BorderLayout.CENTER);

        pFlowPanel1 = new JPanel(new FlowLayout(5));
        pPhases = new JPanel(new BorderLayout(5,5));
        pPhasesHeader = new JPanel(new BorderLayout(5,5));
      //  lbPhases = new JLabel("Project Phases");
        btAddPhase = new JButton("Add Phase");
        btAddPhase.setPreferredSize(new Dimension(175,25));
        btDeletePhase = new JButton("Delete Phase");
        btDeletePhase.setPreferredSize(new Dimension(175,25));
        lstPhasesModel = new DefaultListModel<>();
        lstPhases = new JList<>(lstPhasesModel);
        lstPhases.setCellRenderer(new DefaultListCellRenderer()
                                   {
                                       @Override
                                       public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                                           JLabel listCellRendererComponent = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected,cellHasFocus);
                                           if (index % 2 == 0) setBackground(Color.decode("#EEF1FD"));
                                           return listCellRendererComponent;
                                       }
                                   }
        );
        //lstPhases.setBorder(new LineBorder(Color.black, 1));
        pPhasesButtons = new JPanel(new GridLayout(2,1,5,5));

        pPhases.add(pPhasesHeader, BorderLayout.NORTH);
        //pPhasesHeader.add(lbPhases, BorderLayout.CENTER);
        pPhases.add(pFlowPanel1, BorderLayout.EAST);
        pPhasesButtons.add(btAddPhase);
        pPhasesButtons.add(btDeletePhase);
        pFlowPanel1.add(pPhasesButtons);
        JScrollPane spPhases = new JScrollPane(lstPhases);
        spPhases.setBorder(new TitledBorder(new LineBorder(Color.black, 1), "Project Phases"));
        pPhases.add(spPhases, BorderLayout.CENTER);


        pFlowPanel2 = new JPanel(new FlowLayout(5));
        pMembers = new JPanel(new BorderLayout(5,5));
        pMembersHeader = new JPanel(new BorderLayout(5,5));
//        lbMembers = new JLabel("Project Members");
        btAddMember = new JButton("Add Members");
        btAddMember.setPreferredSize(new Dimension(175,25));
        btDeleteMember = new JButton("Delete Member");
        btDeleteMember.setPreferredSize(new Dimension(175,25));

        btPromoteToAdmin = new JButton("Make Leader");
        btPromoteToAdmin.setPreferredSize(new Dimension(175,25));
        btDegradeToMember = new JButton("Make Member");
        btDegradeToMember.setPreferredSize(new Dimension(175,25));
        lstMemberModel = new DefaultListModel<>();
        lstMembers = new JList(lstMemberModel);
        lstMembers.setCellRenderer(new DefaultListCellRenderer()
                                          {
                                              @Override
                                              public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                                                  JLabel listCellRendererComponent = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected,cellHasFocus);
                                                  if (index % 2 == 0) setBackground(Color.decode("#EEF1FD"));
                                                  return listCellRendererComponent;
                                              }
                                          }
        );
        pMembersButtons = new JPanel(new GridLayout(4,1,5,5));

        pMembers.add(pMembersHeader, BorderLayout.NORTH);
        pMembers.add(pFlowPanel2, BorderLayout.EAST);
        pMembersButtons.add(btAddMember);
        pMembersButtons.add(btDeleteMember);
        pMembersButtons.add(btPromoteToAdmin);
        pMembersButtons.add(btDegradeToMember);
        pFlowPanel2.add(pMembersButtons);
        JScrollPane spMembers = new JScrollPane(lstMembers);
        spMembers.setBorder(new TitledBorder(new LineBorder(Color.black, 1), "Project Members"));
        pMembers.add(spMembers, BorderLayout.CENTER);

        pDescription = new JPanel(new BorderLayout(5,5));
        pDescriptionButtons = new JPanel(new GridLayout(1,1,5,5));
        pFlowPanel3 = new JPanel(new FlowLayout(5));
        taDescription = new JTextArea();
        btUpdateDescription = new JButton("Update Description");
        btUpdateDescription.setPreferredSize(new Dimension(175,25));
        JScrollPane spDescription = new JScrollPane(taDescription);
        //spDescription.setPreferredSize(spMembers.getPreferredSize());
        spDescription.setBorder(new TitledBorder(new LineBorder(Color.black, 1), "Project Description"));
        pDescription.add(spDescription, BorderLayout.CENTER);
        pDescriptionButtons.add(btUpdateDescription, BorderLayout.EAST);
        pFlowPanel3.add(pDescriptionButtons);
        pDescription.add(pFlowPanel3, BorderLayout.EAST);


        pCenter.add(pPhases);
        pCenter.add(pMembers);
        pCenter.add(pDescription);
        add(pCenter, BorderLayout.CENTER);
        add(pHeader, BorderLayout.NORTH);
    }

    public void setProjectName(String projectName)
    {
        lbProjectName.setText("Detail for Project: " + projectName);
    }

    public void setPhases(ArrayList<String> phases)
    {
        lstPhasesModel.clear();
        for(String s : phases)
            lstPhasesModel.addElement(s);
    }

    public void setMemberInformation(ArrayList<String> members, ArrayList<String> roles)
    {
        assert(members.size() == roles.size());
        lstMemberModel.clear();
        for(int i = 0; i < members.size(); ++i)
        {
            lstMemberModel.addElement(members.get(i) + " (" + roles.get(i) + ")");
        }
    }

    public void setDescription(String description) {
        taDescription.setText(description);
    }
}

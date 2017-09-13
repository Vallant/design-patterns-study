package view.swing.project;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * Created by stephan on 08.07.17.
 */
class ProjectViewPanel extends JPanel
{

  final JButton                  btAddProject;
  final JButton                  btDeleteProject;
  final DefaultListModel<String> lstOwnedModel;
  final JList<String>            lstOwned;

  final JButton                  btLeaveProject;
  final DefaultListModel<String> lstInvolvedModel;
  final JList<String>            lstInvolved;

  public ProjectViewPanel()
  {

    super(new GridLayout(2, 1, 5, 5));
    setBorder(new EtchedBorder());
    setPreferredSize(new Dimension(700, 400));
    //setBorder(new EmptyBorder(5,5,5,5));
    JPanel pOwned = new JPanel(new BorderLayout(5, 5));
    pOwned.setPreferredSize(new Dimension(150, 250));
    JPanel pFlowPanel1 = new JPanel(new FlowLayout(0));
    JPanel pOwnedButtons = new JPanel(new GridLayout(2, 1, 5, 5));
    btAddProject = new JButton("Add Project");
    btAddProject.setPreferredSize(new Dimension(130, 25));
    btDeleteProject = new JButton("Delete Project");
    btDeleteProject.setPreferredSize(new Dimension(130, 25));
    lstOwnedModel = new DefaultListModel<>();
    lstOwned = new JList<>(lstOwnedModel);
    lstOwned.setCellRenderer(new DefaultListCellRenderer()
                             {
                               @Override
                               public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                                             boolean isSelected,
                                                                             boolean cellHasFocus)
                               {
                                 JLabel listCellRendererComponent =
                                   (JLabel) super.getListCellRendererComponent(list, value, index, isSelected,
                                     cellHasFocus);
                                 if(index % 2 == 1) setBackground(Color.decode("#EEF1FD"));
                                 return listCellRendererComponent;
                               }
                             }
    );
    lstOwned.setPreferredSize(new Dimension(150, 100));
    JScrollPane spOwned = new JScrollPane(lstOwned);


    spOwned.setBorder(new TitledBorder(new LineBorder(Color.black, 1), "Own Projects"));

    add(pOwned);
    pOwned.add(pFlowPanel1, BorderLayout.EAST);
    pOwnedButtons.add(btAddProject);
    pOwnedButtons.add(btDeleteProject);
    pFlowPanel1.add(pOwnedButtons);
    pOwned.add(spOwned, BorderLayout.CENTER);


    JPanel pInvolved = new JPanel(new BorderLayout(5, 5));
    pInvolved.setPreferredSize(new Dimension(130, 250));
    JPanel pFlowPanel2 = new JPanel(new FlowLayout(0));
    JPanel pInvolvedButtons = new JPanel(new GridLayout(1, 1, 5, 5));
    btLeaveProject = new JButton("Leave Project");
    btLeaveProject.setPreferredSize(new Dimension(130, 25));
    lstInvolvedModel = new DefaultListModel<>();
    lstInvolved = new JList<>(lstInvolvedModel);
    lstInvolved.setCellRenderer(new DefaultListCellRenderer()
                                {
                                  @Override
                                  public Component getListCellRendererComponent(JList<?> list, Object value, int
                                    index, boolean isSelected,
                                                                                boolean cellHasFocus)
                                  {
                                    JLabel listCellRendererComponent =
                                      (JLabel) super.getListCellRendererComponent(list, value, index, isSelected,
                                        cellHasFocus);
                                    if(index % 2 == 1) setBackground(Color.decode("#EEF1FD"));
                                    return listCellRendererComponent;
                                  }
                                }
    );
    JScrollPane spInvolved = new JScrollPane(lstInvolved);
    spInvolved.setBorder(new TitledBorder(new LineBorder(Color.black, 1), "Participating Projects"));

    add(pInvolved);
    pInvolved.add(pFlowPanel2, BorderLayout.EAST);
    pInvolvedButtons.add(btLeaveProject);
    pInvolved.add(spInvolved, BorderLayout.CENTER);
    pFlowPanel2.add(pInvolvedButtons);
  }
}

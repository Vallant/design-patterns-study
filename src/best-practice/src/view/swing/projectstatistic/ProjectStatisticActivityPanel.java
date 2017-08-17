package view.swing.projectstatistic;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;

class ProjectStatisticActivityPanel extends JPanel
{
  private final JPanel pHeader;

  final JComboBox<String> cbPeriod;
  final JComboBox<String> cbMembers;

  final         ProjectStatisticActivityTableModel tblActivityModel;
  final JTable                             tblActivity;
  private final JScrollPane                        scrpTable;
  final         JButton                            btBack;

  private final JPanel  pButtons;
  private final JPanel  pFlowPanel;
  private final JButton btDeleteActivity;
  private final JButton btAddActivity;
  private final JButton btUpdateActivity;

  public ProjectStatisticActivityPanel()
  {
    super(new BorderLayout(5, 5));
    setBorder(new EtchedBorder());
    this.pHeader = new JPanel(new FlowLayout(5));

    this.cbPeriod = new JComboBox<>();
    cbPeriod.addItem("All Time");
    cbPeriod.addItem("Last Year");
    cbPeriod.addItem("Last Month");
    cbPeriod.addItem("Last Week");
    cbPeriod.addItem("Last Day");

    cbMembers = new JComboBox<>();
    cbMembers.addItem("All Members");


    btBack = new JButton("Back");

    this.tblActivityModel = new ProjectStatisticActivityTableModel();
    this.tblActivity = new JTable(tblActivityModel);
    scrpTable = new JScrollPane(tblActivity);

    tblActivity.setBorder(new LineBorder(Color.black, 1));

    pFlowPanel = new JPanel(new FlowLayout(5));
    pButtons = new JPanel(new GridLayout(3, 1, 5, 5));
    btAddActivity = new JButton("Add Activity");
    btDeleteActivity = new JButton("Delete Activity");
    btUpdateActivity = new JButton("Update Activity");

    pButtons.add(btAddActivity);
    pButtons.add(btDeleteActivity);
    pButtons.add(btUpdateActivity);

    pHeader.add(btBack);
    pHeader.add(cbPeriod);
    pHeader.add(cbMembers);
    add(pHeader, BorderLayout.NORTH);
    add(scrpTable, BorderLayout.CENTER);
    pFlowPanel.add(pButtons);
    add(pFlowPanel, BorderLayout.EAST);
  }


  public void setMembers(ArrayList<String> members)
  {
    int index = cbMembers.getSelectedIndex();
    cbMembers.removeAllItems();
    cbMembers.addItem("All Members");
    for(String m : members)
    {
      cbMembers.addItem(m);
    }
    if(index < cbMembers.getItemCount())
      cbMembers.setSelectedIndex(index);
  }
}

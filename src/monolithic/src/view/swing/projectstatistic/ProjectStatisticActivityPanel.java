package view.swing.projectstatistic;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;

class ProjectStatisticActivityPanel extends JPanel
{

  final JComboBox<String> cbPeriod;
  final JComboBox<String> cbMembers;

  final ProjectStatisticActivityTableModel tblActivityModel;
  final JButton                            btBack;

  public ProjectStatisticActivityPanel()
  {
    super(new BorderLayout(5, 5));
    setBorder(new EtchedBorder());
    JPanel pHeader = new JPanel(new FlowLayout(5));

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
    JTable tblActivity = new JTable(tblActivityModel);
    JScrollPane scrpTable = new JScrollPane(tblActivity);

    tblActivity.setBorder(new LineBorder(Color.black, 1));

    JPanel pFlowPanel = new JPanel(new FlowLayout(5));
    JPanel pButtons = new JPanel(new GridLayout(3, 1, 5, 5));
    JButton btAddActivity = new JButton("Add Activity");
    JButton btDeleteActivity = new JButton("Delete Activity");
    JButton btUpdateActivity = new JButton("Update Activity");

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
    cbMembers.removeAllItems();
    cbMembers.addItem("All Members");
    for(String m : members)
    {
      cbMembers.addItem(m);
    }
  }
}

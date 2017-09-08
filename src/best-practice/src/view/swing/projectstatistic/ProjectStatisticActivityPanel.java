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
  final JTable                             tblActivity;
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
    this.tblActivity = new JTable(tblActivityModel);
    JScrollPane scrpTable = new JScrollPane(tblActivity);

    tblActivity.setBorder(new LineBorder(Color.black, 1));

    pHeader.add(btBack);
    pHeader.add(cbPeriod);
    pHeader.add(cbMembers);
    add(pHeader, BorderLayout.NORTH);
    add(scrpTable, BorderLayout.CENTER);
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

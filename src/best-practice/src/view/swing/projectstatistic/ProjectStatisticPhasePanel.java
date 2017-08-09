package view.swing.projectstatistic;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by stephan on 17/07/17.
 */
class ProjectStatisticPhasePanel extends JPanel
{

  private final JPanel pHeader;

  final JComboBox<String> cbPeriod;
  final JComboBox<String> cbMembers;

  final         ProjectStatisticTableModel tblPhaseModel;
  final         JTable                     tblPhases;
  private final JScrollPane                scrpTable;
  final         JButton                    btBack;

  public ProjectStatisticPhasePanel()
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

    this.tblPhaseModel = new ProjectStatisticTableModel("Phasename");
    this.tblPhases = new JTable(tblPhaseModel);
    scrpTable = new JScrollPane(tblPhases);

    tblPhases.setBorder(new LineBorder(Color.black, 1));
    pHeader.add(btBack);
    pHeader.add(cbPeriod);
    pHeader.add(cbMembers);
    add(pHeader, BorderLayout.NORTH);
    add(scrpTable, BorderLayout.CENTER);

  }

  public void setMemberNames(ArrayList<String> memberNames)
  {
    cbMembers.removeAllItems();
    cbMembers.addItem("All Members");
    for(String m : memberNames)
    {
      cbMembers.addItem(m);
    }
  }
}

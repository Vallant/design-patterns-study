package view.swing.personalstatistic;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

class PersonalStatisticActivityPanel extends JPanel
{

  final JComboBox<String> cbPeriod;

  final PersonalStatisticActivityTableModel tblActivityModel;
  final JTable                              tblActivity;
  final JButton                             btBack;

  final JButton btDeleteActivity;
  final JButton btAddActivity;
  final JButton btUpdateActivity;

  public PersonalStatisticActivityPanel()
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


    btBack = new JButton("Back");

    this.tblActivityModel = new PersonalStatisticActivityTableModel();
    this.tblActivity = new JTable(tblActivityModel);
    JScrollPane scrpTable = new JScrollPane(tblActivity);

    tblActivity.setBorder(new LineBorder(Color.black, 1));

    JPanel pFlowPanel = new JPanel(new FlowLayout(5));
    JPanel pButtons = new JPanel(new GridLayout(3, 1, 5, 5));
    btAddActivity = new JButton("Add Activity");
    btDeleteActivity = new JButton("Delete Activity");
    btUpdateActivity = new JButton("Update Activity");

    pButtons.add(btAddActivity);
    pButtons.add(btDeleteActivity);
    pButtons.add(btUpdateActivity);

    pHeader.add(btBack);
    pHeader.add(cbPeriod);
    add(pHeader, BorderLayout.NORTH);
    add(scrpTable, BorderLayout.CENTER);
    pFlowPanel.add(pButtons);
    add(pFlowPanel, BorderLayout.EAST);
  }


}

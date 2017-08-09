package view.swing.personalstatistic;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class PersonalStatisticActivityPanel extends JPanel
{
  final JPanel pHeader;

  final JComboBox<String> cbPeriod;

  final PersonalStatisticActivityTableModel tblActivityModel;
  final JTable                              tblActivity;
  final JScrollPane                         scrpTable;
  final JButton                             btBack;

  final JPanel  pButtons;
  final JPanel  pFlowPanel;
  final JButton btDeleteActivity;
  final JButton btAddActivity;
  final JButton btUpdateActivity;

  public PersonalStatisticActivityPanel()
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


    btBack = new JButton("Back");

    this.tblActivityModel = new PersonalStatisticActivityTableModel();
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
    add(pHeader, BorderLayout.NORTH);
    add(scrpTable, BorderLayout.CENTER);
    pFlowPanel.add(pButtons);
    add(pFlowPanel, BorderLayout.EAST);
  }


}

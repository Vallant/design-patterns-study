package view.swing.personalstatistic;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * Created by stephan on 17/07/17.
 */
class PersonalStatisticPhasePanel extends JPanel
{

  final JComboBox<String> cbPeriod;

  final PersonalStatisticTableModel tblProjectsModel;
  final JTable                      tblPhases;
  final JButton                     btBack;

  public PersonalStatisticPhasePanel()
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

    this.tblProjectsModel = new PersonalStatisticTableModel("Phasename");
    this.tblPhases = new JTable(tblProjectsModel);
    JScrollPane scrpTable = new JScrollPane(tblPhases);

    tblPhases.setBorder(new LineBorder(Color.black, 1));
    pHeader.add(btBack);
    pHeader.add(cbPeriod);
    add(pHeader, BorderLayout.NORTH);
    add(scrpTable, BorderLayout.CENTER);

  }
}

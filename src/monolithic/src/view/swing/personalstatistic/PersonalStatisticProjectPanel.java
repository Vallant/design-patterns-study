package view.swing.personalstatistic;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * Created by stephan on 17/07/17.
 */
public class PersonalStatisticProjectPanel extends JPanel {
    final JPanel pHeader;

    final JComboBox<String> cbPeriod;

    final PersonalStatisticTableModel tblProjectsModel;
    final JTable tblProjects;
    final JScrollPane scrpTable;

    public PersonalStatisticProjectPanel() {
        super(new BorderLayout(5,5));
        setBorder(new EtchedBorder());
        this.pHeader = new JPanel(new FlowLayout(5));

        this.cbPeriod = new JComboBox<>();
        cbPeriod.addItem("All Time");
        cbPeriod.addItem("Last Year");
        cbPeriod.addItem("Last Month");
        cbPeriod.addItem("Last Week");
        cbPeriod.addItem("Last Day");
        cbPeriod.setSelectedIndex(0);




        this.tblProjectsModel = new PersonalStatisticTableModel("Projectname");
        this.tblProjects = new JTable(tblProjectsModel);
        scrpTable = new JScrollPane(tblProjects);

        tblProjects.setBorder(new LineBorder(Color.black, 1));
        pHeader.add(cbPeriod);
        add(pHeader, BorderLayout.NORTH);
        add(scrpTable, BorderLayout.CENTER);

    }


}

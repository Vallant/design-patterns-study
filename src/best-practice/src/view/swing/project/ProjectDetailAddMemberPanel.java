package view.swing.project;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by stephan on 11/07/17.
 */
public class ProjectDetailAddMemberPanel extends JPanel {
    private ArrayList<String> available;

    private final DefaultListModel<String> lstAvailableUsersModel;
    final JList<String> lstAvailableUsers;


    public ProjectDetailAddMemberPanel() {
        super(new BorderLayout(5, 5));


        lstAvailableUsersModel = new DefaultListModel<>();
        lstAvailableUsers = new JList<>(lstAvailableUsersModel);
        lstAvailableUsers.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        JScrollPane spList = new JScrollPane(lstAvailableUsers);
        spList.setBorder(new TitledBorder(new LineBorder(Color.black, 1), "Available Users for Project"));

        add(spList, BorderLayout.CENTER);
        setPreferredSize(new Dimension(350, 200));
        lstAvailableUsers.setCellRenderer(new DefaultListCellRenderer() {
                                              @Override
                                              public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                                                  JLabel listCellRendererComponent = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                                                  if (index % 2 == 1) setBackground(Color.decode("#EEF1FD"));
                                                  return listCellRendererComponent;
                                              }
                                          }
        );
    }

    void setAvailableNames(ArrayList<String> names)
    {
        for(String s : names)
            lstAvailableUsersModel.addElement(s);
    }


}

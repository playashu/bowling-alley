package views;

import javax.swing.*;
import java.awt.*;

public class QueryResultView extends JFrame{

    public QueryResultView(Object[] column, Object[][] data) {
        JTable jt = new JTable(data, column);
        jt.setBounds(30, 40, 200, 300);
        JScrollPane sp = new JScrollPane(jt);
        add(sp);
        setSize(300, 400);
        Dimension screenSize = (Toolkit.getDefaultToolkit()).getScreenSize();
        setLocation(
                ((screenSize.width) / 2) - ((getSize().width) / 2),
                ((screenSize.height) / 2) - ((getSize().height) / 2));
        setVisible(true);
    }
}
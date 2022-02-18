package utils;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class UiComponents {
    public static JPanel createFlowPanel() {
        JPanel panel=new JPanel();
        panel.setLayout(new FlowLayout());
        return panel;
    }
    public static JPanel createGridPanel(int row,int col) {
        JPanel panel=new JPanel();
        panel.setLayout(new GridLayout(row,col));
        return panel;
    }
    public static JPanel createGridPanel(String name,int row,int col) {
        JPanel panel=createGridPanel(row,col);
        panel.setBorder(new TitledBorder(name));
        return panel;
    }
    public static JButton createFlowButton(String name, JPanel iterfacePanel, ActionListener listener) {
        JButton button = new JButton(name);
        button.addActionListener(listener);
        JPanel panel = createFlowPanel();
        panel.add(button);
        iterfacePanel.add(panel);
        return button;
    }
    public static GridLayout createGridLayout(int row,int col) {
        return new GridLayout(row,col);
    }
    public static JTextField createFlowText(String name, JPanel interfacePanel) {
        JLabel label = new JLabel(name);
        JTextField field = new JTextField("", 15);
        interfacePanel.add(label);
        interfacePanel.add(field);
        return field;
    }
//public static JLabel createLabel(String name) {
//
//}
}

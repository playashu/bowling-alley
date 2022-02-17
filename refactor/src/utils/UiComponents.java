package utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class UiComponents {
 public static JButton createButton(String name, JPanel iterfacePanel, ActionListener listener) {
     JButton button = new JButton(name);
     button.addActionListener(listener);
     JPanel panel = new JPanel();
     panel.setLayout(new FlowLayout());
     panel.add(button);
     iterfacePanel.add(panel);
     return button;
 }
// public static JTextField createText(String name)
// {
//
// }
public static JLabel createLabel(String name) {
    JLabel label = new JLabel(name);
    return label;
}
}

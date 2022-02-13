package utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class uiComponents {
 public static JButton createButton(String name, JPanel iterfacePanel, ActionListener listener) {
     JButton button = new JButton(name);
     button.addActionListener(listener);
     JPanel panel = new JPanel();
     panel.setLayout(new FlowLayout());
     panel.add(button);
     iterfacePanel.add(panel);
     return button;
 }
}

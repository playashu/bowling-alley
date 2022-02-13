package utils;

import javax.swing.*;
import java.awt.*;

public class ViewComponents {
 public static JButton createButton(String st, JPanel iterfacePanel) {
     JButton btn = new JButton(st);
     JPanel btnPanel = new JPanel();
     btnPanel.setLayout(new FlowLayout());
     btnPanel.add(btn);
     iterfacePanel.add(btnPanel);
     return btn;
 }

}

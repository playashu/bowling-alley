package views;

import javax.swing.*;

public class AdhocQueryView extends JFrame{
    private JPanel queryPanel;
    private JLabel availQueryLabel;
    private JLabel adHocQueriesLAbel;
    private JButton searchButton;
    private JComboBox availQueries;
    private JLabel availLabel;

    public AdhocQueryView()
    {
        super("Ad-Hoc Queries");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(queryPanel);
    }
}

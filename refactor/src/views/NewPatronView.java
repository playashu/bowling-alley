package views;/* AddPartyView.java
 *
 *  Version
 *  $Id$
 * 
 *  Revisions:
 * 		$Log: NewPatronView.java,v $
 * 		Revision 1.3  2003/02/02 16:29:52  ???
 * 		Added ControlDeskEvent and ControlDeskObserver. Updated Queue to allow access to Vector so that contents could be viewed without destroying. Implemented observer model for most of ControlDesk.
 * 		
 * 
 */

/**
 * Class for GUI components need to add a patron
 *
 */

import utils.UiComponents;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewPatronView implements ActionListener {

	private int maxSize;

	private JFrame win;
	private JButton abort, finished;
	//private JLabel nickLabel, fullLabel, emailLabel;
	private JTextField nickField, fullField, emailField;
	private String nick, full, email;

	private boolean done;

	private String selectedNick, selectedMember;
	private AddPartyView addParty;

	public NewPatronView(AddPartyView v) {
		addParty=v;	
		done = false;
		win = new JFrame("Add Patron");
		win.getContentPane().setLayout(new BorderLayout());
		((JPanel) win.getContentPane()).setOpaque(false);
		JPanel colPanel = new JPanel();
		colPanel.setLayout(new BorderLayout());
		JPanel patronPanel = UiComponents.createGridPanel("Your Info",3,1);
		JPanel nickPanel = UiComponents.createFlowPanel();
		nickField=UiComponents.createFlowText("Nick Name",nickPanel);
		JPanel fullPanel = UiComponents.createFlowPanel();
		fullField=UiComponents.createFlowText("Full Name",fullPanel);

		JPanel emailPanel = UiComponents.createFlowPanel();
		emailField=UiComponents.createFlowText("E-Mail",emailPanel);

		patronPanel.add(nickPanel);
		patronPanel.add(fullPanel);
		patronPanel.add(emailPanel);

		// Button Panel
		JPanel buttonPanel = UiComponents.createGridPanel(4,1);

		Insets buttonMargin = new Insets(4, 4, 4, 4);

		finished = UiComponents.createFlowButton("Add Patron",buttonPanel,this);

		abort = UiComponents.createFlowButton("Abort",buttonPanel,this);

		// Clean up main panel
		colPanel.add(patronPanel, "Center");
		colPanel.add(buttonPanel, "East");

		win.getContentPane().add("Center", colPanel);

		win.pack();

		// Center Window on Screen
		Dimension screenSize = (Toolkit.getDefaultToolkit()).getScreenSize();
		win.setLocation(
			((screenSize.width) / 2) - ((win.getSize().width) / 2),
			((screenSize.height) / 2) - ((win.getSize().height) / 2));
		win.show();

	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(abort)) {
			done = true;
			win.dispose();
		}

		if (e.getSource().equals(finished)) {
			nick = nickField.getText();
			full = fullField.getText();
			email = emailField.getText();
			done = true;
			addParty.updateNewPatron( this );
			win.dispose();
		}

	}

	/*public boolean done() {
		return done;
	}*/

	public String getNick() {
		return nick;
	}

	public String getFull() {
		return full;
	}

	public String getEmail() {
		return email;
	}

}

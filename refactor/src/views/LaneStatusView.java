package views; /**
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */

import events.LaneEvent;
import events.PinsetterEvent;
import managers.LaneManager;
import managers.PinsetterManager;
import models.Bowler;
import models.Lane;

import models.Pinsetter;
import observers.LaneObserver;
import observers.PinsetterObserver;
import utils.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class LaneStatusView implements ActionListener, LaneObserver, PinsetterObserver {

	private JPanel jp;

	private JLabel curBowler, foul, pinsDown;
	private JButton viewLane;
	private JButton viewPinSetter, maintenance;

	private PinSetterView psv;
	private LaneView lv;
	private Lane lane;
	int laneNum;
	int frames;
	boolean laneShowing;
	boolean psShowing;
	boolean last_3Strike;

	public LaneStatusView(Lane lane, int laneNum,int frames) {

		this.lane = lane;
		this.laneNum = laneNum;
		this.frames = frames;
		laneShowing=false;
		psShowing=false;

		psv = new PinSetterView( laneNum );
		Pinsetter ps = lane.getPinsetter();
		PinsetterManager pm = ps.getManager();
		pm.subscribe(psv);

		lv = new LaneView( lane, laneNum,frames);

		LaneManager manager = lane.getLaneManager();
		manager.subscribe(lv);


		jp = new JPanel();
		jp.setLayout(new FlowLayout());
		JLabel cLabel = new JLabel( "Now Bowling: " );
		curBowler = new JLabel( "(no one)" );
		JLabel fLabel = new JLabel( "Foul: " );
		foul = new JLabel( " " );
		JLabel pdLabel = new JLabel( "Pins Down: " );
		pinsDown = new JLabel( "0" );

		// Button Panel
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());

		Insets buttonMargin = new Insets(4, 4, 4, 4);

		JPanel viewLanePanel = new JPanel();
		viewLane = UiComponents.createFlowButton("View Lane",viewLanePanel,this);

		JPanel viewPinSetterPanel = new JPanel();
		viewPinSetter = UiComponents.createFlowButton("Pinsetter",viewPinSetterPanel,this);

		JPanel maintenancePanel = new JPanel();
		maintenance = UiComponents.createFlowButton("      ",maintenancePanel,this);
		maintenance.setBackground( Color.GREEN );

		viewLane.setEnabled( false );
		viewPinSetter.setEnabled( false );


		buttonPanel.add(viewLanePanel);
		buttonPanel.add(viewPinSetterPanel);
		buttonPanel.add(maintenancePanel);

		jp.add( cLabel );
		jp.add( curBowler );
//		jp.add( fLabel );
//		jp.add( foul );
		jp.add( pdLabel );
		jp.add( pinsDown );
		
		jp.add(buttonPanel);
		addListners();

	}

	public JPanel showLane() {
		return jp;
	}
	private void viewPinSetterAction()
	{
		if ( lane.isPartyAssigned() ) {
			if ( psShowing == false ) {
				psv.show();
				psShowing=true;
			} else if ( psShowing == true ) {
				psv.hide();
				psShowing=false;
			}
		}
	}
	private void addListners()
	{
		viewPinSetter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				viewPinSetterAction();
			}
		} );
	}
	public void actionPerformed( ActionEvent e ) {
		if (e.getSource().equals(viewLane)) {
			if ( lane.isPartyAssigned() ) { 
				if ( laneShowing == false ) {
					lv.show();
					laneShowing=true;
				} else if ( laneShowing == true ) {
					lv.hide();
					laneShowing=false;
				}
			}
		}
		if (e.getSource().equals(maintenance)) {
			if ( lane.isPartyAssigned() ) {
				LaneManager laneManager= lane.getLaneManager();
				lane.unPauseGame();
				maintenance.setBackground( Color.GREEN );
			}
		}
	}

	public void receiveLaneEvent(LaneEvent le) {
		curBowler.setText( ( (Bowler)le.getBowler()).getNickName() );
		if ( le.isMechanicalProblem() ) {
			maintenance.setBackground( Color.RED );
		}	
		if ( lane.isPartyAssigned() == false ) {
			viewLane.setEnabled( false );
			viewPinSetter.setEnabled( false );
		} else {
			viewLane.setEnabled( true );
			viewPinSetter.setEnabled( true );
		}
	}

	public void receivePinsetterEvent(PinsetterEvent pe) {
		pinsDown.setText( ( new Integer(pe.totalPinsDown()) ).toString() );
//		foul.setText( ( new Boolean(pe.isFoulCommited()) ).toString() );
		
	}

}

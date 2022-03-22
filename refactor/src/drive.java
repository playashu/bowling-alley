import dbAccess.ConfigCache;
import models.ControlDesk;
import utils.ConnectionFactory;
import views.AdhocQueryView;
import models.LaneConfiguration;
import views.ControlDeskView;

import java.sql.Connection;

public class drive {
	public static void setLookandFeel()
	{
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(AdhocQueryView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(AdhocQueryView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(AdhocQueryView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(AdhocQueryView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
	}
	public static void main(String[] args) {
		int numLanes = Integer.parseInt(ConfigCache.getConfig("NUMBER_OF_LANES"));
		int maxPatronsPerParty=Integer.parseInt(ConfigCache.getConfig("MAX_PATRONS_PER_PARTY"));
		setLookandFeel();

    	LaneConfiguration frameC = new LaneConfiguration(Integer.parseInt(ConfigCache.getConfig("NUMBER_OF_FRAMES")),true);

		ControlDesk controlDesk = new ControlDesk(numLanes,frameC);
		Connection conn= ConnectionFactory.getConnection();
		ControlDeskView cdv = new ControlDeskView( controlDesk, maxPatronsPerParty, frameC);
		controlDesk.getControlDeskManager().subscribe( cdv );

	}
}

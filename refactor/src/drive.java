import dbAccess.ConfigCache;
import models.ControlDesk;
import utils.ConnectionFactory;
import views.ControlDeskView;

import java.sql.Connection;

public class drive {

	public static void main(String[] args) {

		int numLanes = Integer.parseInt(ConfigCache.getConfig("NUMBER_OF_LANES"));
		int maxPatronsPerParty=Integer.parseInt(ConfigCache.getConfig("MAX_PATRONS_PER_PARTY"));

		ControlDesk controlDesk = new ControlDesk(numLanes);
		Connection conn= ConnectionFactory.getConnection();
		ControlDeskView cdv = new ControlDeskView( controlDesk, maxPatronsPerParty);
		controlDesk.getControlDeskManager().subscribe( cdv );

	}
}

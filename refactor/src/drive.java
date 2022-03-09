import models.ControlDesk;
import utils.ConnectionFactory;
import views.ControlDeskView;

import java.sql.Connection;

public class drive {

	public static void main(String[] args) {

		int numLanes = 3;
		int maxPatronsPerParty=5;

		ControlDesk controlDesk = new ControlDesk(numLanes);
		Connection conn= ConnectionFactory.getConnection();
		ControlDeskView cdv = new ControlDeskView( controlDesk, maxPatronsPerParty);
		controlDesk.getControlDeskManager().subscribe( cdv );

	}
}

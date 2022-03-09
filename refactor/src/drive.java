import models.ControlDesk;
import models.frameContext;
import views.ControlDeskView;

public class drive {

	public static void main(String[] args) {

		int numLanes = 3;
		int maxPatronsPerParty=6;
		frameContext frameC = new frameContext(3,true);
		ControlDesk controlDesk = new ControlDesk(numLanes,frameC);

		ControlDeskView cdv = new ControlDeskView( controlDesk, maxPatronsPerParty, frameC);
		controlDesk.getControlDeskManager().subscribe( cdv );

	}
}

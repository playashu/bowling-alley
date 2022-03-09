import models.ControlDesk;
import views.ControlDeskView;

public class drive {

	public static void main(String[] args) {

		int numLanes = 3;
		int maxPatronsPerParty=6;
		int frames = 2;
		ControlDesk controlDesk = new ControlDesk(numLanes,frames);

		ControlDeskView cdv = new ControlDeskView( controlDesk, maxPatronsPerParty, frames);
		controlDesk.getControlDeskManager().subscribe( cdv );

	}
}

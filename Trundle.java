package leJOS;

import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Behavior;

public class Trundle implements Behavior {
    private MovePilot pilot;

    Trundle(MovePilot p) {
        this.pilot = p; // Save the (shared) pilot in a field
    }

    // Start trundling and return control immediately.
    public void action() {
        pilot.forward();
    }

    // Since action returns immediately this is probably never called
    public void suppress() {
    }

    // Is it my turn?
    public boolean takeControl() {
        return true; // Yeah - we are SO eager
    }
}
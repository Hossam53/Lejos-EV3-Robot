package leJOS;

import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Behavior;
import lejos.hardware.sensor.EV3ColorSensor;

import static java.lang.Thread.yield;

public class Dark implements Behavior {
    private boolean suppressed = false;
    private MovePilot pilot;
    private EV3ColorSensor colorSensor;
    private final int DARK_THRESHOLD = 10;

    public Dark(MovePilot pilot) {
        this.pilot = pilot;

    }

    @Override
    public boolean takeControl() {
        int lightLevel = colorSensor.getAmbientMode().sampleSize();
        return !suppressed && pilot.getLinearSpeed() > 100 && lightLevel < DARK_THRESHOLD;
    }

    @Override
    public void suppress() {
        suppressed = true;
    }

    @Override
    public void action() {
        suppressed = false;
        pilot.setLinearSpeed(100);
        while (!suppressed) {
            int lightLevel = colorSensor.getAmbientMode().sampleSize();
            if (lightLevel >= DARK_THRESHOLD) {
                pilot.setLinearSpeed(200);
                return;
            }
            yield();
        }
    }
}


package leJOS;

import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Behavior;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;

public class Backup implements Behavior {
    private boolean suppressed = false;
    private MovePilot pilot;
    private EV3UltrasonicSensor sensor;
    private SampleProvider distance;
    private float[] sample;

    public Backup(MovePilot pilot) {
        this.pilot = pilot;
        this.distance = sensor.getDistanceMode();
        this.sample = new float[distance.sampleSize()];
    }

    public boolean takeControl() {
        distance.fetchSample(sample, 0);
        float dist = sample[0];
        return (dist < 0.2); // Wall is too close
    }

    public void suppress() {
        suppressed = true;
    }

    public void action() {
        suppressed = false;
        pilot.travel(-20); // Move backwards
        int randomAngle = (int) (Math.random() * 2); // 0 or 1
        int angle = (randomAngle == 0) ? 90 : -90;
        pilot.rotate(angle); // Turn left or right
        while (!suppressed && pilot.isMoving()) {
            Thread.yield(); // Wait for action to finish
        }
    }
}


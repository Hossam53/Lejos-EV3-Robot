package leJOS;

import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Behavior;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;

public class Light implements Behavior {
    private  MovePilot pilot;

    private boolean suppressed = false;
    private EV3ColorSensor colorSensor;
    private EV3LargeRegulatedMotor motor;



    public Light(MovePilot pilot) {
        this.pilot = pilot;
    }


    @Override
    public boolean takeControl() {
        int lightLevel = colorSensor.getAmbientMode().sampleSize();
        float[] sample = new float[lightLevel];
        colorSensor.getAmbientMode().fetchSample(sample, 0);
        return (motor.getRotationSpeed() <= 100 && sample[0] >= 0.5);
    }

    @Override
    public void action() {
        suppressed = false;
        motor.setAcceleration(500);
        motor.setSpeed(1000);
        motor.forward();
        while (!suppressed && motor.getRotationSpeed() < 1000) {
            Thread.yield();
        }
        motor.stop(true);
    }

    @Override
    public void suppress() {
        suppressed = true;
    }
}


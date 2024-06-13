package leJOS;

import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class Driver {
    public static void main(String[] args) {
        MovePilot pilot = getPilot();
        pilot.setLinearSpeed(200);
        Behavior trundle = new Trundle(pilot);
        Behavior light = new Light(pilot);
        Behavior Dark = new Dark(pilot);
        Behavior Backup = new Backup(pilot);
        Arbitrator ab = new Arbitrator(new Behavior[] {trundle});
        ab.go(); // This never returns! It is a blocking call.
    }
    private static MovePilot getPilot() {
        BaseRegulatedMotor mL = new EV3LargeRegulatedMotor(MotorPort.A);
        Wheel wL = WheeledChassis.modelWheel(mL, 60).offset(-1 * 29);
        BaseRegulatedMotor mR = new EV3LargeRegulatedMotor(MotorPort.B);
        Wheel wR = WheeledChassis.modelWheel(mR, 60).offset(29);
        Wheel[] wheels = new Wheel[] {wR, wL};
        Chassis chassis = new WheeledChassis(wheels, WheeledChassis.TYPE_DIFFERENTIAL);
        return new MovePilot(chassis);
    }
}
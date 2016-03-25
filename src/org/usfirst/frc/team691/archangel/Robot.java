package org.usfirst.frc.team691.archangel;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Victor;

public class Robot extends IterativeRobot {
	
	private Spark fl;
	private Spark bl;
	private Spark fr;
	private Spark br;
	private Spark intake;
	private Spark gatekeeper;
	private Spark shooter;
	private Victor arm;
	private DigitalInput driveMode;
	private DigitalInput gateLimit;
	private RobotDrive rd;
	private Joystick rJoy;
	private Joystick lJoy;
	private boolean autoArm;
	private boolean autoDrive;
	private long autoTime;
	
    public void robotInit() {
    	fr = new Spark(2);
		fl = new Spark(0);
		br = new Spark(3);
		bl = new Spark(1);
		intake = new Spark(4);
		gatekeeper = new Spark(5);
		shooter = new Spark(6);
		arm = new Victor(7);
		driveMode = new DigitalInput(0);
		gateLimit = new DigitalInput(3);
		rJoy = new Joystick(0);
		lJoy = new Joystick(1);
		rd = new RobotDrive(fl, bl, fr, br);
		autoArm = false;
		autoDrive = false;
		autoTime = System.currentTimeMillis();
    }
    
	public void autonomousInit() {
		autoArm = false;
		autoDrive = false;
		autoTime = System.currentTimeMillis();
    }

    public void autonomousPeriodic() {
    	if(System.currentTimeMillis() < autoTime + 1000) {
			autoArm = true;
		} else {
			autoArm = false;
		}

		if(System.currentTimeMillis() < autoTime + 2000) {
			autoDrive = true;
		} else {
			autoDrive = false;
		}

		if(autoArm) {
			arm.set(1.0);
		}

		if(autoDrive) {
			rd.tankDrive(1.0, 1.0);
		}
    }

    public void teleopPeriodic() {
    	if(driveMode.get()) {
			rd.arcadeDrive(lJoy);
		} else {
			rd.tankDrive(rJoy, lJoy);
		}

		if(lJoy.getRawButton(1) && !gateLimit.get()) {
			intake.set(1.0);
		} else if(lJoy.getRawButton(2)) {
			intake.set(-1.0);
		} else {
			intake.set(0.0);
		}

		if(lJoy.getRawButton(3)) {
			shooter.set(-1.0);
			gatekeeper.set(1.0);
		} else {
			shooter.set(0.0);
			gatekeeper.set(0.0);
		}

		if(lJoy.getRawButton(5)) {
			gatekeeper.set(1.0);
		} else if(lJoy.getRawButton(4)) {
			gatekeeper.set(-1.0);
		} else {
			gatekeeper.set(0.0);
		}

		if(rJoy.getRawButton(3)) {
			arm.set(-1.0);
		} else if(rJoy.getRawButton(2)) {
			arm.set(-1.0);
		} else {
			arm.set(0.0);
		}
	}
}

// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static frc.robot.Constants.DriveTrain.*;
import static frc.robot.Constants.DriveWithJoystick.*;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.drivetrain.DriveTrain;

public class Robot extends TimedRobot {
    private DriveTrain driveTrain;
    private XboxController controller;
    private Dashboard dashboard;
    private DriveWithController driveWithController;

    @Override
    public void robotInit() {
        this.driveTrain = Util.getDriveTrain().get();
        this.controller = new XboxController(CONTROLLER_PORT);
        this.dashboard = new Dashboard(this.driveTrain);
        this.driveWithController = new DriveWithController(
            this.driveTrain, this.controller, this.dashboard);
    }

    @Override
    public void teleopPeriodic() {
        this.dashboard.execute();
        this.driveWithController.execute();
    }

    @Override
    public void disabledInit() {
        Util.convert(this.driveTrain).setAllModes(DISABLE_MODE);
    }
}

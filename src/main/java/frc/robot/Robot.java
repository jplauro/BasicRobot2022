// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static frc.robot.Constants.DriveWithJoystick.*;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.drivetrain.CANSparkMaxDriveTrain;
import frc.robot.drivetrain.DriveTrain;
import frc.robot.drivetrain.PhoenixDriveTrain;

public class Robot extends TimedRobot {
    private DriveTrain driveTrain = new CANSparkMaxDriveTrain();
    private XboxController controller = new XboxController(CONTROLLER_PORT);
    private Dashboard dashboard = new Dashboard(this.driveTrain);
    private DriveWithController driveWithController = new DriveWithController(
        this.driveTrain, this.controller, this.dashboard);

    @Override
    public void teleopPeriodic() {
        this.dashboard.execute();
        this.driveWithController.execute();
    }

    @Override
    public void disabledInit() {
        if (this.driveTrain instanceof CANSparkMaxDriveTrain) {
            CANSparkMaxDriveTrain driveTrain = (CANSparkMaxDriveTrain) this.driveTrain;
            driveTrain.setAllModes(Constants.DriveTrain.CANSparkMaxDriveTrain.DISABLE_MODE);
        } else if (this.driveTrain instanceof PhoenixDriveTrain) {
            PhoenixDriveTrain driveTrain = (PhoenixDriveTrain) this.driveTrain;
            driveTrain.setAllModes(Constants.DriveTrain.PhoenixDriveTrain.DISABLE_MODE);
        }
    }
}

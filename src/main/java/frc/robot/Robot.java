// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static frc.robot.Constants.DriveWithJoystick.*;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.drivetrain.CANSparkMaxDriveTrain;

public class Robot extends TimedRobot {
    private CANSparkMaxDriveTrain driveTrain = new CANSparkMaxDriveTrain();
    private XboxController controller = new XboxController(CONTROLLER_PORT);
    private DriveWithController driveWithController;

    @Override
    public void robotInit() {
        this.driveWithController = new DriveWithController(this.driveTrain, this.controller);
    }

    @Override
    public void teleopPeriodic() {
        this.driveWithController.execute();
    }

    @Override
    public void disabledInit() {
        this.driveTrain.setAllModes(IdleMode.kCoast);
    }
}
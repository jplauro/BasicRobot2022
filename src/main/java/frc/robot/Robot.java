// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;

import static frc.robot.Constants.DriveWithJoystick.*;

public class Robot extends TimedRobot {
  private DriveTrain driveTrain = new DriveTrain();
  private XboxController controller = new XboxController(CONTROLLER_PORT);
  private DriveWithJoystick driveWithJoystick;

  @Override
  public void robotInit() {
    this.driveWithJoystick = new DriveWithJoystick(this.driveTrain, this.controller);
  }

  @Override
  public void teleopPeriodic() {
    this.driveWithJoystick.execute();
  }

  @Override
  public void disabledInit() {
    this.driveTrain.setAllModes(IdleMode.kCoast);
  }
}

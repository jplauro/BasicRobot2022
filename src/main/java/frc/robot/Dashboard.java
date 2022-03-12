package frc.robot;

import static frc.robot.Constants.DriveTrain.*;
import static frc.robot.Constants.DriveWithJoystick.*;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.DriveWithController.DriveMode;
import frc.robot.drivetrain.DriveTrain;
import frc.robot.drivetrain.DriveTrainInterfaces.IOpenLoopRampRate;

public class Dashboard {
    private DriveTrain driveTrain;
    private double deadband = DEADBAND;
    private double openLoopRampRate = OPEN_LOOP_RAMP_RATE;
    private DriveMode driveMode = DRIVE_MODE;
    private double speedAdjustment = SPEED_ADJUSTMENT;
    private double rotationAdjustment = ROTATION_ADJUSTMENT;
    private boolean squareInputs = SQUARE_INPUTS;
    private SendableChooser<DriveMode> driveModeChooser = new SendableChooser<>();

    public Dashboard(DriveTrain driveTrain) {
        this.driveTrain = driveTrain;
        this.driveModeChooser.setDefaultOption(this.driveMode.toString(), this.driveMode);
        
        for (DriveMode driveMode : DriveMode.values()) {
            if (driveMode != DRIVE_MODE) {
                this.driveModeChooser.addOption(driveMode.toString(), driveMode);
            }
        }

        SmartDashboard.putNumber("deadband", DEADBAND);
        SmartDashboard.putNumber("open_loop_ramp_rate", OPEN_LOOP_RAMP_RATE);
        SmartDashboard.putData("drive_mode", this.driveModeChooser);
        SmartDashboard.putNumber("speed_adjustment", SPEED_ADJUSTMENT);
        SmartDashboard.putNumber("rotation_adjustment", ROTATION_ADJUSTMENT);
        SmartDashboard.putBoolean("square_inputs", SQUARE_INPUTS);
    }

    public void execute() {
        double deadband = SmartDashboard.getNumber("deadband", DEADBAND);
        double openLoopRampRate = SmartDashboard.getNumber("open_loop_ramp_rate", OPEN_LOOP_RAMP_RATE);
        this.driveMode = this.driveModeChooser.getSelected();
        this.speedAdjustment = SmartDashboard.getNumber("speed_adjustment", SPEED_ADJUSTMENT);
        this.rotationAdjustment = SmartDashboard.getNumber("rotation_adjustment", ROTATION_ADJUSTMENT);
        this.squareInputs = SmartDashboard.getBoolean("square_inputs", SQUARE_INPUTS);

        if (deadband != this.deadband) {
            this.deadband = deadband;
            this.driveTrain.getDiffDrive().setDeadband(this.deadband);
        }

        if (openLoopRampRate != this.openLoopRampRate && this.driveTrain instanceof IOpenLoopRampRate) {
            this.openLoopRampRate = openLoopRampRate;
            IOpenLoopRampRate driveTrain = (IOpenLoopRampRate) this.driveTrain;
            driveTrain.setAllOpenLoopRampRates(this.openLoopRampRate);
        }
    }

    public DriveMode getDriveMode() {
        return this.driveMode;
    }

    public double getSpeedAdjustment() {
        return this.speedAdjustment;
    }

    public double getRotationAdjustment() {
        return this.rotationAdjustment;
    }

    public boolean getSquareInputs() {
        return this.squareInputs;
    }
}

package frc.robot;

import static frc.robot.Constants.DriveTrain.*;
import static frc.robot.Constants.Dashboard.*;
import static frc.robot.Constants.Dashboard.Shuffleboard.*;
import static frc.robot.Constants.DriveWithController.*;

import java.util.Map;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.ComplexWidget;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.DriveWithController.DriveMode;
import frc.robot.drivetrain.DriveTrain;

public class Dashboard {
    public enum DashboardType {
        SmartDashboard, Shuffleboard;
    }

    private DriveTrain driveTrain;
    private ShuffleboardTab shuffleboardTab;
    private SendableChooser<DriveMode> driveModeChooser = new SendableChooser<>();

    private NetworkTableEntry deadbandEntry;
    private NetworkTableEntry openLoopRampRateEntry;
    private NetworkTableEntry speedAdjustmentEntry;
    private NetworkTableEntry rotationAdjustmentEntry;
    private NetworkTableEntry squareInputsEntry;

    private double deadband = DEADBAND;
    private double openLoopRampRate = OPEN_LOOP_RAMP_RATE;
    private DriveMode driveMode = DRIVE_MODE;
    private double speedAdjustment = SPEED_ADJUSTMENT;
    private double rotationAdjustment = ROTATION_ADJUSTMENT;
    private boolean squareInputs = SQUARE_INPUTS;

    public Dashboard(DriveTrain driveTrain) {
        this.driveTrain = driveTrain;
        this.driveModeChooser.setDefaultOption(DRIVE_MODE.toString(), DRIVE_MODE);

        for (DriveMode driveMode : DriveMode.values()) {
            if (driveMode != DRIVE_MODE) {
                this.driveModeChooser.addOption(driveMode.toString(), driveMode);
            }
        }

        switch (DASHBOARD_TYPE) {
            case SmartDashboard:
                SmartDashboard.putNumber("deadband", DEADBAND);
                SmartDashboard.putNumber("open_loop_ramp_rate", OPEN_LOOP_RAMP_RATE);
                SmartDashboard.putData("drive_mode", this.driveModeChooser);
                SmartDashboard.putNumber("speed_adjustment", SPEED_ADJUSTMENT);
                SmartDashboard.putNumber("rotation_adjustment", ROTATION_ADJUSTMENT);
                SmartDashboard.putBoolean("square_inputs", SQUARE_INPUTS);

                this.deadbandEntry = SmartDashboard.getEntry("deadband");
                this.openLoopRampRateEntry = SmartDashboard.getEntry("open_loop_ramp_rate");
                this.speedAdjustmentEntry = SmartDashboard.getEntry("speed_adjustment");
                this.rotationAdjustmentEntry = SmartDashboard.getEntry("rotation_adjustment");
                this.squareInputsEntry = SmartDashboard.getEntry("square_inputs");
                break;
            case Shuffleboard:
                this.shuffleboardTab = Shuffleboard.getTab(TAB_NAME);
                SimpleWidget deadbandWidget = this.shuffleboardTab.add("deadband", DEADBAND);
                SimpleWidget openLoopRampRateWidget = this.shuffleboardTab.add("open_loop_ramp_rate", OPEN_LOOP_RAMP_RATE);
                ComplexWidget driveModeWidget = this.shuffleboardTab.add("drive_mode", this.driveModeChooser);
                SimpleWidget speedAdjustmentWidget = this.shuffleboardTab.add("speed_adjustment", SPEED_ADJUSTMENT);
                SimpleWidget rotationAdjustmentWidget = this.shuffleboardTab.add("rotation_adjustment", ROTATION_ADJUSTMENT);
                SimpleWidget squaredInputsWidget = this.shuffleboardTab.add("square_inputs", SQUARE_INPUTS);

                if (AUTO_SETUP) {
                    deadbandWidget.withPosition(0, 0).withSize(1, 1).withWidget(BuiltInWidgets.kTextView);
                    openLoopRampRateWidget.withPosition(0, 1).withSize(1, 1).withWidget(BuiltInWidgets.kTextView);
                    driveModeWidget.withPosition(0, 2).withSize(2, 1).withWidget(BuiltInWidgets.kComboBoxChooser);
                    speedAdjustmentWidget.withPosition(0, 4).withSize(1, 1)
                    .withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", 1));
                    rotationAdjustmentWidget.withPosition(0, 5).withSize(1, 1)
                    .withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", 1));
                    squaredInputsWidget.withPosition(0, 6).withSize(1, 1).withWidget(BuiltInWidgets.kToggleSwitch);
                }
                
                this.deadbandEntry = deadbandWidget.getEntry();
                this.openLoopRampRateEntry = openLoopRampRateWidget.getEntry();
                this.speedAdjustmentEntry = speedAdjustmentWidget.getEntry();
                this.rotationAdjustmentEntry = rotationAdjustmentWidget.getEntry();
                this.squareInputsEntry = squaredInputsWidget.getEntry();
                break;
            default:
                throw new AssertionError();
        }
    }

    public void execute() {
        double deadband = this.deadbandEntry.getDouble(this.deadband);
        double openLoopRampRate = this.openLoopRampRateEntry.getDouble(this.openLoopRampRate);
        this.driveMode = this.driveModeChooser.getSelected();
        this.speedAdjustment = this.speedAdjustmentEntry.getDouble(this.speedAdjustment);
        this.rotationAdjustment = this.rotationAdjustmentEntry.getDouble(this.rotationAdjustment);
        this.squareInputs = this.squareInputsEntry.getBoolean(this.squareInputs);

        if (deadband != this.deadband) {
            this.deadband = deadband;
            this.driveTrain.getDiffDrive().setDeadband(this.deadband);
        }

        if (openLoopRampRate != this.openLoopRampRate) {
            this.openLoopRampRate = openLoopRampRate;
            Util.convert(this.driveTrain).setAllOpenLoopRampRates(this.openLoopRampRate);
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

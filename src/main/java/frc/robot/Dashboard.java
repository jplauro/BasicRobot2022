package frc.robot;

import static frc.robot.Constants.DriveTrain.*;
import static frc.robot.Constants.Dashboard.*;
import static frc.robot.Constants.Dashboard.Shuffleboard.*;
import static frc.robot.Constants.DriveWithController.*;

import java.util.EnumMap;
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
import frc.robot.drivetrain.impl.CANSparkMaxDriveTrain;

public class Dashboard {
    public enum DashboardType {
        SmartDashboard, Shuffleboard;
    }

    private CANSparkMaxDriveTrain driveTrain;
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

    public Dashboard(CANSparkMaxDriveTrain driveTrain) {
        this.driveTrain = driveTrain;
        this.driveModeChooser.setDefaultOption(DRIVE_MODE.toString(), DRIVE_MODE);

        for (DriveMode driveMode : DriveMode.values()) {
            if (driveMode != DRIVE_MODE) {
                this.driveModeChooser.addOption(driveMode.toString(), driveMode);
            }
        }

        new EnumMap<DashboardType, Runnable>(DashboardType.class) {{
            put(DashboardType.SmartDashboard, () -> {
                SmartDashboard.putNumber("deadband", DEADBAND);
                SmartDashboard.putNumber("open_loop_ramp_rate", OPEN_LOOP_RAMP_RATE);
                SmartDashboard.putData("drive_mode", driveModeChooser);
                SmartDashboard.putNumber("speed_adjustment", SPEED_ADJUSTMENT);
                SmartDashboard.putNumber("rotation_adjustment", ROTATION_ADJUSTMENT);
                SmartDashboard.putBoolean("square_inputs", SQUARE_INPUTS);

                deadbandEntry = SmartDashboard.getEntry("deadband");
                openLoopRampRateEntry = SmartDashboard.getEntry("open_loop_ramp_rate");
                speedAdjustmentEntry = SmartDashboard.getEntry("speed_adjustment");
                rotationAdjustmentEntry = SmartDashboard.getEntry("rotation_adjustment");
                squareInputsEntry = SmartDashboard.getEntry("square_inputs");
            });

            put(DashboardType.Shuffleboard, () -> {
                shuffleboardTab = Shuffleboard.getTab(TAB_NAME);
                SimpleWidget deadbandWidget = shuffleboardTab.add("deadband", DEADBAND);
                SimpleWidget openLoopRampRateWidget = shuffleboardTab.add("open_loop_ramp_rate", OPEN_LOOP_RAMP_RATE);
                ComplexWidget driveModeWidget = shuffleboardTab.add("drive_mode", driveModeChooser);
                SimpleWidget speedAdjustmentWidget = shuffleboardTab.add("speed_adjustment", SPEED_ADJUSTMENT);
                SimpleWidget rotationAdjustmentWidget = shuffleboardTab.add("rotation_adjustment", ROTATION_ADJUSTMENT);
                SimpleWidget squaredInputsWidget = shuffleboardTab.add("square_inputs", SQUARE_INPUTS);

                if (AUTO_SETUP) {
                    deadbandWidget.withPosition(0, 0).withSize(1, 1).withWidget(BuiltInWidgets.kTextView);
                    openLoopRampRateWidget.withPosition(1, 0).withSize(1, 1).withWidget(BuiltInWidgets.kTextView);
                    driveModeWidget.withPosition(2, 0).withSize(2, 1).withWidget(BuiltInWidgets.kComboBoxChooser);
                    speedAdjustmentWidget.withPosition(4, 0).withSize(1, 1)
                    .withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", 1));
                    rotationAdjustmentWidget.withPosition(5, 0).withSize(1, 1)
                    .withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", 1));
                    squaredInputsWidget.withPosition(6, 0).withSize(1, 1).withWidget(BuiltInWidgets.kToggleSwitch);
                }
                
                deadbandEntry = deadbandWidget.getEntry();
                openLoopRampRateEntry = openLoopRampRateWidget.getEntry();
                speedAdjustmentEntry = speedAdjustmentWidget.getEntry();
                rotationAdjustmentEntry = rotationAdjustmentWidget.getEntry();
                squareInputsEntry = squaredInputsWidget.getEntry();
            });
        }}.get(DASHBOARD_TYPE).run();
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
            this.driveTrain.setAllOpenLoopRampRates(this.openLoopRampRate);
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

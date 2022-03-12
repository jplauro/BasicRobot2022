package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import frc.robot.drivetrain.DriveTrain;

public class DriveWithController {
    private DriveTrain driveTrain;
    private XboxController controller;
    private Dashboard dashboard;

    public enum DriveMode {
        ARCADE, CURVATURE, TANK;

        @Override
        public String toString() {
            // Make only the first letter of the name uppercase
            String firstLetter = name().substring(0, 1).toUpperCase();
            return firstLetter + name().substring(1).toLowerCase();
        }
    }

    public DriveWithController(DriveTrain driveTrain, 
    XboxController controller, Dashboard dashboard) {
        this.driveTrain = driveTrain;
        this.controller = controller;
        this.dashboard = dashboard;
    }

    public void execute() {
        double power = this.dashboard.getSquareInputs() ? 2 : 1;
        double leftTriggerInput = Math.pow(this.controller.getLeftTriggerAxis(), power); // Moving backward
        double rightTriggerInput = Math.pow(this.controller.getRightTriggerAxis(), power); // Moving forward
        double leftJoystickXInput = -this.controller.getLeftX(); // Rotating
        double leftJoystickYInput = -this.controller.getLeftY(); // Moving left
        double rightJoystickYInput = -this.controller.getRightY(); // Moving right
        double rotationInput = Math.pow(leftJoystickXInput, power);

        // Keeping the original sign of the input only if squaring
        if (power == 2) rotationInput *= Math.signum(leftJoystickXInput);
        
        double direction = leftTriggerInput > rightTriggerInput ? -leftTriggerInput : rightTriggerInput;
        double speed = direction * this.dashboard.getSpeedAdjustment();
        double leftSpeed = leftJoystickYInput * this.dashboard.getSpeedAdjustment();
        double rightSpeed = rightJoystickYInput * this.dashboard.getSpeedAdjustment();
        double rotation = rotationInput * this.dashboard.getRotationAdjustment();
        
        switch (this.dashboard.getDriveMode()) {
            case ARCADE:
                // Squared inputs is always false to prevent squaring twice
                this.driveTrain.arcadeDrive(speed, rotation, false);
                break;
            case CURVATURE:
                this.driveTrain.curvatureDrive(speed, rotation, !this.controller.getBButton());
                break;
            case TANK:
                this.driveTrain.tankDrive(leftSpeed, rightSpeed, this.dashboard.getSquareInputs());
                break;
        }
    }
}

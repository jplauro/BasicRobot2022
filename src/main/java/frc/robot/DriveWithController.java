package frc.robot;

import static frc.robot.Constants.DriveWithJoystick.*;

import edu.wpi.first.wpilibj.XboxController;
import frc.robot.drivetrain.CANSparkMaxDriveTrain;

public class DriveWithController {
    private CANSparkMaxDriveTrain driveTrain;
    private XboxController controller;

    public enum DriveMode {
        ARCADE, CURVATURE, TANK;
    }

    public DriveWithController(CANSparkMaxDriveTrain driveTrain, XboxController controller) {
        this.driveTrain = driveTrain;
        this.controller = controller;
    }

    public void execute() {
        double power = SQUARE_INPUTS ? 2 : 1;
        double leftTriggerInput = -Math.pow(this.controller.getLeftTriggerAxis(), power); // Moving backward
        double rightTriggerInput = Math.pow(this.controller.getRightTriggerAxis(), power); // Moving forward
        double leftJoystickInput = -this.controller.getLeftY(); // Moving left
        double rightJoystickInput = -this.controller.getRightY(); // Moving right
        double rotationInput = Math.signum(this.controller.getLeftX()) * Math.pow(this.controller.getLeftX(), power);
        
        double speed = Math.max(-leftTriggerInput, rightTriggerInput) * SPEED_ADJUSTMENT;
        double leftSpeed = leftJoystickInput * SPEED_ADJUSTMENT;
        double rightSpeed = rightJoystickInput * SPEED_ADJUSTMENT;
        double rotation = rotationInput * ROTATION_ADJUSTMENT;

        switch (DRIVE_MODE) {
            case ARCADE:
                // Squared inputs is always false to prevent squaring twice
                this.driveTrain.arcadeDrive(speed, rotation, false);
                break;
            case CURVATURE:
                this.driveTrain.curvatureDrive(speed, rotation, !this.controller.getBButton());
                break;
            case TANK:
                this.driveTrain.tankDrive(leftSpeed, rightSpeed, SQUARE_INPUTS);
                break;
        }
    }
}

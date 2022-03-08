package frc.robot;

import edu.wpi.first.wpilibj.XboxController;

import static frc.robot.Constants.DriveWithJoystick.*;

public class DriveWithJoystick {
    private CANSparkMaxDriveTrain driveTrain;
    private XboxController controller;

    public DriveWithJoystick(CANSparkMaxDriveTrain driveTrain, XboxController controller) {
        this.driveTrain = driveTrain;
        this.controller = controller;
    }

    public void execute() {
        double leftTriggerInput = -Math.pow(this.controller.getLeftTriggerAxis(), 2); // Moving backward
        double rightTriggerInput = Math.pow(this.controller.getRightTriggerAxis(), 2); // Moving forward
        double rotationInput = Math.signum(this.controller.getLeftX()) * Math.pow(this.controller.getLeftX(), 2);

        double speed = Math.max(-leftTriggerInput, rightTriggerInput) * SPEED_ADJUSTMENT;
        double rotation = rotationInput * ROTATION_ADJUSTMENT;

        this.driveTrain.drive(speed, rotation, !this.controller.getBButton());
    }
}
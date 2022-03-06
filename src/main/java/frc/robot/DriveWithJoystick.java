package frc.robot;

import edu.wpi.first.wpilibj.XboxController;

import static frc.robot.Constants.DriveWithJoystick.*;

public class DriveWithJoystick {
    private DriveTrain driveTrain;
    private XboxController controller;

    public DriveWithJoystick(DriveTrain driveTrain, XboxController controller) {
        this.driveTrain = driveTrain;
        this.controller = controller;
    }

    public void execute() {
        double leftTriggerInput = Math.pow(this.controller.getLeftTriggerAxis(), 2); // Moving backward
        double rightTriggerInput = Math.pow(this.controller.getRightTriggerAxis(), 2); // Moving forward
        double rotationInput = Math.signum(this.controller.getLeftX()) * Math.pow(this.controller.getLeftX(), 2);

        double direction = Math.max(leftTriggerInput, rightTriggerInput);
        double sign = leftTriggerInput > rightTriggerInput ? -1 : 1;
        double speed = direction * sign * SPEED_ADJUSTMENT;
        double rotation = rotationInput * ROTATION_ADJUSTMENT;

        this.driveTrain.move(speed, rotation, !this.controller.getBButton());
    }
}
package frc.robot;

import edu.wpi.first.wpilibj.XboxController;

public class DriveWithJoystick {
    private DriveTrain driveTrain;
    private XboxController controller;

    public DriveWithJoystick(DriveTrain driveTrain, XboxController controller) {
        this.driveTrain = driveTrain;
        this.controller = controller;
    }

    public void execute() {
        double rightTriggerInput = Math.pow(this.controller.getRightTriggerAxis(), 2.0);
        double leftTriggerInput = Math.pow(this.controller.getLeftTriggerAxis(), 2.0);
        double rotationInput = (this.controller.getLeftX() >= 0.0 ? 1.0 : -1.0) * Math.pow(this.controller.getLeftX(), 2.0);
        
        double speed = 0.0;
        double rotation = rotationInput * Constants.ROTATION;

        if (rightTriggerInput > leftTriggerInput) { // Moving forward
            speed = rightTriggerInput * Constants.SPEED;
        } else if (rightTriggerInput < leftTriggerInput) { // Moving backward
            speed = leftTriggerInput * -Constants.SPEED;
        }

        this.driveTrain.curvatureInput(speed, rotation, !this.controller.getBButton());
    }
}
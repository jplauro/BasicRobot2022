package frc.robot.drivetrain;

import static frc.robot.Constants.DriveTrain.MotorIDs.*;

import edu.wpi.first.wpilibj.motorcontrol.MotorController;

public enum Motor {
    FRONT_LEFT(FRONT_LEFT_MOTOR_ID),
    REAR_LEFT(REAR_LEFT_MOTOR_ID),
    FRONT_RIGHT(FRONT_RIGHT_MOTOR_ID),
    REAR_RIGHT(REAR_RIGHT_MOTOR_ID);

    public final int ID;
    public MotorController MOTOR = null;

    private Motor(int ID) {
        this.ID = ID;
    }
}
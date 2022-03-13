package frc.robot.drivetrain;

import static frc.robot.Constants.DriveTrain.*;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;

public abstract class DriveTrain {
    private DifferentialDrive diffDrive;
    private MotorControllerGroup leftMotors, rightMotors;

    public enum DriveTrainMode {
        GROUP, FOLLOW;
    }

    public enum MotorGroup {
        LEFT, RIGHT;
    }

    public DriveTrain(MotorController frontLeftMotor, MotorController rearLeftMotor,
    MotorController frontRightMotor, MotorController rearRightMotor) {
        Motor.FRONT_LEFT.MOTOR = frontLeftMotor;
        Motor.REAR_LEFT.MOTOR = rearLeftMotor;
        Motor.FRONT_RIGHT.MOTOR = frontRightMotor;
        Motor.REAR_RIGHT.MOTOR = rearRightMotor;

        this.leftMotors = new MotorControllerGroup(frontLeftMotor, rearLeftMotor);
        this.rightMotors = new MotorControllerGroup(frontRightMotor, rearRightMotor);

        switch (DRIVE_TRAIN_MODE) {
            case GROUP:
                this.diffDrive = new DifferentialDrive(this.leftMotors, this.rightMotors);
                this.rightMotors.setInverted(true);
                break;
            case FOLLOW:
                this.diffDrive = new DifferentialDrive(frontLeftMotor, frontRightMotor);
                frontRightMotor.setInverted(true);
                break;
            default:
                throw new AssertionError();
        }

        this.diffDrive.setDeadband(DEADBAND);
    }

    public DifferentialDrive getDiffDrive() {
        return this.diffDrive;
    }

    public MotorControllerGroup getMotorGroup(MotorGroup motorGroup) {
        switch (motorGroup) {
            case LEFT:
                return this.leftMotors;
            case RIGHT:
                return this.rightMotors;
            default:
                throw new AssertionError();
        }
    }

    public abstract MotorController getMotor(Motor motor);

    public double getSpeed(Motor motor) {
        return this.getMotor(motor).get();
    }

    public void setSpeed(Motor motor, double speed) {
        // Ensures that the speed stays between -1 and 1
        speed = MathUtil.clamp(speed, -1, 1);
        this.getMotor(motor).set(speed);
    }

    public void setAllSpeeds(double speed) {
        for (Motor motor : Motor.values()) {
            this.setSpeed(motor, speed);
        }
    }

    public void arcadeDrive(double speed, double rotation, boolean squareInputs) {
        this.diffDrive.arcadeDrive(speed, rotation);
    }

    public void curvatureDrive(double speed, double rotation, boolean allowTurnInPlace) {
        this.diffDrive.curvatureDrive(speed, rotation, allowTurnInPlace);
    }

    public void tankDrive(double leftSpeed, double rightSpeed, boolean squareInputs) {
        this.diffDrive.tankDrive(leftSpeed, rightSpeed, squareInputs);
    }
}

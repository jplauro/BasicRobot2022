package frc.robot.drivetrain;

import static frc.robot.Constants.DriveTrain.*;

import java.util.EnumMap;
import java.util.Map;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;

public abstract class DriveTrain {
    private DifferentialDrive diffDrive;
    private MotorControllerGroup leftMotors, rightMotors;
    private Map<Motor, MotorController> motorControllers = new EnumMap<>(Motor.class);

    public enum ControlMode {
        GROUP, FOLLOW;
    }

    public enum Motor {
        FRONT_LEFT, REAR_LEFT, FRONT_RIGHT, REAR_RIGHT;
    }

    public enum MotorGroup {
        LEFT, RIGHT;
    }

    public DriveTrain(MotorController frontLeftMotor, MotorController rearLeftMotor,
    MotorController frontRightMotor, MotorController rearRightMotor) {
        this.motorControllers.putAll(Map.of(
            Motor.FRONT_LEFT, frontLeftMotor,
            Motor.REAR_LEFT, rearLeftMotor,
            Motor.FRONT_RIGHT, frontRightMotor,
            Motor.REAR_RIGHT, rearRightMotor
        ));

        this.leftMotors = new MotorControllerGroup(frontLeftMotor, rearLeftMotor);
        this.rightMotors = new MotorControllerGroup(frontRightMotor, rearRightMotor);

        new EnumMap<ControlMode, Runnable>(ControlMode.class) {{
            put(ControlMode.GROUP, () -> {
                diffDrive = new DifferentialDrive(leftMotors, rightMotors);
                rightMotors.setInverted(true);
            });
            
            put(ControlMode.FOLLOW, () -> {
                diffDrive = new DifferentialDrive(frontLeftMotor, frontRightMotor);
                frontRightMotor.setInverted(true);
            });
        }}.get(CONTROL_MODE).run();

        this.diffDrive.setDeadband(DEADBAND);
    }

    public DifferentialDrive getDiffDrive() {
        return this.diffDrive;
    }

    public MotorControllerGroup getMotorGroup(MotorGroup motorGroup) {
        return new EnumMap<MotorGroup, MotorControllerGroup>(MotorGroup.class) {{
            put(MotorGroup.LEFT, leftMotors);
            put(MotorGroup.RIGHT, rightMotors);
        }}.get(motorGroup);
    }

    protected Map<Motor, MotorController> getMotorControllers() {
        return this.motorControllers;
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

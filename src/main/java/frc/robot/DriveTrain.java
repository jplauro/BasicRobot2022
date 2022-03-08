package frc.robot;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;

import static frc.robot.Constants.DriveTrain.*;
import static frc.robot.Constants.DriveTrain.MotorIDs.*;

public abstract class DriveTrain {
    public DifferentialDrive diffDrive;
    public MotorController frontLeftMotor, rearLeftMotor, frontRightMotor, rearRightMotor;
    public MotorControllerGroup leftMotors, rightMotors;
    public List<MotorController> motors = new ArrayList<>();

    public enum Motors {
        FRONT_LEFT(FRONT_LEFT_MOTOR_ID),
        REAR_LEFT(REAR_LEFT_MOTOR_ID),
        FRONT_RIGHT(FRONT_RIGHT_MOTOR_ID),
        REAR_RIGHT(REAR_RIGHT_MOTOR_ID);

        public final int ID;

        private Motors(int ID) {
            this.ID = ID;
        }
    }

    public DriveTrain(MotorController frontLeftMotor, MotorController rearLeftMotor,
    MotorController frontRightMotor, MotorController rearRightMotor) {
        this.frontLeftMotor = frontLeftMotor;
        this.rearLeftMotor = rearLeftMotor;
        this.frontRightMotor = frontLeftMotor;
        this.rearRightMotor = rearRightMotor;

        this.motors.addAll(List.of(
            this.frontLeftMotor, 
            this.rearLeftMotor,
            this.frontRightMotor, 
            this.rearRightMotor
        ));
        
        this.leftMotors = new MotorControllerGroup(this.frontLeftMotor, this.rearLeftMotor);
        this.rightMotors = new MotorControllerGroup(this.frontRightMotor, this.rearRightMotor);

        this.diffDrive = new DifferentialDrive(this.leftMotors, this.rightMotors);
        this.diffDrive.setDeadband(DEADBAND);
        this.rightMotors.setInverted(true);
    }

    public abstract MotorController getMotor(Motors motor);

    public double getSpeed(Motors motor) {
        return this.getMotor(motor).get();
    }

    public void setSpeed(Motors motor, double speed) {
        // Ensures that the speed stays between -1 and 1
        speed = MathUtil.clamp(speed, -1, 1);
        this.getMotor(motor).set(speed);
    }

    public void setAllSpeeds(double speed) {
        for (Motors motor : Motors.values()) {
            this.setSpeed(motor, speed);
        }
    }

    public void drive(double speed, double rotation, boolean allowTurnInPlace) {
        this.diffDrive.curvatureDrive(speed, rotation, allowTurnInPlace);
    }
}
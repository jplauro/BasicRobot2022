package frc.robot;

import java.util.ArrayList;
import java.util.List;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;

import static frc.robot.Constants.DriveTrain.*;
import static frc.robot.Constants.DriveTrain.MotorIDs.*;

public class DriveTrain {
    private DifferentialDrive diffDrive;
    private CANSparkMax frontLeftMotor, rearLeftMotor, frontRightMotor, rearRightMotor;
    private MotorControllerGroup leftMotors, rightMotors;
    private List<Integer> motorIDs = List.of(FRONT_LEFT_MOTOR_ID, 
    REAR_LEFT_MOTOR_ID, FRONT_RIGHT_MOTOR_ID, REAR_RIGHT_MOTOR_ID);
    private List<CANSparkMax> motors = new ArrayList<>();
    private List<RelativeEncoder> encoders = new ArrayList<>();

    public DriveTrain() {
        this.frontLeftMotor = new CANSparkMax(FRONT_LEFT_MOTOR_ID, MOTOR_TYPE);
        this.rearLeftMotor = new CANSparkMax(REAR_LEFT_MOTOR_ID, MOTOR_TYPE);
        this.leftMotors = new MotorControllerGroup(this.frontLeftMotor, this.rearLeftMotor);

        this.frontRightMotor = new CANSparkMax(FRONT_RIGHT_MOTOR_ID, MOTOR_TYPE);
        this.rearRightMotor = new CANSparkMax(REAR_RIGHT_MOTOR_ID, MOTOR_TYPE);
        this.rightMotors = new MotorControllerGroup(this.frontRightMotor, this.rearRightMotor);

        this.motors.addAll(List.of(this.frontLeftMotor, this.rearLeftMotor,
        this.frontRightMotor, this.rearRightMotor));

        for (CANSparkMax motor : this.motors) {
            motor.restoreFactoryDefaults();
            motor.setIdleMode(IDLE_MODE);
            motor.setOpenLoopRampRate(OPEN_LOOP_RAMP_RATE);
            motor.setSmartCurrentLimit(CURRENT_LIMIT);
            this.encoders.add(motor.getEncoder());
        }

        this.diffDrive = new DifferentialDrive(this.leftMotors, this.rightMotors);
        this.diffDrive.setDeadband(DEADBAND);
        this.rightMotors.setInverted(true);
    }

    public CANSparkMax getMotor(int motorID) {
        if (this.motorIDs.contains(motorID)) {
            return this.motors.get(motorID - 1);
        } else return null;
    }

    public double getSpeed(int motorID) {
        CANSparkMax motor = this.getMotor(motorID);
        if (motor != null) {
            return motor.get();
        } else return Double.NaN;
    }

    public void setSpeed(int motorID, double speed) {
        // Ensures that the speed stays between -1 and 1
        speed = MathUtil.clamp(speed, -1, 1);
        CANSparkMax motor = this.getMotor(motorID);
        if (motor != null) {
            motor.set(speed);
        }
    }

    public void setAllSpeeds(double speed) {
        for (int motorID : this.motorIDs) {
            this.setSpeed(motorID, speed);
        }
    }

    public void move(double speed, double rotation, boolean allowTurnInPlace) {
        this.diffDrive.curvatureDrive(speed, rotation, allowTurnInPlace);
    }

    public IdleMode getMode(int motorID) {
        CANSparkMax motor = this.getMotor(motorID);
        if (motor != null) {
            return motor.getIdleMode();
        } else return null;
    }

    public void setMode(int motorID, IdleMode mode) {
        CANSparkMax motor = this.getMotor(motorID);
        if (motor != null) {
            motor.setIdleMode(mode);
        }
    }

    public void setAllModes(IdleMode mode) {
        for (int motorID : this.motorIDs) {
            this.setMode(motorID, mode);
        }
    }

    public double getOpenLoopRampRate(int motorID) {
        CANSparkMax motor = this.getMotor(motorID);
        if (motor != null) {
            return motor.getOpenLoopRampRate();
        } else return Double.NaN;
    }

    public void setOpenLoopRampRate(int motorID, double rampRate) {
        CANSparkMax motor = this.getMotor(motorID);
        if (motor != null) {
            motor.setOpenLoopRampRate(rampRate);
        }
    }

    public void setAllOpenLoopRampRates(double rampRate) {
        for (int motorID : this.motorIDs) {
            this.setOpenLoopRampRate(motorID, rampRate);
        }
    }

    public RelativeEncoder getEncoder(int motorID) {
        if (this.motorIDs.contains(motorID)) {
            return this.encoders.get(motorID - 1);
        } else return null;
    }

    public double getEncoderPosition(int motorID) {
        RelativeEncoder encoder = this.getEncoder(motorID);
        if (encoder != null) {
            return encoder.getPosition();
        } else return Double.NaN;
    }

    public void setEncoderPosition(int motorID, double position) {
        RelativeEncoder encoder = this.getEncoder(motorID);
        if (encoder != null) {
            encoder.setPosition(position);
        }
    }

    public void setAllEncoderPositions(double position) {
        for (int motorID : this.motorIDs) {
            this.setEncoderPosition(motorID, position);
        }
    }
}
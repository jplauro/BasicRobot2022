package frc.robot;

import java.util.ArrayList;
import java.util.List;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class DriveTrain {
    private DifferentialDrive diffDrive;
    private CANSparkMax frontLeftMotor, rearLeftMotor, frontRightMotor, rearRightMotor;
    private List<Integer> motorIDs = List.of(Constants.FRONT_LEFT_MOTOR_ID, 
    Constants.REAR_LEFT_MOTOR_ID, Constants.FRONT_RIGHT_MOTOR_ID, Constants.REAR_RIGHT_MOTOR_ID);
    private List<CANSparkMax> motors = new ArrayList<>();
    private List<RelativeEncoder> encoders = new ArrayList<>();

    public DriveTrain() {
        this.frontLeftMotor = new CANSparkMax(Constants.FRONT_LEFT_MOTOR_ID, Constants.MOTOR_TYPE);
        this.rearLeftMotor = new CANSparkMax(Constants.REAR_LEFT_MOTOR_ID, Constants.MOTOR_TYPE);
        this.frontRightMotor = new CANSparkMax(Constants.FRONT_RIGHT_MOTOR_ID, Constants.MOTOR_TYPE);
        this.rearRightMotor = new CANSparkMax(Constants.REAR_RIGHT_MOTOR_ID, Constants.MOTOR_TYPE);
        this.motors.addAll(List.of(frontLeftMotor, rearLeftMotor, frontRightMotor, rearRightMotor));

        for (CANSparkMax motor : this.motors) {
            motor.restoreFactoryDefaults();
            motor.setIdleMode(Constants.IDLE_MODE);
            motor.setOpenLoopRampRate(Constants.OPEN_LOOP_RAMP_RATE);
            motor.setSmartCurrentLimit(Constants.CURRENT_LIMIT);
            this.encoders.add(motor.getEncoder());
        }

        this.rearLeftMotor.follow(frontLeftMotor);
        this.rearRightMotor.follow(frontRightMotor);

        this.diffDrive = new DifferentialDrive(this.frontRightMotor, this.frontLeftMotor);
        this.diffDrive.setDeadband(Constants.DEADBAND);
        this.frontLeftMotor.setInverted(true);
    }

    public CANSparkMax getMotor(int motorID) {
        if (this.motorIDs.contains(motorID)) {
            return this.motors.get(motorID - 1);
        } else {
            return null;
        }
    }

    public double getSpeed(int motorID) {
        CANSparkMax motor = this.getMotor(motorID);
        if (motor != null) {
            return motor.get();
        } else {
            return 0.0;
        }
    }

    public void setSpeed(int motorID, double speed) {
        // Make sure the speed doesn't go above 1.0 or below -1.0
        speed = speed > 1.0 ? 1.0 : (speed < -1.0 ? -1.0 : speed);
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

    public void curvatureInput(double speed, double rotation, boolean allowTurnInPlace) {
        this.diffDrive.curvatureDrive(speed, rotation, allowTurnInPlace);
    }

    public IdleMode getMode(int motorID) {
        CANSparkMax motor = this.getMotor(motorID);
        if (motor != null) {
            return motor.getIdleMode();
        } else {
            return null;
        }
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
        } else {
            return 0.0;
        }
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
        } else {
            return null;
        }
    }

    public double getEncoderPosition(int motorID) {
        RelativeEncoder encoder = this.getEncoder(motorID);
        if (encoder != null) {
            return encoder.getPosition();
        } else {
            return 0.0;
        }
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
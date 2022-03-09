package frc.robot;

import static frc.robot.Constants.DriveTrain.CANSparkMaxDriveTrain.*;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;

public class CANSparkMaxDriveTrain extends DriveTrain {
    private enum CANSparkMaxMotor {
        FRONT_LEFT(Motor.FRONT_LEFT),
        REAR_LEFT(Motor.REAR_LEFT),
        FRONT_RIGHT(Motor.FRONT_RIGHT),
        REAR_RIGHT(Motor.REAR_RIGHT);

        private final CANSparkMax MOTOR;
        private final RelativeEncoder ENCODER;

        private CANSparkMaxMotor(Motor motor) {
            this.MOTOR = (CANSparkMax) motor.MOTOR;
            this.ENCODER = this.MOTOR.getEncoder();
        }
    }

    public CANSparkMaxDriveTrain() {
        super(
            new CANSparkMax(Motor.FRONT_LEFT.ID, MOTOR_TYPE), 
            new CANSparkMax(Motor.REAR_LEFT.ID, MOTOR_TYPE), 
            new CANSparkMax(Motor.FRONT_RIGHT.ID, MOTOR_TYPE), 
            new CANSparkMax(Motor.REAR_RIGHT.ID, MOTOR_TYPE)
        );

        for (CANSparkMaxMotor motor : CANSparkMaxMotor.values()) {
            motor.MOTOR.restoreFactoryDefaults();
            motor.MOTOR.setIdleMode(IDLE_MODE);
            motor.MOTOR.setOpenLoopRampRate(OPEN_LOOP_RAMP_RATE);
            motor.MOTOR.setSmartCurrentLimit(CURRENT_LIMIT);
        }
    }

    public CANSparkMax getMotor(Motor motor) {
        return CANSparkMaxMotor.valueOf(motor.name()).MOTOR;
    }

    public IdleMode getMode(Motor motor) {
        return this.getMotor(motor).getIdleMode();
    }

    public void setMode(Motor motor, IdleMode mode) {
        this.getMotor(motor).setIdleMode(mode);
    }

    public void setAllModes(IdleMode mode) {
        for (Motor motor : Motor.values()) {
            this.setMode(motor, mode);
        }
    }

    public double getOpenLoopRampRate(Motor motor) {
        return this.getMotor(motor).getOpenLoopRampRate();
    }

    public void setOpenLoopRampRate(Motor motor, double rampRate) {
        this.getMotor(motor).setOpenLoopRampRate(rampRate);
    }

    public void setAllOpenLoopRampRates(double rampRate) {
        for (Motor motor : Motor.values()) {
            this.setOpenLoopRampRate(motor, rampRate);
        }
    }

    public RelativeEncoder getEncoder(Motor motor) {
        return CANSparkMaxMotor.valueOf(motor.name()).ENCODER;
    }

    public double getEncoderPosition(Motor motor) {
        return this.getEncoder(motor).getPosition();
    }

    public void setEncoderPosition(Motor motor, double position) {
        this.getEncoder(motor).setPosition(position);
    }

    public void setAllEncoderPositions(double position) {
        for (Motor motor : Motor.values()) {
            this.setEncoderPosition(motor, position);
        }
    }
}
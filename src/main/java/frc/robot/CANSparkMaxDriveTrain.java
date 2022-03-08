package frc.robot;

import java.util.ArrayList;
import java.util.List;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;

import static frc.robot.Constants.DriveTrain.*;

public class CANSparkMaxDriveTrain extends DriveTrain {
    private List<RelativeEncoder> encoders = new ArrayList<>();

    public CANSparkMaxDriveTrain() {
        super(
            new CANSparkMax(Motors.FRONT_LEFT.ID, MOTOR_TYPE), 
            new CANSparkMax(Motors.REAR_LEFT.ID, MOTOR_TYPE), 
            new CANSparkMax(Motors.FRONT_RIGHT.ID, MOTOR_TYPE), 
            new CANSparkMax(Motors.REAR_RIGHT.ID, MOTOR_TYPE)
        );

        for (Motors motor : Motors.values()) {
            CANSparkMax canSparkMaxMotor = this.getMotor(motor);
            canSparkMaxMotor.restoreFactoryDefaults();
            canSparkMaxMotor.setIdleMode(IDLE_MODE);
            canSparkMaxMotor.setOpenLoopRampRate(OPEN_LOOP_RAMP_RATE);
            canSparkMaxMotor.setSmartCurrentLimit(CURRENT_LIMIT);
            this.encoders.add(canSparkMaxMotor.getEncoder());
        }
    }

    public CANSparkMax getMotor(Motors motor) {
        return (CANSparkMax) this.motors.get(motor.ID - 1);
    }

    public IdleMode getMode(Motors motor) {
        return this.getMotor(motor).getIdleMode();
    }

    public void setMode(Motors motor, IdleMode mode) {
        this.getMotor(motor).setIdleMode(mode);
    }

    public void setAllModes(IdleMode mode) {
        for (Motors motor : Motors.values()) {
            this.setMode(motor, mode);
        }
    }

    public double getOpenLoopRampRate(Motors motor) {
        return this.getMotor(motor).getOpenLoopRampRate();
    }

    public void setOpenLoopRampRate(Motors motor, double rampRate) {
        this.getMotor(motor).setOpenLoopRampRate(rampRate);
    }

    public void setAllOpenLoopRampRates(double rampRate) {
        for (Motors motor : Motors.values()) {
            this.setOpenLoopRampRate(motor, rampRate);
        }
    }

    public RelativeEncoder getEncoder(Motors motor) {
        return this.encoders.get(motor.ID - 1);
    }

    public double getEncoderPosition(Motors motor) {
        return this.getEncoder(motor).getPosition();
    }

    public void setEncoderPosition(Motors motor, double position) {
        this.getEncoder(motor).setPosition(position);
    }

    public void setAllEncoderPositions(double position) {
        for (Motors motor : Motors.values()) {
            this.setEncoderPosition(motor, position);
        }
    }
}
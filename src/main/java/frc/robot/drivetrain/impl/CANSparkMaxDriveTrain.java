package frc.robot.drivetrain.impl;

import static frc.robot.Constants.DriveTrain.*;
import static frc.robot.Constants.DriveTrain.MotorIDs.*;
import static frc.robot.Constants.DriveTrain.CANSparkMax.*;

import java.util.EnumMap;
import java.util.Map;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;

import frc.robot.drivetrain.DriveTrain;
import frc.robot.drivetrain.MotorMode;

public class CANSparkMaxDriveTrain extends DriveTrain {
    private Map<Motor, CANSparkMax> canSparkMaxMotors = new EnumMap<>(Motor.class);
    private Map<Motor, RelativeEncoder> canSparkMaxEncoders = new EnumMap<>(Motor.class);

    public CANSparkMaxDriveTrain() {
        super(
            new CANSparkMax(FRONT_LEFT_MOTOR_ID, MOTOR_TYPE), 
            new CANSparkMax(REAR_LEFT_MOTOR_ID, MOTOR_TYPE), 
            new CANSparkMax(FRONT_RIGHT_MOTOR_ID, MOTOR_TYPE), 
            new CANSparkMax(REAR_RIGHT_MOTOR_ID, MOTOR_TYPE)
        );

        for (Motor motor : Motor.values()) {
            CANSparkMax canSparkMaxMotor = (CANSparkMax) this.getMotorControllers().get(motor);
            this.canSparkMaxMotors.put(motor, canSparkMaxMotor);
            this.canSparkMaxEncoders.put(motor, canSparkMaxMotor.getEncoder());
            canSparkMaxMotor.restoreFactoryDefaults();
            canSparkMaxMotor.setIdleMode(IDLE_MODE.getIdleMode());
            canSparkMaxMotor.setOpenLoopRampRate(OPEN_LOOP_RAMP_RATE);
            canSparkMaxMotor.setSmartCurrentLimit(CURRENT_LIMIT);
        }

        if (CONTROL_MODE == ControlMode.FOLLOW) {
            this.canSparkMaxMotors.get(Motor.REAR_LEFT).follow(canSparkMaxMotors.get(Motor.FRONT_LEFT));
            this.canSparkMaxMotors.get(Motor.REAR_RIGHT).follow(canSparkMaxMotors.get(Motor.FRONT_RIGHT));
        }
    }

    @Override
    public CANSparkMax getMotor(Motor motor) {
        return this.canSparkMaxMotors.get(motor);
    }

    public MotorMode getMode(Motor motor) {
        return new EnumMap<IdleMode, MotorMode>(IdleMode.class) {{
            put(IdleMode.kBrake, MotorMode.BRAKE);
            put(IdleMode.kCoast, MotorMode.COAST);
        }}.get(this.getMotor(motor).getIdleMode());
    }

    public void setMode(Motor motor, MotorMode mode) {
        this.getMotor(motor).setIdleMode(mode.getIdleMode());
    }

    public void setAllModes(MotorMode mode) {
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
        return this.canSparkMaxEncoders.get(motor);
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

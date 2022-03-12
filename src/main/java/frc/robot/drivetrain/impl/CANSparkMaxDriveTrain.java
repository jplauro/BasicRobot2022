package frc.robot.drivetrain.impl;

import static frc.robot.Constants.DriveTrain.*;
import static frc.robot.Constants.DriveTrain.CANSparkMaxDriveTrain.*;

import java.util.EnumMap;
import java.util.Map;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import frc.robot.drivetrain.DriveTrain;
import frc.robot.drivetrain.Motor;
import frc.robot.drivetrain.MotorMode;
import frc.robot.drivetrain.DriveTrainInterfaces.IEncoder;
import frc.robot.drivetrain.DriveTrainInterfaces.IMotor;
import frc.robot.drivetrain.DriveTrainInterfaces.IMotorMode;
import frc.robot.drivetrain.DriveTrainInterfaces.IOpenLoopRampRate;

public class CANSparkMaxDriveTrain extends DriveTrain implements IMotorMode, IOpenLoopRampRate, IEncoder {
    private class CANSparkMaxMotor implements IMotor {
        private final CANSparkMax motor;
        private final RelativeEncoder encoder;

        public CANSparkMaxMotor(MotorController motor) {
            this.motor = (CANSparkMax) motor;
            this.encoder = this.motor.getEncoder();
        }

        public CANSparkMax getMotor() {
            return this.motor;
        }

        public RelativeEncoder getEncoder() {
            return this.encoder;
        }
    }

    private Map<Motor, CANSparkMaxMotor> canSparkMaxMotors = new EnumMap<>(Motor.class);

    public CANSparkMaxDriveTrain() {
        super(
            new CANSparkMax(Motor.FRONT_LEFT.ID, MOTOR_TYPE), 
            new CANSparkMax(Motor.REAR_LEFT.ID, MOTOR_TYPE), 
            new CANSparkMax(Motor.FRONT_RIGHT.ID, MOTOR_TYPE), 
            new CANSparkMax(Motor.REAR_RIGHT.ID, MOTOR_TYPE)
        );

        for (Motor motor : Motor.values()) {
            CANSparkMaxMotor canSparkMaxMotor = new CANSparkMaxMotor(motor.MOTOR);
            canSparkMaxMotors.put(motor, canSparkMaxMotor);
            canSparkMaxMotor.getMotor().restoreFactoryDefaults();
            canSparkMaxMotor.getMotor().setIdleMode(IDLE_MODE.getIdleMode());
            canSparkMaxMotor.getMotor().setOpenLoopRampRate(OPEN_LOOP_RAMP_RATE);
            canSparkMaxMotor.getMotor().setSmartCurrentLimit(CURRENT_LIMIT);
        }
    }
    
    private CANSparkMaxMotor getCANSparkMaxMotor(Motor motor) {
        return this.canSparkMaxMotors.get(motor);
    }

    @Override
    public CANSparkMax getMotor(Motor motor) {
        return this.getCANSparkMaxMotor(motor).getMotor();
    }

    @Override
    public MotorMode getMode(Motor motor) {
        switch (this.getMotor(motor).getIdleMode()) {
            case kBrake:
                return MotorMode.BRAKE;
            default:
                return MotorMode.COAST;
        }
    }

    @Override
    public void setMode(Motor motor, MotorMode mode) {
        this.getMotor(motor).setIdleMode(mode.getIdleMode());
    }

    @Override
    public void setAllModes(MotorMode mode) {
        for (Motor motor : Motor.values()) {
            this.setMode(motor, mode);
        }
    }

    public double getOpenLoopRampRate(Motor motor) {
        return this.getMotor(motor).getOpenLoopRampRate();
    }

    @Override
    public void setOpenLoopRampRate(Motor motor, double rampRate) {
        this.getMotor(motor).setOpenLoopRampRate(rampRate);
    }

    @Override
    public void setAllOpenLoopRampRates(double rampRate) {
        for (Motor motor : Motor.values()) {
            this.setOpenLoopRampRate(motor, rampRate);
        }
    }

    public RelativeEncoder getEncoder(Motor motor) {
        return this.getCANSparkMaxMotor(motor).getEncoder();
    }

    @Override
    public double getEncoderPosition(Motor motor) {
        return this.getEncoder(motor).getPosition();
    }

    @Override
    public void setEncoderPosition(Motor motor, double position) {
        this.getEncoder(motor).setPosition(position);
    }

    @Override
    public void setAllEncoderPositions(double position) {
        for (Motor motor : Motor.values()) {
            this.setEncoderPosition(motor, position);
        }
    }
}

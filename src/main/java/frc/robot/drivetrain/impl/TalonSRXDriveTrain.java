package frc.robot.drivetrain.impl;

import static frc.robot.Constants.DriveTrain.*;
import static frc.robot.Constants.DriveTrain.MotorIDs.*;

import java.util.EnumMap;
import java.util.Map;

import com.ctre.phoenix.motorcontrol.SensorCollection;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import frc.robot.drivetrain.PhoenixDriveTrain;

public class TalonSRXDriveTrain extends PhoenixDriveTrain {
    private Map<Motor, TalonSRX> talonSRXMotors = new EnumMap<>(Motor.class);
    private Map<Motor, SensorCollection> talonSRXSensors = new EnumMap<>(Motor.class);

    public TalonSRXDriveTrain() {
        super(
            new TalonSRX(FRONT_LEFT_MOTOR_ID), 
            new TalonSRX(REAR_LEFT_MOTOR_ID), 
            new TalonSRX(FRONT_RIGHT_MOTOR_ID), 
            new TalonSRX(REAR_RIGHT_MOTOR_ID)
        );

        for (Motor motor : Motor.values()) {
            TalonSRX talonSRXMotor = (TalonSRX) this.getPhoenixMotors().get(motor);
            this.talonSRXMotors.put(motor, talonSRXMotor);
            this.talonSRXSensors.put(motor, talonSRXMotor.getSensorCollection());
            talonSRXMotor.configContinuousCurrentLimit(CURRENT_LIMIT);
        }
    }
    
    @Override
    public TalonSRX getPhoenixMotor(Motor motor) {
        return this.talonSRXMotors.get(motor);
    }

    public SensorCollection getSensors(Motor motor) {
        return this.talonSRXSensors.get(motor);
    }

    public double getEncoderPosition(Motor motor) {
        return this.getSensors(motor).getQuadraturePosition();
    }

    public void setEncoderPosition(Motor motor, double position) {
        this.getSensors(motor).setQuadraturePosition((int) position, 0);
    }

    public void setAllEncoderPositions(double position) {
        for (Motor motor : Motor.values()) {
            this.setEncoderPosition(motor, position);
        }
    }
}

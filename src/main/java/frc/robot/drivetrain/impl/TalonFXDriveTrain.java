package frc.robot.drivetrain.impl;

import static frc.robot.Constants.DriveTrain.MotorIDs.*;

import java.util.EnumMap;
import java.util.Map;

import com.ctre.phoenix.motorcontrol.TalonFXSensorCollection;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import frc.robot.drivetrain.PhoenixDriveTrain;

public class TalonFXDriveTrain extends PhoenixDriveTrain {
    private Map<Motor, TalonFX> talonFXMotors = new EnumMap<>(Motor.class);
    private Map<Motor, TalonFXSensorCollection> talonFXSensors = new EnumMap<>(Motor.class);

    public TalonFXDriveTrain() {
        super(
            new TalonFX(FRONT_LEFT_MOTOR_ID), 
            new TalonFX(REAR_LEFT_MOTOR_ID), 
            new TalonFX(FRONT_RIGHT_MOTOR_ID), 
            new TalonFX(REAR_RIGHT_MOTOR_ID)
        );

        for (Motor motor : Motor.values()) {
            TalonFX talonFXMotor = (TalonFX) this.getPhoenixMotors().get(motor);
            this.talonFXMotors.put(motor, talonFXMotor);
            this.talonFXSensors.put(motor, talonFXMotor.getSensorCollection());
        }
    }

    @Override
    public TalonFX getPhoenixMotor(Motor motor) {
        return this.talonFXMotors.get(motor);
    }

    public TalonFXSensorCollection getSensors(Motor motor) {
        return this.talonFXSensors.get(motor);
    }

    public double getEncoderPosition(Motor motor) {
        return this.getSensors(motor).getIntegratedSensorPosition();
    }

    public void setEncoderPosition(Motor motor, double position) {
        this.getSensors(motor).setIntegratedSensorPosition(position, 0);
    }

    public void setAllEncoderPositions(double position) {
        for (Motor motor : Motor.values()) {
            this.setEncoderPosition(motor, position);
        }
    }
}

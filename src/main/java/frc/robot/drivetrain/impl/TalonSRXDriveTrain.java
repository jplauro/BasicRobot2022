package frc.robot.drivetrain.impl;

import static frc.robot.Constants.DriveTrain.*;

import java.util.EnumMap;
import java.util.Map;

import com.ctre.phoenix.motorcontrol.SensorCollection;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import frc.robot.drivetrain.Motor;
import frc.robot.drivetrain.PhoenixDriveTrain;
import frc.robot.drivetrain.DriveTrainInterfaces.IEncoder;

public class TalonSRXDriveTrain extends PhoenixDriveTrain implements IEncoder {
    private class TalonSRXMotor extends PhoenixMotor {
        private final TalonSRX talonSRX;
        private final SensorCollection sensors;

        public TalonSRXMotor(MotorController motor) {
            super(motor);
            this.talonSRX = (TalonSRX) this.getPhoenixMotor();
            this.sensors = this.talonSRX.getSensorCollection();
        }
        
        public TalonSRX getTalonSRXMotor() {
            return this.talonSRX;
        }

        public SensorCollection getSensors() {
            return this.sensors;
        }
    }

    private Map<Motor, TalonSRXMotor> talonSRXMotors = new EnumMap<>(Motor.class);

    public TalonSRXDriveTrain() {
        super(
            new TalonSRX(Motor.FRONT_LEFT.ID), 
            new TalonSRX(Motor.REAR_LEFT.ID), 
            new TalonSRX(Motor.FRONT_RIGHT.ID), 
            new TalonSRX(Motor.REAR_RIGHT.ID)
        );

        for (Motor motor : Motor.values()) {
            TalonSRXMotor talonSRXMotor = new TalonSRXMotor(motor.MOTOR);
            talonSRXMotors.put(motor, talonSRXMotor);
            talonSRXMotor.getTalonSRXMotor().configContinuousCurrentLimit(CURRENT_LIMIT);
        }
    }

    private TalonSRXMotor getTalonSRXMotor(Motor motor) {
        return this.talonSRXMotors.get(motor);
    }
    
    @Override
    public TalonSRX getPhoenixMotor(Motor motor) {
        return this.getTalonSRXMotor(motor).getTalonSRXMotor();
    }

    public SensorCollection getSensors(Motor motor) {
        return this.getTalonSRXMotor(motor).getSensors();
    }

    @Override
    public double getEncoderPosition(Motor motor) {
        return this.getSensors(motor).getQuadraturePosition();
    }

    @Override
    public void setEncoderPosition(Motor motor, double position) {
        this.getSensors(motor).setQuadraturePosition((int) position, 0);
    }

    @Override
    public void setAllEncoderPositions(double position) {
        for (Motor motor : Motor.values()) {
            this.setEncoderPosition(motor, position);
        }
    }
}

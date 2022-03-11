package frc.robot.drivetrain;

import java.util.EnumMap;
import java.util.Map;

import com.ctre.phoenix.motorcontrol.TalonFXSensorCollection;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.motorcontrol.MotorController;

public class TalonFXDriveTrain extends PhoenixDriveTrain {
    private class TalonFXMotor extends PhoenixMotor {
        private final TalonFX talonFX;
        private final TalonFXSensorCollection sensors;

        public TalonFXMotor(MotorController motor) {
            super(motor);
            this.talonFX = (TalonFX) this.getPhoenixMotor();
            this.sensors = this.talonFX.getSensorCollection();
        }
        
        public TalonFX getTalonFXMotor() {
            return this.talonFX;
        }

        public TalonFXSensorCollection getSensors() {
            return this.sensors;
        }
    }

    private Map<Motor, TalonFXMotor> talonFXMotors = new EnumMap<>(Motor.class);

    public TalonFXDriveTrain() {
        super(
            new TalonFX(Motor.FRONT_LEFT.ID), 
            new TalonFX(Motor.REAR_LEFT.ID), 
            new TalonFX(Motor.FRONT_RIGHT.ID), 
            new TalonFX(Motor.REAR_RIGHT.ID)
        );

        for (Motor motor : Motor.values()) {
            TalonFXMotor talonFXMotor = new TalonFXMotor(motor.MOTOR);
            talonFXMotors.put(motor, talonFXMotor);
        }
    }

    private TalonFXMotor getTalonFXMotor(Motor motor) {
        return this.talonFXMotors.get(motor);
    }
    
    @Override
    public TalonFX getPhoenixMotor(Motor motor) {
        return this.getTalonFXMotor(motor).getTalonFXMotor();
    }

    public TalonFXSensorCollection getSensors(Motor motor) {
        return this.getTalonFXMotor(motor).getSensors();
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

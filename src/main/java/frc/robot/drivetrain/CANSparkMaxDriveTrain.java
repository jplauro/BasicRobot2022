package frc.robot.drivetrain;

import static frc.robot.Constants.DriveTrain.CANSparkMaxDriveTrain.*;

import java.util.EnumMap;
import java.util.Map;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.motorcontrol.MotorController;

public class CANSparkMaxDriveTrain extends DriveTrain {
    private class CANSparkMaxMotor implements IMotor {
        private final CANSparkMax motor;
        private final RelativeEncoder encoder;

        public CANSparkMaxMotor(MotorController motor) {
            this.motor = (CANSparkMax) motor;
            this.encoder = this.motor.getEncoder();
        }

        public CANSparkMax getMotor() {
            return motor;
        }

        public RelativeEncoder getEncoder() {
            return encoder;
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
            canSparkMaxMotor.getMotor().setIdleMode(IDLE_MODE);
            canSparkMaxMotor.getMotor().setOpenLoopRampRate(OPEN_LOOP_RAMP_RATE);
            canSparkMaxMotor.getMotor().setSmartCurrentLimit(CURRENT_LIMIT);
        }
    }

    public CANSparkMax getMotor(Motor motor) {
        return canSparkMaxMotors.get(motor).getMotor();
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
        return canSparkMaxMotors.get(motor).getEncoder();
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
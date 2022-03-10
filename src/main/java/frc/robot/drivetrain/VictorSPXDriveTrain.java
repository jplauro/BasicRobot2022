package frc.robot.drivetrain;

import static frc.robot.Constants.DriveTrain.*;
import static frc.robot.Constants.DriveTrain.VictorSPXDriveTrain.*;

import java.util.EnumMap;
import java.util.Map;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.motorcontrol.MotorController;

public class VictorSPXDriveTrain extends DriveTrain {
    private class VictorSPXMotor implements IMotor {
        private final PhoenixMotorAdapter motor;
        private final VictorSPX phoenixMotor;

        public VictorSPXMotor(MotorController motor) {
            this.motor = (PhoenixMotorAdapter) motor;
            this.phoenixMotor = (VictorSPX) this.motor.getPhoenixMotor();
        }

        public PhoenixMotorAdapter getMotor() {
            return this.motor;
        }

        public VictorSPX getPhoenixMotor() {
            return this.phoenixMotor;
        }
    }

    private Map<Motor, VictorSPXMotor> victorSPXMotors = new EnumMap<>(Motor.class);

    public VictorSPXDriveTrain() {
        super(
            new PhoenixMotorAdapter(new VictorSPX(Motor.FRONT_LEFT.ID)), 
            new PhoenixMotorAdapter(new VictorSPX(Motor.REAR_LEFT.ID)), 
            new PhoenixMotorAdapter(new VictorSPX(Motor.FRONT_RIGHT.ID)), 
            new PhoenixMotorAdapter(new VictorSPX(Motor.REAR_RIGHT.ID))
        );

        for (Motor motor : Motor.values()) {
            VictorSPXMotor victorSPXMotor = new VictorSPXMotor(motor.MOTOR);
            victorSPXMotors.put(motor, victorSPXMotor);
            victorSPXMotor.getPhoenixMotor().configFactoryDefault();
            victorSPXMotor.getPhoenixMotor().setNeutralMode(NEUTRAL_MODE);
            victorSPXMotor.getPhoenixMotor().configOpenloopRamp(OPEN_LOOP_RAMP_RATE);
        }
    }

    public PhoenixMotorAdapter getMotor(Motor motor) {
        return victorSPXMotors.get(motor).getMotor();
    }

    public VictorSPX getPhoenixMotor(Motor motor) {
        return victorSPXMotors.get(motor).getPhoenixMotor();
    }

    public void setMode(Motor motor, NeutralMode mode) {
        this.getPhoenixMotor(motor).setNeutralMode(mode);
    }

    public void setAllModes(NeutralMode mode) {
        for (Motor motor : Motor.values()) {
            this.setMode(motor, mode);
        }
    }

    public void setOpenLoopRampRate(Motor motor, double rampRate) {
        this.getPhoenixMotor(motor).configOpenloopRamp(rampRate);
    }

    public void setAllOpenLoopRampRates(double rampRate) {
        for (Motor motor : Motor.values()) {
            this.setOpenLoopRampRate(motor, rampRate);
        }
    }

    public double getSensorPosition(Motor motor) {
        return this.getPhoenixMotor(motor).getSelectedSensorPosition();
    }

    public void setSensorPosition(Motor motor, double position) {
        this.getPhoenixMotor(motor).setSelectedSensorPosition(position);
    }

    public void setAllSensorPositions(double position) {
        for (Motor motor : Motor.values()) {
            this.setSensorPosition(motor, position);
        }
    }
}
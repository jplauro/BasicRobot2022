package frc.robot.drivetrain;

import static frc.robot.Constants.DriveTrain.*;
import static frc.robot.Constants.DriveTrain.PhoenixDriveTrain.*;

import java.util.EnumMap;
import java.util.Map;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.BaseMotorController;

import edu.wpi.first.wpilibj.motorcontrol.MotorController;

public abstract class PhoenixDriveTrain extends DriveTrain {
    public class PhoenixMotor implements IMotor {
        private final PhoenixMotorAdapter motor;
        private final BaseMotorController phoenixMotor;

        public PhoenixMotor(MotorController motor) {
            this.motor = (PhoenixMotorAdapter) motor;
            this.phoenixMotor = (BaseMotorController) this.motor.getPhoenixMotor();
        }

        public PhoenixMotorAdapter getMotor() {
            return this.motor;
        }

        public BaseMotorController getPhoenixMotor() {
            return this.phoenixMotor;
        }
    }

    public Map<Motor, PhoenixMotor> phoenixMotors = new EnumMap<>(Motor.class);

    public PhoenixDriveTrain(BaseMotorController frontLeftMotor, BaseMotorController rearLeftMotor,
    BaseMotorController frontRightMotor, BaseMotorController rearRightMotor) {
        super(
            new PhoenixMotorAdapter(frontLeftMotor), 
            new PhoenixMotorAdapter(rearLeftMotor), 
            new PhoenixMotorAdapter(frontRightMotor), 
            new PhoenixMotorAdapter(rearRightMotor)
        );

        for (Motor motor : Motor.values()) {
            PhoenixMotor phoenixMotor = new PhoenixMotor(motor.MOTOR);
            phoenixMotors.put(motor, phoenixMotor);
            phoenixMotor.getPhoenixMotor().configFactoryDefault();
            phoenixMotor.getPhoenixMotor().setNeutralMode(NEUTRAL_MODE);
            phoenixMotor.getPhoenixMotor().configOpenloopRamp(OPEN_LOOP_RAMP_RATE);
        }
    }

    public PhoenixMotorAdapter getMotor(Motor motor) {
        return this.phoenixMotors.get(motor).getMotor();
    }

    public abstract BaseMotorController getPhoenixMotor(Motor motor); 

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
}

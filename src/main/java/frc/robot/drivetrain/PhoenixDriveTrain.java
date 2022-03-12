package frc.robot.drivetrain;

import static frc.robot.Constants.DriveTrain.*;

import java.util.EnumMap;
import java.util.Map;

import com.ctre.phoenix.motorcontrol.can.BaseMotorController;

import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import frc.robot.drivetrain.DriveTrainInterfaces.IMotor;
import frc.robot.drivetrain.DriveTrainInterfaces.IMotorMode;
import frc.robot.drivetrain.DriveTrainInterfaces.IOpenLoopRampRate;

public abstract class PhoenixDriveTrain extends DriveTrain implements IMotorMode, IOpenLoopRampRate {
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
    private MotorMode motorMode = IDLE_MODE;
    private double openLoopRampRate = OPEN_LOOP_RAMP_RATE;

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
            phoenixMotor.getPhoenixMotor().setNeutralMode(IDLE_MODE.getNeutralMode());
            phoenixMotor.getPhoenixMotor().configOpenloopRamp(OPEN_LOOP_RAMP_RATE);
        }
    }

    @Override
    public PhoenixMotorAdapter getMotor(Motor motor) {
        return this.phoenixMotors.get(motor).getMotor();
    }

    public abstract BaseMotorController getPhoenixMotor(Motor motor); 

    @Override
    public MotorMode getMode(Motor motor) {
        return this.motorMode;
    }

    @Override
    public void setMode(Motor motor, MotorMode mode) {
        this.motorMode = mode;
        this.getPhoenixMotor(motor).setNeutralMode(mode.getNeutralMode());
    }

    @Override
    public void setAllModes(MotorMode mode) {
        for (Motor motor : Motor.values()) {
            this.setMode(motor, mode);
        }
    }

    @Override
    public double getOpenLoopRampRate(Motor motor) {
        return this.openLoopRampRate;
    }

    @Override
    public void setOpenLoopRampRate(Motor motor, double rampRate) {
        this.openLoopRampRate = rampRate;
        this.getPhoenixMotor(motor).configOpenloopRamp(rampRate);
    }

    @Override
    public void setAllOpenLoopRampRates(double rampRate) {
        for (Motor motor : Motor.values()) {
            this.setOpenLoopRampRate(motor, rampRate);
        }
    }
}

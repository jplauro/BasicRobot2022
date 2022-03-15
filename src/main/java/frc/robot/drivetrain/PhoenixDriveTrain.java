package frc.robot.drivetrain;

import static frc.robot.Constants.DriveTrain.*;

import java.util.EnumMap;
import java.util.Map;

import com.ctre.phoenix.motorcontrol.can.BaseMotorController;

public abstract class PhoenixDriveTrain extends DriveTrain {
    private Map<Motor, PhoenixMotorAdapter> phoenixMotorAdapters = new EnumMap<>(Motor.class);
    private Map<Motor, BaseMotorController> phoenixMotors = new EnumMap<>(Motor.class);
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
            PhoenixMotorAdapter phoenixMotorAdapter = (PhoenixMotorAdapter) this.getMotorControllers().get(motor);
            BaseMotorController phoenixMotor = phoenixMotorAdapter.getPhoenixMotor();
            this.phoenixMotorAdapters.put(motor, phoenixMotorAdapter);
            this.phoenixMotors.put(motor, phoenixMotor);
            phoenixMotor.configFactoryDefault();
            phoenixMotor.setNeutralMode(IDLE_MODE.getNeutralMode());
            phoenixMotor.configOpenloopRamp(OPEN_LOOP_RAMP_RATE);
        }

        if (CONTROL_MODE == ControlMode.FOLLOW) {
            this.phoenixMotors.get(Motor.REAR_LEFT).follow(phoenixMotors.get(Motor.FRONT_LEFT));
            this.phoenixMotors.get(Motor.REAR_RIGHT).follow(phoenixMotors.get(Motor.FRONT_RIGHT));
        }
    }

    protected Map<Motor, BaseMotorController> getPhoenixMotors() {
        return this.phoenixMotors;
    }

    @Override
    public PhoenixMotorAdapter getMotor(Motor motor) {
        return this.phoenixMotorAdapters.get(motor);
    }

    public abstract BaseMotorController getPhoenixMotor(Motor motor); 

    public MotorMode getMode(Motor motor) {
        return this.motorMode;
    }

    public void setMode(Motor motor, MotorMode mode) {
        this.motorMode = mode;
        this.getPhoenixMotor(motor).setNeutralMode(mode.getNeutralMode());
    }

    public void setAllModes(MotorMode mode) {
        for (Motor motor : Motor.values()) {
            this.setMode(motor, mode);
        }
    }

    public double getOpenLoopRampRate(Motor motor) {
        return this.openLoopRampRate;
    }

    public void setOpenLoopRampRate(Motor motor, double rampRate) {
        this.openLoopRampRate = rampRate;
        this.getPhoenixMotor(motor).configOpenloopRamp(rampRate);
    }

    public void setAllOpenLoopRampRates(double rampRate) {
        for (Motor motor : Motor.values()) {
            this.setOpenLoopRampRate(motor, rampRate);
        }
    }
}

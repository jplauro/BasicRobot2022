package frc.robot.drivetrain;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.BaseMotorController;

import edu.wpi.first.wpilibj.motorcontrol.MotorController;

public class PhoenixMotorAdapter implements MotorController {
    private final BaseMotorController phoenixMotor;

    public PhoenixMotorAdapter(BaseMotorController phoenixMotor) {
        this.phoenixMotor = phoenixMotor;
    }

    public BaseMotorController getPhoenixMotor() {
        return this.phoenixMotor;
    }

    @Override
    public void set(double speed) {
        this.phoenixMotor.set(ControlMode.PercentOutput, speed);
    }

    @Override
    public double get() {
        return this.phoenixMotor.getMotorOutputPercent();
    }

    @Override
    public void setInverted(boolean isInverted) {
        this.phoenixMotor.setInverted(isInverted);
    }

    @Override
    public boolean getInverted() {
        return this.phoenixMotor.getInverted();
    }

    @Override
    public void disable() {
        this.phoenixMotor.set(ControlMode.Disabled, 0);
    }

    @Override
    public void stopMotor() {
        this.phoenixMotor.set(ControlMode.PercentOutput, 0);
    }
}

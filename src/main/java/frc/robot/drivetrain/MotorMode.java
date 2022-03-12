package frc.robot.drivetrain;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.revrobotics.CANSparkMax.IdleMode;

public enum MotorMode {
    BRAKE(IdleMode.kBrake, NeutralMode.Brake), 
    COAST(IdleMode.kCoast, NeutralMode.Coast);

    private IdleMode idleMode;
    private NeutralMode neutralMode;

    private MotorMode(IdleMode idleMode, NeutralMode neutralMode) {
        this.idleMode = idleMode;
        this.neutralMode = neutralMode;
    }

    public IdleMode getIdleMode() {
        return this.idleMode;
    }

    public NeutralMode getNeutralMode() {
        return this.neutralMode;
    }
}

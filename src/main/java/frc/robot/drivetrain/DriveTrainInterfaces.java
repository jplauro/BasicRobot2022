package frc.robot.drivetrain;

import edu.wpi.first.wpilibj.motorcontrol.MotorController;

public class DriveTrainInterfaces {
    public interface IMotor {
        MotorController getMotor();
    }

    public interface IMotorMode {
        MotorMode getMode(Motor motor);
        void setMode(Motor motor, MotorMode mode);
        void setAllModes(MotorMode mode);
    }

    public interface IOpenLoopRampRate {
        double getOpenLoopRampRate(Motor motor);
        void setOpenLoopRampRate(Motor motor, double rampRate);
        void setAllOpenLoopRampRates(double rampRate);
    }

    public interface IEncoder {
        double getEncoderPosition(Motor motor);
        void setEncoderPosition(Motor motor, double position);
        void setAllEncoderPositions(double position);
    }
}

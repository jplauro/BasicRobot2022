package frc.robot;

import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Constants {
    public static class DriveTrain {
        public static final MotorType MOTOR_TYPE = MotorType.kBrushless;
        public static final IdleMode IDLE_MODE = IdleMode.kBrake;
        public static final double OPEN_LOOP_RAMP_RATE = 0.2;
        public static final int CURRENT_LIMIT = 75;
        public static final double DEADBAND = 0.05;

        public static class MotorIDs {
            public static final int FRONT_LEFT_MOTOR_ID = 1;
            public static final int REAR_LEFT_MOTOR_ID = 2;
            public static final int FRONT_RIGHT_MOTOR_ID = 3;
            public static final int REAR_RIGHT_MOTOR_ID = 4;
        }
    }

    public static class DriveWithJoystick {
        public static final int CONTROLLER_PORT = 0;
        public static final double SPEED_ADJUSTMENT = 0.7;
        public static final double ROTATION_ADJUSTMENT = -0.6;
    }
}
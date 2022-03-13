package frc.robot;

import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.Dashboard.DashboardType;
import frc.robot.DriveWithController.DriveMode;
import frc.robot.Util.DriveTrainType;
import frc.robot.drivetrain.MotorMode;
import frc.robot.drivetrain.DriveTrain.DriveTrainMode;

public final class Constants {
    public static class DriveTrain {
        public static final DriveTrainType DRIVE_TRAIN_TYPE = DriveTrainType.CANSparkMax;
        public static final DriveTrainMode DRIVE_TRAIN_MODE = DriveTrainMode.GROUP;
        public static final double DEADBAND = 0.05;
        public static final double OPEN_LOOP_RAMP_RATE = 0.2;
        public static final int CURRENT_LIMIT = 75;
        public static final MotorMode IDLE_MODE = MotorMode.BRAKE;
        public static final MotorMode DISABLE_MODE = MotorMode.COAST;

        public static class CANSparkMaxDriveTrain {
            public static final MotorType MOTOR_TYPE = MotorType.kBrushless;
        }

        public static class MotorIDs {
            public static final int FRONT_LEFT_MOTOR_ID = 1;
            public static final int REAR_LEFT_MOTOR_ID = 2;
            public static final int FRONT_RIGHT_MOTOR_ID = 3;
            public static final int REAR_RIGHT_MOTOR_ID = 4;
        }
    }

    public static class Dashboard {
        public static final DashboardType DASHBOARD_TYPE = DashboardType.Shuffleboard;

        public static class Shuffleboard {
            public static final String TAB_NAME = "SmartDashboard";
            public static final boolean AUTO_SETUP = true;
        }
    }

    public static class DriveWithController {
        public static final int CONTROLLER_PORT = 0;
        public static final DriveMode DRIVE_MODE = DriveMode.CURVATURE;
        public static final double SPEED_ADJUSTMENT = 0.7;
        public static final double ROTATION_ADJUSTMENT = 0.6;
        public static final boolean SQUARE_INPUTS = true;
    }
}

package frc.robot;

import static frc.robot.Constants.DriveTrain.*;

import java.util.function.Supplier;

import frc.robot.drivetrain.DriveTrain;
import frc.robot.drivetrain.impl.CANSparkMaxDriveTrain;
import frc.robot.drivetrain.impl.TalonFXDriveTrain;
import frc.robot.drivetrain.impl.TalonSRXDriveTrain;
import frc.robot.drivetrain.impl.VictorSPXDriveTrain;

public final class Util {
    public enum DriveTrainType {
        CANSparkMax, TalonFX, TalonSRX, VictorSPX;
    }

    public static Supplier<DriveTrain> getDriveTrain() {
        switch (DRIVE_TRAIN_TYPE) {
            case CANSparkMax:
                return CANSparkMaxDriveTrain::new;
            case TalonFX:
                return TalonFXDriveTrain::new;
            case TalonSRX:
                return TalonSRXDriveTrain::new;
            case VictorSPX:
                return VictorSPXDriveTrain::new;
            default:
                throw new AssertionError();
        }
    }

    // Method that gets the implementation-specific drive train
    // This must be modified when the DRIVE_TRAIN_TYPE constant is switched
    public static CANSparkMaxDriveTrain convert(DriveTrain driveTrain) {
        if (DRIVE_TRAIN_TYPE == DriveTrainType.CANSparkMax) {
            return (CANSparkMaxDriveTrain) driveTrain;
        } else throw new AssertionError("The drive train conversion method Util#convert() is inaccurate");
    }

    private static String capitalizeWord(String word) {
        String firstLetter = word.substring(0, 1).toUpperCase();
        return firstLetter + word.substring(1).toLowerCase();
    }

    public static String capitalize(String string, boolean onlyFirstWord) {
        if (onlyFirstWord) {
            return capitalizeWord(string);
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            String[] words = string.trim().split("\\s+"); // Regex for whitespace

            for (String word : words) {
                stringBuilder.append(capitalizeWord(word));
            }

            return stringBuilder.toString();
        }
    }
}

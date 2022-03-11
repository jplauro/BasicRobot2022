package frc.robot.drivetrain;

import java.util.EnumMap;
import java.util.Map;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.motorcontrol.MotorController;

public class VictorSPXDriveTrain extends PhoenixDriveTrain {
    private class VictorSPXMotor extends PhoenixMotor {
        private final VictorSPX victorSPX;

        public VictorSPXMotor(MotorController motor) {
            super(motor);
            this.victorSPX = (VictorSPX) this.getPhoenixMotor();
        }
        
        public VictorSPX getVictorSPXMotor() {
            return this.victorSPX;
        }
    }

    private Map<Motor, VictorSPXMotor> victorSPXMotors = new EnumMap<>(Motor.class);

    public VictorSPXDriveTrain() {
        super(
            new VictorSPX(Motor.FRONT_LEFT.ID), 
            new VictorSPX(Motor.REAR_LEFT.ID), 
            new VictorSPX(Motor.FRONT_RIGHT.ID), 
            new VictorSPX(Motor.REAR_RIGHT.ID)
        );

        for (Motor motor : Motor.values()) {
            VictorSPXMotor victorSPXMotor = new VictorSPXMotor(motor.MOTOR);
            victorSPXMotors.put(motor, victorSPXMotor);
        }
    }

    public VictorSPX getPhoenixMotor(Motor motor) {
        return this.victorSPXMotors.get(motor).getVictorSPXMotor();
    }
}

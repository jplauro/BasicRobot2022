package frc.robot.drivetrain.impl;

import static frc.robot.Constants.DriveTrain.MotorIDs.*;

import java.util.EnumMap;
import java.util.Map;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import frc.robot.drivetrain.PhoenixDriveTrain;

public class VictorSPXDriveTrain extends PhoenixDriveTrain {
    private Map<Motor, VictorSPX> victorSPXMotors = new EnumMap<>(Motor.class);

    public VictorSPXDriveTrain() {
        super(
            new VictorSPX(FRONT_LEFT_MOTOR_ID), 
            new VictorSPX(REAR_LEFT_MOTOR_ID), 
            new VictorSPX(FRONT_RIGHT_MOTOR_ID), 
            new VictorSPX(REAR_RIGHT_MOTOR_ID)
        );

        for (Motor motor : Motor.values()) {
            VictorSPX victorSPXMotor = (VictorSPX) this.getPhoenixMotors().get(motor);
            this.victorSPXMotors.put(motor, victorSPXMotor);
        }
    }

    @Override
    public VictorSPX getPhoenixMotor(Motor motor) {
        return this.victorSPXMotors.get(motor);
    }
}

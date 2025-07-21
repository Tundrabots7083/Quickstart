package hardwareConfig.shoulder.constants;


import com.qualcomm.robotcore.hardware.DcMotor;

import com.qualcomm.robotcore.hardware.DcMotorSimple;


import hardwareConfig.motor.constants.MotorConstants;

public class ShoulderConstants {
        static {
                MotorConstants.name = "sholderMotor";
                MotorConstants.motorConfigurationType = "clone";
                MotorConstants.ticksPerRev=1397.1; // gobuilda ticks per rev for 60 rpm;
                MotorConstants.achieveableMaxRPMFraction=1.0;
                MotorConstants.gearing=120.0 / 24.0;
                MotorConstants.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER;
                MotorConstants.direction = DcMotorSimple.Direction.FORWARD;
                MotorConstants.minAngle= -47.0;
                MotorConstants.maxAngle=90.0;
                MotorConstants.startAngle=-47.0;
                MotorConstants.targetAngle=-47.0;
                MotorConstants.feedforward=0.0;
                MotorConstants.tolerableError=0.7; //in degrees

        }
}
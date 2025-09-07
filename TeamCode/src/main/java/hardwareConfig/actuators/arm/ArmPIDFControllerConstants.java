package hardwareConfig.actuators.arm;

import hardwareConfig.baseConstants.PIDFControllerConstants;

public class ArmPIDFControllerConstants  {
    static {
        PIDFControllerConstants.kp=0.01;  //to be tuned for the PID controller
        PIDFControllerConstants.ki=0.0001;
        PIDFControllerConstants.kd=0.0;
        PIDFControllerConstants.kf=0.0;
        PIDFControllerConstants.maxIntegralSum=100;
        PIDFControllerConstants.motorMinPowerLimit=-1;
        PIDFControllerConstants.motorMaxPowerLimit=1;

    }
}
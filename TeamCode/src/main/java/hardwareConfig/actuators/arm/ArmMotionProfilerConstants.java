package hardwareConfig.actuators.arm;

import hardwareConfig.baseConstants.MotionProfilerConstants;

public class ArmMotionProfilerConstants {
    static {
        MotionProfilerConstants.maxVelocity=30;  //deg/s to be tuned for the motion profiler
        MotionProfilerConstants.maxAcceleration=10;  // deg/s**2
    }
}
package hardwareConfig.actuators.shoulder;

import com.acmerobotics.dashboard.config.Config;

import hardwareConfig.baseConstants.MotionProfilerConstants;



@Config
public class ShoulderMotionProfilerConstants {
    static {
        MotionProfilerConstants.maxVelocity=30;  //deg/s to be tuned for the motion profiler
        MotionProfilerConstants.maxAcceleration=10;  // deg/s**2
    }
}

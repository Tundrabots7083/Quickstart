package hardwareControl.shoulder;


import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;


import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;



import hardwareConfig.motor.constants.MotorConstants;
import hardwareConfig.shoulder.constants.ShoulderConstants;

public class ShoulderController {
    /*  private DcMotorEx soulderMotor;

    private double maxAngle;
    private double minAngle;
    private double startAngle;
    private double targetAngle;
    private double tolerableError;
    private double feedforward;
*/

    private boolean initialized = false;

    // Private static instance (eager initialization)
    private static final ShoulderController INSTANCE = new ShoulderController();

    // Private constructor to prevent instantiation
    private ShoulderController() {
        // Initialize hardware, state, or configuration here

    }
    // Initialization method — must be called once at the beginning
    public void initialize(HardwareMap hardwareMap, double startAngle) {
        if (initialized) {
            return;
            //throw new IllegalStateException("ShoulderController has already been initialized.");
        }
/*
         new ShoulderConstants();

        soulderMotor =  hardwareMap.get(DcMotorEx.class, MotorConstants.name);
        MotorConfigurationType motorConfigurationType = soulderMotor.getMotorType().clone();
        motorConfigurationType.setTicksPerRev(MotorConstants.ticksPerRev);
        motorConfigurationType.setGearing(MotorConstants.gearing);
        motorConfigurationType.setAchieveableMaxRPMFraction(MotorConstants.achieveableMaxRPMFraction);
        soulderMotor.setMotorType(motorConfigurationType);
        soulderMotor.setMode(MotorConstants.mode);
        soulderMotor.setDirection(MotorConstants.direction);

        maxAngle = MotorConstants.maxAngle;
        minAngle = MotorConstants.minAngle;
        startAngle = MotorConstants.startAngle;
        targetAngle = MotorConstants.targetAngle;
        tolerableError = MotorConstants.tolerableError;
        feedforward = MotorConstants.feedforward;
        */



        initialized = true;
    }

    public void reset() {
        if(initialized) {
           // follower.breakFollowing();
          //  follower = null;
            initialized = false;
        }
    }

    // Public method to access the singleton instance
    public static ShoulderController getInstance() {
        return INSTANCE;
    }

    // Example method


}

/*usage Example*/

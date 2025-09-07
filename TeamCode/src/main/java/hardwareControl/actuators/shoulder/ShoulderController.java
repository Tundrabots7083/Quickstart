package hardwareControl.actuators.shoulder;


import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import hardwareConfig.baseConstants.MotorConstants;
import hardwareConfig.baseConstants.PIDFControllerConstants;
import hardwareConfig.actuators.shoulder.ShoulderConstants;
import hardwareConfig.actuators.shoulder.ShoulderMotionProfilerConstants;
import hardwareConfig.actuators.shoulder.ShoulderPIDFControllerConstants;
import hardwareControl.actuators.common.MotionProfiler;
import hardwareControl.actuators.common.PIDFController;

@Config
public class ShoulderController {

     private DcMotorEx shoulderMotor;

    private double MAX_POSITION;
    private double MIN_POSITION;
    private double START_POSITION;
    private double targetPosition;
    private double lastPosition;
    private double TOLERABLE_ERROR;
    private double FEED_FORWARD;

    public static double Kp = 0.01;
    public static double Ki = 0.001;
    public static double Kd = 0.0;
    public static double Kf = 0.0;
    public static double Kcos = 0.01;


    /// motion control
    private PIDFController pidfController;

    private MotionProfiler motionProfiler;

    private double currentPosition=0.0;
    public static double mpMaxVelocity = 500;  /// deg/s
    public static double mpMaxAcceleration = 200;   /// deg/s**2
    private double startTime;
    ///

    private boolean initialized = false;
    private boolean isMotionProfileGenerated = false;

    // Private static instance (eager initialization)
    private static final ShoulderController INSTANCE = new ShoulderController();
    private LinearOpMode opMode;
    private Telemetry telemetry;

    // Private constructor to prevent instantiation
    private ShoulderController() {
        // Initialize hardware, state, or configuration here

    }

    // Public method to access the singleton instance
    public static ShoulderController getInstance() {
        return INSTANCE;
    }

    private static void setupConstants(){
        try {
            Class.forName(ShoulderConstants.class.getName());
            Class.forName(ShoulderPIDFControllerConstants.class.getName());
            Class.forName(ShoulderMotionProfilerConstants.class.getName());
        } catch (ClassNotFoundException e) {
            //e.printStackTrace();
        }
    }
    // Initialization method — must be called once at the beginning
    public void initialize(HardwareMap hardwareMap, Telemetry telemetry, LinearOpMode opMode) {
        if (initialized) {
            return;
            //throw new IllegalStateException("ShoulderController has already been initialized.");
        }
        setupConstants();
        this.opMode = opMode;
        this.telemetry  = telemetry;

        initializeMotor(hardwareMap);
        initializeLocalVariablesWithConstants();
        initializePDFController();
        initializeMotionProfiler();

        initialized = true;
    }

    private void initializeMotor(HardwareMap hardwareMap){
        shoulderMotor =  hardwareMap.get(DcMotorEx.class, MotorConstants.name);
        MotorConfigurationType motorConfigurationType = shoulderMotor.getMotorType().clone();
        motorConfigurationType.setTicksPerRev(MotorConstants.ticksPerRev);
        motorConfigurationType.setGearing(MotorConstants.gearing);
        motorConfigurationType.setAchieveableMaxRPMFraction(MotorConstants.achievableMaxRPMFraction);
        shoulderMotor.setMotorType(motorConfigurationType);
        shoulderMotor.setMode(MotorConstants.resetMode);
        shoulderMotor.setMode(MotorConstants.mode);
        shoulderMotor.setDirection(MotorConstants.direction);
    }

    private void initializeLocalVariablesWithConstants(){
        START_POSITION = MotorConstants.startPosition;
        lastPosition = START_POSITION;
        MAX_POSITION = MotorConstants.maxPosition;
        MIN_POSITION = MotorConstants.minPosition;
        TOLERABLE_ERROR = MotorConstants.tolerableError;
        FEED_FORWARD = MotorConstants.feedforward;
    }

    private void initializePDFController(){
  /*        Kp= PIDFControllerConstants.kp;
        Ki=PIDFControllerConstants.ki;
        Kd=PIDFControllerConstants.kd;*/

        pidfController = new PIDFController(Kp, Ki, Kd, Kf);
        pidfController.setOutputLimits(PIDFControllerConstants.motorMinPowerLimit, PIDFControllerConstants.motorMaxPowerLimit); // Motor power limits
        pidfController.setMaxIntegralSum(PIDFControllerConstants.maxIntegralSum); // Prevent integral windup
    }

    private void initializeMotionProfiler(){
       // mpMaxVelocity = MotionProfilerConstants.maxVelocity;
       // mpMaxAcceleration = MotionProfilerConstants.maxAcceleration;

        // Initialize motion profiler (tune these constraints!)
        motionProfiler = new MotionProfiler(mpMaxVelocity, mpMaxAcceleration); // e.g., ticks/s, ticks/s^2


    }

    private void generateMotionProfile(double startPosition, double targetPosition){
        if(isMotionProfileGenerated){
            return;
        }

        // Generate motion profile
        motionProfiler.generateProfile(startPosition, targetPosition);

        startTime = System.nanoTime() / 1_000_000_000.0; // Start time in seconds

        isMotionProfileGenerated=true;

        telemetry.addData("ShoulderCtrl.generateMotionProfile startPosition", startPosition);
        telemetry.addData("ShoulderCtrl.generateMotionProfile targetPosition", targetPosition);
        telemetry.update();
    }
    public void reset() {
        /// TODO: include sensor to detect hardware reset.
        if(initialized) {
            if(this.isBusy()) {
                this.moveToTargetPosition(START_POSITION);
            } else {
                shoulderMotor.setMode(MotorConstants.resetMode);
                shoulderMotor.setMode(MotorConstants.mode);
                shoulderMotor.setDirection(MotorConstants.direction);
                isMotionProfileGenerated=false;
            }
            initialized = false;
        }
    }


    // Example method
    public double getCurrentPosition() {
        MotorConfigurationType motorType = shoulderMotor.getMotorType();
        double ticksPerRev = motorType.getTicksPerRev();
        double gearing = motorType.getGearing();
        double currentTicks = shoulderMotor.getCurrentPosition();
        double rotations = currentTicks / ticksPerRev;
        double degreesPerRotation = 360.0 / gearing;
        double currentPosition = rotations * degreesPerRotation + START_POSITION;


        return rotations * degreesPerRotation + START_POSITION;
    }

    public void moveToTargetPosition(double newTargetPosition){
        this.targetPosition = newTargetPosition;

        generateMotionProfile(lastPosition, targetPosition);


        // Get current time relative to start
        double currentTime = System.nanoTime() / 1_000_000_000.0 - startTime;

        // Get motion profile setpoint
        MotionProfiler.MotionState state = motionProfiler.getMotionState(currentTime);
        double setpointPosition = state.position;

        // Get current encoder position
        currentPosition = this.getCurrentPosition();

        // Calculate motor pidPower using PIDF
        double pidPower = pidfController.calculate(setpointPosition, currentPosition);

        // Calculate gravity compensation power
        double gravityCompPower = Kcos * Math.cos(Math.toRadians(currentPosition));

        double power = pidPower + gravityCompPower;
        // Apply pidPower to motor
        shoulderMotor.setPower(power);


        telemetry.addData("ShoulderController Target position", newTargetPosition);
        telemetry.addData("ShoulderController Current position", currentPosition);
        telemetry.addData("ShoulderController setpointPosition", setpointPosition);
        telemetry.addData("ShoulderController pidPower", pidPower);
        telemetry.addData("ShoulderController gravityCompPower", gravityCompPower);
        telemetry.addData("ShoulderController power", power);
        telemetry.update();

    }

    public boolean isOnTarget(){
          double currentPosition = this.getCurrentPosition();
          boolean isOnTarget = ((Math.abs(targetPosition - currentPosition) <= TOLERABLE_ERROR));
          if (isOnTarget){
              lastPosition = currentPosition;
              isMotionProfileGenerated=false;
          }
          return isOnTarget;
    }

    public void update(){

    }
    public boolean isShoulderStuck(){
        return  false;
     //   double currentPosition = this.getCurrentPosition();
     //   return (Math.abs(currentPosition - targetPosition) > TOLERABLE_ERROR) && !isBusy();
    }

    public boolean isBusy(){
        return shoulderMotor.isBusy();
    }


}

/*usage Example*/

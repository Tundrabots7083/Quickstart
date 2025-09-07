package behaviorControl.BehaviorTrees.BehaviorTreeComponents.ActionFunctions.SpecimenSide;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import behaviorControl.BehaviorTrees.BehaviorTreeComponents.general.ActionFunction;
import behaviorControl.BehaviorTrees.BehaviorTreeComponents.general.BlackBoardSingleton;
import behaviorControl.BehaviorTrees.BehaviorTreeComponents.general.Status;
import hardwareControl.actuators.shoulder.ShoulderController;

/// TODO: implement functionality
public class MoveShoulderToScoreSpecimen implements ActionFunction
{
    private final LinearOpMode opMode;
    Telemetry telemetry;
    ShoulderController shoulderController;
    protected Status lastStatus = Status.FAILURE;

   double targetAngle = 150.00;
    boolean started = false;

    public MoveShoulderToScoreSpecimen(Telemetry telemetry, ShoulderController shoulderController, LinearOpMode opMode) {
        this.telemetry = telemetry;
        this.shoulderController = shoulderController;
        this.opMode = opMode;
        this.init();
    }

    private void init(){
        telemetry.clearAll();
    }

    public Status perform(BlackBoardSingleton blackBoard) {
        Status status;

        if (lastStatus == Status.SUCCESS) {
            return lastStatus;
        }
        if(!started){
            shoulderController.moveToTargetPosition(this.targetAngle);
            started = true;
            status =Status.RUNNING;
        } else {
            if (!shoulderController.isOnTarget()) {
                //   double currentPosition =shoulderController.getCurrentAngle();
                shoulderController.moveToTargetPosition(this.targetAngle);

                // Telemetry for debugging
                //     telemetry.addData("Target Angle", targetAngle);
                //     telemetry.addData("Current Angle", currentPosition);
                //    telemetry.update();


                status = Status.RUNNING;
            } else {
                if (shoulderController.isShoulderStuck()) {
                    status = Status.FAILURE;
                } else {
                    status = Status.SUCCESS;
                }
            }
        }
        this.telemetry.update();
        lastStatus = status;
        return status;
    }

}

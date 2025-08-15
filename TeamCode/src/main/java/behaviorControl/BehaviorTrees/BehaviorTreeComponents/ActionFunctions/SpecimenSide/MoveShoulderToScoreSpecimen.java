package behaviorControl.BehaviorTrees.BehaviorTreeComponents.ActionFunctions.SpecimenSide;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import behaviorControl.BehaviorTrees.BehaviorTreeComponents.general.ActionFunction;
import behaviorControl.BehaviorTrees.BehaviorTreeComponents.general.BlackBoardSingleton;
import behaviorControl.BehaviorTrees.BehaviorTreeComponents.general.Status;
import hardwareControl.shoulder.ShoulderController;

/// TODO: implement functionality
public class MoveShoulderToScoreSpecimen implements ActionFunction
{
    Telemetry telemetry;
    ShoulderController shoulderController;
    protected Status lastStatus = Status.FAILURE;

    /// TODO: set the right poses
   double startAngle;
   double targetAngle = 80.00;
    boolean started = false;

    public MoveShoulderToScoreSpecimen(Telemetry telemetry, ShoulderController shoulderController) {
        this.telemetry = telemetry;
        this.shoulderController = shoulderController;
        this.init();
    }

    private void init(){
        startAngle=95.00;
    }

    public Status perform(BlackBoardSingleton blackBoard) {
        Status status;

        if (lastStatus == Status.SUCCESS) {
            return lastStatus;
        }
        if(!started){
            shoulderController.moveToTargetAngle(this.targetAngle);
            started = true;
            status =Status.RUNNING;
        } else {
            if(shoulderController.isBusy()) {
                status =Status.RUNNING;
            } else {
                if(shoulderController.isShoulderStuck()){
                    status=Status.FAILURE;
                } else {
                    status=Status.SUCCESS;
                }
            }
        }

        shoulderController.update();
        lastStatus = status;
        return status;
    }

}

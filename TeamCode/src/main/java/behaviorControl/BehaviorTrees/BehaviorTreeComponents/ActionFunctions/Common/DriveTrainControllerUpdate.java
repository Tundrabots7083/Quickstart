package behaviorControl.BehaviorTrees.BehaviorTreeComponents.ActionFunctions.Common;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import behaviorControl.BehaviorTrees.BehaviorTreeComponents.general.ActionFunction;
import behaviorControl.BehaviorTrees.BehaviorTreeComponents.general.BlackBoardSingleton;
import behaviorControl.BehaviorTrees.BehaviorTreeComponents.general.Status;
import hardwareControl.driveTrain.DriveTrainController;

public class DriveTrainControllerUpdate implements ActionFunction
{
    Telemetry telemetry;
    DriveTrainController driveTrainController;


    public DriveTrainControllerUpdate (Telemetry telemetry, DriveTrainController driveTrainController) {
        this.telemetry = telemetry;
        this.driveTrainController = driveTrainController;
    }

    public Status perform(BlackBoardSingleton blackBoard) {
        this.driveTrainController.update();

        Status status = Status.SUCCESS;

        return status;
    }

}
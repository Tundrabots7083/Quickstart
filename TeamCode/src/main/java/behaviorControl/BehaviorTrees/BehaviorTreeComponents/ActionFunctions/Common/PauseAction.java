package behaviorControl.BehaviorTrees.BehaviorTreeComponents.ActionFunctions.Common;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import behaviorControl.BehaviorTrees.BehaviorTreeComponents.general.ActionFunction;
import behaviorControl.BehaviorTrees.BehaviorTreeComponents.general.BlackBoardSingleton;
import behaviorControl.BehaviorTrees.BehaviorTreeComponents.general.Status;


public class PauseAction implements ActionFunction {
    private static final double CLAW_OPEN_POSITION = 60;
    private final long pauseDuration;
    Telemetry telemetry;
    protected LinearOpMode opMode;

    protected Status lastStatus = Status.FAILURE;

    public PauseAction (long pauseDuration, Telemetry telemetry, LinearOpMode opMode) {
        this.telemetry = telemetry;
        this.opMode = opMode;
        this.pauseDuration = pauseDuration;
        this.init();
    }

    private void init(){


    }
    @Override
    public Status perform(BlackBoardSingleton blackBoard) {
        Status status;

        telemetry.addData("PauseAction", "perform start");
        telemetry.update();

        opMode.sleep(pauseDuration);


        status=Status.SUCCESS;



        telemetry.addData("PauseAction", "perform finish");
        telemetry.update();
        return status;
    }
}


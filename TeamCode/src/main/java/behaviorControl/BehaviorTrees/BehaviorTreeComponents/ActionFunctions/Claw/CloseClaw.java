package behaviorControl.BehaviorTrees.BehaviorTreeComponents.ActionFunctions.Claw;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import behaviorControl.BehaviorTrees.BehaviorTreeComponents.general.ActionFunction;
import behaviorControl.BehaviorTrees.BehaviorTreeComponents.general.BlackBoardSingleton;
import behaviorControl.BehaviorTrees.BehaviorTreeComponents.general.Status;
import hardwareControl.actuators.claw.ClawController;

public class CloseClaw implements ActionFunction {
    private static final double CLAW_CLOSE_POSITION = 20;
    Telemetry telemetry;
    protected LinearOpMode opMode;

    ClawController clawController;
    protected Status lastStatus = Status.FAILURE;

    public CloseClaw (Telemetry telemetry, ClawController clawController, LinearOpMode opMode) {
        this.telemetry = telemetry;
        this.opMode = opMode;
        this.clawController = clawController;
        this.init();
    }

    private void init(){


    }
    @Override
    public Status perform(BlackBoardSingleton blackBoard) {
        Status status;

        telemetry.addData("CloseClaw", "perform start");
        telemetry.update();

        if (lastStatus == Status.SUCCESS) {
            return lastStatus;
        }

        telemetry.addData("CloseClaw", "current position %.2f", clawController.getLastPosition());
        telemetry.update();

        clawController.moveToPosition(CLAW_CLOSE_POSITION);

        status=Status.SUCCESS;

        lastStatus = status;

        telemetry.addData("CloseClaw", "perform finish");
        telemetry.update();
        return status;
    }
}

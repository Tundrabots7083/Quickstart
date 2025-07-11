package opModes.test;



import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


import behaviorControl.BehaviorTrees.BehaviorTreeComponents.general.Status;

import behaviorControl.BehaviorTrees.BehaviorTree.ScoreSpecimensBehaviorTree;


@Autonomous(name="BT Specimen Scoring", group="specimens")
public class SpecimenScoringOpMode extends LinearOpMode
{
    ScoreSpecimensBehaviorTree behaviorTree = null;


    @Override
    public void runOpMode()
    {
        FtcDashboard dashboard = FtcDashboard.getInstance();
        telemetry = dashboard.getTelemetry();


        telemetry.addData("SpecimenScoringOpMode", "runOpMode started");
        telemetry.update();
        initialize(this);
        waitForStart();


        while (opModeIsActive())
        {
            telemetry.addData("SpecimenScoringOpMode", "runOpMode while started");
            telemetry.update();
            Status result = this.behaviorTree.tick();


            telemetry.addData("SpecimenScoringOpMode", "Behavior tree result: %s",result);
            telemetry.update();


            if(result == Status.SUCCESS){
                telemetry.addData("SpecimenScoringOpMode", "runOpMode success");
                telemetry.update();
                requestOpModeStop();
            }
        }
    }


    private void initialize(LinearOpMode opMode){
        this.behaviorTree = new ScoreSpecimensBehaviorTree(hardwareMap,telemetry);
    }


}



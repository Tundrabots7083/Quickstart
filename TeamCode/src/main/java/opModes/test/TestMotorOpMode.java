package opModes.test;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import behaviorControl.BehaviorTrees.BehaviorTreeComponents.general.Status;

@Autonomous(name="TestMotor", group="specimens")
public class TestMotorOpMode extends LinearOpMode
{

private DcMotorEx testMotor;

    @Override
    public void runOpMode()
    {
        FtcDashboard dashboard = FtcDashboard.getInstance();
        telemetry = dashboard.getTelemetry();


        telemetry.addData("TestMotorOpMode", "runOpMode started");
        telemetry.update();
        waitForStart();

       testMotor =  hardwareMap.get(DcMotorEx.class, "testMotor");


        while (opModeIsActive())
        {
            telemetry.addData("TestMotorOpMode", "runOpMode while started");
            telemetry.update();

            testMotor.setPower(0.2);

        }
    }

}

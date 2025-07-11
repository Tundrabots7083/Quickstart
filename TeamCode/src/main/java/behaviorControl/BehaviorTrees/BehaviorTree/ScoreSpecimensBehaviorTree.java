package behaviorControl.BehaviorTrees.BehaviorTree;

import com.pedropathing.localization.Pose;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.Arrays;
import java.util.List;

import behaviorControl.BehaviorTrees.BehaviorTreeComponents.ActionFunctions.Common.DriveTrainControllerUpdate;
import behaviorControl.BehaviorTrees.BehaviorTreeComponents.ActionFunctions.SpecimenSide.MoveSample2;
import behaviorControl.BehaviorTrees.BehaviorTreeComponents.ActionFunctions.SpecimenSide.MoveSample3;
import behaviorControl.BehaviorTrees.BehaviorTreeComponents.ActionFunctions.SpecimenSide.ScoreSpecimen1;
import behaviorControl.BehaviorTrees.BehaviorTreeComponents.ActionFunctions.SpecimenSide.ScoreSpecimen2;
import behaviorControl.BehaviorTrees.BehaviorTreeComponents.general.BehaviorTree;
import behaviorControl.BehaviorTrees.BehaviorTreeComponents.general.BlackBoardSingleton;
import behaviorControl.BehaviorTrees.BehaviorTreeComponents.general.Node;
import behaviorControl.BehaviorTrees.BehaviorTreeComponents.general.Sequence;
import behaviorControl.BehaviorTrees.BehaviorTreeComponents.general.Status;
import behaviorControl.BehaviorTrees.BehaviorTreeComponents.general.Action;
import hardwareControl.driveTrain.DriveTrainController;


public class ScoreSpecimensBehaviorTree {
    private BehaviorTree tree;
    private Node root;
    private BlackBoardSingleton blackBoard;
    protected Telemetry telemetry;
    protected HardwareMap hardwareMap;
    protected DriveTrainController driveTrainController;
    /// TODO: set the startingPose to the right value
    private final Pose startingPose = new Pose(10, 55, Math.toRadians(0));


    public ScoreSpecimensBehaviorTree(HardwareMap hardwareMap, Telemetry telemetry) {
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;

        Init();
    }
    private void Init() {
        this.blackBoard = BlackBoardSingleton.getInstance(telemetry);
        this.blackBoard.reset();
        this.driveTrainController = DriveTrainController.getInstance();
        this.driveTrainController.reset();
        this.driveTrainController.initialize(hardwareMap, startingPose); //hardwareMap is from opMode


        this.root = new Sequence(
                Arrays.asList(
                        //new Action(new DriveTrainControllerUpdate(telemetry, this.driveTrainController),telemetry),
                        new Action(new ScoreSpecimen1(telemetry, this.driveTrainController),telemetry),
                        new Action(new MoveSample2(telemetry, this.driveTrainController),telemetry),
                        new Action(new MoveSample3(telemetry, this.driveTrainController), telemetry),
                        new Action(new ScoreSpecimen2(telemetry, driveTrainController), telemetry)
                ),telemetry);

        this.tree = new BehaviorTree(root, blackBoard);
    }

    public Status tick() {
        // Clear the bulk cache for each Lynx module hub. This must be performed once per loop
        // as the bulk read caches are being handled manually.
        List<LynxModule> allHubs;

        allHubs = hardwareMap.getAll(LynxModule.class);
        for (LynxModule hub : allHubs) {
            hub.clearBulkCache();
        }
        // Run the behavior tree
        Status result = tree.tick();
        telemetry.addData("ScoreSpecimensBehaviorTree", "Run - Behavior tree result: %s",result);
        telemetry.update();

        return result;
    }
}


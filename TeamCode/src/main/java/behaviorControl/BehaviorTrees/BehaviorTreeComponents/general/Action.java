package behaviorControl.BehaviorTrees.BehaviorTreeComponents.general;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Action extends Node {
    private ActionFunction actionFunction;
    protected Telemetry telemetry;


    public Action(ActionFunction actionFunction, Telemetry telemetry) {
        this.actionFunction = actionFunction;
        this.telemetry = telemetry;
    }

    @Override
    public Status execute(BlackBoardSingleton blackBoard) {
        return actionFunction.perform(blackBoard);
    }
}

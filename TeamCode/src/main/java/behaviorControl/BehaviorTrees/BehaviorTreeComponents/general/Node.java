package behaviorControl.BehaviorTrees.BehaviorTreeComponents.general;

public abstract class Node {
    public abstract Status execute(BlackBoardSingleton globalStore);
}

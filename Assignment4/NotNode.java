
public class NotNode implements EveryThingInterface, ConditionNode {
	ConditionNode cnd;
	public NotNode(EveryThingInterface not) {
		// TODO Auto-generated constructor stub
		cnd = (ConditionNode) not;
	}

	@Override
	public boolean evaluate(Robot robot) {
		// TODO Auto-generated method stub
		return !cnd.evaluate(robot);
	}

}

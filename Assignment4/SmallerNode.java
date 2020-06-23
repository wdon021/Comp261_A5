
public class SmallerNode implements EveryThingInterface, ConditionNode {
	SensorNode left,right ;
	public SmallerNode(EveryThingInterface leftd, EveryThingInterface rightd) {
		// TODO Auto-generated constructor stub
		left = (SensorNode) leftd;
		right = (SensorNode) rightd;
	}

	@Override
	public boolean evaluate(Robot robot) {
		// TODO Auto-generated method stub
		return left.evaluateSens(robot) < right.evaluateSens(robot);
	}

}

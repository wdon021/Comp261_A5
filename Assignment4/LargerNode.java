
public class LargerNode implements EveryThingInterface, ConditionNode {
	SensorNode left,right ;
	public LargerNode(EveryThingInterface leftd,EveryThingInterface rightd) {
		left = (SensorNode) leftd;
		right = (SensorNode) rightd;
	}
	@Override
	public boolean evaluate(Robot robot) {
		// TODO Auto-generated method stub
		return left.evaluateSens(robot) > right.evaluateSens(robot);
	}

}

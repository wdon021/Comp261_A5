
public class AddNode implements EveryThingInterface, SensorNode {
	private SensorNode left, right;
	public AddNode(EveryThingInterface lefts, EveryThingInterface rights) {
		left = (SensorNode) lefts;
		right = (SensorNode) rights;
	}
	@Override
	public int evaluateSens(Robot robot) {
		// TODO Auto-generated method stub
		return left.evaluateSens(robot)+right.evaluateSens(robot);
	}

}

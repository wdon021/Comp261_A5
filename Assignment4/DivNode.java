
public class DivNode implements EveryThingInterface, SensorNode {
	private SensorNode left, right;
	public DivNode(EveryThingInterface lefts, EveryThingInterface rights) {
		// TODO Auto-generated constructor stub
		left = (SensorNode) lefts;
		right = (SensorNode) rights;
	}

	@Override
	public int evaluateSens(Robot robot) {
		// TODO Auto-generated method stub
		return left.evaluateSens(robot)/right.evaluateSens(robot);
	}

}

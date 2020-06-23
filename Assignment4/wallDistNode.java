
public class wallDistNode implements SensorNode {
	public wallDistNode() {}
	@Override
	public int evaluateSens(Robot robot) {
		// TODO Auto-generated method stub
		return robot.getDistanceToWall();
	}
	public String toString() {
		return "Wall Distance";
	}

}

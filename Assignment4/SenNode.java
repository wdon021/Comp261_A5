
public class SenNode implements SensorNode {
	SensorNode child = null;
	public SenNode(SensorNode cn) {
		child = cn;
	}
	@Override
	public int evaluateSens(Robot robot) {
		// TODO Auto-generated method stub
		return child.evaluateSens(robot);
	}
	public String toString() {
		return "SenNode Node" + this.child;
	}

}

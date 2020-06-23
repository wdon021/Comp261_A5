
public class NumBrlsNode implements SensorNode {
	public NumBrlsNode() {}
	@Override
	public int evaluateSens(Robot robot) {
		// TODO Auto-generated method stub
		System.out.println("Number of Barrels: "+robot.numBarrels());
		return robot.numBarrels();
	}
	public String toString() {
		return "Number Node";
	}


}

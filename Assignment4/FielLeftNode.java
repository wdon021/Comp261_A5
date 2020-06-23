
public class FielLeftNode implements SensorNode {
	public FielLeftNode(){}
	@Override
	public int evaluateSens(Robot robot) {
		// TODO Auto-generated method stub
		return robot.getFuel();
	}
	public String toString() {
		return "Field Left";
	}


}

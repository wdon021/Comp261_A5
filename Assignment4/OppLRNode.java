
public class OppLRNode implements SensorNode {
	public OppLRNode() {}
	@Override
	public int evaluateSens(Robot robot) {
		// TODO Auto-generated method stub
		return robot.getOpponentLR();
	}
	public String toString() {
		return "get Opponent LR";
	}


}


public class OppFBNode implements SensorNode {
	public OppFBNode() {}
	@Override
	public int evaluateSens(Robot robot) {
		// TODO Auto-generated method stub
		return robot.getOpponentFB();
	}
	public String toString() {
		return "Get Opponent FB";
	}


}

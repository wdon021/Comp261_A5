
public class BarlLRNode implements SensorNode {
	SensorNode exp = null;
	public BarlLRNode() {}
	public BarlLRNode(EveryThingInterface exp2) {
		exp = (SensorNode) exp2;
		// TODO Auto-generated constructor stub
	}
	@Override
	public int evaluateSens(Robot robot) {
		if(exp!=null) {
			return robot.getBarrelLR(exp.evaluateSens(robot));
		}else {
		// TODO Auto-generated method stub
		return robot.getClosestBarrelLR();}
	}
	public String toString() {
		return "BarrelLR";
	}


}


public class brlFBNode implements SensorNode {
	SensorNode exp = null;
	public brlFBNode() {}
	public brlFBNode(EveryThingInterface exp2) {
		exp = (SensorNode) exp2;
		// TODO Auto-generated constructor stub
	}
	@Override
	public int evaluateSens(Robot robot) {
		// TODO Auto-generated method stub
		if(exp!=null) {
			return robot.getBarrelFB(exp.evaluateSens(robot));
		}else {
		return robot.getClosestBarrelFB();}
	}
	public String toString() {
		return "BarrelFB";
	}


}

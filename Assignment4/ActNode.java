
public class ActNode implements RobotProgramNode{
	private RobotProgramNode child;
	private SensorNode senChild = null;
	public ActNode(RobotProgramNode rpn, EveryThingInterface senChild2) {
		child = rpn;
		senChild = (SensorNode) senChild2;
	}
	public ActNode(RobotProgramNode RPN) {
		child = RPN;
	}
	
	
	public void execute(Robot robot) {
		if(senChild !=null) {
			int runTime = senChild.evaluateSens(robot)-1;
			while(runTime>0) {
				child.execute(robot);
				runTime--;
			}
		}
		child.execute(robot);
	}
	public String toString() {
		return "Act" + this.child;
	}

}

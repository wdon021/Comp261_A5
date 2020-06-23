
public class loopNode implements RobotProgramNode{
	private RobotProgramNode child;
	public loopNode(RobotProgramNode RPN) {
		child = RPN;
	}
	
	//private BlockNode blk = BlockNode(child);
	public void execute(Robot robot) {
		while(true) {
		child.execute(robot);}
	}
	public String toString() {
		return "Loop" + this.child;
	}


}

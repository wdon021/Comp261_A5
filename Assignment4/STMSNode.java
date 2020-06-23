
public class STMSNode implements RobotProgramNode{
	private RobotProgramNode child =null;

	public STMSNode(RobotProgramNode RPN) {
		child = RPN;
	}

	public void execute(Robot robot) {

		child.execute(robot);
	}
	public String toString() {
		return "STMT Node " + this.child.toString();
	}

}

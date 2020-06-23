
public class WhileNode implements RobotProgramNode{
	private RobotProgramNode RPNchild;
	private ConditionNode CNChild;
	public WhileNode(RobotProgramNode RPN, EveryThingInterface CN) {
		RPNchild = RPN;
		CNChild = (ConditionNode) CN;
	}

	public void execute(Robot robot) {
		while(CNChild.evaluate(robot)){
			RPNchild.execute(robot);
			}
		
	}
	public String toString() {
		return "While Node"+ this.RPNchild;
	}
}

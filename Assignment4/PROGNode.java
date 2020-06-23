import java.util.ArrayList;

public class PROGNode implements RobotProgramNode {
	ArrayList<RobotProgramNode> children = new ArrayList<RobotProgramNode>();
	public PROGNode(ArrayList<RobotProgramNode> RPN) {
		children = RPN;
	} 
	public void execute(Robot robot) {
		for(RobotProgramNode rpn : children) {
			rpn.execute(robot);
		}
	}
	public String toString() {
		return "PROG Node" + this.children;
	}

	
}

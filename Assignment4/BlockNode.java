import java.util.ArrayList;

public class BlockNode implements RobotProgramNode{
	private ArrayList<RobotProgramNode> children = new ArrayList<RobotProgramNode>();
	public BlockNode(ArrayList<RobotProgramNode> rpn) {
		children = rpn;
	}
	public void execute(Robot robot) {
		for(RobotProgramNode rpn : children) {
			rpn.execute(robot);
		}
	}
	public String toString() {
		return "BLOCK";
	}


}

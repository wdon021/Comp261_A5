
public class MoveNode implements RobotProgramNode{
	
	public MoveNode() {
	}
	public void execute(Robot robot) {
		robot.move();
	}
	public String toString() {
		return "Move Node";
	}


}

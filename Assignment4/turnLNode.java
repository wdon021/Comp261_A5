
public class turnLNode implements RobotProgramNode{
	public turnLNode() {
	}
	public void execute(Robot robot) {
		robot.turnLeft();
	}
	public String toString() {
		return "Turn Left";
	}

}

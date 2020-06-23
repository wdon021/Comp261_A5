
public class turnRNode implements RobotProgramNode{
	public turnRNode() {
	}
	public void execute(Robot robot) {
		robot.turnRight();
	}
	public String toString() {
		return "Turn Right";
	}
}

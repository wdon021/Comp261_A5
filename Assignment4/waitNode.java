
public class waitNode implements RobotProgramNode{
	public waitNode() {
	}
	public void execute(Robot robot) {
		robot.idleWait();
	}
	public String toString() {
		return "Wait";
	}

}

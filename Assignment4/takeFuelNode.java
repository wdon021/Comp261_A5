
public class takeFuelNode implements RobotProgramNode{
	public takeFuelNode() {
	}
	public void execute(Robot robot) {
		robot.takeFuel();
	}
	public String toString() {
		return "Take Fuel Node";
	}

}


public class TurnAroundNode implements RobotProgramNode {
	public TurnAroundNode() {}
	@Override
	public void execute(Robot robot) {
		// TODO Auto-generated method stub
		robot.turnAround();
	}
	public String toString() {
		return "Turn Around";
	}

}

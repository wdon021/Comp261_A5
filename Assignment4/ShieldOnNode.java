
public class ShieldOnNode implements RobotProgramNode {

	public ShieldOnNode() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(Robot robot) {
		// TODO Auto-generated method stub
		robot.setShield(true);
	}
	public String toString() {
		return "Shield On";
	}

}

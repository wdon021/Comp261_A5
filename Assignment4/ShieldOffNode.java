
public class ShieldOffNode implements RobotProgramNode {

	public ShieldOffNode() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(Robot robot) {
		// TODO Auto-generated method stub
		robot.setShield(false);
	}
	public String toString() {
		return "Shield Off";
	}

}

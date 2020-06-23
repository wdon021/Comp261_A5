
public class AndNode implements EveryThingInterface, ConditionNode {
	ConditionNode cnleft, cnright;
	
	public AndNode(EveryThingInterface left, EveryThingInterface right) {
		// TODO Auto-generated constructor stub
		cnleft = (ConditionNode) left;
		cnright = (ConditionNode) right;
	}

	@Override
	public boolean evaluate(Robot robot) {
		// TODO Auto-generated method stub
		return cnleft.evaluate(robot) & cnright.evaluate(robot);
	}

}

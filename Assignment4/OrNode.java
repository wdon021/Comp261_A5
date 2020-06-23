
public class OrNode implements EveryThingInterface, ConditionNode {
	private ConditionNode cnleft, cnright; 
	public OrNode(EveryThingInterface lefts, EveryThingInterface rights) {
		// TODO Auto-generated constructor stub
		cnleft = (ConditionNode) lefts;
		cnright = (ConditionNode) rights;
	}

	@Override
	public boolean evaluate(Robot robot) {
		// TODO Auto-generated method stub
		return cnleft.evaluate(robot) | cnright.evaluate(robot);
	}

}

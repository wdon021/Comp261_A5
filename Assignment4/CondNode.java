
public class CondNode implements ConditionNode{
	private String CNChild = null;
	private SensorNode sen = null;
	private SensorNode num = null;
	String cond = null;
	ConditionNode cdn = null;
	ConditionNode cdn2 = null;
	public CondNode(String CNC, SensorNode Sen, SensorNode Num) {
		CNChild = CNC;
		sen = Sen;
		num = Num;
	}
	
	public CondNode(String cond, ConditionNode cdn, ConditionNode cdn2) {
		// TODO Auto-generated constructor stub
		this.cond = cond;
		this.cdn = cdn;
		this.cdn2 = cdn2;
	}

	public CondNode(String cond2, ConditionNode cdn3) {
		// TODO Auto-generated constructor stub
		cond = cond2;
		cdn = cdn3;
	}

	@Override
	public boolean evaluate(Robot robot) {
		// TODO Auto-generated method stub
		if(CNChild !=null) {
		int senValue = sen.evaluateSens(robot);
		int numInt = num.evaluateSens(robot);
		if(CNChild.equals("eq")) {
			System.out.println("is the sen value equals numInt");
			return senValue == numInt;
		}else if(CNChild.equals("gt")) {
			System.out.println("is the sen value greater numInt");
			return senValue > numInt;
		}else if(CNChild.equals("lt")) {
			System.out.println("is the sen value lesser numInt");
			return senValue < numInt;
		}}else {
			if(cdn2 !=null) {
				if(cond.equals("and")) {
					return cdn.evaluate(robot) & cdn2.evaluate(robot);
				}else if(cond.equals("or")) {
					return cdn.evaluate(robot) | cdn2.evaluate(robot);
				}
			}else {
				return !cdn.evaluate(robot);
			}
		}
		return false;
	}
	public String toString() {
		return "Condition" + this.CNChild;
	}

	

}

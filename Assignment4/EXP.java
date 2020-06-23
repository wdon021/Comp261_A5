
public class EXP implements SensorNode {
	private SensorNode child = null;
	private SensorNode child2 = null;
	private String op;

	public EXP(SensorNode ssn) {
		child = ssn;
	}

	public EXP(String Op, SensorNode ssn1, SensorNode ssn2) {
		op = Op;
		child = ssn1;
		child2 = ssn2;
	}
	


	@Override
	public int evaluateSens(Robot robot) {
		// TODO Auto-generated method stub
		if(child2 != null) {
			if(op.equals("add")) {
				return child.evaluateSens(robot) + child2.evaluateSens(robot);
			}else if(op.equals("sub")) {
				return child.evaluateSens(robot) - child2.evaluateSens(robot);
			}else if(op.equals("mul")) {
				return child.evaluateSens(robot) * child2.evaluateSens(robot);
			}else if(op.equals("div")) {
				return child.evaluateSens(robot) / child2.evaluateSens(robot);
			}
		}
		return child.evaluateSens(robot);
	}

}

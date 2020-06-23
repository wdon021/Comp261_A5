import java.util.HashMap;

public class AssignNode implements RobotProgramNode{
	//private HashMap<String, SensorNode> variableMap = new HashMap<String, SensorNode>();

	private String varname = null;
	private SensorNode ssn = null;
	//NumNode nnd = null;
	public AssignNode(String var, EveryThingInterface ssn2) {
		varname = var;
		ssn = (SensorNode) ssn2;
		//nnd = new NumNode("0");
	}


	@Override
	public void execute(Robot robot) {
		// TODO Auto-generated method stub
		int value = ssn.evaluateSens(robot);
		robot.varMap.put(varname, value);
	}

}

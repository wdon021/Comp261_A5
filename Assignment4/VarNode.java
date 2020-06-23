
public class VarNode implements SensorNode {
	private String varname;
	public VarNode (String name) {
		varname = name;
	}
	@Override
	public int evaluateSens(Robot robot) {
		// TODO Auto-generated method stub
		
		if(robot.varMap.containsKey(varname)) {
			return robot.varMap.get(varname);
		}else {
			robot.varMap.put(varname, 0);
			return 0;
		}
	}

}

import java.util.ArrayList;

import java.util.stream.Collectors;

public class IfNode implements RobotProgramNode{
	private RobotProgramNode ifBlock = null;
	private RobotProgramNode elseBlock = null;
	private ConditionNode ifCondition = null;
	private ArrayList<ConditionNode> condi = new ArrayList<ConditionNode>();
	private ArrayList<RobotProgramNode> blocks = new ArrayList<RobotProgramNode>();

	public IfNode(RobotProgramNode RPN, EveryThingInterface CN,RobotProgramNode RPN2, ArrayList<EveryThingInterface> condis,ArrayList<RobotProgramNode> blockss ) {
		ifBlock = RPN;
		elseBlock = RPN2;
		ifCondition = (ConditionNode) CN;
		condi = (ArrayList<ConditionNode>) condis.stream().filter(element -> element instanceof EveryThingInterface).map(element ->(ConditionNode) element).collect(Collectors.toList());
		blocks = blockss;
	}

	public void execute(Robot robot) {
		
		if(ifCondition.evaluate(robot)) {
			ifBlock.execute(robot);
			return;
		}else {
		if(!condi.isEmpty()) {
			for(int i=0; i<condi.size();i++) {
				if(condi.get(i).evaluate(robot)) {
					blocks.get(i).execute(robot);
					return;
				}
			}
		}
		if(elseBlock != null) {
			
			elseBlock.execute(robot);}
		}
		
	}
	public String toString() {
		return "IF Node" ;
	}


}

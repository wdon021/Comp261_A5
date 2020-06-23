import java.util.Scanner;

public class BoolNode implements EveryThingInterface, ConditionNode {
	String st;
	public BoolNode(String s) {
		// TODO Auto-generated constructor stub
		st = s;
	}

	@Override
	public boolean evaluate(Robot robot) {
		// TODO Auto-generated method stub
		if(st.equals("true")) {
			return true;
		}
		return false;
	}

}

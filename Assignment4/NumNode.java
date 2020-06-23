
public class NumNode implements SensorNode{
	int number;
	public NumNode(String s) {
		number = Integer.parseInt(s);
	}
	@Override
	public int evaluateSens(Robot robot) {
		// TODO Auto-generated method stub
		return number;
	}
	public String toString() {
		return "Number Node" + this.number;
	}



}

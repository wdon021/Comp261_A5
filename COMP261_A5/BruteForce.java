
public class BruteForce {
	private String T;
	private String S;
	private int tLength;
	private int sLength;
	public BruteForce(String pattern, String text) {
		T = pattern;
		S = text;
		tLength = T.length();
		sLength = S.length();
	}
	
	public int search(String pattern, String text) {
		int patternLength = pattern.length();
		int textLength = text.length();
		for(int k = 0; k<= (textLength-patternLength); k++) {
			boolean found = true;
			for(int i = 0; i<patternLength; i++) {
				
				if(pattern.charAt(i)!=text.charAt(k+i)) {
					found = false;
					break;
				}
			}
			if(found) {
				//System.out.println("BruteForce Found in: "+k);
				return k;
			}
		}
		return -1;
	}
	
	
}

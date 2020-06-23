import java.util.ArrayList;

/**
 * A new KMP instance is created for every substring search performed. Both the
 * pattern and the text are passed to the constructor and the search method. You
 * could, for example, use the constructor to create the match table and the
 * search method to perform the search itself.
 */
public class KMP {
	private String texts;
	private String patterns;
	private int[] M;
	public KMP(String pattern, String text) {
		// TODO maybe fill this in.
		texts = text;
		patterns = pattern;
	}
	private void buildM() {
		int m = patterns.length();
		M = new int[m];
		if(m == 1) {
			M[0] = -1;
			return;
		}
		M[0] = -1;
		M[1] = 0;
		int j = 0;
		int pos = 2;
		while(pos<m) {
			if(patterns.charAt(pos-1)==patterns.charAt(j)) {
				M[pos] = j+1;
				pos++;
				j++;
			}else if(j>0) {
				j = M[j];
			}else {
				M[pos] = 0;
				pos++;
			}
		}
	}

	/**
	 * Perform KMP substring search on the given text with the given pattern.
	 * 
	 * This should return the starting index of the first substring match if it
	 * exists, or -1 if it doesn't.
	 */
	public int search(String pattern, String text) {
		int k = 0;
		int i = 0;
		int n = text.length();
		int m = pattern.length();
		this.buildM();
		
		while((k+i) < n) {
			if(pattern.charAt(i) == text.charAt(k+i)) {
				i = i+1;
				if(i == m) {
					return k;
				}
			} else if(M[i] == -1) {
				k = k+i+1;
				i=0;
			}else {
				k = k+i-M[i];
				i = M[i];
			}
		}
		// TODO fill this in.
		return -1;
	}
}

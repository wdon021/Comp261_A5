import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * A new instance of LempelZiv is created for every run.
 */
public class LempelZiv {
	/**
	 * Take uncompressed input as a text string, compress it, and return it as a
	 * text string.
	 */
	
	public String compress(String input) {
		// TODO fill this in.
		int cursor = 0;
		int windowSize = 127;
		int textSize = input.length();
		//String compressed = "";
		StringBuilder sbc = new StringBuilder();
		while(cursor<textSize) {
			int length = 0;
			int prevMatch = 0;
			while(true) {
			String pattern = input.substring(cursor, ((cursor+length)<textSize)?(cursor+length+1):cursor+length);
			//String pattern = input.substring(0, 1);
			//System.out.println("pattern: "+pattern);
			//System.out.println("pattern cursor: "+cursor);
			//System.out.println("pattern cursor+length: "+(cursor+length+1));
			String windows = input.substring((cursor<windowSize)?0:cursor-windowSize, ((cursor<1)?0:(cursor-1+1)));
			//String windows = input.substring(0, 1);
			//System.out.println("windows: "+windows);
			//System.out.println("windows cursor: "+((cursor<windowSize)?0:cursor-windowSize));
			//System.out.println("windows cursor end: "+(cursor-((cursor<1)?0:1)+1));
			int index = new BruteForce(pattern, windows).search(pattern, windows);
			//System.out.println("index:"+index);
			if(index >=0) {
				prevMatch = cursor-(index+((cursor<windowSize)?0:cursor-windowSize));//index
				//System.out.println("prevMatch: "+prevMatch);
				length = length +1;
				if(cursor>=textSize-1) {
					//System.out.println("["+prevMatch+", "+length+", "+"end"+"]");
					//System.out.println("I am in the cursor>=textSize-1");
					String ends = "["+prevMatch+","+length+","+"|"+"]";
					sbc.append(ends);
					//compressed = compressed + ends;
					cursor = cursor+2;
					break;
				}
				if(cursor+length>=textSize-1) {
					//System.out.println("I am in the cursor+length>=textSize-1");
					String end = "["+prevMatch+","+length+","+input.charAt(cursor+length)+"]";
					//compressed = compressed + end;
					sbc.append(end);
					//System.out.println("endString: "+compressed);
					return sbc.toString();
				}
			}else {
				//System.out.println("["+prevMatch+", "+length+", "+input.charAt(cursor+length)+"]");
				//System.out.println("I am in the else");
				String outputs = "["+prevMatch+","+length+","+input.charAt(cursor+length)+"]";
				sbc.append(outputs);
				//compressed = compressed + outputs;
				cursor = cursor + length+1;
				//System.out.println("cursor:"+cursor);
				break;
			}
			}
		}
		//System.out.println("endString: "+compressed);
		return sbc.toString();
	}

	/**
	 * Take compressed input as a text string, decompress it, and return it as a
	 * text string.
	 */
	public String decompress(String compressed) {
		// TODO fill this in.
		int cursor = 0;
		Scanner s = new Scanner(compressed).useDelimiter("");
		//System.out.println("what do you use?: "+s.delimiter());
		//String[] decoded = null;
		ArrayList<String> decoded = new ArrayList<String>();
		while(s.hasNext()) {
			s.next();			
			String st1 = "";
			while(s.hasNextInt()) {
				st1 = st1+s.next();
			}
			int prev = Integer.parseInt(st1);
			
			s.next();
			String st2 = "";
			while(s.hasNextInt()) {
				st2 = st2+s.next();
			}
			int length = Integer.parseInt(st2);
			s.next();
			String chars = "";
			if(s.hasNext("\\|")) {
				chars = "";
				s.next();
			}else {
				chars = s.next();
			}
			s.next();
			if(prev ==0 & length ==0) {
				//decoded[cursor] = chars;
				decoded.add(cursor, chars);
				cursor++;
			}else {
				for(int j =0; j<length;j++) {
					//decoded[cursor] = decoded[cursor-prev];
					decoded.add(cursor, decoded.get(cursor-prev));
					cursor++;
				}
				decoded.add(cursor, chars);
				//decoded[cursor] = chars;
				cursor++;
			}
		}
		//String finalDecoded = "";
		StringBuilder sbc = new StringBuilder();
		for(String st:decoded) {
			//finalDecoded = finalDecoded +st;
			sbc.append(st);
		}
		//System.out.println(finalDecoded);
		return sbc.toString();
	}

	/**
	 * The getInformation method is here for your convenience, you don't need to
	 * fill it in if you don't want to. It is called on every run and its return
	 * value is displayed on-screen. You can use this to print out any relevant
	 * information from your compression.
	 */
	public String getInformation() {
		return "";
	}
}

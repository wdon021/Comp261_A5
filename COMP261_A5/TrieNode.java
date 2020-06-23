
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TrieNode implements Comparable<TrieNode>{
	public String nodeName = null;
	public Character word = null;
	public int frequency;
	public TrieNode Parent = null;
	public TrieNode leftChild = null;
	public TrieNode rightChild = null;
	public String code = "";
	public Character TrieCharacter = null; 
	public HashMap<Character, TrieNode> children = new HashMap<Character, TrieNode>();
	
	public TrieNode(Character words, int freq) {
		word = words;
		frequency = freq;
	}
	public TrieNode(int freq) {
		frequency = freq;
	}
	public TrieNode() {
		// TODO Auto-generated constructor stub
	}
	public int compareTo(TrieNode other) {
		if(this.frequency>other.frequency) {return 1;}
		else if(this.frequency == other.frequency) {return 0;}
		return -1;
	}
	public void insert(String codes, char c) {
		TrieNode words = this;
		for(int i = 0; i < codes.length(); i++) {
			char cs = codes.charAt(i);
			//System.out.println("working on insert"); 
			if(words.children.get(cs)==null) {
				words.children.put(cs, new TrieNode());
			}
			words = words.children.get(cs);
		}
		words.TrieCharacter = c;
		
	}

	public StringBuilder getAll(String prefix){
		//List<Character> results = new ArrayList<Character>();
		StringBuilder sbc  = new StringBuilder();
		TrieNode words = this;
		String saveString = prefix;
		for(int i =0; i<saveString.length(); i++) {
			//System.out.println("Current Curser: "+ i);
			char c = saveString.charAt(i);
			//System.out.println("working on getAll: "+ c); 
			if(words.children.get(c)==null) {
				return null;
			}
			words = words.children.get(c);
			if(words.TrieCharacter!=null) {
				//System.out.println("getting character "+ words.TrieCharacter);
				//results.add(words.TrieCharacter);
				sbc.append(words.TrieCharacter);
				//saveString = saveString.substring(i+1);

				//System.out.println("How much I got left "+ saveString.length());
				words = this;
				//i = -1;
			}
		}
		return sbc;
	}
//	private void getAllFrom(TrieNode words, List<Character> results) {
//		// TODO Auto-generated method stub
//		results.add(words.TrieCharacter);
//		System.out.println("getAllFrom "+ words.TrieCharacter);
//		for(TrieNode tn: words.children.values()) {
//			getAllFrom(tn, results);
//		}
//		
//	}
	
	
	

}

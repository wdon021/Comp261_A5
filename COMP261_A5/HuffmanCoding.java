import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * A new instance of HuffmanCoding is created for every run. The constructor is
 * passed the full text to be encoded or decoded, so this is a good place to
 * construct the tree. You should store this tree in a field and then use it in
 * the encode and decode methods.
 */
public class HuffmanCoding {
	/**
	 * This would be a good place to compute and store the tree.
	 */
	private TrieNode root;
	private TrieNode tn = new TrieNode();
	public PriorityQueue<TrieNode> pq = new PriorityQueue<TrieNode>();
	private String texts;
	private HashMap<Character, String> encoder = new HashMap<Character, String>();
	private HashMap<Character, Integer> frequency = new HashMap<Character, Integer>();
	//private String encodedText = "";

	public HuffmanCoding(String text) {
		// TODO fill this in.
		texts = text;
	}

	/**
	 * Take an input string, text, and encode it with the stored tree. Should
	 * return the encoded text as a binary string, that is, a string containing
	 * only 1 and 0.
	 */
	public String encode(String text) {
		for(int i=0; i<text.length(); i++) {
			char c = text.charAt(i);
			if(!frequency.containsKey(c)) {
				frequency.put(c, 1);
			}else {
				frequency.put(c, frequency.get(c)+1);
			}			 
		}
		for(char keys:frequency.keySet()) {
			//TrieNode Node = new TrieNode(c, frequency.get(keys));
			pq.add(new TrieNode(keys, frequency.get(keys)));
		}
		while(pq.size()>1) {
			TrieNode left= pq.poll();
			TrieNode right = pq.poll();
			TrieNode parent = new TrieNode((left.frequency+right.frequency));
			parent.nodeName = ""+left.word + right.word;
			parent.leftChild = left;
			parent.rightChild = right;
			left.Parent = parent;
			right.Parent = parent;
			pq.add(parent);
			if(pq.size()<2) {
				this.root = parent; 
			}
		}
		Queue<TrieNode> todo = new ArrayDeque<TrieNode>();
		todo.offer(root);
		while(!todo.isEmpty()) {
			TrieNode tr = todo.poll();
			if(tr.leftChild!=null) {
				todo.offer(tr.leftChild);
				tr.leftChild.code = tr.code+"0";
				if(tr.leftChild.word!=null) {// or I can use if word.length() == 1
					encoder.put(tr.leftChild.word, tr.leftChild.code);
					tn.insert(tr.leftChild.code, tr.leftChild.word);
					System.out.println("left Character: "+tr.leftChild.word+ " code: "+ tr.leftChild.code);
				}
			}
			if(tr.rightChild!=null) {
				todo.offer(tr.rightChild);
				tr.rightChild.code = tr.code + "1";
				if(tr.rightChild.word!=null) {
					encoder.put(tr.rightChild.word, tr.rightChild.code);
					tn.insert(tr.rightChild.code, tr.rightChild.word);
					System.out.println("right Character: "+tr.rightChild.word+ " code: "+ tr.rightChild.code);
				}
			}
		}
		
		StringBuilder sb = new StringBuilder();
		
		for(int i =0; i<text.length();i++) {
			//encodedText = encodedText + encoder.get(text.charAt(i));
			sb.append(encoder.get(text.charAt(i)));
		}
		System.out.println("Encoded finish ");
		
		
		// TODO fill this in.
		return sb.toString();
	}

	/**
	 * Take encoded input as a binary string, decode it using the stored tree,
	 * and return the decoded text as a text string.
	 */
	public String decode(String encoded) {
		// TODO fill this in.
		
		//String decoded="";
		System.out.println("decode start");
		
		StringBuilder sbc  = tn.getAll(encoded);
//		StringBuilder sbc = new StringBuilder();
//		TrieNode rootNode = pq.peek();
//		int len = encoded.length();
//		for(int i =0; i<len; i++) {
//			if(encoded.charAt(i) == '0') {
//				rootNode = rootNode.leftChild;
//			}else {
//				rootNode = rootNode.rightChild;
//			}
//			
//			if(rootNode.leftChild == null) {
//				sbc.append(rootNode.word);
//				rootNode = pq.peek();
//			}
//		}
		//List<Character> found = tn.getAll(encoded);
//		for(Character c: found) {
//			//decoded = decoded+c;
//			sbc.append(c);
//		}
		System.out.println("decoded finish ");
		return sbc.toString();
	}

	/**
	 * The getInformation method is here for your convenience, you don't need to
	 * fill it in if you don't wan to. It is called on every run and its return
	 * value is displayed on-screen. You could use this, for example, to print
	 * out the encoding tree.
	 */
	public String getInformation() {
		return "";
	}
}

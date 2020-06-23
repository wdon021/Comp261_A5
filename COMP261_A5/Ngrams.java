/**
 * Ngrams predictive probabilities for text
 */
import java.lang.Float;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class Ngrams {
	/**
	 * The constructor would be a good place to compute and store the Ngrams probabilities.
	 * Take uncompressed input as a text string, and store a List of Maps. The n-th such
	 * Map has keys that are prefixes of length n. Each value is itself a Map, from 
	 * characters to floats (this is the probability of the char, given the prefix).
	 */
	public List<HashMap<String,HashMap<Character, Float>>> ngram = new ArrayList<HashMap<String,HashMap<Character, Float>>>();;  /* nb. suggestion only - you don't have to use 
                                                     this particular data structure */
	ArrayList<Integer> nlist = new ArrayList<Integer>();
	public Ngrams() {}
	public void Ngramsd(String input) {
		//int n = 0;
		List<Integer> ns = Collections.unmodifiableList(Arrays.asList(0,1,2,3,4,5));

		for(int n: ns) {
			HashMap<String , HashMap<Character, Integer>> counts = new HashMap<String, HashMap<Character, Integer>>();
			HashMap<String , HashMap<Character, Float>> countsFloat = new HashMap<String, HashMap<Character, Float>>();
			System.out.println("n equals: "+n);
			for(int i=0; i<input.length()-1-n; i++) {

				String prefix = input.substring(i, i+n);
				//System.out.println("My input prefix: " +prefix);	
				Character c = input.charAt(i+n);
				//System.out.println("My input character: " +c);	
				if(counts.containsKey(prefix)) {
					//System.out.println("I Contained the prefix: "+ prefix);
					HashMap<Character, Integer> entrys = counts.get(prefix);
					if(entrys.containsKey(c)) {
						entrys.put(c, entrys.get(c)+1);
						//System.out.println("I am in old prefix "+prefix+" old Character: "+ c);
					}else {
						entrys.put(c, 1);
						//System.out.println("I am in old prefix "+prefix+" new Character: "+ c);
					}
				}else {
					//System.out.println("I am in new prefix "+prefix+" new Character: "+ c);
					HashMap<Character, Integer> charpro = new HashMap<Character, Integer>();
					charpro.put(c, 1);
					counts.put(prefix, charpro);

				}
			}
			for(String st: counts.keySet()) {
			    //System.out.println("String of key: "+st);
				
				countsFloat.put(st, probs(counts.get(st)));
			}

			ngram.add(countsFloat);
		}

		// TODO fill this in.
	}
	private HashMap<Character, Float> probs(HashMap<Character, Integer> hist){
		HashMap<Character, Float> actual = new HashMap<Character, Float>();
		int total = 0;
		for(int i : hist.values()) {
			total = total+i;
			//System.out.println("total: "+ total);
		}
		for(Character c: hist.keySet()) {
			//System.out.println("char: "+c+" float: "+ ((float) hist.get(c) / total));
			
			actual.put(c, ((float) hist.get(c) / total));
		}
		return actual;

	}

	/**
	 * Take a string, and look up the probability of each character in it, under the Ngrams model.
	 * Returns a List of Floats (which are the probabilities).
	 */
	public List <Float> findCharProbs(String mystring) {
		List<Float> actualProbs = new ArrayList<Float>();
		int n =5;

		for(int i =0; i<=n-1;i++) {
			String prefix1 = mystring.substring(0, i);
			char char2 = mystring.charAt(i);
			probFinder(prefix1,char2, i, actualProbs);
		}
		
		for(int i=0; i<mystring.length()-n;i++) {
			String prefix = mystring.substring(i, i+n);
			char charac = mystring.charAt(i+n);
			//System.out.println("prefix in findcharprob: "+prefix );
			//System.out.println("character in findcharprob: "+charac);
			probFinder(prefix,charac, 5, actualProbs);
			
		}
		
		// TODO fill this in.
		return actualProbs;
	}
	private void probFinder(String pre, char c,int n, List<Float> lf) {
		if(n>=0) {
		HashMap<String, HashMap<Character, Float>> ngramFive = ngram.get(n);
			if(ngramFive.containsKey(pre)) {
				System.out.println("I have the prefix: "+pre);
				HashMap<Character, Float> ngramFiveChar = ngramFive.get(pre);
				if(ngramFiveChar.containsKey(c)) {
					System.out.println("I found it the character: "+c);
					System.out.println("I found it the character in n = "+n);
					nlist.add(n);
					System.out.println("I found it the character probability"+ ngramFiveChar.get(c));
					lf.add(ngramFiveChar.get(c));
					return;
				}else {
					System.out.println("recursive have the prefix: "+pre+" but does not contain the char: "+c);
					probFinder(pre.substring(1), c, n-1,lf);
				}
			}else {
				System.out.println("recursive does not contain prefix: "+pre);
				probFinder(pre.substring(1), c, n-1,lf);
			}
		}

	}

	/**
	 * Take a list of probabilites (floats), and return the sum of the logs (base 2) in the list.
	 */
	public float calcTotalLogProb(List<Float> charProbs) {
		// TODO fill this in.
		float logSum = 0;
		//float total = 0;
		for(float num: charProbs) {
			logSum = logSum + log(num, 2);
			//total = total + num*log((1/num), 2);
			//logSum = logSum + (float) Math.log(num);
		}
		
		//System.out.println("Total bit string: "+ total);
		return logSum;
	}
	private Float log(Float x, int base) {
		return (float) (Math.log(x)/Math.log(base));
	}
}

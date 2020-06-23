import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


public class BoyerMooreSearch {
	private int characterSize = 256;
	//calculate bad character shift of pattern
	public void PreprocessBadChar(String pattern, int m, int[] badc) {
		
		for(int i =0; i<characterSize;i++) {
			badc[i] = m;
			System.out.println("I am in PreprocessBadChar loop 1");
		}
		for(int i = 0; i<m-1; i++) {
			badc[pattern.charAt(i)] = m - i -1;
			System.out.println("I am in PreprocessBadChar loop 2");
			
		}
		
	}
	
	public void CalculateSuffixes(String pattern, int m, int[] suff) {
		int f =0;
		char[] pat = pattern.toCharArray();
		suff[m-1] = m;
		int g = m-1;
		
		for(int i = m-2; i>=0;i--) {
			System.out.println("I am in CalculateSuffixes loop 1");
			if(i>g & suff[i+m-1-f]<i-g) {
				suff[i] =suff[i+m-1-f];
			}else {
				if(i<g) {
					g =i;
				}
				f=i;
				while(g>=0 && pat[g]==pat[g+m-1-f]) {
					System.out.println("I am in CalculateSuffixes while 1");
					g--;
				}
				suff[i] = f- g;
			}
		}
		//return suff;
	}
	
	//calculate good suffix shift within the pattern
	public void preprocessGoodSuffix(String pattern, int m, int[] goodsf) {
		int [] suff = new int[pattern.length()];
		this.CalculateSuffixes(pattern, m, suff);
		for(int i =0; i <m; i++) {
			goodsf[i] = m;
			System.out.println("I am in preprocessGoodSuffix loop 1");
		}
		
		for(int i = m-1; i>=0;i--) {
			System.out.println("I am in preprocessGoodSuffix loop 2");
			if(suff[i] ==i+1) {
				for(int j = 0;j<m-1-i;j++) {
					System.out.println("I am in preprocessGoodSuffix loop 3");
					if(goodsf[j]==m) {
						goodsf[j] = m - 1 -i;
					}
				}
			}
		}
		for(int i =0; i<=m-2;i++) {
			System.out.println("I am in preprocessGoodSuffix loop 4");
			goodsf[m-1-suff[i]]=m-1-i;
		}
		
	}
	
	//put it all togather
	public void boyerMoore(String pattern, int m, String text, int n) {
//		m = pattern.length();
//		n = text.length();
		int [] goodsuff = new int [pattern.length()];
		int [] badChar = new int[characterSize];
		PreprocessBadChar(pattern, m, badChar);
		preprocessGoodSuffix(pattern, m, goodsuff);
		char [] pat = pattern.toCharArray();
		char [] txt = text.toCharArray();
		int j =0;
		while(j<n-m) {
			System.out.println("I am in boyerMoore while 1 :"+ j );
			for(int i=m-1; i>=0 && pat[i]== txt[i+j];i--) {
				System.out.println("I am in boyerMoore loop 1");
				if(i<0) {
					System.out.println("I want to print"+j);
					j = j+goodsuff[0];
				}else {
					j = j+ Math.max(goodsuff[i], badChar[txt[i+j]]-m+1+i);
				}
			}
		}
		
	}
	
		
	}

	
	
	



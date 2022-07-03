package pl.kretkowl.fuzzyt;

import static java.lang.Math.abs;
import static java.lang.Math.min;

import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
 
public class Levenshtein {
 
    private final static Pattern wordBoundary = Pattern.compile("[^\\w]+");

    static String tokenSortString(String s) {
        return Arrays.stream(wordBoundary.split(s))
            .sorted()
            .collect(Collectors.joining(""));
    }

    public static float tokenSortRatio(String a, String b) {
        return ldRatio(tokenSortString(a), tokenSortString(b));

    }

    public static float ldRatio(String a, String b) {
        float len = a.length() + b.length();

        return (len - ld(a, b))/len;
    }

	public static int ld(String a, String b) { 
		return distance(a, b, -1);
	}

    /*
     * stolen from rosetta code
     */
	private static int distance(String a, String b, int max) {
		if (a == b) return 0;
		int la = a.length();
		int lb = b.length();
		if (max >= 0 && abs(la - lb) > max) return max+1;
		if (la == 0) return lb;
		if (lb == 0) return la;
		if (la < lb) {
			int tl = la; la = lb; lb = tl;
			String ts = a;  a = b; b = ts;
		}
 
		int[] cost = new int[lb+1];
		for (int i=0; i<=lb; i+=1) {
			cost[i] = i;
		}
 
		for (int i=1; i<=la; i+=1) {
			cost[0] = i;
			int prv = i-1;
			int min = prv;
			for (int j=1; j<=lb; j+=1) {
				int act = prv + (a.charAt(i-1) == b.charAt(j-1) ? 0 : 1);
				cost[j] = min(1+(prv=cost[j]), min(1+cost[j-1], act));
				if (prv < min) min = prv;
			}
			if (max >= 0 && min > max) return max+1;
		}
		if (max >= 0 && cost[lb] > max) return max+1;
		return cost[lb];	
	}
}

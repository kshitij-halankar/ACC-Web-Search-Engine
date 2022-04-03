package websearchengine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;



public class Editdistance {
//	public static HashMap<String, String> links = new HashMap<>();
	public static List<String> links = new ArrayList<>();
//	static Scanner obj = new Scanner(System.in);

	public static void searching(ArrayList<String> dictionary, String word) {

		// HashMap<Integer, String> people = new HashMap<Integer, String>();

		ArrayList<String> listedited = new ArrayList<>();

		//Collections.sort(dictionary);

		for (String s : dictionary) {

			if (editDistance(s, word) == 1) {
				listedited.add(s);
			}

		}
		System.out.println(listedited);
	}

	public static int editDistance(String word1, String word2) {
		int len1 = word1.length();
		int len2 = word2.length();

		// len1+1, len2+1, because finally return dp[len1][len2]
		int[][] dp = new int[len1 + 1][len2 + 1];

		for (int i = 0; i <= len1; i++) {
			dp[i][0] = i;
		}

		for (int j = 0; j <= len2; j++) {
			dp[0][j] = j;
		}

		// iterate though, and check last char
		for (int i = 0; i < len1; i++) {
			char c1 = word1.charAt(i);
			for (int j = 0; j < len2; j++) {
				char c2 = word2.charAt(j);

				// if last two chars equal
				if (c1 == c2) {
					// update dp value for +1 length
					dp[i + 1][j + 1] = dp[i][j];
				} else {
					int replace = dp[i][j] + 1;
					int insert = dp[i][j + 1] + 1;
					int delete = dp[i + 1][j] + 1;

					int min = replace > insert ? insert : replace;
					min = delete > min ? min : delete;
					dp[i + 1][j + 1] = min;
				}
			}
		}

		return dp[len1][len2];
	}


}

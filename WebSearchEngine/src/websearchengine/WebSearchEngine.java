package websearchengine;

import java.util.ArrayList;
import java.util.HashMap;

public class WebSearchEngine {

	public static void main(String[] args) {
		InvertedIndex invertedIndex = new InvertedIndex();

//		web crawl - recursion
//		regex - 2 times | visited ignore
//		html to text - Jsoup
		String urlToCrawl = "https://www.geeksforgeeks.org/data-structures";
		Crawler c = new Crawler();
		c.crawl(urlToCrawl, invertedIndex);
//		store - trie

//		inverted index - 

		// Printing the words present in the dictionary
		invertedIndex.createDictionary();
		System.out.println("Number of words: " + invertedIndex.getDictionary().size());
		ArrayList<String> dictionary = invertedIndex.getDictionary();
		/*
		 * for (int i = 0; i < dictionary.size(); i++) {
		 * System.out.println(dictionary.get(i)); }
		 */

		// Storing the trie object in output file
		invertedIndex.createSerializableFile();
		// Taking input from serializable file
		Trie inputTrie = invertedIndex.readFromSerializableFile();

		// Printing the links in which the word tutorialspoint is present along with the
		// frequency
		if (inputTrie.search("geeksforgeeks", inputTrie.root) == true) {
			HashMap<String, Integer> tempMap = inputTrie.find("geeksforgeeks", inputTrie.root).wordObject
					.getindicesHolder();
			for (String strKey : tempMap.keySet()) {
				System.out.print("tutorialspoint" + " : Key  : " + strKey + "........");
				System.out.print(tempMap.get(strKey) + "\n");
			}
		} else {
			System.out.println("Not Found");
		}
//		sort as per frequency - merge sort

//		search - pattern matching on cache - Boyer Moore

//		cache sort - LRU

//		if not found dictionary - edit distance

	}

}

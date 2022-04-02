package websearchengine;

import static websearchengine.Functions.quickSort;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class WebSearchEngine {
	static Scanner obj = new Scanner(System.in);

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
		ArrayList<String> dictionary = invertedIndex.getDictionary();
		for (int i = 0; i < dictionary.size(); i++) {
			if (invertedIndex.trie.search(invertedIndex.trie.dictionary.get(i), invertedIndex.trie.root)) {

				IndexObject indexObject = invertedIndex.trie.find(invertedIndex.trie.dictionary.get(i),
						invertedIndex.trie.root).wordObject;
				quickSort(indexObject, 0, indexObject.indicesHolder.size() - 1);
				invertedIndex.trie.setIndexObject(invertedIndex.trie.root, indexObject);

				for (int j = 0; j < indexObject.indicesHolder.size(); j++) {
					System.out.println(
							invertedIndex.trie.dictionary.get(i) + " : Key  : " + indexObject.indicesHolder.get(j).url
									+ "........" + indexObject.indicesHolder.get(j).frequency);
				}

			}

		}

		// Storing the trie object in output file
		invertedIndex.createSerializableFile();
		// Taking input from serializable file
		Trie inputTrie = invertedIndex.readFromSerializableFile();

		// Printing the links in which the word tutorialspoint is present along with the
		// frequency
		// frequency
		if (inputTrie.search("geeksforgeeks", inputTrie.root) == true) {
			ArrayList<LinkIndex> tempMap = inputTrie.find("geeksforgeeks", inputTrie.root).wordObject
					.getindicesHolder();
			for (LinkIndex temp : tempMap) {
				System.out.print("geeksforgeeks" + " : Key  : " + temp.url + "........");
				System.out.print(temp.frequency + "\n");
			}
		} else {
			System.out.println("Not Found");
		}
//		sort as per frequency - merge sort

//		search - pattern matching on cache - Boyer Moore

//		cache sort - LRU

//		if not found dictionary - edit distance

		// Editdistance
		Editdistance editdistance = new Editdistance();
		System.out.println("Enter a word that needs to be searched");
		String uword = obj.next();
		Editdistance.searching(dictionary, uword);
	}

}

package websearchengine;

import static websearchengine.Functions.quickSort;

import java.util.ArrayList;
import java.util.Scanner;

public class WebSearchEngine {
	static Scanner obj = new Scanner(System.in);
	static Cache searchCache = new Cache();

	public static void main(String[] args) {
		InvertedIndex invertedIndex = new InvertedIndex();

		// web crawl - recursion
		// regex - 2 times | visited ignore
		// html to text - Jsoup
		String urlToCrawl = "https://www.geeksforgeeks.org/data-structures";
		Crawler c = new Crawler();
		c.crawl(urlToCrawl, invertedIndex);
		// store - trie
		// inverted index -
		// Printing the words present in the dictionary
		invertedIndex.createDictionary();
		ArrayList<String> dictionary = invertedIndex.getDictionary();
		for (int i = 0; i < dictionary.size(); i++) {
			if (invertedIndex.trie.search(invertedIndex.trie.dictionary.get(i), invertedIndex.trie.root)) {

				IndexObject indexObject = invertedIndex.trie.find(invertedIndex.trie.dictionary.get(i),
						invertedIndex.trie.root).wordObject;
				// sort as per frequency - merge sort
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

		// test
		while (true) {
			Scanner sc = new Scanner(System.in);
			System.out.println("Enter word to search: ");
			String searchWord = sc.next();
			if (searchWord.equals("EXIT")) {
				break;
			}
			// search - pattern matching on cache - Boyer Moore
			// check cache
			ArrayList<LinkIndex> cachedLinks = searchCache.fetchCache(searchWord);
			if (cachedLinks != null && !cachedLinks.isEmpty()) {
				// show results
				searchCache.updateCache(searchWord, cachedLinks);
				for (LinkIndex temp : cachedLinks) {
					System.out.print(searchWord + " : Key  : " + temp.url + "........");
					System.out.print(temp.frequency + "\n");
				}
			} else if (dictionary.contains(searchWord)) {
				// if word not present in cache - check dictionary and fetch from inverted index
				// Taking input from serializable file
				Trie inputTrie = invertedIndex.readFromSerializableFile();

				// Printing the links in which the word tutorialspoint is present along with the
				// frequency
				// frequency
				if (inputTrie.search(searchWord, inputTrie.root) == true) {
					ArrayList<LinkIndex> tempMap = inputTrie.find(searchWord, inputTrie.root).wordObject
							.getindicesHolder();
					// and update cache
					// cache sort - LRU
					searchCache.updateCache(searchWord, tempMap);

					// fetch word from cache
					for (LinkIndex temp : tempMap) {
						System.out.print(searchWord + " : Key  : " + temp.url + "........");
						System.out.print(temp.frequency + "\n");
					}
				} else {
					System.out.println("Not Found");
				}
			} else {
				// if not found dictionary - edit distance
				// Editdistance
				Editdistance editdistance = new Editdistance();
				System.out.println("Enter a word that needs to be searched");
				String uword = obj.next();
				Editdistance.searching(dictionary, uword);
			}
		}
	}
}

package websearchengine;

import java.util.ArrayList;
import java.util.Scanner;

public class WebSearchEngine {
	static Scanner obj = new Scanner(System.in);
	static Cache searchCache = new Cache();

	public static void main(String[] args) {
		WebSearchEngine wb = new WebSearchEngine();
		InvertedIndex invertedIndex = new InvertedIndex();
		Trie inputTrie = invertedIndex.readFromSerializableFile();
		if (inputTrie == null) {
			// web crawl - recursion
			// regex - 2 times | visited ignore
			// html to text - Jsoup
			wb.crawl(invertedIndex);
		}
		ArrayList<String> dictionary = invertedIndex.createDictionary();
//		ArrayList<String> dictionary = invertedIndex.getDictionary();
		// test
//		while (true) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter word to search: ");
		String searchWord = sc.next();
//			if (searchWord.equals("EXIT")) {
//				break;
//			}
		String[] searchWords = searchWord.split("\\W+");

		// } else if (dictionary.contains(searchWord)) {
		// if word not present in cache - check dictionary and fetch from inverted index
		// Taking input from serializable file
//			Trie inputTrie = invertedIndex.readFromSerializableFile();
		// Printing the links in which the word tutorialspoint is present along with the
		// frequency
		if (!wb.searchInCache(searchWord) && !wb.searchInTrie(inputTrie, searchWord)) {
			wb.checkSpelling(dictionary, searchWord);
		}
	}

	public void crawl(InvertedIndex invertedIndex) {
		String urlToCrawl = "https://www.geeksforgeeks.org/data-structures";
		Crawler c = new Crawler();
		c.crawl(urlToCrawl, invertedIndex);
		// store - trie
		// inverted index -
		// Printing the words present in the dictionary
		invertedIndex.createDictionary();
		ArrayList<String> dictionary = invertedIndex.getDictionary();

		// Storing the trie object in output file
		invertedIndex.sortLinkIndex();
		invertedIndex.createSerializableFile();
	}

	public void search() {

	}

	public boolean searchInCache(String searchWord) {
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
			return true;
		}
		return false;
	}

	public boolean searchInTrie(Trie inputTrie, String searchWord) {
		if (inputTrie.search(searchWord, inputTrie.root) == true) {
			ArrayList<LinkIndex> tempMap = inputTrie.find(searchWord, inputTrie.root).wordObject.getindicesHolder();

			// fetch word from cache
			ArrayList<LinkIndex> tempMap2 = new ArrayList<LinkIndex>();
			int pageCount = tempMap.size() < 5 ? tempMap.size() : 5;
			for (int i = 0; i < pageCount; i++) {
				tempMap2.add(tempMap.get(i));
			}
			// and update cache
			// cache sort - LRU
			searchCache.updateCache(searchWord, tempMap2);
			// show results
			// searchCache.updateCache(searchWord, cachedLinks);
			for (LinkIndex temp : searchCache.fetchCache(searchWord)) {
				System.out.print(searchWord + " : Key  : " + temp.url + "........");
				System.out.print(temp.frequency + "\n");
			}
			return true;
		}
		return false;
	}

	public void checkSpelling(ArrayList<String> dictionary, String searchWord) {
		// if not found dictionary - edit distance
		// Editdistance
		Editdistance editdistance = new Editdistance();
		Editdistance.searching(dictionary, searchWord);
	}

}

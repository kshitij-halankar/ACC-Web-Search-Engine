package websearchengine;

import java.util.ArrayList;
import java.util.Scanner;

public class WebSearchEngine {
	static Scanner obj = new Scanner(System.in);
	static Cache searchCache = new Cache();

	public static void main(String[] args) {
		InvertedIndex invertedIndex = new InvertedIndex();
		WebSearchEngine wb = new WebSearchEngine();
		// wb.search(invertedIndex);
	}

	public void crawl(InvertedIndex invertedIndex) {
		String urlToCrawl = "https://www.geeksforgeeks.org/data-structures";
		Crawler c = new Crawler();
		c.MAX_CRAWL_LIMIT = 13;
		c.crawl(urlToCrawl, invertedIndex); // Storing the trie object in output file
//		System.out.println(c.actualCrawl);
		invertedIndex.createDictionary(); // store index words in dictionary
		invertedIndex.sortLinkIndex(); // sort index
		invertedIndex.createSerializableFile();
	}

	public ArrayList<LinkIndex> search(InvertedIndex invertedIndex, String searchWord) {
		Scanner sc = new Scanner(System.in);
		// System.out.println("Enter word to search: ");
		// String searchWord = sc.next();
		if (searchWord.equals("EXIT")) {
			System.out.println("exiting search!");
			System.exit(0);
		}
		String[] searchWords = searchWord.split("\\W+");
		searchWord = searchWords[0];
		ArrayList<LinkIndex> outputLinks = searchInCache(invertedIndex, searchWord, false, 0);
		return outputLinks;

//		int countMore = 0;
//		while (true) {
//			System.out.println("Do you want more results? (y/n)");
//			String more = sc.next();
//			if (more.equals("y")) {
//				countMore += 5;
//				searchInCache(invertedIndex, searchWord, true, countMore);
//			} else {
//				break;
//			}
//		}
	}

	// search - pattern matching on cache - Boyer Moore
	public ArrayList<LinkIndex> searchInCache(InvertedIndex invertedIndex, String searchWord, boolean more,
			int countMore) {
		ArrayList<LinkIndex> cachedLinks = searchCache.fetchCache(searchWord); // check cache
		if (!more && (cachedLinks != null && !cachedLinks.isEmpty())) {
			// show results
			searchCache.updateCache(searchWord, cachedLinks);
			for (LinkIndex temp : cachedLinks) {
				System.out.print(searchWord + " : Key  : " + temp.url + "........");
				System.out.print(temp.frequency + "\n");

			}
			return cachedLinks;
		} else {
			ArrayList<LinkIndex> allLinks = searchInTrie(invertedIndex, searchWord, more, countMore);
			return allLinks;
		}
	}

	// if word not present in cache - check dictionary and fetch from inverted index
	public ArrayList<LinkIndex> searchInTrie(InvertedIndex invertedIndex, String searchWord, boolean more,
			int countMore) {
		ArrayList<LinkIndex> tempMap2 = null;
		boolean noMoreResults = false;
		// Trie inputTrie = initializeInvertedIndex(invertedIndex);
		Trie inputTrie = null;
		ArrayList<String> dictionary = null;
		try {
			inputTrie = invertedIndex.readFromSerializableFile();
			if (inputTrie == null) {
				crawl(invertedIndex);
				inputTrie = invertedIndex.readFromSerializableFile();
			}
		} catch (Exception e) {
//			System.out.println("File not found");
			crawl(invertedIndex);
		}
		invertedIndex.trie = inputTrie;
		try {
			dictionary = invertedIndex.getDictionary();
			if (dictionary == null || dictionary.isEmpty()) {
				dictionary = invertedIndex.createDictionary();
			}
		} catch (Exception ex) {
			if (dictionary == null || dictionary.isEmpty()) {
				dictionary = invertedIndex.createDictionary();
			}
		}

		if (inputTrie.search(searchWord, inputTrie.root) == true) {
			ArrayList<LinkIndex> tempMap = inputTrie.find(searchWord, inputTrie.root).wordObject.getindicesHolder();
			tempMap2 = new ArrayList<LinkIndex>();
			if (more) {
				int pageCount = tempMap.size() < 5 ? countMore : 5 + countMore;
				if (pageCount >= 0 && pageCount <= tempMap.size()) {
					for (int i = countMore; i < pageCount; i++) {
						tempMap2.add(tempMap.get(i));
					}
				} else {
					for (int i = countMore; i < tempMap.size(); i++) {
						tempMap2.add(tempMap.get(i));
					}
					noMoreResults = true;
				}
			} else {
				int pageCount = tempMap.size() < 5 ? tempMap.size() : 5;
				for (int i = 0; i < pageCount; i++) {
					tempMap2.add(tempMap.get(i));
				}
			}
			if (tempMap2 != null && !tempMap2.isEmpty()) {
				searchCache.updateCache(searchWord, tempMap2); // update cache | cache sort - LRU

				// show results
				// searchCache.updateCache(searchWord, cachedLinks);
				for (LinkIndex temp : searchCache.fetchCache(searchWord)) {
					System.out.print(searchWord + " : Key  : " + temp.url + "........");
					System.out.print(temp.frequency + "\n");
				}
			}
			if (noMoreResults)
				System.out.println("No more results.");
		}
//		else {
//			checkSpelling(dictionary, searchWord);
//		}
		return (tempMap2 != null && !tempMap2.isEmpty()) ? tempMap2 : null;
	}

	// if not found dictionary - spell check using edit distance
	public ArrayList<String> checkSpelling(ArrayList<String> dictionary, String searchWord) {
		ArrayList<String> finallist = new ArrayList<>();
//		Editdistance editdistance = new Editdistance();
		finallist = Editdistance.searching(dictionary, searchWord);
//		System.out.println("This word is not present in the dictionary, some suggested words are given below ");
//		for (String a : finallist) {
//			System.out.println(a);
//		}
		return finallist;
	}

}

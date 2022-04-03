package websearchengine;

import java.util.ArrayList;
import java.util.Scanner;

public class WebSearchEngine {
	static Scanner obj = new Scanner(System.in);
	static Cache searchCache = new Cache();

	public static void main(String[] args) {
		WebSearchEngine wb = new WebSearchEngine();
		wb.search();
	}

	public void crawl(InvertedIndex invertedIndex) {
		String urlToCrawl = "https://www.geeksforgeeks.org/data-structures";
		Crawler c = new Crawler();
		c.MAX_CRAWL_LIMIT = 100;
		c.crawl(urlToCrawl, invertedIndex); // Storing the trie object in output file
		System.out.println(c.actualCrawl);
		invertedIndex.sortLinkIndex(); // sort index
		invertedIndex.createDictionary(); // store index words in dictionary
		invertedIndex.createSerializableFile();
	}

	public void search() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter word to search: ");
		String searchWord = sc.next();
		if (searchWord.equals("EXIT")) {
			System.out.println("exiting search!");
			System.exit(0);
		}
		String[] searchWords = searchWord.split("\\W+");
		searchInCache(searchWord, false, 0);
		int countMore = 0;
		while (true) {
			System.out.println("Do you want more results? (y/n)");
			String more = sc.next();
			if (more.equals("y")) {
				countMore += 5;
				searchInCache(searchWord, true, countMore);
			} else {
				break;
			}
		}
	}

	// search - pattern matching on cache - Boyer Moore
	public ArrayList<LinkIndex> searchInCache(String searchWord, boolean more, int countMore) {
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
			ArrayList<LinkIndex> allLinks = searchInTrie(searchWord, more, countMore);
			return allLinks;
		}
	}

	// if word not present in cache - check dictionary and fetch from inverted index
	public ArrayList<LinkIndex> searchInTrie(String searchWord, boolean more, int countMore) {
		ArrayList<LinkIndex> tempMap2 = null;
		// Taking input from serializable file
		InvertedIndex invertedIndex = new InvertedIndex();
		Trie inputTrie = null;
		try {
			inputTrie = invertedIndex.readFromSerializableFile();
		} catch (Exception ex) {
			System.out.println("Error in reading trie file. " + ex.toString());
		}
		if (inputTrie == null) {
			crawl(invertedIndex); // web crawl - recursion | regex | visited ignore | html to text - Jsoup
		}
		ArrayList<String> dictionary = invertedIndex.getDictionary();
		if (dictionary == null || dictionary.isEmpty()) {
			dictionary = invertedIndex.createDictionary();
		}
		if (inputTrie.search(searchWord, inputTrie.root) == true) {
			ArrayList<LinkIndex> tempMap = inputTrie.find(searchWord, inputTrie.root).wordObject.getindicesHolder();
			tempMap2 = new ArrayList<LinkIndex>();
			if (more) {
				int pageCount = tempMap.size() < 5 ? 0 : 5 + countMore;
				if (pageCount > 0) {
					for (int i = countMore; i < pageCount; i++) {
						tempMap2.add(tempMap.get(i));
					}
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
		} else {
			checkSpelling(dictionary, searchWord);
		}
		return (tempMap2 != null && !tempMap2.isEmpty()) ? tempMap2 : null;
	}

	// if not found dictionary - spell check using edit distance
	public void checkSpelling(ArrayList<String> dictionary, String searchWord) {
		Editdistance editdistance = new Editdistance();
		Editdistance.searching(dictionary, searchWord);
	}

}

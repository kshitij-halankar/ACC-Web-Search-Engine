package websearchengine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Cache {

	public static final int MAX_CACHE_SIZE = 10;
	public String word = "";
	public HashMap<String, Integer> links = new HashMap<>();

	public static ArrayList<CacheObject> searchCache = new ArrayList<>(MAX_CACHE_SIZE);

//	public boolean searchCache(String searchWord) {
//		for (CacheObject co : searchCache) {
//			if (co.getCacheWord().equalsIgnoreCase(searchWord)) {
//				return true;
//			}
//		}
//		return false;
//	}
	
//	public static void main (String args[]) {
//		ArrayList<String> a = new ArrayList<>();
//		a.add("q");
//		a.add("w");
//		a.add("e");
//		a.add("r");
//		a.add(0,"c");
//		for(String b : a)
//			System.out.println(b+""+a.indexOf(b));
//	}

	public ArrayList<LinkIndex> fetchCache(String searchWord) {
		for (CacheObject cacheObj : searchCache) {
			if (cacheObj.getCacheWord().equalsIgnoreCase(searchWord)) {
				return cacheObj.getCachedLinks();
			}
		}
		return null;
	}

	public int fetchCacheLoc(String searchWord) {
		for (CacheObject cacheObj : searchCache) {
			System.out.println("cacheObj.word: " + cacheObj.word + " | " + searchCache.indexOf(cacheObj));
			if (cacheObj.getCacheWord().equalsIgnoreCase(searchWord)) {
				return searchCache.indexOf(cacheObj);
			}
		}
		return -1;
	}

	public boolean updateCache(String word, ArrayList<LinkIndex> links) {
		try {
//			if (searchCache.isEmpty()) { // place 1st element at top
//				searchCache.add(0, new CacheObject(word, links));
//			} else {
//				int cacheLoc = fetchCacheLoc(word);
//				if (cacheLoc > -1) { // if an existing element is searched again, swap with top most element
//					// already present.
//					if (searchCache.size() > 1) // if its not already top element then swap
//						Collections.swap(searchCache, 0, cacheLoc);
//				} else {
//					// not present.
//					// place 2nd+ element at top and push 1st to next
//					if (searchCache.size() >= MAX_CACHE_SIZE) { // if cache is full then remove bottom most element
//						searchCache.remove(MAX_CACHE_SIZE - 1);
//					}
//					searchCache.add(searchCache.size(), new CacheObject(word, links));
//					for (int i = searchCache.size() - 1; i > 0; i--) {
//						Collections.swap(searchCache, i, i - 1);
//					}
//				}
//			}
			
			if (searchCache.isEmpty()) { // place 1st element at top
				searchCache.add(new CacheObject(word, links));
			} else {
				int cacheLoc = fetchCacheLoc(word);
				if (cacheLoc > -1) {
					searchCache.remove(cacheLoc);
				}
				if (searchCache.size() >= MAX_CACHE_SIZE) { // if cache is full then remove bottom most element
					searchCache.remove(MAX_CACHE_SIZE - 1);
				}
				searchCache.add(0, new CacheObject(word, links));
			}
			
		} catch (Exception ex) {
			System.out.println(ex.toString());
			return false;
		}
		return true;
	}
}

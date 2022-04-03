package websearchengine;

import java.util.ArrayList;

public class CacheObject {

	String word;
	ArrayList<LinkIndex> links;

	CacheObject(String cacheWord, ArrayList<LinkIndex> cachedLinks) {
		word = cacheWord;
		links = cachedLinks;
	}

	public String getCacheWord() {
		return word;
	}

	public ArrayList<LinkIndex> getCachedLinks() {
		return links;
	}

}

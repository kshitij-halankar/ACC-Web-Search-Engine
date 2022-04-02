package websearchengine;

import java.util.ArrayList;

//Java implementation of search and insert operations
//on Trie
class TrieNode implements java.io.Serializable {
	static final int ALPHABET_SIZE = 26;
	TrieNode[] children = new TrieNode[ALPHABET_SIZE];
	IndexObject wordObject;

	// isEndOfWord is true if the node represents
	// end of a word
	boolean isEndOfWord;

	TrieNode() {
		isEndOfWord = false;
		for (int i = 0; i < ALPHABET_SIZE; i++)
			children[i] = null;
	}

}

public class Trie implements java.io.Serializable {

	// Alphabet size (# of symbols)

	// trie node
	TrieNode root;
	ArrayList<String> dictionary;

	// If not present, inserts key into trie
	// If the key is prefix of trie node,
	// just marks leaf node
	void insert(String key, TrieNode root, String document) {
		int level;
		int length = key.length();
		int index;

		TrieNode pCrawl = root;

		for (level = 0; level < length; level++) {
			index = Character.toLowerCase(key.charAt(level)) - 'a';
			if (pCrawl.children[index] == null)
				pCrawl.children[index] = new TrieNode();

			pCrawl = pCrawl.children[index];
		}

		if (pCrawl.isEndOfWord) {
			pCrawl.wordObject.insertIndex(document);
		} else {
			pCrawl.isEndOfWord = true;
			pCrawl.wordObject = new IndexObject();
			pCrawl.wordObject.insertIndex(document);
		}

		// System.out.println(noOfWords);
	}

	// Returns true if key presents in trie, else false
	boolean search(String key, TrieNode root) {
		int level;
		int length = key.length();
		int index;
		TrieNode pCrawl = root;
		key = key.toLowerCase();

		for (level = 0; level < length; level++) {
			index = key.charAt(level) - 'a';

			if (pCrawl.children[index] == null)
				return false;

			pCrawl = pCrawl.children[index];
		}
		// System.out.println(pCrawl.index);
		return (pCrawl.isEndOfWord);
	}

	public TrieNode find(String key, TrieNode root) {
		int level;
		int length = key.length();
		int index;
		TrieNode pCrawl = root;
		key = key.toLowerCase();

		for (level = 0; level < length; level++) {
			index = key.charAt(level) - 'a';

			if (pCrawl.children[index] == null)
				return null;

			pCrawl = pCrawl.children[index];
		}
		// System.out.println(pCrawl.index);
		return pCrawl;
	}

	public void createDictionary(TrieNode root, ArrayList<Character> str, int level) {

		if (root.isEndOfWord) {
			String word = "";
			for (int i = 0; i < level; i++) {
				word = word + str.get(i);
			}
			// System.out.println(word);
			dictionary.add(word);
		}

		int i;
		for (i = 0; i < 26; i++) {
			// if NON NULL child is found
			// add parent key to str and
			// call the createDictionary function recursively
			// for child node
			if (root.children[i] != null) {
				str.add(level, (char) (i + 'a'));
				// System.out.println(str);
				createDictionary(root.children[i], str, level + 1);
			}
		}
	}

	public void setIndexObject(TrieNode root, IndexObject object) {
		root.wordObject = object;
	}

	// Driver
	public static void main(String args[]) {
	}

}
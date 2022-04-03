package websearchengine;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class InvertedIndex {
	Trie trie;

	InvertedIndex() {
		trie = new Trie();
		trie.root = new TrieNode();
		trie.dictionary = new ArrayList<String>();
	}

	public void insertObject(String word, String urlStr) {
		trie.insert(word, trie.root, urlStr);
	}

	public ArrayList<String> createDictionary() {
		ArrayList<Character> str = new ArrayList<Character>();
		trie.createDictionary(trie.root, str, 0);
		return trie.dictionary;
	}

	public ArrayList<String> getDictionary() {
		return trie.dictionary;
	}

	public void createSerializableFile() {
		try {
			FileOutputStream fileOut = new FileOutputStream("InvertedIndex.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(trie);
			out.close();
			fileOut.close();
			// System.out.printf("Serialized data is saved in Output.ser");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Trie readFromSerializableFile() {
		Trie inputTrie = null;
		try {
			FileInputStream fileIn = new FileInputStream("InvertedIndex.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			inputTrie = (Trie) in.readObject();
			in.close();
			fileIn.close();
		} catch (IOException i) {
			System.out.println(i.toString());
			return null;
		} catch (ClassNotFoundException c) {
			System.out.println("Trie class not found");
			c.printStackTrace();
			return null;
		}

		return inputTrie;
	}

	public void sortLinkIndex() {
		for (int i = 0; i < trie.dictionary.size(); i++) {
			IndexObject indexObject = trie.find(trie.dictionary.get(i), trie.root).wordObject;
			// sort as per frequency - merge sort
			Functions fn = new Functions();
			fn.quickSort(indexObject, 0, indexObject.indicesHolder.size() - 1);
			trie.setIndexObject(trie.root, indexObject);
		}
	}

}

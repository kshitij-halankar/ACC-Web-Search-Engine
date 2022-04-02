package websearchengine;

import java.io.IOException;
import java.util.ArrayList;

import static websearchengine.Functions.quickSort;

public class SortTest {



    public static void main(String[] args) throws IOException {
        String text = "This is a test string to see if the inverted index code works or not and I "
                + "have inroduced some place some duplicate words in here to calculate their index";
        System.out.println("===============================================================");
        String[] keys = text.split(" ");
        Trie trie = new Trie();
        trie.root = new TrieNode();
        trie.dictionary = new ArrayList<String>();
        for (int i = 0; i < keys.length; i++) {
            trie.insert(keys[i], trie.root, "WebsiteName");
        }
        System.out.println("===========================================================");
//        for(int i = 0; i < 3; i++) {
//            trie.insert("index", trie.root, "WebsiteName2");
//            trie.insert("a", trie.root, "WebsiteName2");
//        }
        for(int i = 0; i < 32; i++) {
            trie.insert("there", trie.root, "WebsiteName3");
            trie.insert("a", trie.root, "WebsiteName4");
        }


        ArrayList<Character> str = new ArrayList<Character>();
        trie.createDictionary(trie.root, str, 0);
        for (int i = 0; i < trie.dictionary.size(); i++) {
            if (trie.search(trie.dictionary.get(i), trie.root)) {

                IndexObject indexObject = trie.find(trie.dictionary.get(i), trie.root).wordObject;
                quickSort(indexObject,0,indexObject.freq.size()-1);
                for (int j = 0; j < indexObject.freq.size(); j++) {
                    System.out.println(indexObject.getWord() + " : Key  : " + indexObject.documentName.get(j) + "........"+indexObject.freq.get(j));
                }

            }

        }

    }
}

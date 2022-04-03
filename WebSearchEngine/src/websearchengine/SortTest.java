package websearchengine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

//import static websearchengine.Functions.quickSort;

public class SortTest {

//
//
	public static void main(String[] args) throws IOException {
		String urlStr = "https://www.geeksforgeeks.org/data-structures";
		URL url = new URL(urlStr); // create new url to visit
		String line;

		// visit url
//		BufferedReader webReader = new BufferedReader(new InputStreamReader(url.openStream()));
//		Document webDoc = Jsoup.parse(url.openStream(), "UTF-8", urlStr);
		Document webDoc = Jsoup.connect(urlStr).get();
		System.out.println(webDoc.text());

		Elements links = webDoc.select("a");
		for (Element link : links) {
			String abs = link.attr("abs:href");
			System.out.println("abs: "+abs);
			System.out.println();
		}
	}
//        String text = "This is a test string to see if the inverted index code works or not and I "
//                + "have inroduced some place some duplicate words in here to calculate their index";
//        System.out.println("===============================================================");
//        String[] keys = text.split(" ");
//        Trie trie = new Trie();
//        trie.root = new TrieNode();
//        trie.dictionary = new ArrayList<String>();
//        /*
//        for (int i = 0; i < keys.length; i++) {
//            trie.insert(keys[i], trie.root, "WebsiteName");
//        }*/
//        System.out.println("===========================================================");
////        for(int i = 0; i < 3; i++) {
////            trie.insert("index", trie.root, "WebsiteName2");
////            trie.insert("a", trie.root, "WebsiteName2");
////        }
//        for(int i = 0; i < 32; i++) {
//            trie.insert("there", trie.root, "WebsiteName3");
//            trie.insert("a", trie.root, "WebsiteName4");
//        }
//        
//        trie.insert("there", trie.root, "WebsiteName4");
//
//
//        ArrayList<Character> str = new ArrayList<Character>();
//        trie.createDictionary(trie.root, str, 0);
//        System.out.println(trie.dictionary.size());
//        for (int i = 0; i < trie.dictionary.size(); i++) {
//            if (trie.search(trie.dictionary.get(i), trie.root)) {
//
//                IndexObject indexObject = trie.find(trie.dictionary.get(i), trie.root).wordObject;
//                quickSort(indexObject,0,indexObject.indicesHolder.size()-1);
//                for (int j = 0; j < indexObject.indicesHolder.size(); j++) {
//                    System.out.println(trie.dictionary.get(i) + " : Key  : " + indexObject.indicesHolder.get(j).url + "........"+indexObject.indicesHolder.get(j).frequency);
//                }
//
//            }
//
//        }
//
//    }
}

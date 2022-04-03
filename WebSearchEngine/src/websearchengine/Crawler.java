package websearchengine;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Crawler {

	public static int MAX_CRAWL_LIMIT = 10;
	private static HashSet<String> linksToCrawl = new HashSet<>();
	private static List<String> visitedLinks = new ArrayList<>();
//	private static final String urlFinderPattern = "(http|https):\\/\\/([\\w_-]+(?:(?:\\.[\\w_-]+)+))([\\w.,@?^=%&:\\/~+#-]*[\\w@?^=%&\\/~+#-])";
//	private static final Pattern urlPattern = Pattern.compile(urlFinderPattern);
//	private static final String currentPath = System.getProperty("user.dir");
//	private static final String webFile = currentPath + "Temp.html";
	private static final String skipLinks = "(http|https)?:\\/\\/.*(.js|.png|.jpg|.docx|.pptx|.jpeg|.xml|.pdf|.gif)";
//	public static int actualCrawl = 0;
//	public static void main(String[] args) {
//	}

	public void crawl(String urlStr, InvertedIndex invertedIndex) {
		try {
//			actualCrawl++;
			Document webDoc = Jsoup.connect(urlStr).get(); // visit URL
			Elements links = webDoc.select("a"); // retrieve all links from web page
			for (Element link : links) {
				String abs = link.attr("abs:href"); // for each link retrieve absolute URL
				if (!visitedLinks.contains(abs) && linksToCrawl.size() <= MAX_CRAWL_LIMIT) {
					linksToCrawl.add(abs); // ignore if already visited or crawl limit reached
				}
			}

			// apply regex to get words and exclude any special characters
			String fileContents = webDoc.body().text(); // parse and read HTML file body
			String[] words = fileContents.split("\\W+");
			for (String word : words) {
				// logic for trie and inverted index
				try {
					invertedIndex.insertObject(word, urlStr);
				} catch (Exception e) {
					continue;
				}
			}

			// now crawl next page
			crawlNext(linksToCrawl.iterator().next(), invertedIndex);
		} catch (Exception e) {
//			actualCrawl--;
			System.out.println(e.toString());
			linksToCrawl.remove(urlStr);
			crawlNext(linksToCrawl.iterator().next(), invertedIndex);
			// crawl(linksToCrawl.iterator().next());
		}
	}

	public void crawlNext(String nextLink, InvertedIndex invertedIndex) {
		linksToCrawl.remove(nextLink);
		if (nextLink.matches(skipLinks)) {
			crawlNext(linksToCrawl.iterator().next(), invertedIndex);
		} else if (visitedLinks.contains(nextLink)) {
			crawlNext(linksToCrawl.iterator().next(), invertedIndex);
		} else {
			if (visitedLinks.size() <= MAX_CRAWL_LIMIT) {
				visitedLinks.add(nextLink);
				crawl(nextLink, invertedIndex);
			}
		}
	}

}

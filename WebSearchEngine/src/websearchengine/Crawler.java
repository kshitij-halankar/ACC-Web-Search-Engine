package websearchengine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Crawler {

//	public static HashMap<String, String> links = new HashMap<>();
	public static final int MAX_CRAWL_LIMIT = 10;
	private static HashSet<String> linksToCrawl = new HashSet<>();
	private static List<String> visitedLinks = new ArrayList<>();
	private static final String urlFinderPattern = "(http|https):\\/\\/([\\w_-]+(?:(?:\\.[\\w_-]+)+))([\\w.,@?^=%&:\\/~+#-]*[\\w@?^=%&\\/~+#-])";
	private static final Pattern urlPattern = Pattern.compile(urlFinderPattern);
	private static final String currentPath = System.getProperty("user.dir");
	private static final String webFile = currentPath + "Temp.html";
	private static final String skipLinks = "(http|https)?:\\/\\/.*(.js|.png|.jpg|.docx|.pptx|.jpeg|.xml)";

	public static void main(String[] args) {
//		String urlStr = "https://www.tutorialspoint.com";

//		String urlToCrawl = "https://www.geeksforgeeks.org/data-structures";
//		Crawler c = new Crawler();
//		InvertedIndex invertedIndex = new InvertedIndex();
//		c.crawl(urlToCrawl, invertedIndex);
//		System.out.println(linksToCrawl.size());

//		for (String link : links)
//			System.out.println(link);
	}

	public void crawl(String urlStr, InvertedIndex invertedIndex) {
		try {
//			System.out.println("visiting: " + urlStr);
			URL url = new URL(urlStr); // create new url to visit
			String line;
			// check logic
//			if (!linksToCrawl.isEmpty()) {
//				linksToCrawl.remove(linksToCrawl.iterator().next());
//			}

			// visit url
			BufferedReader webReader = new BufferedReader(new InputStreamReader(url.openStream()));
			StringBuffer webPage = new StringBuffer();
//			System.out.println(readr.readLine());

			// read all data from webpage
			while ((line = webReader.readLine()) != null) {
				webPage.append(line); // append to store in file

				// retrieve links
				Matcher urlMatcher = urlPattern.matcher(line);
				while (urlMatcher.find()) {
					String link = urlMatcher.group();
					if (!visitedLinks.contains(link) && linksToCrawl.size() <= MAX_CRAWL_LIMIT) {
						linksToCrawl.add(link);
					}
				}
			}

			// store all data into temporary html file
			if (webPage != null && webPage.length() > 0) {
				String webPageContent = webPage.toString();
//				PrintWriter pWriter = new PrintWriter(webFile);
//				pWriter.write(webPageContent);
//				pWriter.close();

				// read and parse temporary file to retrieve words
				// this should be async?
				// File htmlFile = new File(webFile);
				// if (!htmlFile.isDirectory() && htmlFile.isFile()) {
//				String fileContents = Jsoup.parse(htmlFile, "UTF-8").text();
				Document webDoc = Jsoup.parse(webPageContent, "UTF-8");
				String fileContents = webDoc.body().text();

				// apply regex to get words and exclude any special characters
				String[] words = fileContents.split("\\W+");
				for (String word : words) {
//					System.out.println(word);
					try {
						invertedIndex.insertObject(word, urlStr);
					} catch (Exception e) {
						continue;
					}
				}

				// logic for trie and inverted index

				// }
			}

			// now crawl next page
			crawlNext(linksToCrawl.iterator().next(), invertedIndex);
//			String nextLink = linksToCrawl.iterator().next();
//			if (!visitedLinks.contains(nextLink)) {
//				if (isValidURL(nextLink)) {
//					System.out.println("nextLink " + nextLink);
//					visitedLinks.add(nextLink);
//					crawl(nextLink);
//				} else {
//					visitedLinks.add(nextLink);
//					links.remove(nextLink);
//					nextLink = links.iterator().next();
//					crawl(nextLink);
//				}
//			}
		} catch (Exception e) {
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

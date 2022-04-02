package websearchengine;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;

public class Crawler {

//	public static HashMap<String, String> links = new HashMap<>();
	private static HashSet<String> linksToCrawl = new HashSet<>();
	private static List<String> visitedLinks = new ArrayList<>();
	private static final String urlFinderPattern = "(http|https):\\/\\/([\\w_-]+(?:(?:\\.[\\w_-]+)+))([\\w.,@?^=%&:\\/~+#-]*[\\w@?^=%&\\/~+#-])";
	private static final Pattern urlPattern = Pattern.compile(urlFinderPattern);
	private static final String currentPath = System.getProperty("user.dir");
	private static final String webFile = currentPath + "Temp.html";
	private static final String skipLinks = "(http|https)?:\\/\\/.*(.js|.png|.jpg|.docx|.pptx|.jpeg)";

	public static void main(String[] args) {
		String urlStr = "https://www.tutorialspoint.com";
		crawl(urlStr);
		System.out.println(linksToCrawl.size());
//		for (String link : links)
//			System.out.println(link);
	}

//	public static crawl2() {
//		try {
//			System.out.println("visited: " + urlStr);
//			URL url = new URL(urlStr);
//			String superPattern = "(http|https):\\/\\/([\\w_-]+(?:(?:\\.[\\w_-]+)+))([\\w.,@?^=%&:\\/~+#-]*[\\w@?^=%&\\/~+#-])";
//			Pattern superPat = Pattern.compile(superPattern);
//			String line;
//			// check logic
//			if (!links.isEmpty()) {
//				links.remove(links.iterator().next());
//			}
//			BufferedReader readr = new BufferedReader(new InputStreamReader(url.openStream()));
////			System.out.println(readr.readLine());
//			while ((line = readr.readLine()) != null) {
////				System.out.println(line);
//				Matcher m = superPat.matcher(line);
//				while (m.find()) {
////					System.out.println(m.group());
////					links.put(m.group(),null);
//					String link = m.group();
//					if (!visitedLinks.contains(link)) {
//						links.add(m.group());
//					}
//				}
//			}
//			System.out.println(links.size());
//			String nextLink = links.iterator().next();
//			if (links.size() < 2000 && !visitedLinks.contains(nextLink)) {
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
//		} catch (Exception e) {
//			System.out.println(e.toString());
//			crawl(links.iterator().next());
//		}
//	}

	public static void crawl(String urlStr) {
		try {
			System.out.println("visiting: " + urlStr);
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
					if (!visitedLinks.contains(link) && linksToCrawl.size() <= 2000) {
						linksToCrawl.add(link);
					}
				}
			}

			// store all data into temporary html file
			if (!webPage.isEmpty()) {
				String webPageContent = webPage.toString();
//				PrintWriter pWriter = new PrintWriter(webFile);
//				pWriter.write(webPageContent);
//				pWriter.close();

				// read and parse temporary file to retrieve words
				// this should be async?
				// File htmlFile = new File(webFile);
				// if (!htmlFile.isDirectory() && htmlFile.isFile()) {
//				String fileContents = Jsoup.parse(htmlFile, "UTF-8").text();
				String fileContents = Jsoup.parse(webPageContent).text();

				// logic for trie and inverted index

				// }
			}

			// now crawl next page

			crawlNext(linksToCrawl.iterator().next());
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
			crawlNext(linksToCrawl.iterator().next());
			//crawl(linksToCrawl.iterator().next());
		}
	}

	public static void crawlNext(String nextLink) {
		linksToCrawl.remove(nextLink);
		if (nextLink.matches(skipLinks)) {
			crawlNext(linksToCrawl.iterator().next());
		} else if (visitedLinks.contains(nextLink)) {
			crawlNext(linksToCrawl.iterator().next());
		} else {
			if (visitedLinks.size() <= 2000) {
				visitedLinks.add(nextLink);
				crawl(nextLink);
			}
		}
	}

//	public static boolean isValidURL(String link) {
//		
//		System.out.println("link to validate: " + link);
//		if (link.matches(avoidedLinks)) {
//			System.out.println("inside");
//			return false;
//		}
//		return true;
//	}

}

package websearchengine;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Crawler {

//	public static HashMap<String, String> links = new HashMap<>();
	public static List<String> links = new ArrayList<>();

	public static void main(String[] args) {
		String urlStr = "https://www.tutorialspoint.com/index.htm";
		// TODO Auto-generated method stub
		crawl(urlStr);
		for (String link : links)
			System.out.println(link);
	}

	public static void crawl(String urlStr) {
		try {
			URL url = new URL(urlStr);
			String superPattern = "(http|ftp|https):\\/\\/([\\w_-]+(?:(?:\\.[\\w_-]+)+))([\\w.,@?^=%&:\\/~+#-]*[\\w@?^=%&\\/~+#-])";
			Pattern superPat = Pattern.compile(superPattern);
			String line;
			// check logic
			if (!links.isEmpty()) {
				links.remove(0);
			}
			BufferedReader readr = new BufferedReader(new InputStreamReader(url.openStream()));
//			System.out.println(readr.readLine());
			while ((line = readr.readLine()) != null) {
//				System.out.println(line);
				Matcher m = superPat.matcher(line);
				while (m.find()) {
//					System.out.println(m.group());
//					links.put(m.group(),null);
					links.add(m.group());
				}
			}
			System.out.println(links.size());
			if (links.size() < 2000) {
				crawl(links.get(0));
			}
		} catch (Exception e) {
			System.out.println(e.toString());
			crawl(links.get(0));
		}
	}

}

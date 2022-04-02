package websearchengine;

import java.util.ArrayList;
import java.util.HashMap;

public class IndexObject implements java.io.Serializable {
	
	public ArrayList<LinkIndex> indicesHolder = new ArrayList<LinkIndex>();

	public void insertIndex(String document) {
		boolean urlFound = false;
		
		if (indicesHolder.size() == 0) {
			LinkIndex ind = new LinkIndex();
			ind.url = document;
			ind.frequency = 1;
			indicesHolder.add(ind);
		} else {
			for (int i = 0; i < indicesHolder.size(); i++) {
				if (indicesHolder.get(i).url.equals(document)) {
					LinkIndex ind = new LinkIndex();
					ind.url = document;
					ind.frequency = indicesHolder.get(i).frequency + 1;
					indicesHolder.remove(i);
					indicesHolder.add(ind);
					urlFound = true;
					break;
				}
			}
			if (!urlFound) {
				LinkIndex ind = new LinkIndex();
				ind.url = document;
				ind.frequency = 1;
				indicesHolder.add(ind);
			}
		}

	}

	public ArrayList<LinkIndex> getindicesHolder() {
		return indicesHolder;
	}

}

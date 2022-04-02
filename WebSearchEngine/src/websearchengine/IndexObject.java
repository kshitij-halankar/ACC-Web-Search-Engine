package websearchengine;

import java.util.HashMap;

public class IndexObject implements java.io.Serializable{
    private HashMap<String, Integer> indicesHolder = new HashMap<>();
 

    public void insertIndex(String document) {
        int frequency;
        if(indicesHolder.containsKey(document)) {
            frequency = indicesHolder.get(document);
        }
        else
            frequency = 0;

        frequency = frequency + 1;
        indicesHolder.put(document, frequency);
    }

    public HashMap<String, Integer> getindicesHolder() {
        return indicesHolder;
    }
}

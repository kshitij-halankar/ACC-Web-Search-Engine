package websearchengine;

import java.util.ArrayList;
import java.util.HashMap;

public class IndexObject implements java.io.Serializable{
    private String word;
    private HashMap<String, Integer> indicesHolder = new HashMap<>();
    public ArrayList<String> documentName = new ArrayList<>();
    public ArrayList<Integer> freq = new ArrayList<>();

    IndexObject(String word){
        this.word = word;
    }
    public void insertIndex(String document) {
//        int frequency;
//        if(indicesHolder.containsKey(document)) {
//            frequency = indicesHolder.get(document);
//
//        }
//        else
//            frequency = 0;
//
//        frequency = frequency + 1;
//        indicesHolder.put(document, frequency);

        int frequency1;
        if(documentName.contains(document)) {
            int index = documentName.indexOf(document);
            frequency1 = freq.get(index);
            frequency1++;
            freq.remove(index);
            freq.add(index,frequency1);
        }
        else{
            frequency1 = 1;
            documentName.add(document);
            freq.add(frequency1);
        }


    }

    public String getWord() {
        return word;
    }

    public HashMap<String, Integer> getindicesHolder() {
        return indicesHolder;
    }



}

package project;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;

public class hashMap {

    static HashSet<String> stopwords;
    public static void main(String[] args) throws IOException {
        try{
            //Set file path here
            FileReader fr1 = new FileReader("stopwords.txt");
            try (BufferedReader br1 = new BufferedReader(fr1)) {
                stopwords = new HashSet<>();
                String line1;
                while ((line1 = br1.readLine()) != null) {
                    line1 = line1.toLowerCase();
                    stopwords.add(line1);
                }
            } catch(Exception e) { }
        } catch(Exception e) { }
        //Set file path here
        File folder = new File("/home/deepika/Desktop/research/2013");
        File[] listOfFiles = folder.listFiles();
        for (File listOfFile : listOfFiles) {
            if(listOfFile.isDirectory()) {
                    File[] listOfFiles2 = listOfFile.listFiles();
                    for (File listOfFile2 : listOfFiles2) {
                        if (listOfFile2.isFile()) {
                            //Set file path here
                            buildWordMap("/home/deepika/Desktop/research/2013/"+listOfFile.getName()+"/"+listOfFile2.getName());
                        }
                    }
            }
        }
        //Set file path here
        FileWriter fos = new FileWriter("/home/deepika/Desktop/research/2013.txt"); 
        PrintWriter dos = new PrintWriter(fos);
        list = sortByValueInDecreasingOrder(wordMap);
        System.out.println("List of repeated word from file and their count");
        for (Map.Entry<String, Integer> entry : list) {
            if (entry.getValue() > 1) {
                String format;
                format = String.format("%-30s%s%n", entry.getKey(),entry.getValue());
                dos.print(format);
            }
        }
    }
    
    static List<Entry<String, Integer>> list;
    static Map<String, Integer> wordMap = new HashMap<>();
    
    public static Map<String, Integer> buildWordMap(String fileName) {

        try (FileInputStream fis = new FileInputStream(fileName);
                DataInputStream dis = new DataInputStream(fis);
                BufferedReader br = new BufferedReader(new InputStreamReader(dis))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.toLowerCase();
                StringTokenizer st = new StringTokenizer(line," :><=;\\!/.,    &@#$%^*|~?'\"(){}[]");
                String word;
                String regex = "[0-9]+";
                while(st.hasMoreTokens()) {
                    word = st.nextToken();
                    if (wordMap.containsKey(word)) {
                        wordMap.put(word, (wordMap.get(word) + 1));
                    } else if(!stopwords.contains(word) && !word.matches(regex)){
                        wordMap.put(word, 1);
                    }
                }
            }
        } catch (IOException e){ }
        return wordMap;
    }

    public static List<Entry<String, Integer>> sortByValueInDecreasingOrder(Map<String, Integer> wordMap) {
        Set<Entry<String, Integer>> entries = wordMap.entrySet();
        List<Entry<String, Integer>> list3 = new ArrayList<>(entries);
        Collections.sort(list3, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });
        return list3;
    }    
}
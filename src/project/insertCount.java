/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

public class insertCount {

    static HashSet<String> stopwords;
    static int count=0;
    public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {
        try {
            FileReader fr1 = new FileReader("/home/deepika/Desktop/research/stopwords.txt");
            try (BufferedReader br1 = new BufferedReader(fr1)) {
                stopwords = new HashSet<>();
                String line1;
                while ((line1 = br1.readLine()) != null) {
                    line1 = line1.toLowerCase();
                    stopwords.add(line1);
                }
            } catch(Exception e){ }
        } catch(Exception e) { }
        File folder = new File("/home/deepika/Desktop/research/2015");
        File[] listOfFiles = folder.listFiles();
            for (File listOfFile : listOfFiles) {
                if(listOfFile.isDirectory()) {
                    File[] listOfFiles2 = listOfFile.listFiles();
                    for (File listOfFile2 : listOfFiles2) {
                    if (listOfFile2.isFile()) {
                        count = count+1;
                        buildWordMap("/home/deepika/Desktop/research/2015/"+listOfFile.getName()+"/"+listOfFile2.getName());
                    }
                }
            }
        }
        Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        Connection con=DriverManager.getConnection("jdbc:derby://localhost:1527/deepika","deepika","deepika");   
        list = sortByValueInDecreasingOrder(wordMap);
        System.out.println("List of repeated word from file and their count");
        for (Map.Entry<String, Integer> entry : list) {
            if (entry.getValue() > 1) {
                int k=(entry.getValue()*10000)/count;
                Statement stmt=(Statement)con.createStatement();
                String insert="update deepika.words set y2015="+k+" where word='"+entry.getKey()+"'";
                try{
                    stmt.executeUpdate(insert);
                } catch(Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        con.close();
    }
    static List<Map.Entry<String, Integer>> list;
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

    public static List<Map.Entry<String, Integer>> sortByValueInDecreasingOrder(Map<String, Integer> wordMap) {
        Set<Map.Entry<String, Integer>> entries = wordMap.entrySet();
        List<Map.Entry<String, Integer>> list3 = new ArrayList<>(entries);
        Collections.sort(list3, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });
        return list3;
    }
}
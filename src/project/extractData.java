package project;

import de.l3s.boilerpipe.BoilerpipeProcessingException;
import java.net.URL;
import de.l3s.boilerpipe.extractors.ArticleExtractor;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class extractData {

    public static void main(String[] args) throws IOException {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            Connection con=DriverManager.getConnection("jdbc:derby://localhost:1527/deepika","deepika","deepika");
            Statement stmt=(Statement)con.createStatement();
            try {
                String select="select * from deepika.citywithurl where yearnum=2011 and (monthname='12') and city_name='mumbai'";    
                try {
                    ResultSet st=stmt.executeQuery(select);
                    while(st.next()){
                        String url=st.getString(1);
                        String h= "/"+url.replace("/", "")+".txt";
                        int year=st.getInt(2);
                        String month=st.getString(3);
                        String city=st.getString(4);
                        BufferedWriter output;
                        
                        //Set file path here
                        File dir1 = new File("/home/deepika/Desktop/research/"+year);
                        dir1.mkdir();
                        File dir2 = new File(dir1+"/"+month);
                        dir2.mkdir();
                        try{
                            File dir= new File(dir2+h);
                            dir.createNewFile();
                            String article;
                            try {
                                URL u=new URL(url);
                                article = ArticleExtractor.INSTANCE.getText(u);
                                output = new BufferedWriter(new FileWriter(dir));
                                output.write(city);
                               output.newLine();
                                output.write(article);
                                output.close();
                            } catch (BoilerpipeProcessingException | IOException e ) {
                                System.err.println("error1"+e.getLocalizedMessage());
                            }
                        } catch(Exception e) {
                            System.out.println("error2"+e.getLocalizedMessage());
                        }
                    }
                } catch(Exception e) {
                    System.err.println("error3"+e.getMessage());
                }
            } catch(Exception e) {
                System.err.println("error4"+e.getMessage());
            }
        } catch(ClassNotFoundException | SQLException e) {
            System.err.println("error5"+e.getMessage());
        }   
    }
}
package project;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
public class Project 
{
    public static List<String>extractLinks(String url) throws Exception {
        final ArrayList<String> result = new ArrayList<String>();

        Document doc = Jsoup.connect(url).timeout(0).get();
        try {
            Elements links = doc.select("a[href]");
            for (Element link : links) {
                try {
                    if(link.toString().contains("/archive/year-") && !link.toString().contains("month-13"))
                        result.add(link.attr("abs:href"));
                } catch(Exception e){ }
            }
        } catch(Exception e) {
            System.out.println("Unknown exception");
        }
        return result;
    }

    public static List<String>extractLinks2(String url) throws Exception {
	final ArrayList<String> result = new ArrayList<String>();
	Document doc=null;
	try {
            doc = Jsoup.connect(url).timeout(0).get();
	} catch (IOException e1) {
            String s =e1.getMessage();
            System.out.println(s);
	}
        try {
            Elements links = doc.select("a[href]");
            for (Element link : links) {
               try {
                    if(link.toString().contains("http://timesofindia.indiatimes.com//")  && (link.toString().contains("//city/ahmedabad")
                                                                                            ||link.toString().contains("//city/allahabad")
                                                                                            ||link.toString().contains("//city/aurangabad")
                                                                                            ||link.toString().contains("//city/bengaluru")
                                                                                            ||link.toString().contains("//city/bhopal")
                                                                                            ||link.toString().contains("//city/bhubaneswar")
                                                                                            ||link.toString().contains("//city/chandigarh")
                                                                                            ||link.toString().contains("//city/chennai")
                                                                                            ||link.toString().contains("//city/coimbatore")
                                                                                            ||link.toString().contains("//city/delhi")
                                                                                            ||link.toString().contains("//city/goa")
                                                                                            ||link.toString().contains("//city/gurgaon")
                                                                                            ||link.toString().contains("//city/guwahati")
                                                                                            ||link.toString().contains("//city/hyderabad")
                                                                                            ||link.toString().contains("//city/indore")
                                                                                            ||link.toString().contains("//city/jaipur")
                                                                                            ||link.toString().contains("//city/kanpur")
                                                                                            ||link.toString().contains("//city/kochi")
                                                                                            ||link.toString().contains("//city/kolhapur")
                                                                                            ||link.toString().contains("//city/kolkata")
                                                                                            ||link.toString().contains("//city/kozhikode")
                                                                                            ||link.toString().contains("//city/lucknow")
                                                                                            ||link.toString().contains("//city/ludhiana")
                                                                                            ||link.toString().contains("//city/madurai")
                                                                                            ||link.toString().contains("//city/mangaluru")
                                                                                            ||link.toString().contains("//city/mumbai")
                                                                                            ||link.toString().contains("//city/mysuru")
                                                                                            ||link.toString().contains("//city/nagpur")
                                                                                            ||link.toString().contains("//city/nashik")
                                                                                            ||link.toString().contains("//city/navi-mumbai")
                                                                                            ||link.toString().contains("//city/noida")
                                                                                            ||link.toString().contains("//city/patna")
                                                                                            ||link.toString().contains("//city/pune")
                                                                                            ||link.toString().contains("//city/raipur")
                                                                                            ||link.toString().contains("//city/ranchi")
                                                                                            ||link.toString().contains("//city/surat")
                                                                                            ||link.toString().contains("//city/thane")
                                                                                            ||link.toString().contains("//city/thiruvananthapuram")
                                                                                            ||link.toString().contains("//city/trichy")
                                                                                            ||link.toString().contains("//city/vadodara")
                                                                                            ||link.toString().contains("//city/varanasi")
                                                                                            ||link.toString().contains("//city/visakhapatnam")))				  
                    result.add(link.attr("abs:href"));
                } catch(Exception e) { }
            }
	} catch(Exception e){
		System.out.println("Unknown exception");
	}
        return result;
    }

    public static int num_days(String year,String month) throws Exception {
	int y = Integer.parseInt(year);
	int m = Integer.parseInt(month);
	int days=0;
	 
	switch(m) {
            case 1:days=31; break;
            case 2:if(y%4==0)days=29;else days=28; break;
            case 3:days=31;	 break;
            case 4:days=30;	 break;
            case 5:days=31;	 break;
            case 6:days=30;	 break;
            case 7:days=31;	 break;
            case 8:days=31;	 break;
            case 9:days=30;	 break;
            case 10:days=31;	 break;
            case 11:days=30;	 break;
            case 12:days=31;	 break;
	}
	return days;
    }
 
    public static long startTime(int date,String month,String year) throws Exception {
        Date d1 = new SimpleDateFormat("yyyy-M-dd").parse((String)"1899-12-30");
	Date d2 = new SimpleDateFormat("yyyy-M-dd").parse((String)year+"-"+month+"-"+date);
	 
	long diff = Math.abs(d1.getTime() - d2.getTime());
	long diffDays = diff / (86400000);
	return diffDays;
    }
 
    public static void main(String[] args) throws IOException {  
        try {
            String insert="";
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            Connection con=DriverManager.getConnection("jdbc:derby://localhost:1527/deepika","deepika","deepika");
            Statement stmt=(Statement)con.createStatement();
            String u1 = "http://timesofindia.indiatimes.com/";
            String yearnum = "2001";
            String month = "12";
            for(int i=1;i<=num_days(yearnum,month);i++) {
		String s=yearnum+"/"+month+"/"+i+"/archivelist/year-"+yearnum+",month-"+month+",starttime-"+startTime(i,month,yearnum)+".cms";
                List<String> links2 =extractLinks2(u1+s);
                for(String link3 : links2) {
                    StringTokenizer st3=new StringTokenizer(link3,".");
                    for(int j=0;j<2;j++)
                        st3.nextToken();
                    StringTokenizer st4=new StringTokenizer(st3.nextToken(),"/");
                    for(int k=0;k<3;k++)
                        insert ="insert into deepika.citywithurl values('"+link3+"',"+yearnum+",'"+month+"','"+st4.nextToken()+"')";
                    try {
                        stmt.executeUpdate(insert);
                    }
                    catch(Exception e) { System.out.println(e.getMessage()); }
                }
            }
            con.close();
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        } catch(ClassNotFoundException e) {
            System.out.println(e);
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }   
    }
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Process;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * @author kurniandha
 */
public class Weather {
    private String cityID;
    private String cityName;
    private String finalURL = "http://api.openweathermap.org/data/2.5/forecast?id=" + this.cityID + "&APPID=35ba9f8b299ca192a0bb124302077388";

    public String getFinalURL() {
        return finalURL;
    }

    public void setFinalURL(String finalURL) {
        this.finalURL = finalURL;
    }
    
    public Weather(String name) {
        cityName = name;
    }
    
    public void crawlData () throws IOException {
        URL url = new URL(this.finalURL);
        HttpURLConnection connect = (HttpURLConnection)url.openConnection();
        
        connect.setRequestMethod("GET");
        connect.setRequestProperty("User-Agent", "Mozilla/5.0");
        int response = connect.getResponseCode();
        
        if (response == HttpURLConnection.HTTP_OK) {
            BufferedReader input = new BufferedReader(new InputStreamReader(connect.getInputStream()));
            String inputLine;
            StringBuilder responseStr = new StringBuilder();
            while ((inputLine = input.readLine()) != null) {
                responseStr.append(inputLine);
            }
            input.close();
            
            PrintWriter output = new PrintWriter("Data/data.json");
            output.write(responseStr.toString());
            output.flush();
            output.close();
        } else {
            System.out.println("Error");
        }
    }
}

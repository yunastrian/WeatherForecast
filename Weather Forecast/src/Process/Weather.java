/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Process;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


/**
 *
 * @author kurniandha
 */
public class Weather {
    private String cityID;
    private final String cityName;
    JSONArray citylist;
    JSONParser parser;
    private String finalURL;

    public String getFinalURL() {
        return finalURL;
    }
    
    public Weather(String name) {
        cityName = name;
    }
    
    public void getWeatherData(String cityID) {
        this.finalURL = "http://api.openweathermap.org/data/2.5/forecast?id=" + cityID + "&APPID=35ba9f8b299ca192a0bb124302077388";
        try {
            this.crawlData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public JSONObject parseData() {
        JSONParser parsernow = new JSONParser();
        JSONObject data = new JSONObject();
        
        try {
            data = (JSONObject) parsernow.parse (new FileReader("Data/data.json"));
        } catch (FileNotFoundException e) {
          System.out.println("Cannot find file!");
        } catch (ParseException e) {
          System.out.println("Failed");
        } catch (IOException e) {
          e.printStackTrace();
        }

        return data;
    }
    
    public void preCityFinder() {
        this.parser = new JSONParser();
        
        try {
            Object obj = this.parser.parse(new FileReader("Data/data.json"));
            this.citylist = (JSONArray) obj;
        } catch (FileNotFoundException e) {
            System.out.println("Cannot find file");
        } catch (ParseException e) {
            System.out.println("Fail");
        } catch (IOException e) {
        }
    }
    
    public ArrayList<JSONObject> findCityID () {
        ArrayList<JSONObject> cityResult = new ArrayList<>();
        for (Object obj : this.citylist) {
            JSONObject temp = (JSONObject) obj;
            String tempName = temp.get("name").toString();
            if ((tempName.toLowerCase()).equals(cityName.toLowerCase())) {
                JSONObject result = temp;
                cityResult.add(result);
            }
        }
        return cityResult;
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

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
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    private ArrayList<JSONObject> data;
    
    public String getFinalURL() {
        return finalURL;
    }
    
    public Weather(String name) {
        cityName = name;
        data = new ArrayList<>();
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
            data = (JSONObject) parsernow.parse (new FileReader("result.json"));
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
            Object obj = this.parser.parse(new FileReader("city.list.json"));
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
            
            PrintWriter output = new PrintWriter("result.json");
            output.write(responseStr.toString());
            output.flush();
            output.close();
        } else {
            System.out.println("Error");
        }
    }

    public String getCityName(JSONObject data) {
        JSONObject detail = (JSONObject) data.get("city");
        return detail.get("name").toString();
    }

    public String getDailyWindSpeed(JSONObject data) {
        JSONObject detail = (JSONObject) data.get("wind");
        return detail.get("speed").toString();
    }

    public String getDailyWindDirection(JSONObject data) {
        JSONObject detail = (JSONObject) data.get("wind");
        return detail.get("deg").toString();
    }

    public String getDailyTemp(JSONObject data) {
        JSONObject detail = (JSONObject) data.get("main");
        Double temp = (((Number)detail.get("temp")).doubleValue()) - 273.15;
        DecimalFormat df = new DecimalFormat("#.#");
        return String.valueOf(df.format(temp));
    }

    public String getDailyWeatherMain(JSONObject data) {
        JSONArray weather = (JSONArray) data.get("weather");
        JSONObject detail = (JSONObject) weather.get(0);
        return detail.get("main").toString();
    }

    public String getDailyWeatherDetail(JSONObject data) {
        JSONArray weather = (JSONArray) data.get("weather");
        JSONObject detail = (JSONObject) weather.get(0);
        String input = detail.get("description").toString();
        String output = input.substring(0, 1).toUpperCase() + input.substring(1);
        return output;
    }

    public String getDailyDaySimplified(JSONObject data) {
        String result = "";
        String dateTime = data.get("dt_txt").toString();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = format.parse(dateTime);
            SimpleDateFormat dateFormat = new SimpleDateFormat("E");
            result = dateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public String getDailyDayFull(JSONObject data) {
        String result = "";
        String dateTime = data.get("dt_txt").toString();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = format.parse(dateTime);
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE");
            result = dateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public String getDailyMonth(JSONObject data) {
        String result = "";
        String dateTime = data.get("dt_txt").toString();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = format.parse(dateTime);
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM");
            result = dateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public String getDailyYear(JSONObject data) {
        String result = "";
        String dateTime = data.get("dt_txt").toString();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = format.parse(dateTime);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
            result = dateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public String getDailyDate(JSONObject data) {
        String result = "";
        String dateTime = data.get("dt_txt").toString();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = format.parse(dateTime);
            SimpleDateFormat dateFormat = new SimpleDateFormat("d");
            result = dateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public String getDateTime(JSONObject data) {
        return data.get("dt_txt").toString();
  }
}
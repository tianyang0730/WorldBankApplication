package model.data_retrieval;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;

public class World_Bank_Data_Retriever implements Data_Retriever {

    public static final String API_COUNTRY_ID_CANADA = "can";
    public static final String API_COUNTRY_ID_CHINA = "chn";
    public static final String API_COUNTRY_ID_UNITED_STATES = "usa";
   
    public static final String API_AGRICULTURAL_LAND_DATA_INDICATOR = "AG.LND.AGRI.ZS";
    public static final String API_CO2_EMISSIONS_DATA_INDICATOR = "EN.ATM.CO2E.PC";
    public static final String API_PM25_DATA_INDICATOR = "EN.ATM.PM25.MC.M3";
    public static final String API_GDP_PER_CAPITA_DATA_INDICATOR = "NY.GDP.PCAP.CD";
    public static final String API_FOREST_AREA_DATA_INDICATOR = "AG.LND.FRST.ZS";

    // First key in HashMap result is the most recent year.
    @Override
    public HashMap<Integer, Double> retrieve_country_data(String country_name, String data_indicator, int start_year, int end_year) {
        String country_ID = switch (country_name) {
            case "Canada" -> World_Bank_Data_Retriever.API_COUNTRY_ID_CANADA;
            case "China" -> World_Bank_Data_Retriever.API_COUNTRY_ID_CHINA;
            case "USA" -> World_Bank_Data_Retriever.API_COUNTRY_ID_UNITED_STATES;
            default -> "";
        };

        HashMap<Integer, Double> fetched_data = new HashMap<>(); // Key is year, and value is requested data for that year.
        try {
            URL fetch_URL = new URL(String.format("http://api.worldbank.org/v2/country/%s/indicator/%s?date=%d:%d&format=json",
                    country_ID, data_indicator, start_year, end_year));
            System.out.println(fetch_URL);
            HttpURLConnection cn = (HttpURLConnection) fetch_URL.openConnection();
            cn.setRequestMethod("GET");
            cn.connect();

            if (cn.getResponseCode() == 200) {
                StringBuilder JSON_response = new StringBuilder();
                Scanner scan = new Scanner(fetch_URL.openStream());
                while (scan.hasNext()) {
                    JSON_response.append(scan.nextLine());
                }
                scan.close();

                JsonArray entries = new JsonParser().parse(String.valueOf(JSON_response)).getAsJsonArray().get(1).getAsJsonArray();
                for (int i = 0; i < entries.size(); i++) {
                    JsonObject entry_data = entries.get(i).getAsJsonObject();
                    JsonElement entry_value = entry_data.get("value");
                    System.out.printf("Year: %d -> Value: %f\n", entry_data.get("date").getAsInt(),
                            entry_value.isJsonNull() ? null : entry_value.getAsDouble()); // Debugging
                    fetched_data.put(entry_data.get("date").getAsInt(), entry_value.isJsonNull() ? null : entry_value.getAsDouble());
                }
             //   System.out.println(fetched_data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return fetched_data;
    }

    public static void main(String[] args) {
        //new Country_World_Bank_Data("can", 2000, 2001).retrieve_data("SP.POP.TOTL");
    }

}

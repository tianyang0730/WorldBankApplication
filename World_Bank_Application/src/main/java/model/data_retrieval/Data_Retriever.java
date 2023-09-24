package model.data_retrieval;

import java.util.HashMap;

public interface Data_Retriever {

    // First key in HashMap result is the most recent year.
    HashMap<Integer, Double> retrieve_country_data(String country_name, String data_indicator, int start_year, int end_year);
}

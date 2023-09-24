package model.analysis_types;

import model.data_retrieval.Data_Retriever;
import model.data_retrieval.Data_Retriever_Factory;
import model.data_retrieval.World_Bank_Data_Retriever;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class Analysis_Type_1 implements Analysis_Type {



	@Override
	public double[][] perform(String country_name, int start_year, int end_year) {
        Data_Retriever r = Data_Retriever_Factory.create_retriever(Data_Retriever_Factory.RETRIEVER_TYPE_WORLD_BANK);
        HashMap<Integer, Double> Land_Area_data =  r.retrieve_country_data(country_name, World_Bank_Data_Retriever.API_AGRICULTURAL_LAND_DATA_INDICATOR, start_year, end_year);

        // Checks if all data for each year was retrieved.
        if (Collections.frequency(Land_Area_data.values(), null) != 0) {
            System.out.println("Yes");
            return null;
        }

        double[][] results = new double[1][2];
        double total = 0.0;
        
        for(int i = 0; i < Land_Area_data.size(); i++) {
        	total+= Land_Area_data.get(start_year + i);
        }
        
        double average_for_selected_years = total / Land_Area_data.size();
		double rest_of_agricultural_area = 100.0 - average_for_selected_years;
        
        results[0][0] = average_for_selected_years;
		results[0][1] = rest_of_agricultural_area;

        return results;
	}

}

package model.analysis_types;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

import model.data_retrieval.Data_Retriever;
import model.data_retrieval.Data_Retriever_Factory;
import model.data_retrieval.World_Bank_Data_Retriever;

public class Analysis_Type_3 implements Analysis_Type{
	
	@Override
    public double[][] perform(String country_name, int start_year, int end_year) {

        Data_Retriever r = Data_Retriever_Factory.create_retriever(Data_Retriever_Factory.RETRIEVER_TYPE_WORLD_BANK);
        HashMap<Integer, Double> CO2_emissions_data =  r.retrieve_country_data(country_name, World_Bank_Data_Retriever.API_CO2_EMISSIONS_DATA_INDICATOR, start_year, end_year);
       HashMap<Integer, Double> GDP_data =  r.retrieve_country_data(country_name, World_Bank_Data_Retriever.API_GDP_PER_CAPITA_DATA_INDICATOR, start_year, end_year);
        
        // Checks if all data for each year was retrieved.
        if (Collections.frequency(CO2_emissions_data.values(), null) != 0 || Collections.frequency(GDP_data.values(), null) != 0) {
            System.out.println("Yes");
            return null;
        }
        
    
        double[][] results = new double[CO2_emissions_data.size()][2];
        for(int i = 0; i < GDP_data.size(); i++) {
        	for(int j = 0; j < 2; j++) {
        	results[i][j] = start_year+i;
        	results[i][++j] = CO2_emissions_data.get(start_year + i)/GDP_data.get(start_year + i);
        	}
        }
        System.out.println(Arrays.deepToString(results));

        return results;
    }

}

package model.analysis_types;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

import model.data_retrieval.Data_Retriever;
import model.data_retrieval.Data_Retriever_Factory;
import model.data_retrieval.World_Bank_Data_Retriever;

public class Analysis_Type_2 implements Analysis_Type {

	@Override
	public double[][] perform(String country_name, int start_year, int end_year) {

		Data_Retriever r = Data_Retriever_Factory.create_retriever(Data_Retriever_Factory.RETRIEVER_TYPE_WORLD_BANK);
		HashMap<Integer, Double> PM25_data = r.retrieve_country_data(country_name,
				World_Bank_Data_Retriever.API_PM25_DATA_INDICATOR, start_year - 1, end_year);
		HashMap<Integer, Double> Land_Area_data = r.retrieve_country_data(country_name,
				World_Bank_Data_Retriever.API_FOREST_AREA_DATA_INDICATOR, start_year - 1, end_year);

		// Checks if all data for each year was retrieved.
		if (Collections.frequency(PM25_data.values(), null) != 0
				|| Collections.frequency(Land_Area_data.values(), null) != 0) {
			System.out.println("Yes");
			return null;
		}

		System.out.println(PM25_data);
		System.out.println("-------------------------------------------------------------------------------------------");
		System.out.println(Land_Area_data);
		System.out.println("-------------------------------------------------------------------------------------------");
		double[][] results = new double[PM25_data.size() - 1][4];
		for (int i = 0; i < PM25_data.size() - 1; i++) {
			for (int j = 0; j < 4; j++) {
				double PM25_difference = PM25_data.get(start_year + i) - PM25_data.get(start_year + i - 1);
				double Land_Area_difference = Land_Area_data.get(start_year + i)
						- Land_Area_data.get(start_year + i - 1);

				double annual_percentage_change_of_mean_annual_exposure_of_pm25 = (PM25_difference
						/ PM25_data.get(start_year + i - 1)) * 100;
				double annual_percentage_change_of_forest_area = (Land_Area_difference
						/ Land_Area_data.get(start_year + i - 1)) * 100;

				results[i][j] = start_year + i;
				results[i][++j] = annual_percentage_change_of_mean_annual_exposure_of_pm25;
				results[i][++j] = start_year + i;
				results[i][++j] = annual_percentage_change_of_forest_area;
			}
		}
		System.out.println(Arrays.deepToString(results));
		return results;
	}

}
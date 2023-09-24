package testing;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;
import ui.UI_Main;

public class Test_UC2 {

	@Test
	public void UC2SuccessfulTest1() {
		String expected = "China";

		UI_Main frame = new UI_Main();
		frame.country_dropdown.setSelectedItem("China");

		String selected = (String) frame.country_dropdown.getSelectedItem();

		assertEquals(selected, expected);
		System.out.println("UC2SuccessfulTest1 result: Proceed...");
	}

	@Test
	public void UC2FailTest1() {
		String country = "Brazil";

		UI_Main frame = new UI_Main();
		frame.country_dropdown.setSelectedItem(country);

		String selected = (String) frame.country_dropdown.getSelectedItem();

		assertEquals(selected, null);
		System.out.println("UC2FailTest1 result: Data not available for selected country");
	}

	@Test
	public void UC2SuccessfulTest2() {

		try {

			UI_Main frame = new UI_Main();
			ArrayList<String> validCountries = frame.iterate_valid_countries();

			Boolean isValid = false;

			String getItemAtIndex = (String) frame.country_dropdown.getItemAt(2);

			frame.country_dropdown.setSelectedItem(getItemAtIndex);

			String selected = (String) frame.country_dropdown.getSelectedItem();

			if (validCountries.contains(selected)) {

				isValid = true;
			}

			assertEquals(isValid, true);
			System.out.println("UC2SuccessfulTest2 result: Proceed...");

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	@Test
	public void UC2FailTest2() {

		try {
			UI_Main frame = new UI_Main();
			ArrayList<String> validCountries = frame.iterate_valid_countries();

			Boolean isValid = false;

			String getItemAtIndex = (String) frame.country_dropdown.getItemAt(3);

			frame.country_dropdown.setSelectedItem(getItemAtIndex);

			String selected = (String) frame.country_dropdown.getSelectedItem();

			if (validCountries.contains(selected)) {

				isValid = true;
			}

			assertEquals(isValid, false);
			System.out.println("UC2FailTest2 result: Data not available for selected country");

		} catch (Exception e) {

			e.printStackTrace();
		}

	}
	
	@Test
	public void UC2SuccessfulTest3() {

		try {

			UI_Main frame = new UI_Main();
			ArrayList<String> validCountries = frame.iterate_valid_countries();

			Boolean isValid = false;

			String getItemAtIndex = (String) frame.country_dropdown.getItemAt(4);

			frame.country_dropdown.setSelectedItem(getItemAtIndex);

			String selected = (String) frame.country_dropdown.getSelectedItem();

			if (validCountries.contains(selected)) {

				isValid = true;
			}

			assertEquals(isValid, true);
			System.out.println("UC2SuccessfulTest2 result: Proceed...");

		} catch (Exception e) {

			e.printStackTrace();
		}

	}
}
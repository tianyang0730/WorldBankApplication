package testing;

import static org.junit.Assert.*;

import org.junit.Test;
import ui.UI_Main;

public class Test_UC4 {

	@Test
	public void ValidYearData() {

		try {
			UI_Main frame = new UI_Main();
			Boolean isValid = false;
			String startYear = "2010";
			String endYear = "2012";

			frame.start_year_dropdown.setSelectedItem(startYear);
			frame.end_year_dropdown.setSelectedItem(endYear);

			if (frame.start_year_dropdown.getSelectedItem() == startYear && frame.end_year_dropdown.getSelectedItem() == endYear) {
				isValid = true;
			}

			assertEquals(isValid, true);
			System.out.println("Selected years are valid for data retrival.");

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	@Test
	public void InvalidYearData() {

		try {
			UI_Main frame = new UI_Main();
			Boolean isValid = false;
			String startYear = "2009";
			String endYear = "2014";

			frame.start_year_dropdown.setSelectedItem(startYear);
			frame.end_year_dropdown.setSelectedItem(endYear);

			if (frame.start_year_dropdown.getSelectedItem() == startYear && frame.end_year_dropdown.getSelectedItem() == endYear) {
				isValid = true;
			}

			assertEquals(isValid, false);
			System.out.println("Selected years are unavailable for data retrival.");

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	@Test
	public void CorrectYearRange() {

		try {
			UI_Main frame = new UI_Main();
			Boolean isValid = false;

			int startYear = Integer.parseInt((String) frame.start_year_dropdown.getItemAt(0));
			int endYear = Integer.parseInt((String) frame.end_year_dropdown.getItemAt(1));

			if (startYear < endYear) {
				isValid = true;
			}

			assertEquals(isValid, true);
			System.out.println("Selected start year is before selected end year.");

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	@Test
	public void IncorrectYearRange() {

		try {
			UI_Main frame = new UI_Main();
			Boolean isValid = false;

			int startYear = Integer.parseInt((String) frame.start_year_dropdown.getItemAt(1));
			int endYear = Integer.parseInt((String) frame.end_year_dropdown.getItemAt(0));

			if (startYear < endYear) {
				isValid = true;
			}

			assertEquals(isValid, false);
			System.out.println("Selected start year is after selected end year. Please select a valid year range.");
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	@Test
	public void SameYear() {

		try {
			UI_Main frame = new UI_Main();
			Boolean isValid = false;

			int startYear = Integer.parseInt((String) frame.start_year_dropdown.getItemAt(0));
			int endYear = Integer.parseInt((String) frame.end_year_dropdown.getItemAt(0));

			if (startYear == endYear) {
				isValid = true;
			}

			assertEquals(isValid, true);
			System.out.println("Cannot retrive data for between two identical years.");
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

}
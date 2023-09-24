package ui;

public class CountriesIterator implements Iterator {

	int index = 0;
	//implementation of Iterator design pattern method
	public boolean hasNext() {

		if (index < UI_Main.import_JSON_array().size()) {

			return true;
		}
		return false;
	}
	
	//implementation of Iterator design pattern method
	public Object next() {
		if (this.hasNext()) {

			return UI_Main.import_JSON_array().get(index++);

		}
		return null;
	}

}

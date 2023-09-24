package ui;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.json.simple.parser.ParseException;

public interface Iterator {
	   public boolean hasNext();
	   public Object next();
}

package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner; 

public class Model_Authentication {

	public static final int LOGIN_SUCCESSFUL = 0;
	public static final int LOGIN_UNSUCCESSFUL = 1;

    public int login(String username, String password) {
    	String find = username+"="+password;
    	try {
    	      File myObj = new File("Users.txt");
    	      Scanner myReader = new Scanner(myObj);
    	      while (myReader.hasNextLine()) {
    	        String data = myReader.nextLine();
    	        if(find.equals(data)) {
    	        	return LOGIN_SUCCESSFUL;
    	        }
    	      }
    	      myReader.close();
    	    } catch (FileNotFoundException e) {
    	      System.out.println("Error: Credentials file not found.");
    	      e.printStackTrace();
    	    }
    	return LOGIN_UNSUCCESSFUL;
    }
}

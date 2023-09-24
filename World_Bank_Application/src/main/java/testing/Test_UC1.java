package testing;

import model.Model_Authentication;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Test_UC1 {
	
	@Test
	public void correctUserAndPass() {
		String user1 = "greg", pw1 = "123";
		Model_Authentication login = new Model_Authentication();
		int result = login.login(user1, pw1);
		assertEquals(result, 0);
	}
	
	@Test
	public void IncorrectUser() {
		String user1 = "Jeff", pw1 = "123";
		Model_Authentication login = new Model_Authentication();
		int result = login.login(user1, pw1);
		assertEquals(result, 1);
	}
	
	@Test
	public void BlankUser() {
		String user1 = "", pw1 = "123";
		Model_Authentication login = new Model_Authentication();
		int result = login.login(user1, pw1);
		assertEquals(result, 1);
	}
	
	@Test
	public void IncorrectPass() {
		String user1 = "greg", pw1 = "abc";
		Model_Authentication login = new Model_Authentication();
		int result = login.login(user1, pw1);
		assertEquals(result, 1);
	}
	
	@Test
	public void BlankPassword() {
		String user1 = "greg", pw1 = "";
		Model_Authentication login = new Model_Authentication();
		int result = login.login(user1, pw1);
		assertEquals(result, 1);
	}
	

}
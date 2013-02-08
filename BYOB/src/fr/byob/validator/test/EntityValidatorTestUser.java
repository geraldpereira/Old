package fr.byob.validator.test;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.byob.client.exception.ValidationException;
import fr.byob.validator.EntityValidator;
import fr.byob.validator.exception.InternalValidationException;

public class EntityValidatorTestUser {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testValidateEntity() {

		// TODO Renvoyer des listes d'erreurs en validation
		fr.byob.validator.test.TestUser test = new fr.byob.validator.test.TestUser();
		test.setUserid("ov");
//		test.setPassword("sazaki@gmailcom;");
		ArrayList<String> tests = new ArrayList<String>();
//		tests.add("toto");
		test.setTests(tests);
		try {
			EntityValidator.getInstance().validateEntity(test);
		} catch (ValidationException e) {
			fail(e.getMessage());
		}catch (InternalValidationException e) {
		}
	}
	
	private String getStringFromList (List<String> list){
		StringBuilder sb = new StringBuilder();
		for (String str : list){
			sb.append(str).append("\r");
		}
		return sb.toString();
		
	}

}

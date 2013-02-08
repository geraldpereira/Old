package fr.byob.validator.test;

import static org.junit.Assert.fail;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.byob.client.exception.BYOBException;
import fr.byob.client.exception.ValidationException;
import fr.byob.validator.EntityValidator;
import fr.byob.validator.exception.InternalValidationException;

public class EntityValidatorTestLink {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testValidateEntity() {

		// TODO Renvoyer des listes d'erreurs en validation
		fr.byob.validator.test.TestLink test = new fr.byob.validator.test.TestLink();
		test.setLinkid(1);
		test.setTitle("toto");
		test.setDescription("toto");
		test.setUrl("http://192.168.1.1:8080/Project/webFroms/webForm.aspx");
//		test.setPassword("sazaki@gmailcom;");
//		ArrayList<String> tests = new ArrayList<String>();
//		tests.add("toto");
//		test.setTests(tests);
		try {
			EntityValidator.getInstance().validateEntity(test);
		} catch (ValidationException e) {
			System.out.println(getStringFromList(BYOBException.getStringList(e)));
			fail(e.getMessage());
		}catch (InternalValidationException e) {
			System.out.println(getStringFromList(BYOBException.getStringList(e.validationExceptionFactory(TestInternationalizationUtils.getMessages()))));
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

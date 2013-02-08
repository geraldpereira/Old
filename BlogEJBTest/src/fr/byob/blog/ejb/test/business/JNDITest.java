package fr.byob.blog.ejb.test.business;

import javax.naming.Binding;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.byob.blog.ejb.test.EJBTest;

public class JNDITest extends EJBTest {

	
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}

	@Test
	public final void testJNDI() {
	    try {
            NamingEnumeration<NameClassPair> jndi = context.list("");
            while(jndi.hasMore()){
                System.out.println(jndi.next());
            }
            
            NamingEnumeration<Binding> jndi2 = context.listBindings("");
            while(jndi2.hasMore()){
                System.out.println(jndi2.next());
            }
        } catch (NamingException e) {
            e.printStackTrace();
        }
	}

	
}

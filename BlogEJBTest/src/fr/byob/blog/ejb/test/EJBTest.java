package fr.byob.blog.ejb.test;


import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.junit.After;
import org.junit.Before;

public class EJBTest {

	 protected Context context;
	
	@Before
	public void setUp() throws Exception {
		
		Properties props = new Properties();
//        props.setProperty("java.naming.factory.initial","com.sun.enterprise.naming.SerialInitContextFactory");
//        props.setProperty("java.naming.factory.url.pkgs","com.sun.enterprise.naming");
//        props.setProperty("java.naming.factory.state","com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl");
//        props.setProperty("org.omg.CORBA.ORBInitialHost", "localhost");
//        props.setProperty("org.omg.CORBA.ORBInitialPort", "3700");

		props.setProperty("java.naming.factory.initial","org.jnp.interfaces.NamingContextFactory");
        props.setProperty("java.naming.provider.url","jnp://localhost:1099/");
        props.setProperty("java.naming.factory.url.pkgs","org.jboss.naming:org.jnp.interfaces");
        props.setProperty("org.omg.CORBA.ORBInitialHost", "localhost");
        props.setProperty("org.omg.CORBA.ORBInitialPort", "3700");
        
        props.setProperty("javax.naming.Context.SECURITY_PRINCIPAL", "admin");
        props.setProperty("javax.naming.Context.SECURITY_CREDENTIALS", "R257tJQ+70OhtMQ2eb7AGA==");
        props.setProperty("USER_ID", "admin");
        props.setProperty("PASSWORD", "reAKafgbbaz5/Sp3NFG51Q==");
        
        
//        props.setProperty("javax.naming.Context.SECURITY_AUTHENTICATION", "3700");
        

        

        

        
        
        context = new InitialContext(props);
	}

	@After
	public void tearDown() throws Exception {
	}

}

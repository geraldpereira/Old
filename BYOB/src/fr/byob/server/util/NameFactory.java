package fr.byob.server.util;

import java.util.Properties;

import javax.naming.CompoundName;
import javax.naming.InvalidNameException;
import javax.naming.Name;

public abstract class NameFactory {

	public static Name getName(String jndiPath){
		Properties props = new Properties();
		props.put("jndi.syntax.direction", "left_to_right");
		props.put("jndi.syntax.separator", "/");
		Name name = null;
		try {
			name = new CompoundName(jndiPath, props);
		} catch (InvalidNameException e) {
			e.printStackTrace();
		}
		return name;
	}
}

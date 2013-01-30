/**
 * 
 */
package data;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Test the scoped environment.
 * 
 * @author Tillmann Rendel
 */
public class TestEnvironment {

	@Test
	public final void test() {
		final Environment<String, String> env = new Environment<String, String>();

		env.put("x", "1");
		env.put("y", "2");

		assertEquals("1", env.get("x"));
		assertEquals("2", env.get("y"));

		env.put("x", "3");

		assertEquals("3", env.get("x"));
		assertEquals("2", env.get("y"));

		env.beginScope();

		assertEquals("3", env.get("x"));
		assertEquals("2", env.get("y"));

		env.put("x", "4");

		assertEquals("4", env.get("x"));
		assertEquals("2", env.get("y"));

		env.beginScope();

		assertEquals("4", env.get("x"));
		assertEquals("2", env.get("y"));

		env.put("y", "5");
		env.put("z", "6");

		assertEquals("4", env.get("x"));
		assertEquals("5", env.get("y"));
		assertEquals("6", env.get("z"));

		env.endScope();

		assertEquals("4", env.get("x"));
		assertEquals("2", env.get("y"));
		assertEquals(null, env.get("z"));

		env.put("y", "7");

		assertEquals("4", env.get("x"));
		assertEquals("7", env.get("y"));

		env.endScope();

		assertEquals("3", env.get("x"));
		assertEquals("2", env.get("y"));
	}

}

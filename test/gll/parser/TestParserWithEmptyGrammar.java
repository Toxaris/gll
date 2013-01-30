/**
 * 
 */
package gll.parser;

import gll.grammar.Sort;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

/**
 * Test that the parser correctly handles the empty grammar.
 * 
 * <pre>
 * S  ::=
 * </pre>
 * 
 * <p>
 * This grammar should not accept any words, not even the empty word.
 * </p>
 * 
 * @author Tillmann Rendel
 */
public class TestParserWithEmptyGrammar extends AllTests {
	private Sort S = new Sort("S");

	/**
	 * Create the grammar.
	 */
	@Before
	public void setUp() {
	}

	/**
	 * Test that the empty word {@code ""} is rejected.
	 * 
	 * @throws IOException
	 *             when something goes wrong with the Reader.
	 */
	@Test
	public final void testEmpty() throws IOException {
		assertRejected(S, "");
	}

	/**
	 * Test that the word {@code "hello world"} is rejected.
	 * 
	 * @throws IOException
	 *             when something goes wrong with the Reader.
	 */
	@Test
	public final void testHelloWorld() throws IOException {
		assertRejected(S, "hello world");
	}
}

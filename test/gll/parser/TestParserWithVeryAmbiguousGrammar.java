/**
 * 
 */
package gll.parser;

import gll.grammar.Sort;
import gll.grammar.TerminalSymbol;

import java.io.IOException;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Test that the parser correctly handles a very ambiguous grammar.
 * 
 * <pre>
 * S  ::=  b  |  S S  |  S S S
 * </pre>
 * 
 * <p>
 * This is grammar Γ₂ from Scott and Johnstone (2010, Sec. 5). They remark that
 * on this grammar, "standard GLR parsers are O(n⁴).".
 * </p>
 * 
 * @author Tillmann Rendel
 */
public class TestParserWithVeryAmbiguousGrammar extends AllTests {
	private final Sort S = new Sort("S");

	/**
	 * Create the grammar.
	 */
	@Before
	public void setUp() {
		final TerminalSymbol b = TerminalSymbol.singleton('b');

		S.add(b);
		S.add(S, S);
		S.add(S, S, S);
	}

	/**
	 * Test that the word bⁱ for some i is accepted.
	 * 
	 * @throws IOException
	 *             when something goes wrong with the Reader.
	 * 
	 * @param i
	 */
	public final void testB(int i) throws IOException {
		final StringBuilder builder = new StringBuilder(300);
		while (i-- > 0) {
			builder.append('b');
		}
		assertAccepted(S, builder.toString());
	}

	/**
	 * Test that the word {@code "b"} is accepted.
	 * 
	 * @throws IOException
	 *             when something goes wrong with the Reader.
	 */
	@Test
	public final void testB001() throws IOException {
		testB(1);
	}

	/**
	 * Test that the word {@code "bb"} is accepted.
	 * 
	 * @throws IOException
	 *             when something goes wrong with the Reader.
	 */
	@Test
	public final void testB002() throws IOException {
		testB(2);
	}

	/**
	 * Test that the word {@code "bbb"} is rejected.
	 * 
	 * @throws IOException
	 *             when something goes wrong with the Reader.
	 */
	@Test
	public final void testB003() throws IOException {
		testB(3);
	}

	/**
	 * Test that the word {@code "bbbb"} is accepted.
	 * 
	 * @throws IOException
	 *             when something goes wrong with the Reader.
	 */
	@Test
	public final void testB004() throws IOException {
		testB(4);
	}

	/**
	 * Test that the word {@code "bbbbb"} is accepted.
	 * 
	 * @throws IOException
	 *             when something goes wrong with the Reader.
	 */
	@Test
	public final void testB005() throws IOException {
		testB(5);
	}

	/**
	 * Test that the word b¹⁰ is accepted.
	 * 
	 * @throws IOException
	 *             when something goes wrong with the Reader.
	 */
	@Test
	public final void testB010() throws IOException {
		testB(10);
	}

	/**
	 * Test that the word b²⁵ is accepted.
	 * 
	 * @throws IOException
	 *             when something goes wrong with the Reader.
	 */
	@Test
	@Ignore
	public final void testB025() throws IOException {
		testB(25);
	}

	/**
	 * Test that the word b⁵⁰ is accepted.
	 * 
	 * @throws IOException
	 *             when something goes wrong with the Reader.
	 */
	@Test
	@Ignore
	public final void testB050() throws IOException {
		testB(50);
	}

	/**
	 * Test that the word b⁷⁵ is accepted.
	 * 
	 * @throws IOException
	 *             when something goes wrong with the Reader.
	 */
	@Test
	@Ignore
	public final void testB075() throws IOException {
		testB(75);
	}

	/**
	 * Test that the word b¹⁰⁰ is accepted.
	 * 
	 * @throws IOException
	 *             when something goes wrong with the Reader.
	 */
	@Test
	@Ignore
	public final void testB100() throws IOException {
		testB(100);
	}

	/**
	 * Test that the word b¹²⁵ is accepted.
	 * 
	 * @throws IOException
	 *             when something goes wrong with the Reader.
	 */
	@Test
	@Ignore
	public final void testB125() throws IOException {
		testB(125);
	}

	/**
	 * Test that the word b¹⁵⁰ is accepted.
	 * 
	 * @throws IOException
	 *             when something goes wrong with the Reader.
	 */
	@Test
	@Ignore
	public final void testB150() throws IOException {
		testB(150);
	}

	/**
	 * Test that the word b¹⁷⁵ is accepted.
	 * 
	 * @throws IOException
	 *             when something goes wrong with the Reader.
	 */
	@Test
	@Ignore
	public final void testB175() throws IOException {
		testB(175);
	}

	/**
	 * Test that the word b²⁰⁰ is accepted.
	 * 
	 * @throws IOException
	 *             when something goes wrong with the Reader.
	 */
	@Test
	@Ignore
	public final void testB200() throws IOException {
		testB(200);
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
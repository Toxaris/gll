/**
 * 
 */
package gll.parser;

import gll.grammar.Sort;
import gll.gss.Initial;
import gll.sppf.NonterminalSymbolDerivation;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

/**
 * @author Tillmann Rendel
 */
public class Parser {
	/**
	 * Parse the contents of a reader according to a syntactic sort.
	 * 
	 * @param sort
	 * @param reader
	 * @return
	 * @throws IOException
	 */
	public static NonterminalSymbolDerivation parse(final Sort sort, final Reader reader) throws IOException {
		final Parser parser = new Parser(reader);
		parser.parse(sort);
		return parser.getResult();
	}

	/**
	 * Parse the contents of a string according to a syntactic sort.
	 * 
	 * @param sort
	 * @param reader
	 * @return
	 * @throws IOException
	 */
	public static NonterminalSymbolDerivation parse(final Sort sort, final String string) {
		final Parser parser = new Parser(new StringReader(string));
		try {
			parser.parse(sort);
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
		return parser.getResult();
	}

	/**
	 * The reader to access the token stream.
	 */
	private final Reader reader;

	/**
	 * Parser state.
	 */
	private final ParsingState state = new ParsingState();

	/**
	 * Create Parser.
	 * 
	 * @param sort
	 * @param reader
	 */
	private Parser(final Reader reader) {
		this.reader = reader;
	}

	/**
	 * @return
	 */
	private NonterminalSymbolDerivation getResult() {
		return state.getResult();
	}

	/**
	 * Extract the next token from the token stream.
	 * 
	 * @return
	 * @throws IOException
	 */
	private int nextToken() throws IOException {
		final int high = reader.read();
		if (high < 0) {
			return high;
		} else if (Character.isHighSurrogate((char) high)) {
			final int low = reader.read();
			if (low < 0) {
				throw new IOException("Missing low surrogate at end of input");
			} else if (Character.isLowSurrogate((char) low)) {
				return Character.toCodePoint((char) high, (char) low);
			} else {
				throw new IOException("Invalid low surrogate");
			}
		} else {
			return high;
		}
	}

	/**
	 * The parser.
	 * 
	 * @return
	 * @throws IOException
	 */
	private void parse(final Sort start) throws IOException {
		state.start = start;
		state.active.add(new StartProcess(new Initial(), start));

		int codepoint;
		do {
			codepoint = nextToken();
			state.nextToken(codepoint);

			while (!state.active.isEmpty()) {
				final Process current = state.active.poll();
				current.execute(state, codepoint);
			}
		} while (codepoint >= 0);

		// state.writeGSS("c:/users/rendel/documents/gss.dot");
	}
}

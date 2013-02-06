/**
 * 
 */
package gll.parser;

import static gll.grammar.common.Characters.COLON;
import static gll.grammar.common.Characters.LETTER;
import static gll.grammar.common.Characters.LPAREN;
import static gll.grammar.common.Characters.RPAREN;
import static gll.grammar.common.Characters.SPACE;
import gll.grammar.Sort;

import com.google.caliper.Param;
import com.google.caliper.SimpleBenchmark;

/**
 * Benchmark the parser using various variants of the "balanced smileys"
 * grammar.
 * 
 * @author Tillmann Rendel
 */
public class TimeParserWithBalancedSmileys extends SimpleBenchmark {
	/**
	 * The start nonterminal symbol of the grammar.
	 */
	private final Sort S = new Sort("S");

	/**
	 * The string to be parsed.
	 */
	@Param({ "abc", "def", "xyz" })
	String input;

	/**
	 * Create the grammar.
	 */
	@Override
	public void setUp() {
		final Sort P = new Sort("P");

		S.add();
		S.add(LETTER);
		S.add(SPACE);
		S.add(COLON);
		S.add(COLON, P);
		S.add(LPAREN, S, RPAREN);
		S.add(S, S);

		P.add(LPAREN);
		P.add(RPAREN);
	}

	public void timeParser(final int reps) {
		for (int i = 0; i < reps; i++) {
			Parser.parse(S, input);
		}
	}
}

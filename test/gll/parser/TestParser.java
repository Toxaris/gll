/**
 * 
 */
package gll.parser;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * @author Tillmann Rendel
 */
@RunWith(Suite.class)
@SuiteClasses({ TestParserWithEmptyGrammar.class,
		TestParserWithSingletonGrammar.class,
		TestParserWithRightRecursion.class,
		TestParserWithLeftRecursion.class,
		TestParserWithHiddenLeftRecursion.class,
		TestParserWithWhiteboardExample.class,
		TestParserWithFactoredGrammar.class,
		TestParserWithVeryAmbiguousGrammar.class,
		TestParserWithBalancedSmileys.class})
public class TestParser {
}

/**
 * 
 */
package gll.gss;

import gll.parser.State;
import gll.sppf.SymbolDerivation;

/**
 * A (graph-structured) stack.
 * 
 * @author Tillmann Rendel
 */
public abstract class Stack extends GSSNode<Link> {
	/**
	 * Schedule processes according to this stack frame.
	 * 
	 * @param state
	 *            the parser state
	 * @param result
	 * @param codepoint
	 *            the current codepoint to parse
	 */
	public abstract void schedule(State state, SymbolDerivation<?, ?> result, int codepoint);
}
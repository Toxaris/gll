/**
 * 
 */
package gll.gss;

import gll.parser.State;
import gll.sppf.SymbolDerivation;

import java.util.Collections;
import java.util.Set;

/**
 * The initial stack frame on the graph-structured stack.
 * 
 * @author Tillmann Rendel
 */
public class Initial extends Stack {
	/**
	 * Create the initial stack frame.
	 * 
	 * <p>
	 * Use {@link #create()} to ensure maximal sharing.
	 * </p>
	 */
	public Initial() {
	}

	/**
	 * {@inheritDoc}
	 * 
	 * <p>
	 * This implementation returns an empty set.
	 * </p>
	 * 
	 * @return an empty set.
	 */
	@Override
	public Set<Link> getChildren() {
		return Collections.emptySet();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * <p>
	 * This implementation returns an empty array.
	 * </p>
	 */
	@Override
	public String[] getLabels() {
		return new String[] {};
	}

	/**
	 * Schedule processes according to this stack frame.
	 * 
	 * <p>
	 * This implementation does nothing.
	 * </p>
	 * 
	 * @param state
	 *            the parser state
	 * @param codepoint
	 *            the current codepoint to parse
	 */
	@Override
	public void schedule(final State state, final SymbolDerivation<?, ?> result, final int codepoint) {
	}

}
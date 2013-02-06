/**
 * 
 */
package gll.gss;

import gll.grammar.Slot;
import gll.parser.State;
import gll.sppf.Intermediate;
import gll.sppf.SymbolDerivation;

import java.util.Collections;
import java.util.Set;

/**
 * An directed edge in the graph-structured stack, connecting the stack frame of
 * a process to the stack frame of its caller.
 * 
 * <p>
 * Each edge is annotated with a derivation. This derivation will become the
 * left child of the derivation that is created when the call is completed.
 * </p>
 * 
 * @author Tillmann Rendel
 */
public class Link extends GSSNode<Stack> {
	/**
	 * The stack frame of the caller.
	 */
	private final Stack caller;

	/**
	 * The associated derivation.
	 */
	private final Intermediate<?> derivation;

	/**
	 * Create a link.
	 * 
	 * @param caller
	 *            the stack frame of the caller
	 * @param derivation
	 *            the associated deriation
	 */
	public Link(final Stack caller, final Intermediate<?> derivation) {
		this.caller = caller;
		this.derivation = derivation;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * <p>
	 * This implementation returns the stack of the caller that created this
	 * link.
	 * </p>
	 */
	@Override
	public Set<Stack> getChildren() {
		return Collections.singleton(caller);
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
	 * Return whether this node is used to represent a multi-edge or labeled
	 * edge.
	 * 
	 * <p>
	 * This implementation returns {@code true}.
	 * </p>
	 */
	@Override
	public boolean isEdge() {
		return true;
	}

	/**
	 * Schedule a process according to this link.
	 * 
	 * @param state
	 *            the parser state
	 */
	public void schedule(final State state, final SymbolDerivation<?, ?> result, final Slot slot) {
		final Intermediate<?> y = state.append(slot, derivation, result);
		state.scheduleNow(slot, caller, y);
	}
}

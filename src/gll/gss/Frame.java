/**
 * 
 */
package gll.gss;

import gll.grammar.Slot;
import gll.parser.State;
import gll.sppf.IntermediateDerivation;
import gll.sppf.SymbolDerivation;

import java.util.HashSet;
import java.util.Set;

/**
 * A stack frame on the graph-structured stack.
 * 
 * @author Tillmann Rendel
 */
public class Frame extends Stack {
	/**
	 * The slot just after the call that created this stack frame.
	 * 
	 * <p>
	 * This specifies a return address in the grammar. Parsing resumes at that
	 * address when this stack frame is popped.
	 * </p>
	 */
	private final Slot slot;

	/**
	 * The stacks to use after popping this stack frame.
	 */
	private final Set<Link> stacks = new HashSet<Link>();

	/**
	 * The position in the token stream at which this stack frame was created.
	 * 
	 * <p>
	 * This information is necessary to avoid merging incompatible stack frames.
	 * </p>
	 */
	private final int token;

	/**
	 * Create a stack frame with the given data.
	 * 
	 * <p>
	 * Use {@link #create(Slot, Stack, int)} or
	 * {@link Stack#node(Slot, Stack, int)} to ensure maximal sharing.
	 * </p>
	 * 
	 * @param slot
	 * @param stack
	 * @param token
	 */
	public Frame(final Slot slot, final int token) {
		super();
		this.slot = slot;
		this.token = token;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * <p>
	 * This implementation returns the set of links pointing to this stack
	 * frame.
	 * </p>
	 */
	@Override
	public Set<Link> getChildren() {
		return stacks;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * <p>
	 * This implementation returns the grammar slot just after the call that
	 * created this stack frame and the position of the token that was being
	 * processed when this stack frame was created.
	 * </p>
	 */
	@Override
	public String[] getLabels() {
		return new String[] { slot.toString(), Integer.toString(token) };
	}

	/**
	 * Link an additional parent frame to this frame.
	 * 
	 * @param caller
	 *            the additional parent frame
	 * @param derivation
	 *            the associated derivation
	 */
	public Link link(final Stack that, final IntermediateDerivation<?> derivation) {
		final Link result = new Link(that, derivation);
		stacks.add(result);
		return result;
	}

	/**
	 * Schedule processes according to this stack frame.
	 * 
	 * <p>
	 * This implementation schedules a process for every parent stack frame of
	 * this stack frame.
	 * </p>
	 * 
	 * @param state
	 *            the parser state
	 * @param result
	 *            the result flowing back to the caller.
	 * @param codepoint
	 *            the current codepoint to parse
	 */
	@Override
	public void schedule(final State state, final SymbolDerivation<?, ?> result, final int codepoint) {
		state.markPopped(this, result);

		for (final Link link : stacks) {
			link.schedule(state, result, slot);
		}
	}
}
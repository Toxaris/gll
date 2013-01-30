/**
 * 
 */
package gll.parser;

import gll.grammar.Slot;
import gll.gss.Stack;
import gll.sppf.IntermediateDerivation;

/**
 * A process descriptor during parsing.
 * 
 * @author Tillmann Rendel
 */
public class SlotProcess extends Process {
	/**
	 * Current derivation.
	 */
	private final IntermediateDerivation<?> derivation;

	/**
	 * The grammar slot we have to parse next
	 */
	private final Slot slot;

	/**
	 * Create Descriptor.
	 * 
	 * @param slot
	 *            the grammar slot we have to parse next
	 * @param stack
	 *            our stack
	 */
	public SlotProcess(final Slot slot, final Stack stack, final IntermediateDerivation<?> derivation) {
		super(stack);
		this.slot = slot;
		this.derivation = derivation;
	}

	/**
	 * Execute one step of this process.
	 * 
	 * @param state
	 *            the parser state
	 * @param codepoint
	 *            the codepoint to parse
	 */
	@Override
	public void execute(final State state, final int codepoint) {
		slot.parse(state, stack, derivation, codepoint);
	}
}

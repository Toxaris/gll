/**
 * 
 */
package gll.grammar;

import gll.gss.Stack;
import gll.parser.State;
import gll.sppf.DerivationLabel;
import gll.sppf.IntermediateDerivation;
import gll.sppf.SymbolDerivation;
import gll.sppf.SymbolIntermediateDerivation;

/**
 * A grammar slot.
 * 
 * @author Tillmann Rendel
 */
public abstract class Slot implements DerivationLabel {
	/**
	 * Create Slot.
	 */
	public Slot() {
	}

	public abstract void appendPrefix(Slot slot, StringBuilder prefix);

	public abstract void appendSuffix(Slot slot, StringBuilder prefix, StringBuilder suffix);

	/**
	 * @param state
	 * @param derivation
	 * @param result
	 * @return
	 */
	public abstract SymbolIntermediateDerivation createDerivation(State state, IntermediateDerivation<?> derivation,
			SymbolDerivation<?, ?> result);

	/**
	 * Parse according to this slot.
	 * 
	 * @param state
	 *            the parser state
	 * @param frame
	 *            the stack frame of the current parser process
	 * @param derivation
	 *            the current derivation
	 * @param codepoint
	 *            the current codepoint to parse
	 */
	public abstract void parse(State state, Stack frame, IntermediateDerivation<?> derivation, int codepoint);

	@Override
	public String toString() {
		final StringBuilder prefix = new StringBuilder();
		final StringBuilder suffix = new StringBuilder();
		suffix.append(" ·");
		appendSuffix(this, prefix, suffix);
		prefix.append(suffix);
		return prefix.toString();
	}
}

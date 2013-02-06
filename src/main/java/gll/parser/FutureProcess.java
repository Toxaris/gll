/**
 * 
 */
package gll.parser;

import gll.gss.Stack;
import gll.sppf.TerminalSymbolDerivation;

/**
 * @author Tillmann Rendel
 * 
 */
public class FutureProcess extends Process {
	private final TerminalSymbolDerivation derivation;

	/**
	 * Create future process.
	 * 
	 */
	public FutureProcess(final Stack stack,
			final TerminalSymbolDerivation derivation) {
		super(stack);
		this.derivation = derivation;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute(final State state, final int codepoint) {
		stack.schedule(state, derivation, codepoint);
	}
}

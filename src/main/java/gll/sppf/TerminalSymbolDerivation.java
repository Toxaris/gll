/**
 * 
 */
package gll.sppf;

import java.util.Collections;
import java.util.Set;

/**
 * A derivation in the shared packed parse forest that represents the derivation
 * for a token.
 * 
 * @author Tillmann Rendel
 */
public class TerminalSymbolDerivation extends SymbolDerivation<InputSymbol, SPPFNode<?>> {
	/**
	 * Create TokenDerivation.
	 * 
	 * @param codepoint
	 *            the codepoint of the token associated with the new derivation
	 * @param position
	 *            the position of the token associated with the new derivation
	 */
	public TerminalSymbolDerivation(final InputSymbol label) {
		super(label);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void extractText(final StringBuilder builder) {
		builder.appendCodePoint(getLabel().getCodepoint());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<? extends SPPFNode<?>> getChildren() {
		return Collections.emptySet();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Position getFirst() {
		return getLabel();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fomegastar.syntax.parser.Derivation#getRight()
	 */
	@Override
	public Position getLast() {
		return getLabel();
	}
}

/**
 * 
 */
package gll.sppf;

import java.util.List;

/**
 * An intermediate node in the shared packed parse forest.
 * 
 * <p>
 * Together with binary nodes ({@link Binary}), these nodes are used to encode a
 * linked list of symbol derivations.
 * </p>
 * 
 * @author Tillmann Rendel
 * 
 */
public abstract class Intermediate<Label extends DerivationLabel> extends Derivation<Label, Binary> {

	/**
	 * Create Intermediate.
	 * 
	 * @param label
	 */
	public Intermediate(final Label label) {
		super(label);
	}

	public abstract void getSubderivations(final List<SymbolDerivation<?, ?>> result);
}
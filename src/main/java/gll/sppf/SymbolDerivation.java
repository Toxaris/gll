/**
 * 
 */
package gll.sppf;

/**
 * A derivation in the shared packed parse forest that represents the derivation
 * for a symbol.
 * 
 * @author Tillmann Rendel
 */
public abstract class SymbolDerivation<Label extends DerivationLabel, Child extends SPPFNode<?>> extends
		Derivation<Label, Child> {

	/**
	 * Create symbol derivation.
	 * 
	 * @param label
	 */
	public SymbolDerivation(final Label label) {
		super(label);
	}

	/**
	 * @return
	 */
	public Object extract() {
		throw new Error("Parser problem!");
	}

	/**
	 * @param builder
	 */
	public abstract void extractText(StringBuilder builder);
}

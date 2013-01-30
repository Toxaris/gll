/**
 * 
 */
package gll.sppf;


/**
 * @author Tillmann Rendel
 * 
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

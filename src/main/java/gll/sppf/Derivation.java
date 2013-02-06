/**
 * 
 */
package gll.sppf;

/**
 * A derivation node in the shared packed parse forest.
 * 
 * <p>
 * Derivation nodes are labeled with grammar slots or syntactic sorts and the
 * input range they describe. Derivation nodes are merged if they are labeled
 * identically, that is, if they are labeled with the same grammar slot or
 * syntactic sort and the same input range.
 * </p>
 * 
 * <p>
 * Initially, derivation nodes point to zero or one packed node, but after
 * merging, derivation nodes can point to arbitrarily many packed nodes.
 * </p>
 * 
 * @param <Label>
 *            The type of the label of this derivation node.
 * @param <Child>
 *            The type of the children of this derivation node.
 * 
 * @author Tillmann Rendel
 */
public abstract class Derivation<Label extends DerivationLabel, Child extends SPPFNode<?>> extends SPPFNode<Child> {
	private final Label label;

	public Derivation(final Label label) {
		this.label = label;
	}

	public abstract Position getFirst();

	/**
	 * Return the label.
	 * 
	 * @return the label
	 */
	public Label getLabel() {
		return label;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * <p>
	 * This implementation returns the grammar slot or syntactic sort associated
	 * with this derivation, the position of the first token described by this
	 * derivation, and the position of the last token described by this
	 * derivation.
	 * </p>
	 */
	@Override
	public String[] getLabels() {
		return new String[] { getLabel().toString(), getFirst().getPositionString(), getLast().getPositionString() };
	}

	public abstract Position getLast();

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("(");
		builder.append(getLabel());
		builder.append(", ");
		builder.append(getFirst().getPositionString());
		builder.append(", ");
		builder.append(getLast().getPositionString());
		builder.append(")");

		return builder.toString();
	}
}

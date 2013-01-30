/**
 * 
 */
package gll.sppf;

import graph.GraphNode;
import graph.dot.Color;

/**
 * A node of the shared packed parse forest. This can either be a derivation
 * node or a packed node.
 * 
 * @author Tillmann Rendel
 */
public abstract class SPPFNode<Child extends SPPFNode<?>> extends GraphNode<Child> {
	/**
	 * Return the color this node should be drawn in. The same color is used for
	 * the node, the text of the node, and all edges starting at the node.
	 * 
	 * <p>
	 * This implementation returns {@link Color#blue}.
	 * </p>
	 * 
	 * @return the color this node should be drawn in.
	 */
	@Override
	public Color getColor() {
		return Color.blue;
	}
}

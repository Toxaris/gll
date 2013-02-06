/**
 * 
 */
package gll.sppf;

import graph.GraphNode;
import graph.dot.Color;

/**
 * A node of the shared packed parse forest (SPPF). This can either be a
 * derivation node or a packed node.
 * 
 * <p>
 * The SPPF is a bipartite graph, that is, every edge connects a derivation node
 * and a packed node, but never two nodes of the same kind. We encode this in
 * the type system, using the {@code <Child>} type parameter.
 * </p>
 * 
 * @author Tillmann Rendel
 * 
 * @param <Child>
 *            The type of the SPPF nodes directly linked to from this node.
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

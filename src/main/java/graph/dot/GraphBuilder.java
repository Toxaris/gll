/**
 * 
 */
package graph.dot;

import graph.GraphNode;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Build a graph in dot format (for graphviz).
 * 
 * @author Tillmann Rendel
 */
public class GraphBuilder {
	/**
	 * The nodes.
	 */
	private final Map<Object, Node> nodes = new HashMap<Object, Node>();

	/**
	 * The edges.
	 */
	private final Set<Edge> edges = new HashSet<Edge>();

	/**
	 * The next node identifier.
	 */
	private int nextNodeID = 1;

	/**
	 * Generates a fresh node identifier.
	 * 
	 * @return fresh node identifier.
	 */
	private int generateNodeID() {
		return nextNodeID++;
	}

	/**
	 * Creates a node with the given label.
	 * 
	 * <p>
	 * If a node for that object already exists, the existing node is returned.
	 * </p>
	 * 
	 * @param object
	 *            an object to associate with this node.
	 * 
	 * @param label
	 *            the label of the node.
	 * 
	 * @return a node that is associated with the {@code object}.
	 */
	public Node createNode(Object object, Color color, boolean rounded,
			String... label) {
		Node result = nodes.get(object);
		if (result == null) {
			result = new Node(generateNodeID(), color, rounded, label);
			nodes.put(object, result);
		}
		return result;
	}

	/**
	 * Create an edge with the given label.
	 * 
	 * <p>
	 * If an edge for that object already exists, the existing edge is returned.
	 * </p>
	 * 
	 * @param from
	 *            the source node of the new edge.
	 * @param to
	 *            the target node of the new edge.
	 * @param label
	 *            the label of the new edge.
	 * @return an edge that is associated with the {@code object}.
	 */
	public Edge createEdge(Node from, int port, Node to, Color color,
			String label, boolean constraint) {
		Edge result = new Edge(from, port, to, color, label, constraint);
		edges.add(result);
		return result;
	}

	/**
	 * Print the graph build so far.
	 * 
	 * @param out
	 *            the writer to print to.
	 */
	public void write(PrintWriter out) {
		out.format("digraph {\n");
		out.format("  node [fontname=\"%s\"]\n", "Candara");
		for (Node node : nodes.values()) {
			node.write(out);
		}
		for (Edge edge : edges) {
			edge.write(out);
		}
		out.format("}\n");
		out.flush();
		out.close();
	}

	/**
	 * Check whether a node has been created for some object.
	 * 
	 * @param object
	 *            the object to check.
	 * @return {@code true} if a node has been created for the {@code object},
	 *         {@code false} otherwise.
	 */
	public boolean alreadyVisited(Object object) {
		return nodes.containsKey(object);
	}

	public Node visit(final GraphNode<? extends GraphNode<?>> root) {
		boolean old = alreadyVisited(root);
		Node parent = root.toDot(this);

		Set<? extends GraphNode<?>> nodes = new HashSet<GraphNode<?>>(
				root.getChildren());
		List<? extends GraphNode<?>> indexed = root.getIndexedChildren();
		nodes.removeAll(indexed);

		if (!old) {
			int index = 0;
			for (GraphNode<?> node : indexed) {
				if (node.isEdge() && node.getChildren().size() == 1) {
					node = node.getChildren().iterator().next();
				}

				boolean constraint = !alreadyVisited(node);
				Node child = visit(node);
				createEdge(parent, index, child, parent.getColor(), "",
						constraint);
				parent.setPort(index, root.getPortName(index));
				index++;
			}

			for (GraphNode<?> node : nodes) {
				if (node.isEdge() && node.getChildren().size() == 1) {
					node = node.getChildren().iterator().next();
				}

				boolean constraint = !alreadyVisited(node);
				Node child = visit(node);
				createEdge(parent, -1, child, parent.getColor(), "", constraint);
			}
		}
		return parent;
	}
}

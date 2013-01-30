/**
 * 
 */
package graph.dot;

import java.io.PrintWriter;

/**
 * @author Tillmann Rendel
 * 
 */
public class Edge {
	public final Node source;
	public final int port;
	public final Node target;
	public final Color color;
	private final String label;
	private final boolean constraint;

	/**
	 * Create edge.
	 * 
	 * @param source
	 * @param target
	 */
	public Edge(Node source, int port, Node target, Color color, String label, boolean constraint) {
		this.source = source;
		this.port = port;
		this.target = target;
		this.color = color;
		this.label = label;
		this.constraint = constraint;
	}

	/**
	 * @param out
	 */
	public void write(PrintWriter out) {
		if (port < 0) {
			out.format(
					"  %d:s -> %d [label=<<I>%s </I>>, color=\"%s\", fontcolor=\"%s\", constraint=\"%s\"];\n",
					source.id, target.id, label, color, color, constraint);
		} else {
			out.format(
					"  %d:%d:s -> %d [label=<<I>%s </I>>, color=\"%s\", fontcolor=\"%s\", constraint=\"%s\"];\n",
					source.id, port, target.id, label, color, color, constraint);
		}
	}
}

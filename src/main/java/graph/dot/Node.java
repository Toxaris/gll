/**
 * 
 */
package graph.dot;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Tillmann Rendel
 *
 */
public class Node {
	public final int id;
	public final String[] labels;
	public final Color color;
	public final boolean rounded;
	public final List<String> ports = new ArrayList<String>();
	
	/**
	 * Create node.
	 * @param color
	 */
	public Node(int id, Color color, boolean rounded, String... labels) {
		this.id = id;
		this.labels = labels;
		this.color = color;
		this.rounded = rounded;
	}

	/**
	 * @param out
	 */
	public void write(PrintWriter out) {
		out.format("  %d [", id);
		
		out.format("label=\"{{");
		boolean first = true;
		for (String label : labels) {
			if (first) {
				first = false;
			} else {
				out.format("|");
			}
			out.format("%s", label);
		}
		out.format("}");
		
		if (ports.size() > 0) {
			out.format("|{");
			first = true;
			int id = 0;
			for (String port : ports) {
				if (first) {
					first = false;
				} else {
					out.format("|");
				}
				out.format("<%d>%s", id, port);
				id++;
			}
			out.format("}");
		}
		
		out.format("}\", ");
		
		out.format("color=\"%s\", ", color);
		out.format("fontcolor=\"%s\", ", color);
		out.format("shape=\"%s\"];\n", rounded ? "Mrecord" : "record");
	}

	/**
	 * Return the color.
	 *
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}
	
	public void setPort(int index, String text) {
		for (int i = index - ports.size(); i >= 0; i--) {
			ports.add("");
		}
		ports.set(index, text);
	}
}

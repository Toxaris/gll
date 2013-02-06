/**
 * 
 */
package graph.dot;

/**
 * A color specified by its red, green and blue components.
 * 
 * @author Tillmann Rendel
 */
public class RGB extends Color {
	/**
	 * The red component of the color.
	 * 
	 * <p>
	 * This should be a value between 0 and 255.
	 */
	private final int red;

	/**
	 * The green component of the color.
	 * 
	 * <p>
	 * This should be a value between 0 and 255.
	 */
	private final int green;

	/**
	 * The blue component of the color.
	 * 
	 * <p>
	 * This should be a value between 0 and 255.
	 */
	private final int blue;

	/**
	 * Create a RGB color specification.
	 * 
	 * <p>
	 * The components should be values between 0 and 255.
	 * </p>
	 * 
	 * @param red
	 *            the red component of the color.
	 * @param green
	 *            the green component of the color.
	 * @param blue
	 *            the blue component of the color.
	 */
	private RGB(int red, int green, int blue) {
		this.red = red;
		this.green = green;
		this.blue = blue;
	}

	/**
	 * Return the color in GraphViz format.
	 */
	@Override
	public String toString() {
		return String.format("#%2x%2x%2x", red, green, blue);
	}

	/**
	 * @param red
	 * @param green
	 * @param blue
	 * @return
	 */
	public static RGB create(int red, int green, int blue) {
		return new RGB(red, green, blue);
	}
}
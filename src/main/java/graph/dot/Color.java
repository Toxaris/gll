/**
 * 
 */
package graph.dot;

/**
 * A color.
 * 
 * @author Tillmann Rendel
 */
public abstract class Color {
	public static final Color base03 = Color.rgb(0, 43, 54);
	public static final Color base02 = Color.rgb(7, 54, 66);
	public static final Color base01 = Color.rgb(88, 110, 117);
	public static final Color base00 = Color.rgb(101, 123, 131);
	public static final Color base0 = Color.rgb(131, 148, 150);
	public static final Color base1 = Color.rgb(147, 161, 161);
	public static final Color base2 = Color.rgb(238, 232, 21);
	public static final Color base3 = Color.rgb(10, 253, 246);
	public static final Color yellow = Color.rgb(181, 137, 0);
	public static final Color orange = Color.rgb(203, 75, 22);
	public static final Color red = Color.rgb(220, 50, 47);
	public static final Color magenta = Color.rgb(211, 54, 130);
	public static final Color violet = Color.rgb(108, 113, 196);
	public static final Color blue = Color.rgb(38, 139, 210);
	public static final Color cyan = Color.rgb(42, 161, 152);
	public static final Color green = Color.rgb(133, 153, 0);

	
	public static Color rgb(int red, int green, int blue) {
		return RGB.create(red, green, blue);
	}
}

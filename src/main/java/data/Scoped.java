/**
 * 
 */
package data;

/**
 * Mutable values with support for scoping to undo mutations.
 * 
 * @author Tillmann Rendel
 */
public interface Scoped {
	/**
	 * Begin a scope.
	 * 
	 * <p>
	 * A call to {@code endScope()} resets this object to its state at the
	 * corresponding call to {@link #beginScope()}.
	 * </p>
	 */
	void beginScope();

	/**
	 * End a scope.
	 * 
	 * <p>
	 * A call to {@code endScope()} resets this object to its state at the
	 * corresponding call to {@link #beginScope()}.
	 * </p>
	 */
	void endScope();
}
/**
 * 
 */
package data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

/**
 * A scoped environment.
 * 
 * @author Tillmann Rendel
 */
public class Environment<Key, Value> {
	/**
	 * For each scope, the set of keys that have been bound in that scope.
	 */
	private final Stack<Set<Key>> changed = new Stack<Set<Key>>();

	/**
	 * For each key, a stack of bindings in different scopes.
	 */
	private final Map<Key, Stack<Value>> entries = new HashMap<Key, Stack<Value>>();

	/**
	 * Create an empty environment.
	 */
	/**
	 * Create Environment.
	 * 
	 */
	public Environment() {
		beginScope();
	}

	/**
	 * Begin a scope.
	 * 
	 * <p>
	 * The corresponding call to {@link #endScope()} removes all bindings that
	 * are added after the call to {@code beginScope()}.
	 * </p>
	 */
	public void beginScope() {
		changed.add(new HashSet<Key>());
	}

	/**
	 * End a scope.
	 * 
	 * <p>
	 * The call to {@code endScope()} removes all bindings that were added after
	 * the corresponding call to {@link #beginScope()}.
	 * </p>
	 */
	public void endScope() {
		for (final Key key : changed.pop()) {
			entries.get(key).pop();
		}
	}

	/**
	 * Look up the value of bound to a key in the current scope.
	 * 
	 * @param key
	 *            the key to look up
	 * @return the value the {@code key} is bound to
	 */
	public Value get(final Key key) {
		final Stack<Value> bindings = entries.get(key);
		if (bindings == null || bindings.isEmpty()) {
			return null;
		} else {
			return bindings.peek();
		}
	}

	/**
	 * Bind a key to a value in the current scope;
	 * 
	 * @param key
	 *            the key to bind
	 * @param value
	 *            the value to bind to
	 */
	public void put(final Key key, final Value value) {
		final Set<Key> recentlyChanged = changed.peek();

		if (recentlyChanged.contains(key)) {
			final Stack<Value> bindings = entries.get(key);
			bindings.set(bindings.size() - 1, value);
		} else {
			Stack<Value> bindings;
			if (entries.containsKey(key)) {
				bindings = entries.get(key);
			} else {
				bindings = new Stack<Value>();
				entries.put(key, bindings);
			}
			bindings.push(value);
			recentlyChanged.add(key);
		}
	}
}

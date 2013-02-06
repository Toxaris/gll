/**
 * 
 */
package cache;

/**
 * A base class for memoizing functions with two arguments.
 * 
 * @author Tillmann Rendel
 */
public abstract class Cache2<A, B, C> extends Cache1<A, Function1<B, C>> implements Function2<A, B, C> {
	protected Cache1<B, C> compute(final A key1) {
		return new Cache1<B, C>() {
			protected C compute(B key2) {
				return Cache2.this.compute(key1, key2);
			}
		};
	}

	/**
	 * Compute the memoized function.
	 * 
	 * @param key1 the first argument
	 * @param key2 the second argument
	 * @return the result
	 */
	protected abstract C compute(final A key1, final B key2);

	/**
	 * Apply the memoized function.
	 * 
	 * @param key1 the first argument
	 * @param key2 the second argument
	 * @return the result
	 */
	public C apply(final A key1, final B key2) {
		return apply(key1).apply(key2);
	}
}
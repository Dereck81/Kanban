package pe.edu.utp.dsa.DSA;

public class Tuple<T, U> {
	public T first;
	public U second;

	/**
	 * Constructs a new tuple with the specified values.
	 *
	 * @param x the value for the first element
	 * @param y the value for the second element
	 */
	public Tuple(T x, U y) {
		first = x;
		second = y;
	}
}

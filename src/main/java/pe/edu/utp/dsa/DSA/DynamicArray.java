package pe.edu.utp.dsa.DSA;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class DynamicArray<T> implements Iterable<T> {
	private T[] arr;
	private int size;
	private int capacity;

	public DynamicArray() {
		capacity = 0;
		size = 0;
		arr = (T[]) new Object[capacity];
	}

	public Iterator<T> iterator() {
		return new DynamicArrayIterator();
	}

	private class DynamicArrayIterator implements Iterator<T> {
		private int currentIndex = 0;

		@Override
		public boolean hasNext() {
			return currentIndex < size;
		}

		@Override
		public T next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			return arr[currentIndex++];
		}
	}

	private void grow() {
		assert (size >= capacity);

		int newCapacity = capacity + 4;
		T[] newArr = (T[]) new Object[newCapacity];
		System.arraycopy(arr, 0, newArr, 0, capacity);
		size = capacity;
		capacity = newCapacity;
		arr = newArr;
	}

	public T at(int i) {
		// no exception handling because we are too intelligent for that
		return arr[i];
	}

	public void pushBack(T elem) {
		if (size == capacity)
			grow();
		arr[size++] = elem;
	}

	public T popBack() {
		if (size == 0)
			return null;
		return arr[--size];
	}

	public T peekBack() {
		if (size == 0)
			return null;
		return arr[size - 1];
	}
}

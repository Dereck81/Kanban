package pe.edu.utp.dsa.DSA;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Stack<T> {
    private T[] arr;
    private int capacity;
    private int top;

    /**
     * Constructs a stack with the specified capacity.
     *
     * @param capacity the capacity of the stack
     */
    @SuppressWarnings("unchecked")
    public Stack(int capacity){
        arr = (T[]) new Object[capacity];
        this.capacity = capacity;
        top = -1;
    }

    /**
     * Pushes an element onto the stack.
     *
     * @param element the element to push onto the stack
     */
    public void push(T element){
        if(size() == capacity) return;
        top++;
        arr[top] = element;
    }

    /**
     * Peeks at the top element of the stack without removing it.
     *
     * @return the top element of the stack, or null if the stack is empty
     */
    public T peek(){
        if(isEmpty()) return null;
        return arr[top];
    }

    /**
     * Pops the top element off the stack.
     *
     * @return the top element of the stack, or null if the stack is empty
     */
    public T pop(){
        if (isEmpty()) return null;
        T element = peek();
        arr[top--] = null;
        return element;
    }

    /**
     * Checks if the stack is empty.
     *
     * @return true if the stack is empty, false otherwise
     */
    public boolean isEmpty(){
        return top == -1;
    }

    /**
     * Gets the number of elements in the stack.
     *
     * @return the size of the stack
     */
    public int size(){
        return top+1;
    }

    /**
     * Clears the stack, removing all elements.
     */
    @SuppressWarnings("unchecked")
    public void clear(){
        arr = (T[]) Stream.of(arr)
                .map(i -> null)
                .toArray(Object[]::new);
        top = -1;
    }

    /**
     * Converts the stack to an array.
     *
     * @return an array containing all elements in the stack
     */
    public T[] toArray(){
        return arr;
    }

    /**
     * Converts the stack to a list.
     *
     * @return a list containing all elements in the stack
     */
    public List<T> toList(){
        return Arrays.asList(arr);
    }

}
